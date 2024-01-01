package me.nrules.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.io.*;

public class LoadConfig extends Module {

    public LoadConfig() {
        super(LoadConfig.piska2() + LoadConfig.piska3() + LoadConfig.piska4() + LoadConfig.piska5(), Category.CONFIG);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Loa");
    }

    public static String piska3() {
        return piska2.replace("GHO", "d");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "Conf");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ig");
    }

    @Override
    public void onEnable() {
        loadKeybinds();
        loadHacks();
        Utils.printMessage(ChatFormatting.DARK_GRAY + "[+]" + ChatFormatting.GRAY + " Настройки успешно загружены!");
        setToggled(false);
    }

    public void loadKeybinds() {
        try {
            File file = new File(".//crash-reports//crash-2021-06-06_19.57.49-client.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.toLowerCase().trim();
                String[] s = curLine.split(":");
                String hack = s[0];
                int id = Keyboard.getKeyIndex(s[1].toUpperCase());
                for (Module mod : Main.moduleManager.getModuleList()) {
                    if (hack.equalsIgnoreCase("key-" + mod.getName().toLowerCase().replace(" ", ""))) {
                        mod.setKey(id);
                    }
                }
            }
            br.close();
        } catch (Exception err) {

        }
    }

    public void loadHacks() {
        try {
            File file = new File(".//crash-reports//crash-2021-06-06_19.55.33-client.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.toLowerCase().trim();
                String name = curLine.split(":")[0];
                boolean isOn = Boolean.parseBoolean(curLine.split(":")[1]);
                for (Module mod : Main.moduleManager.getModuleList()) {
                    if (mod.getName().toLowerCase().replace(" ", "").equals(name)) {
                        mod.setToggled(isOn);
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}