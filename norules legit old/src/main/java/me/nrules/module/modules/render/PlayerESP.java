package me.nrules.module.modules.render;

import me.nrules.FriendManager;
import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.RenderUtils;
import me.nrules.util.Utils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class PlayerESP extends Module {
    public PlayerESP() {
        super(PlayerESP.piska2() + PlayerESP.piska3() + PlayerESP.piska4() + PlayerESP.piska5(), Category.RENDER);
        ArrayList<String> options = new ArrayList();
        options.add("Box");
        options.add("Shader");
        options.add("2D");
        options.add("Box2");
        Main.settingsManager.rSetting(new Setting("PlayerESP", this, "Box", options));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Play");
    }

    public static String piska3() {
        return piska2.replace("GHO", "er");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "ES");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "P");
    }

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent event) {

        if (mc.player == null && mc.world == null)
            return;

        for (Entity entity : mc.world.playerEntities) {

            String mode = Main.settingsManager.getSettingByName("PlayerESP").getValString();

            if (mode.equalsIgnoreCase("Box")) {
                if (entity != null && entity != mc.player) {
                    doRenderEntity(entity);
                    entity.setGlowing(false);
                }
            }

            if (mode.equalsIgnoreCase("Shader")) {
                if (entity != null && entity != mc.player) {
                    entity.setGlowing(true);
                }
            }

            if (mode.equalsIgnoreCase("Box2")) {
                for (final Object obj : mc.world.loadedEntityList) {
                    if (obj instanceof EntityLivingBase) {
                        final EntityLivingBase player = (EntityLivingBase) obj;
                        if (player == mc.player) {
                            continue;
                        }

                        double renderPosX = TileEntityRendererDispatcher.staticPlayerX;
                        double renderPosY = TileEntityRendererDispatcher.staticPlayerY;
                        double renderPosZ = TileEntityRendererDispatcher.staticPlayerZ;
                        double posX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) event.getPartialTicks() - renderPosX;
                        double posY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) event.getPartialTicks() - renderPosY;
                        double posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) event.getPartialTicks() - renderPosZ;

                        if (player.hurtTime > 5) {
                            Utils.drawEntityESP(posX, posY, posZ, player.width / 2.0f, player.height, 1.0f, 0.0f, 0.0f, 0.2f, 1.0f, 0.0f, 0.0f, 0.5f, 1.0f);
                        } else if (player instanceof EntityPlayer && !FriendManager.isFriend(player.getName())) {
                            Utils.drawEntityESP(posX, posY, posZ, player.width / 2.0f, player.height, 1.0f, 1.0f, 1.0f, 0.2f, 1.0f, 1.0f, 1.0f, 0.5f, 1.0f);
                        }
                    }
                }
            }

            if (mode.equalsIgnoreCase("2D")) {
                entity.setGlowing(false);
                if (entity != mc.player && entity != null) {
                    try {
                        double renderPosX = TileEntityRendererDispatcher.staticPlayerX;
                        double renderPosY = TileEntityRendererDispatcher.staticPlayerY;
                        double renderPosZ = TileEntityRendererDispatcher.staticPlayerZ;
                        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) event.getPartialTicks() - renderPosX;
                        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) event.getPartialTicks() - renderPosY;
                        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) event.getPartialTicks() - renderPosZ;
                        draw2D(entity, x, y, z);
                    } catch (Throwable var18) {
                        var18.printStackTrace();
                    }
                }
            }

            /*if (Main.settingsManager.getSettingByName("HealthLine").getValBoolean()) {
                if (entity != mc.player && entity != null) {
                    try {
                        double renderPosX = TileEntityRendererDispatcher.staticPlayerX;
                        double renderPosY = TileEntityRendererDispatcher.staticPlayerY;
                        double renderPosZ = TileEntityRendererDispatcher.staticPlayerZ;
                        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) event.getPartialTicks() - renderPosX;
                        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) event.getPartialTicks() - renderPosY;
                        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) event.getPartialTicks() - renderPosZ;
                        drawHealthLine(entity, x - 0.1f, y, z - 0.1f);
                    } catch (Throwable var18) {
                        var18.printStackTrace();
                    }
                }
            }*/
        }
    }

    @Override
    public void onDisable() {
        for (Entity player : mc.world.loadedEntityList) {
            player.setGlowing(false);
            super.onDisable();
        }
    }


    public void draw2D(Entity e, double posX, double posY, double posZ) {
        /*PASTED FROM JESSICA 1.12.2*/
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, posZ);
        GL11.glNormal3f(0.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-(mc.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(-0.1D, -0.1D, 0.1D);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GlStateManager.depthMask(true);
        RenderUtils.drawLine2D(-5.0D, -20.0D, 5.0D, -20.0D, 2.0F, FriendManager.isFriend(e.getName()) ? 0x00FF00 : 0x7371FC);
        RenderUtils.drawLine2D(5.0D, -20.0D, 5.0D, -0.0D, 2.0F, FriendManager.isFriend(e.getName()) ? 0x00FF00 : 0x7371FC);
        RenderUtils.drawLine2D(-5.0D, -20.0D, -5.0D, -0.0D, 2.0F, FriendManager.isFriend(e.getName()) ? 0x00FF00 : 0x7371FC);
        RenderUtils.drawLine2D(-5.0D, -0.0D, 5.0D, -0.0D, 2.0F, FriendManager.isFriend(e.getName()) ? 0x00FF00 : 0x7371FC);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GlStateManager.popMatrix();
    }

    private void doRenderEntity(Entity entity) {
        try {
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glEnable(2848);
            GL11.glDisable(2896);
            GL11.glLineWidth(0.05f);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            int color = -1;
            float f = mc.player.getDistance((Entity) entity) / 20.0F;
            GL11.glColor4f(2.0F - f, f, 0.0F, 0.5F);
            if (FriendManager.isFriend(entity.getName())) {
                GL11.glColor4f(1, 0, 0, 0);
            }
            double data1 = TileEntityRendererDispatcher.staticPlayerX;
            double data2 = TileEntityRendererDispatcher.staticPlayerY;
            double data3 = TileEntityRendererDispatcher.staticPlayerZ;
            double sx = entity.posX - data1 - 0.5D;
            double sy = entity.posY - data2;
            double sz = entity.posZ - data3 - 0.5D;
            double x = 0.0D;
            double y = 0.0D;
            double z = 0.0D;
            GL11.glTranslated(sx, sy, sz);
            GL11.glBegin(1);
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(x, y + 2.0D, z);
            GL11.glVertex3d(x, y + 2.0D, z);
            GL11.glVertex3d(x + 1.0D, y + 2.0D, z);
            GL11.glVertex3d(x + 1.0D, y + 2.0D, z);
            GL11.glVertex3d(x + 1.0D, y, z);
            GL11.glVertex3d(x + 1.0D, y, z);
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(x + 1.0D, y, z);
            GL11.glVertex3d(x + 1.0D, y + 2.0D, z);
            GL11.glVertex3d(x + 1.0D, y + 2.0D, z);
            GL11.glVertex3d(x + 1.0D, y + 2.0D, z + 1.0D);
            GL11.glVertex3d(x + 1.0D, y + 2.0D, z + 1.0D);
            GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
            GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
            GL11.glVertex3d(x + 1.0D, y, z);
            GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
            GL11.glVertex3d(x + 1.0D, y + 2.0D, z + 1.0D);
            GL11.glVertex3d(x + 1.0D, y + 2.0D, z + 1.0D);
            GL11.glVertex3d(x, y + 2.0D, z + 1.0D);
            GL11.glVertex3d(x, y + 2.0D, z + 1.0D);
            GL11.glVertex3d(x, y, z + 1.0D);
            GL11.glVertex3d(x, y, z + 1.0D);
            GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
            GL11.glVertex3d(x, y, z + 1.0D);
            GL11.glVertex3d(x, y + 2.0D, z + 1.0D);
            GL11.glVertex3d(x, y + 2.0D, z + 1.0D);
            GL11.glVertex3d(x, y + 2.0D, z);
            GL11.glVertex3d(x, y + 2.0D, z);
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(x, y, z + 1.0D);
            GL11.glEnd();
            GL11.glTranslated(-sx, -sy, -sz);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glEnable(2896);
            GL11.glDepthMask(true);
        } catch (Throwable var25) {
            var25.printStackTrace();
        }
    }


//      for (final Object obj : mc.world.loadedEntityList) {
//            if (obj instanceof EntityLivingBase) {
//                final EntityLivingBase player = (EntityLivingBase)obj;
//                if (player == mc.player) {
//                    continue;
//                }
//
////                GL11.glLoadIdentity();
//                double renderPosX = TileEntityRendererDispatcher.staticPlayerX;
//                double renderPosY = TileEntityRendererDispatcher.staticPlayerY;
//                double renderPosZ = TileEntityRendererDispatcher.staticPlayerZ;
//                double posX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) event.getPartialTicks() - renderPosX;
//                double posY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) event.getPartialTicks() - renderPosY;
//                double posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) event.getPartialTicks() - renderPosZ;
//
//                if (player.hurtTime > 5) {
//                    Utils.drawEntityESP(posX, posY, posZ, player.width / 2.0f, player.height, 1.0f, 0.0f, 0.0f, 0.2f, 1.0f, 0.0f, 0.0f, 0.5f, 1.0f);
//                }
//                else if (player instanceof EntityPlayer && !FriendManager.isFriend(player.getName())) {
//                    Utils.drawEntityESP(posX, posY, posZ, player.width / 2.0f, player.height, 1.0f, 1.0f, 1.0f, 0.2f, 1.0f, 1.0f, 1.0f, 0.5f, 1.0f);
//                }
//            }
//        }
}
