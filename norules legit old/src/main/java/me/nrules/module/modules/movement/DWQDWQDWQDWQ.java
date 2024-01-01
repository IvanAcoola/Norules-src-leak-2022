package me.nrules.module.modules.movement;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.Utils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;

public class DWQDWQDWQDWQ extends Module {
    public DWQDWQDWQDWQ() {
        super(DWQDWQDWQDWQ.piska2() + DWQDWQDWQDWQ.piska3() + DWQDWQDWQDWQ.piska4() + DWQDWQDWQDWQ.piska5(), Category.MOVEMENT);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Hi");
    }

    public static String piska3() {
        return piska2.replace("GHO", "ghJ");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "um");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "p");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null || mc.world == null)
            return;

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            float f = Utils.getDirection();
            mc.player.motionX -= MathHelper.sin(f) * 0.05;
            mc.player.motionY = 0.42f;
            mc.player.motionZ += MathHelper.cos(f) * 0.05;
        }

    }

    @Override
    public boolean onPacketSent(Packet<?> packet) {
        if (packet instanceof CPacketPlayer) {
            try {
                Field ground = CPacketPlayer.class.getDeclaredFields()[5];
                ground.setAccessible(true);
                for (int i = 0; i < 1; i++)
                ground.setBoolean(packet, true);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void onDisable() {
        Timer.setTickLength(50f);
        super.onDisable();
    }
}
