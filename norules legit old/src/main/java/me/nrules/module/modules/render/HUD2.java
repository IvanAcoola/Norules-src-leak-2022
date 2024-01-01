package me.nrules.module.modules.render;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.font.IFont;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.Refrence;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import static org.lwjgl.opengl.GL11.*;
import java.util.ArrayList;

public class HUD2 extends Module {
    public HUD2() {
        super(HUD2.piska2() + HUD2.piska3() + HUD2.piska4(), Category.RENDER);
        ArrayList<String> options = new ArrayList();
        options.add("Rainbow");
        options.add("Pulsive");
        Main.settingsManager.rSetting(new Setting("HUD", this, "Pulsive", options));
        Main.settingsManager.rSetting(new Setting("ShowKey", this, true));
        Main.settingsManager.rSetting(new Setting("FPS", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";

    public static String piska2() {
        return piska.replace("GOGA", "H");
    }

    public static String piska3() {
        return piska2.replace("GHO", "U");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "D");
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Pre event) {
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.POTION_ICONS)) {
            event.setCanceled(true);
        }
    }


    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        if (mc.player == null || mc.world == null)
            return;

        if (mc.currentScreen == null && !mc.gameSettings.showDebugInfo) {
            String string3 = Refrence.piska2() + Refrence.piska3() + Refrence.piska4();
            String string78 = Refrence.VERSION;
//            IFont.SANS_SMALL.drawStringWithShadow(string3, 4, 12, -1);
        }
    }
}
