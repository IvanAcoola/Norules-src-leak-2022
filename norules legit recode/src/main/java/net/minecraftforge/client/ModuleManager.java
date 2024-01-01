package net.minecraftforge.client;

import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.client.event.register.*;
import net.minecraftforge.client.event.fml.ChangedEvent;
import net.minecraftforge.client.event.handler.EntityJoinWorld;
import net.minecraftforge.client.event.common.ServerTick;
import net.minecraftforge.client.event.common.RegistryEvent;
import net.minecraftforge.client.event.common.MissingMappings;
import net.minecraftforge.client.event.common.PlayerLoggedInEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public ModuleManager() {


        (modules = new ArrayList<Module>()).clear();
        //combat
        modules.add(new RegisterItemHandler());
        modules.add(new ASMEventCommon());
        modules.add(new ColorHandler());
        modules.add(new WorldEvent());
        modules.add(new ModelRegistry());

        //MISC
        modules.add(new ChangedEvent());

        //Movement
        modules.add(new EntityJoinWorld());

        //Render
        modules.add(new RegistryEvent());
        modules.add(new PlayerLoggedInEvent());
        modules.add(new MissingMappings());
        modules.add(new ServerTick());
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

    public Module get(Class<?> clas) {
        return getModuleList().stream().filter(module -> module.getClass() == clas).findFirst().orElse(null);
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

    public int getLongestActiveModule(net.minecraftforge.font.FontRenderer fr) {
        int length = 0;
        for (Module mod : modules) {
            if (mod.isToggled()) {
                if (fr.getStringWidth(mod.getName()) > length) {
                    length = (int) fr.getStringWidth(mod.getName());
                }
            }
        }
        return length;
    }

    public boolean isModuleEnabled(Class klass) {
        Module module = (Module) this.getModule(klass);
        return module == null ? false : module.isToggled();
    }

    public ArrayList<Module> getModuleList() {
        return this.modules;
    }


    public int getBoxHeight(net.minecraftforge.font.FontRenderer fr, int margin) {
        int length = 0;
        for (Module mod : modules) {
            if (mod.isToggled()) {
                length += fr.getFontHeight() + margin;
            }
        }
        return length;
    }

    public static List<Module> getModulesByCategory(Category c) {
        List<Module> modules = new ArrayList<Module>();

        for (Module m : ForgeInternalHandler.moduleManager.modules) {
            if (m.getCategory() == c)
                modules.add(m);
        }
        return modules;
    }


}