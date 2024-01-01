package me.nrules.module.modules.render;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockHighlight extends Module {
    public BlockHighlight() {
        super(BlockHighlight.piska2() + BlockHighlight.piska3() + BlockHighlight.piska4() + BlockHighlight.piska5(), Category.RENDER);
        Main.settingsManager.rSetting(new Setting("PositionX", this, 0.1,0.1, 2, false));
        Main.settingsManager.rSetting(new Setting("PositionY", this, 0.1,0.1, 2, false));
        Main.settingsManager.rSetting(new Setting("PositionZ", this, 0.1,0.1, 2, false));
        Main.settingsManager.rSetting(new Setting("-PositionX", this, 0.1,0.1, 2, false));
        Main.settingsManager.rSetting(new Setting("-PositionY", this, 0.1,0.1, 2, false));
        Main.settingsManager.rSetting(new Setting("-PositionZ", this, 0.49,0.1, 2, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "RGR";
    private static final String piska5 = "FEW";

    public static String piska2() {
        return piska.replace("GOGA", "Vie");
    }

    public static String piska3() {
        return piska2.replace("GHO", "wMod");
    }

    public static String piska4() {
        return piska3.replace("RGR", "e");
    }

    public static String piska5() {
        return piska5.replace("FEW", "l");
    }


    @SubscribeEvent
    public void onRenderTick(RenderHandEvent event) {
        if (mc.player == null || mc.world == null)
            return;

        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        if (rendermanager.options.mainHand.opposite() == EnumHandSide.LEFT) {
            GlStateManager.translate(Main.settingsManager.getSettingByName("PositionX").getValDouble(),Main.settingsManager.getSettingByName("PositionY").getValDouble(), Main.settingsManager.getSettingByName("PositionZ").getValDouble());
        }
        if (rendermanager.options.mainHand.opposite() == EnumHandSide.LEFT) {
            GlStateManager.translate(-Main.settingsManager.getSettingByName("-PositionX").getValDouble(), -Main.settingsManager.getSettingByName("-PositionY").getValDouble(), -Main.settingsManager.getSettingByName("-PositionZ").getValDouble());
        }
    }
}