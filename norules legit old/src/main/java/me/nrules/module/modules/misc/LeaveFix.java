package me.nrules.module.modules.misc;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.Utils;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LeaveFix extends Module {
    public LeaveFix() {
        super(LeaveFix.piska2() + LeaveFix.piska3() + LeaveFix.piska4() + LeaveFix.piska5(), Category.MISC);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Dea");
    }

    public static String piska3() {
        return piska2.replace("GHO", "thC");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "oor");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ds");
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.player.isDead) {
            Utils.printMessage("\u00a7c" + "You were died: " + (int) mc.player.posX * 100 / 100.0 + " " + (int) mc.player.posY * 100 / 100.0 + " " + (int) mc.player.posZ * 100 / 100.0);
        }
    }
}

