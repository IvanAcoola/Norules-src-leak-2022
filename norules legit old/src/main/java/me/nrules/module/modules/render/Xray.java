package me.nrules.module.modules.render;

import me.nrules.Main;
import me.nrules.clickgui.settings.BlockListSetting;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;

public class Xray extends Module {
    public Xray() {
        super(Xray.piska2() + Xray.piska3() + Xray.piska4() + Xray.piska5(), Category.RENDER);
        Main.settingsManager.rSetting(new Setting("Diamonds", this, false));
        Main.settingsManager.rSetting(new Setting("Emerald", this, false));
        Main.settingsManager.rSetting(new Setting("Gold", this, false));
        Main.settingsManager.rSetting(new Setting("Iron", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "X");
    }

    public static String piska3() {
        return piska2.replace("GHO", "R");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "a");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "y");
    }

    public static BlockListSetting blocks = new BlockListSetting((Block) null);

    @SubscribeEvent
    public void onUpdate(TickEvent.RenderTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        boolean diamond = Main.settingsManager.getSettingByName("Diamonds").getValBoolean();
        boolean emerald = Main.settingsManager.getSettingByName("Emerald").getValBoolean();
        boolean gold = Main.settingsManager.getSettingByName("Gold").getValBoolean();
        boolean iron = Main.settingsManager.getSettingByName("Iron").getValBoolean();

        BlockListSetting DIA_IRO = new BlockListSetting(Blocks.DIAMOND_ORE, Blocks.IRON_ORE);
        BlockListSetting DIA_EME = new BlockListSetting(Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE);
        BlockListSetting DIA_GOL = new BlockListSetting(Blocks.DIAMOND_ORE, Blocks.GOLD_ORE);

        BlockListSetting IRO_DI = new BlockListSetting(Blocks.DIAMOND_ORE, Blocks.IRON_ORE);
        BlockListSetting IRO_EME = new BlockListSetting(Blocks.EMERALD_ORE, Blocks.IRON_ORE);
        BlockListSetting IRO_GOL = new BlockListSetting(Blocks.GOLD_ORE, Blocks.IRON_ORE);

        BlockListSetting EME_DI = new BlockListSetting(Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE);
        BlockListSetting EME_GOL = new BlockListSetting(Blocks.EMERALD_ORE, Blocks.GOLD_ORE);
        BlockListSetting EME_IRO = new BlockListSetting(Blocks.EMERALD_ORE, Blocks.IRON_ORE);

        BlockListSetting GOL_DI = new BlockListSetting(Blocks.GOLD_ORE, Blocks.DIAMOND_ORE);
        BlockListSetting GOL_EME = new BlockListSetting(Blocks.GOLD_ORE, Blocks.EMERALD_ORE);
        BlockListSetting GOL_IRO = new BlockListSetting(Blocks.GOLD_ORE, Blocks.IRON_ORE);

        BlockListSetting DIA_EME_IRO = new BlockListSetting(Blocks.EMERALD_ORE, Blocks.IRON_ORE, Blocks.DIAMOND_ORE);
        BlockListSetting DIA_GOL_IRO = new BlockListSetting(Blocks.DIAMOND_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE);

        BlockListSetting GOL_EME_IRO = new BlockListSetting(Blocks.GOLD_ORE, Blocks.EMERALD_ORE, Blocks.IRON_ORE);
        BlockListSetting GOL_DIA_IRO = new BlockListSetting(Blocks.GOLD_ORE, Blocks.DIAMOND_ORE, Blocks.IRON_ORE);

        BlockListSetting IRO_EME_DIA = new BlockListSetting(Blocks.IRON_ORE, Blocks.EMERALD_ORE, Blocks.DIAMOND_ORE);
        BlockListSetting IRO_DIA_GOL = new BlockListSetting(Blocks.IRON_ORE, Blocks.DIAMOND_ORE, Blocks.GOLD_ORE);

        BlockListSetting EME_GOL_IRO = new BlockListSetting(Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE);
        BlockListSetting EME_DIA_IRO = new BlockListSetting(Blocks.EMERALD_ORE, Blocks.DIAMOND_ORE, Blocks.IRON_ORE);

        BlockListSetting DIA_EME_GOL = new BlockListSetting(Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE);

        BlockListSetting ALL = new BlockListSetting(Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.EMERALD_ORE, Blocks.DIAMOND_ORE);

        BlockListSetting block_diamond = new BlockListSetting(Blocks.DIAMOND_ORE);
        BlockListSetting block_iron = new BlockListSetting(Blocks.IRON_ORE);
        BlockListSetting block_gold = new BlockListSetting(Blocks.GOLD_ORE);
        BlockListSetting block_emerald = new BlockListSetting(Blocks.EMERALD_ORE);

        if (diamond) {
            blocks = block_diamond;
        }
        if (emerald) {
            blocks = block_emerald;
        }
        if (gold) {
            blocks = block_gold;
        }
        if (iron) {
            blocks = block_iron;
        }

        if (diamond && iron) {
            blocks = DIA_IRO;
        }
        if (diamond && gold) {
            blocks = DIA_GOL;
        }
        if (diamond && emerald) {
            blocks = DIA_EME;
        }

        if (iron && diamond) {
            blocks = IRO_DI;
        }
        if (iron && gold) {
            blocks = IRO_GOL;
        }
        if (iron && emerald) {
            blocks = IRO_EME;
        }

        if (gold && diamond) {
            blocks = GOL_DI;
        }
        if (gold && iron) {
            blocks = GOL_IRO;
        }
        if (gold && emerald) {
            blocks = GOL_EME;
        }

        if (emerald && diamond) {
            blocks = EME_DI;
        }
        if (emerald && iron) {
            blocks = EME_IRO;
        }
        if (emerald && gold) {
            blocks = EME_GOL;
        }

        if (diamond && emerald && iron) {
            blocks = DIA_EME_IRO;
        }

        if (diamond && gold && iron) {
            blocks = DIA_GOL_IRO;
        }
        if (diamond && emerald && iron) {
            blocks = DIA_EME_IRO;
        }

        if (gold && emerald && iron) {
            blocks = GOL_EME_IRO;
        }
        if (gold && diamond && iron) {
            blocks = GOL_DIA_IRO;
        }

        if (emerald && diamond && iron) {
            blocks = IRO_EME_DIA;
        }
        if (gold && diamond && iron) {
            blocks = IRO_DIA_GOL;
        }

        if (diamond && emerald && gold) {
            blocks = DIA_EME_GOL;
        }

        if (gold && emerald && iron) {
            blocks = EME_GOL_IRO;
        }
        if (emerald && diamond && iron) {
            blocks = EME_DIA_IRO;
        }

        if (diamond && emerald && gold && iron) {
            blocks = ALL;
        }

        try {
            BlockRendererDispatcher renderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
            if (renderer.getClass().getName().equals("codechicken.lib.render.block.CCBlockRendererDispatcher")) {
                Field parentDispatcher = renderer.getClass().getDeclaredField("parentDispatcher");
                parentDispatcher.setAccessible(true);
                renderer = (BlockRendererDispatcher) parentDispatcher.get(renderer);
            }
            Field blockModelRenderer = renderer.getClass().getDeclaredField("field_175027_c");
            blockModelRenderer.setAccessible(true);
            blockModelRenderer.set(renderer, new NForgeBlockModelRenderer(mc.getBlockColors()));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDisable() {
        mc.renderGlobal.loadRenderers();
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.renderGlobal.loadRenderers();

    }

}
