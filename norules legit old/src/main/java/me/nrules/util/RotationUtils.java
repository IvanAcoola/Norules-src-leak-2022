package me.nrules.util;

import me.nrules.helper.GCDFix;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class RotationUtils {

    public static float[] getNeededRotations(final Entity entityLivingBase) {
        final double d = entityLivingBase.posX - Minecraft.getMinecraft().player.posX;
        final double d2 = entityLivingBase.posZ - Minecraft.getMinecraft().player.posZ;
        final double d3 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (Minecraft.getMinecraft().player.getEntityBoundingBox().minY + (Minecraft.getMinecraft().player.getEntityBoundingBox().maxY - Minecraft.getMinecraft().player.getEntityBoundingBox().minY));
        final double d4 = MathHelper.sqrt(d * d + d2 * d2);
        final float f = (float)(MathHelper.atan2(d2, d) * 180.0 / 3.141592653589793) - 90.0f;
        final float f2 = (float)(-(MathHelper.atan2(d3, d4) * 180.0 / 3.141592653589793));
        return new float[] { f, f2 };
    }

    public static Minecraft mc = Minecraft.getMinecraft();

    public static float[] lookAtRandomed(Entity entityIn) {
        double diffX = entityIn.posX - mc.player.posX;
        double diffZ = entityIn.posZ - mc.player.posZ;
        double diffY;

        if (entityIn instanceof EntityLivingBase) {
            diffY = entityIn.posY + entityIn.getEyeHeight()
                    - (mc.player.posY + mc.player.getEyeHeight()) - 0.4;
        } else {
            diffY = (entityIn.getEntityBoundingBox().minY + entityIn.getEntityBoundingBox().maxY) / 2.0D
                    - (mc.player.posY + mc.player.getEyeHeight());
        }

        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) ((float) (((Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f)) + MathUtilsNR.random(-2, 2));
        float pitch = (float) ((float) (-(Math.atan2(diffY, dist) * 180.0 / Math.PI)) + MathUtilsNR.random(-2, 2));
        yaw = mc.player.rotationYaw
                + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
        pitch = mc.player.rotationPitch
                + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));

        return new float[] { yaw, pitch };
    }


    public static float[] getRatations(Entity e) {
        double diffX = e.posX - mc.player.posX;
        double diffZ = e.posZ - mc.player.posZ;
        double diffY;

        if (e instanceof EntityLivingBase) {
            diffY = e.posY + e.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight()) - 0.42;
        } else {
            diffY = (e.getEntityBoundingBox().minY + e.getEntityBoundingBox().maxY) / 2.0D - (mc.player.posY + mc.player.getEyeHeight());
        }

        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) (((Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f)) + RandomUtils.nextFloat(-2, 2);
        float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0 / Math.PI)) + RandomUtils.nextFloat(-2, 2);
        yaw = mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
        pitch = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -90F, 90F);
        return new float[]{yaw, pitch};
    }

    public static float getAngleChange(EntityLivingBase entityIn) {
        float yaw = getNeededRotations(entityIn)[0];
        float pitch = getNeededRotations(entityIn)[1];
        float playerYaw = mc.player.rotationYaw;
        float playerPitch = mc.player.rotationPitch;
        if (playerYaw < 0)
            playerYaw += 360;
        if (playerPitch < 0)
            playerPitch += 360;
        if (yaw < 0)
            yaw += 360;
        if (pitch < 0)
            pitch += 360;
        float yawChange = Math.max(playerYaw, yaw) - Math.min(playerYaw, yaw);
        float pitchChange = Math.max(playerPitch, pitch) - Math.min(playerPitch, pitch);
        return yawChange + pitchChange;
    }


    public static float[] getNeededRotations(EntityLivingBase entityIn) {
        double d0 = entityIn.posX - mc.player.posX;
        double d1 = entityIn.posZ - mc.player.posZ;
        double d2 = entityIn.posY + entityIn.getEyeHeight() - (mc.player.getEntityBoundingBox().minY + mc.player.getEyeHeight());

        double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1);
        float f = (float) (MathHelper.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float) (-(MathHelper.atan2(d2, d3) * 180.0D / Math.PI));
        return new float[]{f, f1};
    }

    public static float[] getRotations(EntityLivingBase entityIn, float speed) {
        float yaw = updateRotation(mc.player.rotationYaw,
                getNeededRotations(entityIn)[0],
                speed);
        float pitch = updateRotation(mc.player.rotationPitch, getNeededRotations(entityIn)[1],
                speed);
        return new float[]{yaw, pitch};
    }

    public static float updateRotation(float currentRotation, float intendedRotation, float increment) {
        float f = MathHelper.wrapDegrees(intendedRotation - currentRotation);

        if (f > increment)
            f = increment;

        if (f < -increment)
            f = -increment;

        return currentRotation + f;
    }

    public static float[] getNeededRotations1(EntityLivingBase entityIn) {
        double d0 = entityIn.posX - mc.player.posX;
        double d1 = entityIn.posZ - mc.player.posZ;
        double d2 = entityIn.posY + entityIn.getEyeHeight() - (mc.player.getEntityBoundingBox().minY + (mc.player.getEntityBoundingBox().maxY - mc.player.getEntityBoundingBox().minY));
        double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1);
        float f = (float) (MathHelper.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float) (-(MathHelper.atan2(d2, d3) * 180.0D / Math.PI));
        return new float[]{f, f1};
    }



}
