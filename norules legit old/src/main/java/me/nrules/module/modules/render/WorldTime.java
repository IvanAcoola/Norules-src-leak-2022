package me.nrules.module.modules.render;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldTime extends Module {
    public WorldTime() {
        super(WorldTime.piska2() + WorldTime.piska3() + WorldTime.piska4() + WorldTime.piska5(), Category.RENDER);
        Main.settingsManager.rSetting(new Setting("Red", this, 155, 0, 255, false));
        Main.settingsManager.rSetting(new Setting("Blue", this, 252, 0, 255, false));
        Main.settingsManager.rSetting(new Setting("Green", this, 101, 0, 255, false));
        Main.settingsManager.rSetting(new Setting("RGB", this, false));

    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "SkyC");
    }

    public static String piska3() {
        return piska2.replace("GHO", "ol");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "o");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "r");
    }

    @SubscribeEvent
    public void onUpdate(EntityViewRenderEvent.FogColors event) {
        if (mc.player == null || mc.world == null)
            return;

        if (!Main.settingsManager.getSettingByName("RGB").getValBoolean()) {
            event.setRed((float) (Main.settingsManager.getSettingByName("Red").getValDouble() / 255.0F));
            event.setGreen((float) (Main.settingsManager.getSettingByName("Green").getValDouble() / 255.0F));
            event.setBlue((float) (Main.settingsManager.getSettingByName("Blue").getValDouble() / 255.0F));
        }

        if (Main.settingsManager.getSettingByName("RGB").getValBoolean()) {
            event.setRed((long) Main.settingsManager.getSettingByName("Red").getValDouble() * 1000);
            event.setBlue((long) Main.settingsManager.getSettingByName("Blue").getValDouble() * 1000);
            event.setGreen((long) Main.settingsManager.getSettingByName("Green").getValDouble() * 1000);
        }
    }
}
