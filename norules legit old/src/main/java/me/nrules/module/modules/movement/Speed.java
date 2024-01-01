package me.nrules.module.modules.movement;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Speed extends Module {
    public Speed() {
        super(Speed.piska2() + Speed.piska3() + Speed.piska4() + Speed.piska5(), Category.MOVEMENT);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "S");
    }

    public static String piska3() {
        return piska2.replace("GHO", "p");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "ee");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "d");
    }

    private int airMoves = 0;

    @SubscribeEvent
    public void onUpdate(InputUpdateEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown()) {
            if (mc.player.onGround) {
                mc.player.jump();
                airMoves = 0;
            } else {
                Timer.setTickLength(50f / 1.08f);
                if (airMoves >= 3) mc.player.jumpMovementFactor = 0.022f;
                if (airMoves >= 4 && (airMoves % 2) == 0.0) {
                    mc.player.motionY = -0.32f - 0.009 * Math.random();
                    mc.player.jumpMovementFactor = 0.0238f;
                }
                airMoves++;
            }
        }
    }

    @Override
    public void onDisable() {
        Timer.setTickLength(50f);
        super.onDisable();
    }
}