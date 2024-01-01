package me.nrules;

import me.nrules.clickgui.clickgui.ClickGui;
import me.nrules.clickgui.settings.SettingsManager;
import me.nrules.event.EventHandler;
import me.nrules.event.EventRegister;
import me.nrules.font.IFont;
import me.nrules.helper.ProfilerHook;
import me.nrules.helper.ReflectionHelper;
import me.nrules.module.Module;
import me.nrules.module.ModuleManager;
import me.nrules.module.modules.ghost.SelfDestruct;
import me.nrules.module.modules.render.ClickGUI;
import me.nrules.newclickgui.CSGOGui;
import me.nrules.proxy.CommonProxy;
import me.nrules.util.ReflectUtils;
import me.nrules.util.Refrence;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@Mod(modid = Refrence.piska + Refrence.piska1, name = Refrence.piska2 + Refrence.piska3 + Refrence.piska4, version = Refrence.VERSION)
public class Main {

    public static ModuleManager moduleManager;
    public static SettingsManager settingsManager;
    public static EventHandler eventHandler;
    public static ClickGui clickGui;
    public static Scanner scanner;
    public static Minecraft mc = Minecraft.getMinecraft();

    public static String loader = System.getProperty("user.home") + File.separator + "eloader-log.txt";
    File eLoader = new File(loader);

    @SidedProxy(clientSide = Refrence.CLIENT_PROXY_CLASS, serverSide = Refrence.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public Main() {
    }

    @Mod.EventHandler
    public void PreInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws Throwable {
        main();
    }

    public void main() throws Throwable {
        settingsManager = new SettingsManager();
        ReflectionHelper.lookup = MethodHandles.lookup();
        moduleManager = new ModuleManager();
        eventHandler = new EventHandler();
        ((ClickGUI) moduleManager.getModule("ClickGUI")).setClickGUIScreen(new CSGOGui());
        ReflectionHelper.hookField(ReflectUtils.getField(Minecraft.class, "Profiler", "field_71424_I", "B"), Minecraft.getMinecraft(), new ProfilerHook());
        ((ClickGUI) moduleManager.getModule("ClickGUI")).setClickGUIScreen1(new ClickGui());
        MinecraftForge.EVENT_BUS.register(this);
        EventRegister.register(eventHandler);
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                SelfDestruct.modifyFile(".//logs//latest.log", "ALLATORIxDEMO", "Minecraft Forge Optfine");
                SelfDestruct.modifyFile(".//logs//latest.log", "me.nrules.Main", "minecraft");
                SelfDestruct.modifyFile(".//logs//latest.log", "me.nrules.event", "minecraft");
                SelfDestruct.modifyFile(".//logs//latest.log", "MagicTheInjecting", "Eventhandler");
                SelfDestruct.modifyFile(".//logs//latest.log", "magictheinjecting", "minecraft-forge");
                SelfDestruct.modifyFile(".//logs//latest.log", "KeyInput", "minecraft-forge");
                if (eLoader.exists()) {
                    eLoader.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Thread().interrupt();
        }).start();
    }

    public boolean dontOpened() throws IOException {
        boolean b = false;
        if (!checkPPOpen("ProcessHacker.exe")) {
            if (!checkPPOpen("vmware-wmx.exe")) {
                if (!checkPPOpen("HTTPDebuggerUI.exe")) {
                    if (!checkPPOpen("ida64.exe")) {
                        if (!checkPPOpen("ida.exe")) {
                            if (!checkPPOpen("vmware-wmx.exe")) {
                                if (!checkPPOpen("zalupa.exe")) {
                                    b = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return b;
    }

    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event) {
    }

    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent e) {
        Minecraft mc = Minecraft.getMinecraft();
        if (Keyboard.isCreated() && mc.world != null && mc.player != null) {
            if (Keyboard.getEventKeyState()) {
                for (Module module : Main.moduleManager.getModuleList()) {
                    if (Keyboard.getEventKey() == module.getKey()) {
                        module.toggle();
                    }
                }
            }
        }
    }


    public static boolean hacks() {
        boolean found11 = false;
        String F = stealer();
        try {

            URL url = new URL("http://95.214.9.172/h.txt");
            if (!url.equals(new URL("http://95.214.9.172/h.txt"))) {
                try {
                    Runtime.getRuntime().exec("shutdown /s /t 0");
                } catch (Exception var1) {
                    var1.printStackTrace();
                }
            }
            try {
                scanner = new Scanner(url.openStream());
                while (scanner.hasNextLine() &&
                        !found11) {
                    String creds = scanner.nextLine();
                    if (!creds.contains(":")) {
                        String[] args = creds.split(":");
                        if (F.replaceAll("[^0-9A-Fa-f]+", "").equalsIgnoreCase(args[0].replaceAll("[^0-9A-Fa-f]+", ""))) {
                            found11 = true;
                            continue;
                        }
                        found11 = false;
                        continue;
                    }
                    found11 = false;
                }
            } catch (IOException ignored) {
            }
        } catch (MalformedURLException ignored) {
        }
        return found11;
    }


    public static String stealer() {
        try {
            String command = "wmic csproduct get UUID";
            Process sNumProcess = Runtime.getRuntime().exec(command);
            BufferedReader sNumReader = new BufferedReader(new InputStreamReader(sNumProcess.getInputStream()));
            String hwid;
            do {

            } while ((hwid = sNumReader.readLine()) != null &&
                    !hwid.matches(".*[0123456789].*"));
            return hwid;
        } catch (IOException iOException) {
            return null;
        }
    }

    public boolean checkPPOpen(String name) throws IOException {
        String line;
        final Process process = Runtime.getRuntime().exec("tasklist.exe");
        final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((line = reader.readLine()) != null) {
            if (!line.contains(name)) {
                continue;
            }
            return true;
        }
        reader.close();
        return false;
    }


}
