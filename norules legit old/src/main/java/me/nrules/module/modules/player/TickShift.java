package me.nrules.module.modules.player;

import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.movement.Timer;
import me.nrules.util.EntityUtils;
import net.minecraft.client.tutorial.MovementStep;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class TickShift extends Module {
    private Setting ticks = new Setting("Ticks", this, 18, 1, 200, true);
    private Setting timer = new Setting("Timer", this, 1.8, 1, 3, false);

    private boolean canTimer = false;
    private int tick = 0;

    public TickShift() {
        super("TickShift", Category.PLAYER);

        setmgr.rSetting(ticks);
        setmgr.rSetting(timer);
    }

    public void onEnable() {
        canTimer = false;
        tick = 0;
    }

    public void onDisable() {
        canTimer = false;
        tick = 0;
        Timer.setTickLength(50);
    }

    public void update() {
        if(mc.player == null && mc.world == null) return;

        if (tick <= 0) tick = 0; canTimer = false; Timer.setTickLength(50);
        if (tick > 0 && EntityUtils.isMoving(mc.player)) {
            tick--;
            Timer.setTickLength((float) (50 / timer.getValDouble()));
        }
        if (!EntityUtils.isMoving(mc.player)) tick++;
        if (tick >= ticks.getValInt()) tick = ticks.getValInt();
    }

       @Override
   public boolean onPacketSent(Packet<?> packet) {
    if(packet instanceof CPacketPlayer) {
        tick--;

        if(tick <= 0) tick = 0;
    }
return true;}
}
