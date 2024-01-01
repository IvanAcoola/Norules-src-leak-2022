package me.nrules.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class BlockUtil {
    public static Block getBlock(BlockPos pos) {
        return getState(pos).getBlock();
    }
    public static IBlockState getState(BlockPos blockPos) {
        return Minecraft.getMinecraft().world.getBlockState(blockPos);
    }

    public static float getHardness(BlockPos pos) {
		return getState(pos).getPlayerRelativeBlockHardness(Minecraft.getMinecraft().player, Minecraft.getMinecraft().world, pos);
	}
}
