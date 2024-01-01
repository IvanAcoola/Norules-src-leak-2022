package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.EntityUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoFarm extends Module {
    public AutoFarm() {
        super(AutoFarm.piska2() + AutoFarm.piska3() + AutoFarm.piska4() + AutoFarm.piska5(), Category.PLAYER);

    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Aut");
    }

    public static String piska3() {
        return piska2.replace("GHO", "oFa");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "r");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "m");
    }

    private BlockPos currentBlockPos;

    @SubscribeEvent
    public void preTick(LivingEvent.LivingUpdateEvent e) {
        if (mc.player == null && mc.world == null)
            return;

        if (isToggled()) {
            double r = 3.5f;
            for (double y = mc.player.posY + r; y > mc.player.posY - r; y -= 1.0D) {
                for (double x = mc.player.posX - r; x < mc.player.posX + r; x += 1.0D) {
                    for (double z = mc.player.posZ - r; z < mc.player.posZ + r; z += 1.0D) {
                        BlockPos blockPos = new BlockPos(x, y, z);
                        if (this.isBlockValid(blockPos, mc)) {
                            if (this.currentBlockPos == null) {
                                this.currentBlockPos = blockPos;
                            }
                        }
                    }
                }
            }
        }

        if (this.currentBlockPos != null) {
            this.doFarming(mc);
            this.currentBlockPos = null;
        }
    }

    private void doFarming(final Minecraft mc) {
        mc.playerController.processRightClickBlock(mc.player, mc.world, currentBlockPos, EntityUtils.getFacingDirectionToPosition(currentBlockPos), new Vec3d(currentBlockPos.getX() / 2F, currentBlockPos.getY() / 2F, currentBlockPos.getZ() / 2F), EnumHand.MAIN_HAND);
    }

    private boolean isBlockValid(BlockPos position, final Minecraft mc) {
        boolean temp = false;
        Block block = mc.world.getBlockState(position).getBlock();
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemSeeds || mc.player.getHeldItemMainhand().getItem() instanceof ItemSeedFood) {
            if (block instanceof BlockFarmland) {
                if (mc.world.getBlockState(position.up()).getBlock() == Blocks.AIR) {
                    temp = true;
                }
            }
        }

        return temp && mc.player.getDistance(position.getX(), position.getY(), position.getZ()) <= 3.5f;
    }
}