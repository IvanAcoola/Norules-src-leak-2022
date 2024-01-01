package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;

public class NoPush extends Module {
    public NoPush() {
        super(NoPush.piska2() + NoPush.piska3() + NoPush.piska4() + NoPush.piska5(), Category.PLAYER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "N");
    }

    public static String piska3() {
        return piska2.replace("GHO", "oP");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "u");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "sh");
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.player == null && mc.world == null)
            return;


        mc.player.entityCollisionReduction = 1f;
    }


    @Override
    public void onDisable() {
        mc.player.entityCollisionReduction = 0.1f;
        super.onDisable();
    }
}
