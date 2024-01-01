package me.nrules.module.modules.player;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class VClip extends Module {
    public VClip() {
        super(VClip.piska2() + VClip.piska3() + VClip.piska4() + VClip.piska5(), Category.PLAYER);
        Main.settingsManager.rSetting(new Setting("H", this, 56, 0, 200, true));
        Main.settingsManager.rSetting(new Setting("Y", this, 56, 0, 200, true));
        Main.settingsManager.rSetting(new Setting("-Y", this, 56, 0, 200, true));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "V");
    }

    public static String piska3() {
        return piska2.replace("GHO", "C");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "li");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "p");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        float tp = (float) Main.settingsManager.getSettingByName("Y").getValDouble();
        for (int i = 0; i < 1; i++) {
            mc.player.setEntityBoundingBox(mc.player.getEntityBoundingBox().offset(0, tp, 0));
            Main.moduleManager.getModule(VClip.class).setToggled(false);
        }

        float tp1 = (float) Main.settingsManager.getSettingByName("H").getValDouble();
        for (int i = 0; i < 1; i++) {
            double direction = Utils.getDirection();
            mc.player.setEntityBoundingBox(mc.player.getEntityBoundingBox().offset(Math.sin(direction) * tp1, 0, Math.cos(direction) * tp1));
            Main.moduleManager.getModule(VClip.class).setToggled(false);
        }
        float tp2 = (float) Main.settingsManager.getSettingByName("-Y").getValDouble();
        for (int i = 0; i < 1; i++) {
            mc.player.setEntityBoundingBox(mc.player.getEntityBoundingBox().offset(0, -tp2, 0));
            Main.moduleManager.getModule(VClip.class).setToggled(false);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
