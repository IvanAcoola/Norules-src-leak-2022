package me.nrules.module.modules.render;

import me.nrules.Main;
import me.nrules.clickgui.clickgui.ClickGui;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.newclickgui.CSGOGui;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;


public class ClickGUI extends Module {


    private CSGOGui clickGUIScreen;
    private ClickGui clickGUIScreen1;

    public ClickGUI() {
        super(ClickGUI.piska2() + ClickGUI.piska3() + ClickGUI.piska4() + ClickGUI.piska5(), Category.RENDER);
        this.setKey(Keyboard.KEY_RSHIFT);
        Main.settingsManager.rSetting(new Setting("Particle", this, true));
        ArrayList<String> options = new ArrayList();
        options.add("New");
        options.add("Old");
        Main.settingsManager.rSetting(new Setting(ClickGUI.piska2() + ClickGUI.piska3() + ClickGUI.piska4() + ClickGUI.piska5(), this, "New", options));
    }


    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Cli");
    }

    public static String piska3() {
        return piska2.replace("GHO", "ck");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "GU");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "I");
    }

    public CSGOGui getClickGUIScreen() {
        return this.clickGUIScreen;
    }

    public ClickGui getClickGUIScreen1() {
        return this.clickGUIScreen1;
    }

    public void setClickGUIScreen(CSGOGui clickGui) {
        this.clickGUIScreen = clickGui;
    }
    public void setClickGUIScreen1(ClickGui clickGui) {
        this.clickGUIScreen1 = clickGui;
    }

    Minecraft mc = Minecraft.getMinecraft();

    public void onEnable() {
        String mode = Main.settingsManager.getSettingByName(ClickGUI.piska2() + ClickGUI.piska3() + ClickGUI.piska4() + ClickGUI.piska5()).getValString();
        if (mode.equalsIgnoreCase("New")) {
            mc.displayGuiScreen(getClickGUIScreen());
            toggle();
        } else if (mode.equalsIgnoreCase("Old")) {
            mc.displayGuiScreen(getClickGUIScreen1());
            toggle();
        }
        super.onEnable();
    }
}