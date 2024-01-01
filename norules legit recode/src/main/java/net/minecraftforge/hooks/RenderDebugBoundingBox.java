package net.minecraftforge.hooks;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.client.event.register.ASMEventCommon;
import net.minecraftforge.utils.ReflectionHelper;

import java.util.Map;

public class RenderDebugBoundingBox extends RenderManager {
//


    private boolean renderOutlines;
    private boolean debugBoundingBox;
    private double renderPosX;
    private double renderPosY;
    private double renderPosZ;
    private final Map<String, RenderPlayer> skinMap = Maps.<String, RenderPlayer>newHashMap();

    public void setRenderOutlines(boolean renderOutlinesIn) {
        this.renderOutlines = renderOutlinesIn;
    }

    public void setDebugBoundingBox(boolean debugBoundingBoxIn) {
        this.debugBoundingBox = debugBoundingBoxIn;
    }

    public double getRenderPosX() {
        return renderPosX;
    }

    public double getRenderPosY() {
        return renderPosY;
    }

    public double getRenderPosZ() {
        return renderPosZ;
    }

    public void setRenderPosition(double renderPosXIn, double renderPosYIn, double renderPosZIn) {
        this.renderPosX = renderPosXIn;
        this.renderPosY = renderPosYIn;
        this.renderPosZ = renderPosZIn;
    }

    @Override
    public void renderEntity(Entity entityIn, double x, double y, double z, float yaw, float partialTicks, boolean p_188391_10_) {
        Render<Entity> render = null;

        try {
            if (this.debugBoundingBox && !entityIn.isInvisible() && !p_188391_10_ && !Minecraft.getMinecraft().isReducedDebug()) {
                try {
//                    ASMEventCommon.renderDebugBoundingBox(entityIn, x, y, z, yaw, partialTicks);
                } catch (Throwable throwable) {
                    throw new ReportedException(CrashReport.makeCrashReport(throwable, "Rendering entity hitbox in world"));
                }
            }
        } catch (Throwable throwable3) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable3, "Rendering entity in world");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being rendered");
            entityIn.addEntityCrashInfo(crashreportcategory);
            CrashReportCategory crashreportcategory1 = crashreport.makeCategory("Renderer details");
            crashreportcategory1.addCrashSection("Assigned renderer", render);
            crashreportcategory1.addCrashSection("Location", CrashReportCategory.getCoordinateInfo(x, y, z));
            crashreportcategory1.addCrashSection("Rotation", Float.valueOf(yaw));
            crashreportcategory1.addCrashSection("Delta", Float.valueOf(partialTicks));
            throw new ReportedException(crashreport);
        }
    }

    public RenderDebugBoundingBox(TextureManager p_i46180_1_, RenderItem itemRendererIn) {
        super(p_i46180_1_, itemRendererIn);
        this.renderEngine = p_i46180_1_;

    }


    private void renderDebugBoundingBox1(Entity entityIn, double x, double y, double z, float entityYaw, float partialTicks) throws Throwable {
        for (int i = 0; i < 1; i++) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("работаит"));
        }//        Minecraft.getMinecraft().player.jump();
        //        System.out.println("ХУЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙЙ");
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        float f = entityIn.width / 2.0F;
        double d = ReflectionHelper.getRenderPosX();
        double d5 = ReflectionHelper.getRenderPosY();
        double d4 = ReflectionHelper.getRenderPosZ();
        ASMEventCommon hitboxes = ((ASMEventCommon) ForgeInternalHandler.moduleManager.get(ASMEventCommon.class));
        AxisAlignedBB axisalignedbb =/* hitboxes.isToggled() && Main.settingsManager.getSettingByName("Visible").getValBoolean() ? entityIn.getEntityBoundingBox().shrink(hitboxes.getCustomSize(entityIn)) : */new AxisAlignedBB(10.0D, 10.0D, 10.0D, 10.0D, 10.0D, 10.0D);
        ;
        RenderGlobal.drawBoundingBox(axisalignedbb.minX - entityIn.posX + x, axisalignedbb.minY - entityIn.posY + y, axisalignedbb.minZ - entityIn.posZ + z, axisalignedbb.maxX - entityIn.posX + x, axisalignedbb.maxY - entityIn.posY + y, axisalignedbb.maxZ - entityIn.posZ + z, 1.0F, 1.0F, 1.0F, 1.0F);
        Entity[] aentity = entityIn.getParts();
        if (aentity != null) {
            for (Entity entity : aentity) {
                double d0 = (entity.posX - entity.prevPosX) * (double) partialTicks;
                double d1 = (entity.posY - entity.prevPosY) * (double) partialTicks;
                double d2 = (entity.posZ - entity.prevPosZ) * (double) partialTicks;
//                double d9 = ReflectionHelper.getRenderPosX();
//                double d11 = ReflectionHelper.getRenderPosY();
//                double d12 = ReflectionHelper.getRenderPosZ();
                AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(10.0D, 10.0D, 10.0D, 10.0D, 10.0D, 10.0D);
//                RenderGlobal.drawBoundingBox(axisalignedbb1.minX - renderPosX + d0, axisalignedbb1.minY - renderPosY + d1, axisalignedbb1.minZ - renderPosZ + d2, axisalignedbb1.maxX - renderPosX + d0, axisalignedbb1.maxY - renderPosY + d1, axisalignedbb1.maxZ - renderPosZ + d2, 0.25F, 1.0F, 0.0F, 1.0F);
            }
        }

        if (entityIn instanceof EntityLivingBase) {
            float f1 = 0.01F;
            RenderGlobal.drawBoundingBox(x - (double) f, y + (double) entityIn.getEyeHeight() - 0.009999999776482582D, z - (double) f, x + (double) f, y + (double) entityIn.getEyeHeight() + 0.009999999776482582D, z + (double) f, 1.0F, 0.0F, 0.0F, 1.0F);
        }

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        Vec3d vec3d = entityIn.getLook(partialTicks);
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, y + (double) entityIn.getEyeHeight(), z).color(0, 0, 255, 255).endVertex();
        bufferbuilder.pos(x + vec3d.x * 2.0D, y + (double) entityIn.getEyeHeight() + vec3d.y * 2.0D, z + vec3d.z * 2.0D).color(0, 0, 255, 255).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
    }


}
