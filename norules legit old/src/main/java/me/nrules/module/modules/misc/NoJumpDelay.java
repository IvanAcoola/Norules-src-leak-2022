package me.nrules.module.modules.misc;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;

public class NoJumpDelay extends Module {
    public NoJumpDelay() {
        super(NoJumpDelay.piska2() + NoJumpDelay.piska3() + NoJumpDelay.piska4() + NoJumpDelay.piska5(), Category.MISC);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "No");
    }

    public static String piska3() {
        return piska2.replace("GHO", "D");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "ela");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "y");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        noDelayJump();
    }

    public void noDelayJump() {
        try {
            Field noDelayJump = EntityLivingBase.class.getDeclaredField("field_70773_bE");
            Field noStepDelay = PlayerControllerMP.class.getDeclaredField("field_78780_h");

            noStepDelay.setAccessible(true);
            noDelayJump.setAccessible(true);

            noStepDelay.setInt(mc.playerController, 0);
            noDelayJump.setInt(mc.player, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
