package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;

public class FastLadder extends Module {
    public FastLadder() {
        super(FastLadder.piska2() + FastLadder.piska3() + FastLadder.piska4() + FastLadder.piska5(), Category.PLAYER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Fas");
    }

    public static String piska3() {
        return piska2.replace("GHO", "tLa");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "dd");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "er");
    }


    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent e) {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.player.isOnLadder()) {
            mc.player.jump();
        }
    }

    @Override
    public boolean onPacketSent(Packet<?> packet) {
        if (packet instanceof CPacketPlayer) {
            if (mc.player.isOnLadder() && mc.player != null && mc.world != null) {
                try {
                    Field ground = CPacketPlayer.class.getDeclaredFields()[5];
                    ground.setAccessible(true);
                    ground.setBoolean(packet, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

}

