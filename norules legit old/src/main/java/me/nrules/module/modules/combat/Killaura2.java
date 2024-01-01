package me.nrules.module.modules.combat;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.ColorUtils;
import me.nrules.util.MathUtilsNR;
import me.nrules.util.RotationUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class Killaura2 extends Module {
    public Killaura2() {
        super(Killaura2.piska2() + Killaura2.piska3() + Killaura2.piska4() + Killaura2.piska5(), Category.COMBAT);
        Main.settingsManager.rSetting(new Setting("Range", this, 3.67, 3, 7, false));
        Main.settingsManager.rSetting(new Setting("MarkBox", this, true));
        Main.settingsManager.rSetting(new Setting("AutoCrit", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Ki");
    }

    public static String piska3() {
        return piska2.replace("GHO", "ll");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "au");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ra");
    }

    public static EntityLivingBase target;

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null || mc.world == null)
            return;

        for (Entity entity : mc.world.loadedEntityList) {
            if (entity != mc.player && entity instanceof EntityPlayer && !entity.isDead && mc.player.getDistance(entity) <= Main.settingsManager.getSettingByName("Range").getValDouble()) {

                rotation(mc.player, entity);
                attack(entity);
            }
        }

    }

    private void rotation(EntityPlayerSP player, Entity target) {
        player.rotationYaw = RotationUtils.lookAtRandomed(target)[0];
        player.rotationPitch = RotationUtils.lookAtRandomed(target)[1];

        mc.player.rotationYawHead = RotationUtils.lookAtRandomed(target)[0];
        mc.player.renderYawOffset = RotationUtils.lookAtRandomed(target)[0];
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (!Main.settingsManager.getSettingByName("MarkBox").getValBoolean())
            return;

        for (Entity target : mc.world.loadedEntityList) {
            if (target != mc.player && !target.isDead && mc.player.getDistance(target) <= Main.settingsManager.getSettingByName("Range").getValDouble()) {
                int color = ColorUtils.skyRainbow(100, 20);
                float red = (float) (color >> 16 & 255) / 255.0F;
                float green = (float) (color >> 8 & 255) / 255.0F;
                float blue = (float) (color & 255) / 255.0F;

                int drawTime = (int) (System.currentTimeMillis() % 2000);
                boolean drawMode = drawTime > 1000;
                double drawPercent = drawTime / 1000.0;

                if (!drawMode) {
                    drawPercent = 1 - drawPercent;
                } else {
                    drawPercent -= 1;
                }
                drawPercent = MathUtilsNR.easeInOutQuad(drawPercent);

                List<Vec3d> points = new ArrayList<>();
                AxisAlignedBB bb = target.getEntityBoundingBox();
                double radius = bb.maxX - bb.minX;
                double height = bb.maxY - bb.minY;
                double posX = target.lastTickPosX + (target.posX - target.lastTickPosX) * mc.getRenderPartialTicks();
                double posY = target.lastTickPosY + (target.posY - target.lastTickPosY) * mc.getRenderPartialTicks();
                if (drawMode) {
                    posY -= 0.5;
                } else {
                    posY += 0.5;
                }
                double posZ = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * mc.getRenderPartialTicks();
                for (int i = 0; i < 360; i += 7) {
                    points.add(new Vec3d(posX - Math.sin(i * Math.PI / 180F) * radius, posY + height * drawPercent,
                            posZ + Math.cos(i * Math.PI / 180F) * radius));
                }
                points.add(points.get(0));

                mc.entityRenderer.disableLightmap();
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glBegin(GL11.GL_LINE_STRIP);
                double baseMove = (drawPercent > 0.5 ? 1.0 - drawPercent : drawPercent) * 2;
                double min = height / 60 * 20 * (1.0 - baseMove) * (drawMode ? -1 : 1);
                for (int i = 0; i <= 20; i++) {
                    double moveFace = (height / 60F) * i * baseMove;
                    if (drawMode) {
                        moveFace = -moveFace;
                    }
                    Vec3d firstPoint = points.get(0);
                    GL11.glVertex3d(firstPoint.x - mc.getRenderManager().viewerPosX,
                            firstPoint.y - moveFace - min - mc.getRenderManager().viewerPosY,
                            firstPoint.z - mc.getRenderManager().viewerPosZ);
                    GL11.glColor4f(red, green, blue, 0.7F * (i / 20F));
                    for (Vec3d vec3 : points) {
                        GL11.glVertex3d(vec3.x - mc.getRenderManager().viewerPosX,
                                vec3.y - moveFace - min - mc.getRenderManager().viewerPosY,
                                vec3.z - mc.getRenderManager().viewerPosZ);
                    }
                    GL11.glColor4f(0F, 0F, 0F, 0F);
                }
                GL11.glEnd();
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDisable(GL11.GL_LINE_SMOOTH);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glPopMatrix();
            }
        }
    }

    public void attack(Entity e) {
        if (timerHelper.hasReached(513f) && Main.settingsManager.getSettingByName("AutoCrit").getValBoolean() && mc.player.fallDistance > 0.19f) {
            timerHelper.reset();
            mc.playerController.attackEntity(mc.player, e);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        } else if (!Main.settingsManager.getSettingByName("AutoCrit").getValBoolean()) {
            if (timerHelper.hasReached(600f)) {
                timerHelper.reset();
                mc.playerController.attackEntity(mc.player, e);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }

    public void onDisable() {
        target = null;
        super.onDisable();
    }
}


