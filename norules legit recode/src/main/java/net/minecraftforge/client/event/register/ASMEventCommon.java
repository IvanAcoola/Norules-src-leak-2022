package net.minecraftforge.client.event.register;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.clickgui.setting.Setting;
import net.minecraftforge.client.Category;
import net.minecraftforge.client.Module;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

public class ASMEventCommon extends Module {
    public ASMEventCommon() {
        super(ASMEventCommon.piska2() + ASMEventCommon.piska3() + ASMEventCommon.piska4() + ASMEventCommon.piska5(), Category.Combat, "Увеличивает размер хитбокса игрока", 3);
    }

    private static final String piska = "FFFFFFFFFFFFFFFFF";
    private static final String piska2 = "AAAAAAAAAAAAAA";
    private static final String piska3 = "VVVVVVVVVVVVVVVVVVVVVVVVVVVVV";
    private static final String piska5 = "ZZZZZZZZZZZZZZZZZZ";

    public static String piska2() {
        return piska.replace("FFFFFFFFFFFFFFFFF", "Hi");
    }

    public static String piska3() {
        return piska2.replace("AAAAAAAAAAAAAA", "tBo");
    }

    public static String piska4() {
        return piska3.replace("VVVVVVVVVVVVVVVVVVVVVVVVVVVVV", "x");
    }

    public static String piska5() {
        return piska5.replace("ZZZZZZZZZZZZZZZZZZ", "");
    }

    private static RayTraceResult mv;

    @Override
    public void setup() {
        super.setup();
        this.rSetting(new Setting("Width1", this, 1, 0.1, 5, false));
        this.rSetting(new Setting("Visible", this, false));
    }

    @SubscribeEvent
    public void m(MouseEvent e) {
        if (mc.player == null || mc.world == null) return;

        final Object[] objects = getEntityCustom(mc.player.rotationPitch, mc.player.rotationYaw, 3, 0, 0.0F);
        if (objects == null) {
            return;
        }
        mc.objectMouseOver = new RayTraceResult((Entity) objects[0], (Vec3d) objects[1]);
        mc.pointedEntity = (Entity) objects[0];
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (ForgeInternalHandler.settingsManager.getSettingByName("Visible").getValBoolean() && mc.player != null && mc.world != null) {
            for (Entity en : mc.world.loadedEntityList) {
                if (en != mc.player && en instanceof EntityLivingBase && ((EntityLivingBase) en).deathTime == 0 && !(en instanceof EntityArmorStand) && !en.isInvisible()) {
                    this.rh(en, Color.WHITE);
                }
            }

        }
//
//        if (ForgeInternalHandler.settingsManager.getSettingByName("Visible").getValBoolean() && mc.player != null && mc.world != null) {
//            for (Entity entity : mc.world.loadedEntityList) {
//                if (entity == mc.player) return;
//                rh(entity, Color.WHITE);
//            }
//        }
    }

//
//    public static void renderDebugBoundingBox(Entity entityIn, double x, double y, double z, float entityYaw, float partialTicks) {
//        try {
//            GlStateManager.depthMask(false);
//            GlStateManager.disableTexture2D();
//            GlStateManager.disableLighting();
//            GlStateManager.disableCull();
//            GlStateManager.disableBlend();
//            float f = entityIn.width / 2.0F;
//            AxisAlignedBB axisalignedbb = entityIn.getEntityBoundingBox();
//            RenderGlobal.drawBoundingBox(axisalignedbb.minX - entityIn.posX + x, axisalignedbb.minY - entityIn.posY + y, axisalignedbb.minZ - entityIn.posZ + z, axisalignedbb.maxX - entityIn.posX + x, axisalignedbb.maxY - entityIn.posY + y, axisalignedbb.maxZ - entityIn.posZ + z, 1.0F, 1.0F, 1.0F, 1.0F);
//            Entity[] aentity = entityIn.getParts();
//
//            if (aentity != null) {
//                for (Entity entity : aentity) {
//                    double d0 = (entity.posX - entity.prevPosX) * (double) partialTicks;
//                    double d1 = (entity.posY - entity.prevPosY) * (double) partialTicks;
//                    double d2 = (entity.posZ - entity.prevPosZ) * (double) partialTicks;
//                    AxisAlignedBB axisalignedbb1 = entity.getEntityBoundingBox();
//                    RenderGlobal.drawBoundingBox(axisalignedbb1.minX - mc.getRenderManager().viewerPosX + d0, axisalignedbb1.minY - mc.getRenderManager().viewerPosY + d1, axisalignedbb1.minZ - mc.getRenderManager().viewerPosZ + d2, axisalignedbb1.maxX - mc.getRenderManager().viewerPosX + d0, axisalignedbb1.maxY - mc.getRenderManager().viewerPosY + d1, axisalignedbb1.maxZ - mc.getRenderManager().viewerPosZ + d2, 0.25F, 1.0F, 0.0F, 1.0F);
//                }
//            }
//
//            if (entityIn instanceof EntityLivingBase) {
//                float f1 = 0.01F;
//                RenderGlobal.drawBoundingBox(x - (double) f, y + (double) entityIn.getEyeHeight() - 0.009999999776482582D, z - (double) f, x + (double) f, y + (double) entityIn.getEyeHeight() + 0.009999999776482582D, z + (double) f, 1.0F, 0.0F, 0.0F, 1.0F);
//            }
//
//            Tessellator tessellator = Tessellator.getInstance();
//            BufferBuilder bufferbuilder = tessellator.getBuffer();
//            Vec3d vec3d = entityIn.getLook(partialTicks);
//            bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
//            bufferbuilder.pos(x, y + (double) entityIn.getEyeHeight(), z).color(0, 0, 255, 255).endVertex();
//            bufferbuilder.pos(x + vec3d.x * 2.0D, y + (double) entityIn.getEyeHeight() + vec3d.y * 2.0D, z + vec3d.z * 2.0D).color(0, 0, 255, 255).endVertex();
//            tessellator.draw();
//            GlStateManager.enableTexture2D();
//            GlStateManager.enableLighting();
//            GlStateManager.enableCull();
//            GlStateManager.disableBlend();
//            GlStateManager.depthMask(true);
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//    }

