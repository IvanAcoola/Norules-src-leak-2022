package me.nrules.module.modules.movement;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.movement.Timer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Timer extends Module {
    public Timer() {
        super(Timer.piska2() + Timer.piska3() + Timer.piska4() + Timer.piska5(), Category.MOVEMENT);
        Main.settingsManager.rSetting(new Setting("TimerSpeed", this, 1.02, 0.1, 5, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Ti");
    }

    public static String piska3() {
        return piska2.replace("GHO", "m");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "e");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "r");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        setTickLength((float) (50F / Main.settingsManager.getSettingByName("TimerSpeed").getValDouble()));
    }

    public static void setTickLength(float tickLength) {
        try {
            Field fTimer = mc.getClass().getDeclaredField("field_71428_T");
            fTimer.setAccessible(true);
            Field fTickLength = net.minecraft.util.Timer.class.getDeclaredField("field_194149_e");
            fTickLength.setAccessible(true);
            fTickLength.setFloat(fTimer.get(mc), tickLength);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void onDisable() {
        setTickLength(50F);
        super.onDisable();
    }


}
