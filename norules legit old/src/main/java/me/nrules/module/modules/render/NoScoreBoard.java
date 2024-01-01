package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoScoreBoard extends Module {
    public NoScoreBoard() {
        super(NoScoreBoard.piska2() + NoScoreBoard.piska3() + NoScoreBoard.piska4() + NoScoreBoard.piska5(), Category.MISC);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "NoSc");
    }

    public static String piska3() {
        return piska2.replace("GHO", "oreB");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "oar");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "d");
    }

    @SubscribeEvent
    public void onUpdate(RenderGameOverlayEvent.Pre event) {
        if (mc.player == null && mc.world == null)
            return;

        GuiIngameForge.renderObjective = false;
    }

    @Override
    public void onDisable() {
        GuiIngameForge.renderObjective = true;
        super.onDisable();
    }
}