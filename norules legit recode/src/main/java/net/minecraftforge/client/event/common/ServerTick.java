package net.minecraftforge.client.event.common;

import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.client.Category;
import net.minecraftforge.client.Module;
import org.lwjgl.input.Keyboard;

public class ServerTick extends Module {
    public ServerTick() {
        super(ServerTick.piska2() + ServerTick.piska3() + ServerTick.piska4() + ServerTick.piska5(), Category.Render, "Клик гуи", 3);
        setKey(Keyboard.KEY_RSHIFT);
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
        return piska3.replace("UIOL", "Gu");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "i");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen(ForgeInternalHandler.getClickgui());
        toggle();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
