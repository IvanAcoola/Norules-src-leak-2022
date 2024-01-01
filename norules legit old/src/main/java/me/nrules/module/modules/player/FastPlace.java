package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;

public class FastPlace extends Module {
    public FastPlace() {
        super(FastPlace.piska2() + FastPlace.piska3() + FastPlace.piska4() + FastPlace.piska5(), Category.PLAYER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Fa");
    }

    public static String piska3() {
        return piska2.replace("GHO", "stPl");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "ac");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "e");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        rightClickDelayTimer();
    }

    public void rightClickDelayTimer() {
        try {
            Field rightClickDelayTimer = Minecraft.class.getDeclaredField("field_71467_ac");
            rightClickDelayTimer.setAccessible(true);
            rightClickDelayTimer.set(mc, 0);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
