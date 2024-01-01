package me.nrules.module.modules.movement;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.TimerHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CCCCCCCCCCCC extends Module {
    public CCCCCCCCCCCC() {
        super(CCCCCCCCCCCC.piska2() + CCCCCCCCCCCC.piska3() + CCCCCCCCCCCC.piska4() + CCCCCCCCCCCC.piska5(), Category.MOVEMENT);
        Main.settingsManager.rSetting(new Setting("Simple", this, false));
        Main.settingsManager.rSetting(new Setting("GroundSpoof", this, false));
        Main.settingsManager.rSetting(new Setting("Jump", this, false));
    }

    TimerHelper timerHelper = new TimerHelper();

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "N");
    }

    public static String piska3() {
        return piska2.replace("GHO", "o");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "Fal");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "l");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (Main.settingsManager.getSettingByName("Simple").getValBoolean()) {
            if (mc.player.fallDistance > 2f)
                mc.player.connection.sendPacket(new CPacketPlayer(true));
        }

        if (Main.settingsManager.getSettingByName("Jump").getValBoolean()) {
            if (mc.player.fallDistance > 2f && timerHelper.hasReached(695)) {
                timerHelper.reset();
                mc.player.jump();
                mc.player.connection.sendPacket(new CPacketPlayer(true));

            }
        }
    }

    @Override
    public boolean onPacketSent(Packet<?> packet) {
        if (packet instanceof CPacketPlayer && !((CPacketPlayer) packet).isOnGround() && mc.player.fallDistance > 3.00 && Main.settingsManager.getSettingByName("GroundSpoof").getValBoolean()) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
        }
        return true;
    }

}