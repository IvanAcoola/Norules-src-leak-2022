package me.nrules.util;

import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.*;

import me.nrules.FriendManager;

import java.util.List;

public class EntityUtils
{
    public static boolean isMoving(EntityLivingBase entity) {
        return entity.moveForward != 0 || entity.moveStrafing != 0;
    }

    public static EntityPlayer getTarget(final float range, float wallRange) {
        EntityPlayer currentTarget = null;
        for (int size = mc.world.playerEntities.size(), i = 0; i < size; ++i) {
            final EntityPlayer player = mc.world.playerEntities.get(i);
            if (!isntValid(player, range, wallRange)) {
                if (currentTarget == null) currentTarget = player;
                else if (mc.player.getDistanceSq(player) < mc.player.getDistanceSq(currentTarget)) currentTarget = player;
            }
        }
        return currentTarget;
    }

        public static EntityPlayer getTarget(final float range) {
        return getTarget(range, range);
    }

    public static boolean isntValid(final EntityPlayer entity, final double range, double wallRange) {
        return (mc.player.getDistance(entity) > (mc.player.canEntityBeSeen(entity) ? range : wallRange)) || entity == mc.player || entity.getHealth() <= 0.0f || entity.isDead || FriendManager.isFriend(entity.getName());
    }

    public static Minecraft mc = Minecraft.getMinecraft();


    public static EnumFacing getFacingDirectionToPosition(BlockPos position) {
        EnumFacing direction = null;
        if (!mc.world.getBlockState(position.add(0, 1, 0)).isFullBlock()) {
            direction = EnumFacing.UP;
        } else if (!mc.world.getBlockState(position.add(0, -1, 0)).isFullBlock()) {
            direction = EnumFacing.DOWN;
        } else if (!mc.world.getBlockState(position.add(1, 0, 0)).isFullBlock()) {
            direction = EnumFacing.EAST;
        } else if (!mc.world.getBlockState(position.add(-1, 0, 0)).isFullBlock()) {
            direction = EnumFacing.WEST;
        } else if (!mc.world.getBlockState(position.add(0, 0, 1)).isFullBlock()) {
            direction = EnumFacing.SOUTH;
        } else if (!mc.world.getBlockState(position.add(0, 0, 1)).isFullBlock()) {
            direction = EnumFacing.NORTH;
        }
        return direction;
    }

    public static boolean isFluid(double y) {
        return BlockUtil.getBlock(new BlockPos(mc.player.posX, mc.player.posY + y,
                mc.player.posZ)) instanceof BlockLiquid;
    }

    public static float getSensitivityMultiplier() {
        float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        return (f * f * f * 8.0F) * 0.15F;
    }

    public static BlockPos GetLocalPlayerPosFloored()
    {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }
    public static ItemStack item() {
        return mc.player.getHeldItemMainhand();
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
                final float var12 = var11.getCollisionBorderSize();

                AxisAlignedBB var13 = var11.getEntityBoundingBox().expand(var12, var12, var12);
                var13 = var13.expand(expand, expand, expand);
                final RayTraceResult var14 = var13.calculateIntercept(var3, var5);
                if (var13.contains(var3)) {
                    if (0.0 < var9 || var9 == 0.0) {
                        entity = var11;
                        var6 = ((var14 == null) ? var3 : var14.hitVec);
                        var9 = 0.0;
                    }
                }
                else if (var14 != null) {
                    final double var15 = var3.distanceTo(var14.hitVec);
                    if (var15 < var9 || var9 == 0.0) {
                        boolean canRiderInteract = false;
                        if (var11 == var2.getRidingEntity() && !canRiderInteract) {
                            if (var9 == 0.0) {
                                entity = var11;
                                var6 = var14.hitVec;
                            }
                        }
                        else {
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
        return new Object[] { entity, var6 };
    }

    public static float getDirection() {
        return getDirection(mc.player);
    }

    public static float getDirection(EntityLivingBase entity) {
        float moveYaw = entity.rotationYaw;
        if (entity.moveForward < 0.0f)
            moveYaw += 180.0f;

        float forward = 1.0f;
        if (entity.moveForward < 0.0f)
            forward = -0.5f;

        if (entity.moveForward > 0.0f)
            forward = 0.5f;

        if (entity.moveStrafing > 0.0f) {
            moveYaw -= 90.0f * forward;
        }
        if (entity.moveStrafing < 0.0f) {
            moveYaw += 90.0f * forward;
        }
        return (float) Math.toRadians(moveYaw);
    }


    public static void strafe(double speed) {
        strafe(getDirection(), speed);
    }

    public static void strafe(float targetYaw, double speed) {

        double x = -Math.sin(targetYaw) * speed;
        double z = Math.cos(targetYaw) * speed;
        mc.player.motionX = x;
        mc.player.motionZ = z;
    }

}