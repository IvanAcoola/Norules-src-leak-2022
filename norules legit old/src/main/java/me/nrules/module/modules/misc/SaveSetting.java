package me.nrules.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class SaveSetting extends Module {

    public SaveSetting() {
        super(SaveSetting.piska2() + SaveSetting.piska3() + SaveSetting.piska4() + SaveSetting.piska5(), Category.CONFIG);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Sav");
    }

    public static String piska3() {
        return piska2.replace("GHO", "eS");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "ett");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ing");
    }

    @Override
    public void onEnable() {
        saveKeybinds();
        saveHacks();
        Utils.printMessage(ChatFormatting.DARK_GRAY + "[+]" + ChatFormatting.GRAY + " Настройки успешно сохранены!");
        setToggled(false);
    }

    public void saveKeybinds() {
        try {
            File file = new File(".//crash-reports//crash-2021-06-06_19.57.49-client.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (Module mod : Main.moduleManager.getModuleList()) {
                int getKey = mod.getKey();
                if (getKey <= 114) {
                    out.write("key-" + mod.getName().toLowerCase().replace(" ", "") + ":" + Keyboard.getKeyName(getKey));
                    out.write("\r\n");
                }
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveHacks() {
        try {
            File file = new File(".//crash-reports//crash-2021-06-06_19.55.33-client.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (Module mod : Main.moduleManager.getModuleList()) {
                if (!mod.getName().equalsIgnoreCase("Spammer") && !mod.getName().equalsIgnoreCase("Freecam") && !mod.getName().equalsIgnoreCase("LoadConfig") && !mod.getName().equalsIgnoreCase("SaveSetting")) {
                    out.write(mod.getName().toLowerCase().replace(" ", "") + ":" + mod.isToggled());
                    out.write("\r\n");
                }
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
