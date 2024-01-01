package me.nrules.module.modules.movement;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.RandomUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;

public class NoSlow extends Module {
    public NoSlow() {
        super(NoSlow.piska2() + NoSlow.piska3() + NoSlow.piska4() + NoSlow.piska5(), Category.MOVEMENT);
    }

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
        return piska3.replace("UIOL", "Sl");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ow");
    }

    @SubscribeEvent
    public void onUpdate(InputUpdateEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (!mc.player.isSneaking() && mc.player.isHandActive()) {
            mc.player.movementInput.moveStrafe /= 0.2F;
            mc.player.movementInput.moveForward /= 0.2F;
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(new BlockPos(mc.player.posX, mc.player.posY - 1e02f, mc.player.posZ), mc.player.getHorizontalFacing(), mc.player.getActiveHand(), 0f, 1e02f, 0f));
        }
        if (mc.player.fallDistance > 0.7f + RandomUtils.nextFloat(0.05f, 0.1f) && mc.player.isHandActive()) {
            mc.player.motionX *= 0.9f;
            mc.player.motionZ *= 0.9f;
        }
    }

    public static void pressedFalse() {
        try {
            Field pressed = KeyBinding.class.getDeclaredFields()[8];
            pressed.setAccessible(true);
            pressed.setBoolean(mc.gameSettings.keyBindSneak, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pressedTrue() {
        try {
            Field pressed = KeyBinding.class.getDeclaredFields()[8];
            pressed.setAccessible(true);
            pressed.setBoolean(mc.gameSettings.keyBindSneak, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}