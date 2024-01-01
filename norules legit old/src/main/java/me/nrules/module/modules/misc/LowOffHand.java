package me.nrules.module.modules.misc;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;

public class LowOffHand extends Module {
    public LowOffHand() {
        super(LowOffHand.piska2() + LowOffHand.piska3() + LowOffHand.piska4() + LowOffHand.piska5(), Category.MISC);
        Main.settingsManager.rSetting(new Setting("Size", this, 0.5, 0.1, 1, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "LowO");
    }

    public static String piska3() {
        return piska2.replace("GHO", "ff");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "Han");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "d");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        onSizeItem((float) Main.settingsManager.getSettingByName("Size").getValDouble());
    }


    public void onSizeItem(float size) {
        try {
            Field OffHand = ItemRenderer.class.getDeclaredField("field_187471_h");
            OffHand.setAccessible(true);
            OffHand.setFloat(mc.getItemRenderer(), size);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
