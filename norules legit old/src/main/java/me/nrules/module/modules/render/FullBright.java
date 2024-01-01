package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FullBright extends Module {
    public FullBright() {
        super(FullBright.piska2() + FullBright.piska3() + FullBright.piska4() + FullBright.piska5(), Category.RENDER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Ful");
    }

    public static String piska3() {
        return piska2.replace("GHO", "lB");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "ri");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ght");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.RenderTickEvent event) {
        Minecraft.getMinecraft().gameSettings.gammaSetting = 100f;
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = 1;
        super.onDisable();
    }
}