    private void rh(Entity e, Color c) {
        if (e instanceof EntityLivingBase) {
            double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) getTimer().renderPartialTicks - mc.getRenderManager().viewerPosX;
            double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) getTimer().renderPartialTicks - mc.getRenderManager().viewerPosY;
            double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) getTimer().renderPartialTicks - mc.getRenderManager().viewerPosZ;
            float ex = (float) ((double) e.getCollisionBorderSize() + ForgeInternalHandler.settingsManager.getSettingByName("Width1").getValDouble());
            AxisAlignedBB bbox = e.getEntityBoundingBox().expand(ex, ex, ex);
            AxisAlignedBB axis = new AxisAlignedBB(bbox.minX - e.posX + x, bbox.minY - e.posY + y, bbox.minZ - e.posZ + z, bbox.maxX - e.posX + x, bbox.maxY - e.posY + y, bbox.maxZ - e.posZ + z);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glLineWidth(1.0F);
            GL11.glColor3d(c.getRed(), c.getGreen(), c.getBlue());
            RenderGlobal.drawSelectionBoundingBox(axis, 1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
//            Tessellator tessellator = Tessellator.getInstance();
//            BufferBuilder bufferbuilder = tessellator.getBuffer();
//            Vec3d vec3d = e.getLook(getTimer().renderPartialTicks);
//            bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
//            bufferbuilder.pos(x, y + (double) e.getEyeHeight(), z).color(0, 0, 255, 255).endVertex();
//            bufferbuilder.pos(x + vec3d.x * 2.0D, y + (double) e.getEyeHeight() + vec3d.y * 2.0D, z + vec3d.z * 2.0D).color(0, 0, 255, 255).endVertex();
//            tessellator.draw();
//            GlStateManager.enableTexture2D();
//            GlStateManager.enableLighting();
//            GlStateManager.enableCull();
//            GlStateManager.disableBlend();
//            GlStateManager.depthMask(true);
        }
    }

    public static net.minecraft.util.Timer getTimer() {
        return ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "timer", "field_71428_T");
    }

    public static Object[] getEntityCustom(float pitch, float yaw, final double distance, final double expand, final float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        final Entity var2 = mc.getRenderViewEntity();
        Entity entity = null;
        if (var2 == null || mc.world == null) {
            return null;
        }
        mc.mcProfiler.startSection("pick");
        final Vec3d var3 = var2.getPositionEyes(0.0f);
        final Vec3d var4 = var2.getLook(0.0f);
        final Vec3d var5 = var3.addVector(var4.x * distance, var4.y * distance, var4.z * distance);
        Vec3d var6 = null;
        final float var7 = 1.0f;
        final List var8 = mc.world.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox().offset(var4.x * distance, var4.y * distance, var4.z * distance).expand(var7, var7, var7));
        double var9 = distance;
        for (int var10 = 0; var10 < var8.size(); ++var10) {
            final Entity var11 = (Entity) var8.get(var10);
            if (var11.canBeCollidedWith()) {
                float ex = (float) ((double) entity.getCollisionBorderSize() + ForgeInternalHandler.settingsManager.getSettingByName("Width1").getValDouble());
                AxisAlignedBB ax = entity.getEntityBoundingBox().expand(ex, ex, ex);
                final float var12 = var11.getCollisionBorderSize();

//                AxisAlignedBB var13 = var11.getEntityBoundingBox().expand(ex, ex, ex);
//                var13 = var13.expand(expand, expand, expand);
                final RayTraceResult var14 = ax.calculateIntercept(var3, var5);
                if (ax.contains(var3)) {
                    if (0.0 < var9 || var9 == 0.0) {
                        entity = var11;
                        var6 = ((var14 == null) ? var3 : var14.hitVec);
                        var9 = 0.0;
                    }
                } else if (var14 != null) {
                    final double var15 = var3.distanceTo(var14.hitVec);
                    if (var15 < var9 || var9 == 0.0) {
                        boolean canRiderInteract = false;
                        if (var11 == var2.getRidingEntity() && !canRiderInteract) {
                            if (var9 == 0.0) {
                                entity = var11;
                                var6 = var14.hitVec;
                            }
                        } else {
                            entity = var11;
                            var6 = var14.hitVec;
                            var9 = var15;
                        }
                    }
                }
            }
        }
        if (var9 < distance && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
        }
        mc.mcProfiler.endSection();
        if (entity == null || var6 == null) {
            return null;
        }
        return new Object[]{entity, var6};
    }

}