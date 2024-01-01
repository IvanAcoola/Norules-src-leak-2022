package net.minecraftforge;

import net.minecraftforge.clickgui.Clickgui;
import net.minecraftforge.clickgui.setting.SettingsManager;
import net.minecraftforge.font.FontRenderer;
import net.minecraftforge.hooks.ProfilerHook;
import net.minecraftforge.client.Module;
import net.minecraftforge.client.ModuleManager;
import net.minecraftforge.hooks.RenderDebugBoundingBox;
import net.minecraftforge.proxy.CommonProxy;
import net.minecraftforge.refrences.Refrence;
import net.minecraftforge.utils.ReflectUtils;
import net.minecraftforge.utils.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.lang.invoke.MethodHandles;

@Mod(modid = Refrence.piska + Refrence.piska1, name = Refrence.piska2 + Refrence.piska3 + Refrence.piska4, version = Refrence.VERSION)
public class ForgeInternalHandler {

    @SidedProxy(clientSide = Refrence.CLIENT_PROXY_CLASS, serverSide = Refrence.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    public static ModuleManager moduleManager;
    public static FontRenderer SANS_SMALL, SANS_BIG, OCRA;
    public static SettingsManager settingsManager;
    private static Clickgui clickgui;
    public ForgeInternalHandler() {
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
        clickgui = new Clickgui();

        registerHooks();

        registerFont();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void registerFont() {
        try {
            SANS_SMALL = new FontRenderer("C:/Windows/Fonts/arial.ttf", 0.25f, 0f);
            SANS_BIG = new FontRenderer("C:/Windows/Fonts/ariblk.ttf", 0.25f, 0f);
            OCRA = new FontRenderer("C:/Windows/Fonts/Roboto-Regular_0.ttf", 0.27f, 0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void registerHooks() {
        try {
            System.out.println("unsuccess");
		/* hooks loader*/
//            ReflectionHelper.hookField(ReflectUtils.getField(Minecraft.class, "renderManager", "field_175616_W", "R"), Minecraft.getMinecraft(), new RenderDebugBoundingBox(Minecraft.getMinecraft().renderEngine, Minecraft.getMinecraft().getRenderItem()));
//            ReflectionHelper.hookField(ReflectUtils.getField(Minecraft.class, "mcProfiler", "field_71424_I", "B"), Minecraft.getMinecraft(), new ProfilerHook());
//            new GuiNewChatHook(Minecraft.getMinecraft());
//            ReflectionHelper.hookField(ReflectUtils.getField(GuiIngame.class, "persistantChatGUI", "field_73840_e", "I"), Minecraft.getMinecraft().ingameGUI, new GuiNewChatHook(Minecraft.getMinecraft()));
//            ReflectionHelper.hookField(ReflectUtils.getField(Minecraft.class, "ingameGUI", "field_71456_v", "q"), Minecraft.getMinecraft(), new GuiInGameHook(Minecraft.getMinecraft()));
            System.out.println("success");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent e) {
        Minecraft mc = Minecraft.getMinecraft();
        if (Keyboard.isCreated() && mc.world != null && mc.player != null) {
            if (Keyboard.getEventKeyState()) {
                for (Module module : ForgeInternalHandler.moduleManager.getModuleList()) {
                    if (Keyboard.getEventKey() == module.getKey()) {
                        module.toggle();
                    }
                }
            }
        }
    }



    public static final Clickgui getClickgui() {
        return clickgui;
    }

    public static SettingsManager getSettingsManager() {
        return settingsManager;
    }
}
