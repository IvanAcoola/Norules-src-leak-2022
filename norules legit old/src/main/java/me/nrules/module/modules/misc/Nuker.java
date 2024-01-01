package me.nrules.module.modules.misc;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Nuker extends Module {
    public Nuker() {
        super(Nuker.piska2() + Nuker.piska3() + Nuker.piska4() + Nuker.piska5(), Category.PLAYER);
        Main.settingsManager.rSetting(new Setting("Distance", this, 2, 1, 5, true));
    }

    private static String piska = "GOGA";
    private static String piska2 = "GHO";
    private static String piska3 = "UIOL";
    private static String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Nu");
    }

    public static String piska3() {
        return piska2.replace("GHO", "k");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "e");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "r");
    }

    private Block selected = null;
    private BlockPos currentPos = null;

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.player == null || mc.world == null)
            return;

        this.currentPos = this.getClosestBlock(false);

        if (this.currentPos != null) {
            if (this.canBreak(this.currentPos)) {
                float[] angle = Utils.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d(this.currentPos.getX() + 0.5f, this.currentPos.getY() + 0.5f, this.currentPos.getZ() + 0.5f));
                mc.player.rotationYawHead = angle[0];
                mc.player.renderYawOffset = angle[0];
                mc.playerController.onPlayerDamageBlock(this.currentPos, mc.player.getHorizontalFacing());
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }

    private boolean canBreak(BlockPos pos) {
        final IBlockState blockState = Minecraft.getMinecraft().world.getBlockState(pos);
        final Block block = blockState.getBlock();

        return block.getBlockHardness(blockState, Minecraft.getMinecraft().world, pos) != -1;
    }

    private BlockPos getClosestBlock(boolean selection) {
        final Minecraft mc = Minecraft.getMinecraft();

        BlockPos ret = null;

        float maxDist = (float) Main.settingsManager.getSettingByName("Distance").getValDouble();
        for (float x = maxDist; x >= -maxDist; x--) {
            for (float y = maxDist; y >= -maxDist; y--) {
                for (float z = maxDist; z >= -maxDist; z--) {
                    final BlockPos pos = new BlockPos(mc.player.posX + x, mc.player.posY + y, mc.player.posZ + z);
                    final double dist = mc.player.getDistance(pos.getX(), pos.getY(), pos.getZ());
                    if (dist <= maxDist && (mc.world.getBlockState(pos).getBlock() != Blocks.AIR && !(mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid)) && canBreak(pos)) {
                        if (selection) {
                            if ((this.selected == null) || !mc.world.getBlockState(pos).getBlock().equals(this.selected)) {
                                continue;
                            }
                        }
                        if (pos.getY() < mc.player.posY)
                            continue;

                        maxDist = (float) dist;
                        ret = pos;
                    }
                }
            }
        }
        return ret;
    }

}