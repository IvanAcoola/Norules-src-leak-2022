package me.nrules.module.modules.render;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChestUI extends Module {
    public ChestUI() {
        super(ChestUI.piska2() + ChestUI.piska3() + ChestUI.piska4() + ChestUI.piska5(), Category.RENDER);
        Main.settingsManager.rSetting(new Setting("Chest", this, false));
        Main.settingsManager.rSetting(new Setting("EChest", this, true));
        Main.settingsManager.rSetting(new Setting("Shulker", this, false));
        Main.settingsManager.rSetting(new Setting("Spawner", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Che");
    }

    public static String piska3() {
        return piska2.replace("GHO", "st");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "U");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "I");
    }

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        RenderManager rm = Minecraft.getMinecraft().getRenderManager();

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableDepth();
        for (TileEntity entity : mc.world.loadedTileEntityList) {
            AxisAlignedBB bb;
            if (entity instanceof TileEntityChest) {
                bb = new AxisAlignedBB(entity.getPos()).offset(-rm.viewerPosX, -rm.viewerPosY, -rm.viewerPosZ);

                if (Main.settingsManager.getSettingByName("Chest").getValBoolean()) {
                    if (entity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) > 9) {
                        RenderGlobal.drawSelectionBoundingBox(bb,
                                (float) 0,
                                (float) 0,
                                (float) 0,
                                0);

                        RenderGlobal.renderFilledBox(bb,
                                (float) 1,
                                (float) 50,
                                (float) 50,
                                156);
                    }
                }

            } else if (entity instanceof TileEntityShulkerBox) {
                bb = new AxisAlignedBB(entity.getPos()).offset(-rm.viewerPosX, -rm.viewerPosY, -rm.viewerPosZ);

                if (Main.settingsManager.getSettingByName("Shulker").getValBoolean()) {

                    if (entity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) > 9) {
                        RenderGlobal.drawSelectionBoundingBox(bb,
                                (float) 255,
                                (float) 165,
                                (float) 76,
                                100);

                        RenderGlobal.renderFilledBox(bb,
                                (float) 255,
                                (float) 165,
                                (float) 76,
                                100);
                    }
                }

            } else if (entity instanceof TileEntityEnderChest) {
                bb = new AxisAlignedBB(entity.getPos()).offset(-rm.viewerPosX, -rm.viewerPosY, -rm.viewerPosZ);

                if (Main.settingsManager.getSettingByName("EChest").getValBoolean()) {
                    if (entity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) > 9) {
                        RenderGlobal.drawSelectionBoundingBox(bb,
                                (float) 55,
                                (float) 100,
                                (float) 55,
                                0);

                        RenderGlobal.renderFilledBox(bb,
                                (float) 75,
                                (float) 0,
                                (float) 130,
                                100);

                    }
                }
            } else if (entity instanceof TileEntityMobSpawner) {
                bb = new AxisAlignedBB(entity.getPos()).offset(-rm.viewerPosX, -rm.viewerPosY, -rm.viewerPosZ);

                if (Main.settingsManager.getSettingByName("Spawner").getValBoolean()) {
                    if (entity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) > 9) {
                        RenderGlobal.drawSelectionBoundingBox(bb,
                                (float) 45,
                                (float) 45,
                                (float) 155,
                                100);

                        RenderGlobal.renderFilledBox(bb,
                                (float) 45,
                                (float) 45,
                                (float) 155,
                                100);

                    }
                }
            }
        }
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
    }
}
