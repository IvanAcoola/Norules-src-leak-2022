package me.nrules.module.modules.movement;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;

public class NoWeb extends Module {
    public NoWeb() {
        super(NoWeb.piska2() + NoWeb.piska3() + NoWeb.piska4() + NoWeb.piska5(), Category.PLAYER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "N");
    }

    public static String piska3() {
        return piska2.replace("GHO", "oW");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "e");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "b");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        Web(false);
    }

    public void Web(boolean web) {
        try {
            Field isInWeb = Entity.class.getDeclaredField("field_70134_J");
            isInWeb.setAccessible(true);
            isInWeb.setBoolean(mc.player, false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
