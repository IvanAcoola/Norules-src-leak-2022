package me.nrules.module;

import me.nrules.Main;
import me.nrules.module.modules.combat.*;
import me.nrules.module.modules.fun.*;
import me.nrules.module.modules.ghost.*;
import me.nrules.module.modules.misc.*;
import me.nrules.module.modules.movement.*;
import me.nrules.module.modules.movement.Timer;
import me.nrules.module.modules.player.*;
import me.nrules.module.modules.render.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.io.*;
import java.util.*;

public class ModuleManager {

    public static Minecraft mc = Minecraft.getMinecraft();
    public static FontRenderer fr = mc.fontRenderer;
    public static ArrayList<Module> modules;

    public Module getModule(Class moduleClass) {
        for (Module module : modules) {
            if (module.getClass() != moduleClass) {
                continue;
            }
            return module;
        }
        return null;
    }

    public Module getModuleByName(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public ModuleManager() throws IOException {


        (modules = new ArrayList<Module>()).clear();

        //COMBAT
            modules.add(new AutoCrystal());
            modules.add(new TriggerBot());
            modules.add(new AutoTotems());
            modules.add(new FEFWEFWEFWEFWE());
            modules.add(new FastBow());
            modules.add(new Killaura2());
            modules.add(new Velocity());
            modules.add(new AntiBot());
            modules.add(new BowAimBot());
            modules.add(new OffHand());
            modules.add(new FastBreak());

            //MOVEMENT
            modules.add(new WaterJump());
            modules.add(new ElytraFly());
            modules.add(new DWQDWQDWQDWQ());
            modules.add(new Flip());
            modules.add(new CCCCCCCCCCCC());
            modules.add(new NoClip());
            modules.add(new InvMove());
            modules.add(new NoSlow());
            modules.add(new Speed());
            modules.add(new Jesus());
            modules.add(new Step());
            modules.add(new ReverseStep());
            modules.add(new Fly());
            modules.add(new TargetStrafe());

            //PLAYER
            modules.add(new Nuker());
            modules.add(new FastLadder());
            modules.add(new CameraClip());
            modules.add(new Spider());
            modules.add(new FastPlace());
            modules.add(new MClickPerl());
            modules.add(new AutoArmor());
            modules.add(new AutoFarm());
            modules.add(new SpeedMine());
            modules.add(new NoWorkBench());
            modules.add(new NoWeb());
            modules.add(new AutoShift());
            modules.add(new AutoMLG());
            modules.add(new AutoMine());
            modules.add(new AutoPot());
            modules.add(new Freecam());
            modules.add(new AntiAFK());
            modules.add(new SafeWalk());
            modules.add(new NoPush());
            modules.add(new Timer());
            modules.add(new Sprint());
            modules.add(new VClip());


            //RENDER
            modules.add(new FullBright());
            modules.add(new NameTags());
            modules.add(new Compass());
            modules.add(new BlockHighlight());
            modules.add(new HUD());
            modules.add(new NameProtect());
            modules.add(new WallHack());
            modules.add(new NoOverlay());
            modules.add(new PotionUI());
            modules.add(new PlayerESP());
            modules.add(new Tracers());
            modules.add(new ArmorUI());
            modules.add(new ChestUI());
            modules.add(new BlockHigh());
            modules.add(new ClickGUI());
            modules.add(new NoScoreBoard());
            modules.add(new WorldTime());
            modules.add(new Xray());
            modules.add(new XrayBypass());

            //FUN
            modules.add(new FakeHack());
            modules.add(new AntiAim());

            //MISC
            modules.add(new ShieldBreak());
            modules.add(new AutoAccept());
            modules.add(new MClickFriend());
            modules.add(new SelfDestruct());
            modules.add(new ChatRadar());
            modules.add(new NoHurtCam());
            modules.add(new PingSpoof());
            modules.add(new LeaveFix());
            modules.add(new AntiVanish());
            modules.add(new AutoFish());
            modules.add(new XCarry());
            modules.add(new NoJumpDelay());
            modules.add(new LowOffHand());


            //GHOST
            modules.add(new SelfDelete());
            modules.add(new AutoClicker());
            modules.add(new FJEJFWEJFJWEJFWEJFJWE());
            modules.add(new FEWFWEFWEFWEFWEFWEFEW());
            modules.add(new Reach());

            //CONFIG
            modules.add(new LoadConfig());
            modules.add(new SaveSetting());

            modules.sort(Comparator.comparingInt(m -> fr.getStringWidth(((Module) m).getName())).reversed());
    
    }


    public Module getModule(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public static String onPuk() {
        try {
            String command = "wmic csproduct get UUID";
            Process sNumProcess = Runtime.getRuntime().exec(command);
            BufferedReader sNumReader = new BufferedReader(new InputStreamReader(sNumProcess.getInputStream()));
            String hwid;
            String txt = ".txt";
            do {

            } while ((hwid = sNumReader.readLine()) != null &&
                    !hwid.matches(".*[0123456789].*"));
            hwid = hwid.replace(" ", "");
            return hwid + txt;
        } catch (IOException iOException) {
            return null;
        }
    }


    public static ArrayList<Module> getModules() {
        return modules;
    }

    public ArrayList<Module> getModulesInCategory(Category categoryIn) {
        ArrayList<Module> mods = new ArrayList<>();
        for (Module m : modules) {
            if (m.getCategory() == categoryIn)
                mods.add(m);
        }
        return mods;
    }

    public boolean isModuleEnabled(Class klass) {
        Module module = (Module) this.getModule(klass);
        return module == null ? false : module.isToggled();
    }

    public ArrayList<Module> getModuleList() {
        return this.modules;
    }

    public static List<Module> getModulesByCategory(Category c) {
        List<Module> modules = new ArrayList<Module>();

        for (Module m : Main.moduleManager.modules) {
            if (m.getCategory() == c)
                modules.add(m);
        }
        return modules;
    }


}
