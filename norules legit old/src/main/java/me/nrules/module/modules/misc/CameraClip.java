package me.nrules.module.modules.misc;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.event.EventRegister;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;

public class CameraClip extends Module {
    public CameraClip() {
        super("CameraClip", Category.RENDER);
        Main.settingsManager.rSetting(new Setting("ThirdView", this, 5, 1, 50, true));
    }

    @SubscribeEvent
    public void onRender(EntityViewRenderEvent.CameraSetup event) {
        orient((float) event.getRenderPartialTicks());
    }

    private void orient(float partialTicks) {
        float thirdDistance = 4;
        Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
        float f = entity.getEyeHeight();
        double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double) partialTicks;
        double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double) partialTicks + (double) f;
        double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double) partialTicks;

        if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isPlayerSleeping()) {
            f = (float) ((double) f + 1.0D);
            GlStateManager.translate(0.0F, 0.3F, 0.0F);

            if (!Minecraft.getMinecraft().gameSettings.debugCamEnable) {
                BlockPos blockpos = new BlockPos(entity);
                IBlockState iblockstate = Minecraft.getMinecraft().world.getBlockState(blockpos);
                net.minecraftforge.client.ForgeHooksClient.orientBedCamera(Minecraft.getMinecraft().world, blockpos, iblockstate, entity);

            }
        } else if (Minecraft.getMinecraft().gameSettings.thirdPersonView > 0) {
            double d3 =  (thirdDistance + (4.0F - thirdDistance + Main.settingsManager.getSettingByName("ThirdView").getValDouble()) - 5);

            if (Minecraft.getMinecraft().gameSettings.debugCamEnable) {
            } else {
                float f1 = entity.rotationYaw;
                float f2 = entity.rotationPitch;

                if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
                    f2 += 180.0F;
                }

                double d4 = (double) (-MathHelper.sin(f1 * 0.017453292F) * MathHelper.cos(f2 * 0.017453292F)) * d3;
                double d5 = (double) (MathHelper.cos(f1 * 0.017453292F) * MathHelper.cos(f2 * 0.017453292F)) * d3;
                double d6 = (double) (-MathHelper.sin(f2 * 0.017453292F)) * d3;

                for (int i = 0; i < 8; ++i) {
                    float f3 = (float) ((i & 1) * 2 - 1);
                    float f4 = (float) ((i >> 1 & 1) * 2 - 1);
                    float f5 = (float) ((i >> 2 & 1) * 2 - 1);
                    f3 = f3 * 0.1F;
                    f4 = f4 * 0.1F;
                    f5 = f5 * 0.1F;
                    RayTraceResult raytraceresult = Minecraft.getMinecraft().world.rayTraceBlocks(new Vec3d(d0 + (double) f3, d1 + (double) f4, d2 + (double) f5), new Vec3d(d0 - d4 + (double) f3 + (double) f5, d1 - d6 + (double) f4, d2 - d5 + (double) f5));

                    if (raytraceresult != null) {
                        double d7 = raytraceresult.hitVec.distanceTo(new Vec3d(d0, d1, d2));

                        if (!Main.moduleManager.getModuleByName("CameraClip").isToggled())

                            if (d7 < d3) {
                                d3 = d7;
                            }
                    }
                }

                GlStateManager.rotate(entity.rotationPitch - f2, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(entity.rotationYaw - f1, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(0.0F, 0.0F, (float) (-d3));
                GlStateManager.rotate(f1 - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(f2 - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
            }
        } else {

        }

        if (!Minecraft.getMinecraft().gameSettings.debugCamEnable) {
            if (entity instanceof EntityAnimal) {
            }
        }

        d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double) partialTicks;
        d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double) partialTicks + (double) f;
        d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double) partialTicks;
        try {
            Field cloudFog = EntityRenderer.class.getDeclaredFields()[28];
            cloudFog.setAccessible(true);
            cloudFog.set(Minecraft.getMinecraft().entityRenderer, Minecraft.getMinecraft().renderGlobal.hasCloudFog(d0, d1, d2, partialTicks));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
