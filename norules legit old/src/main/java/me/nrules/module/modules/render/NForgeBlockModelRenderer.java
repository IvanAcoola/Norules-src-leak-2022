package me.nrules.module.modules.render;

import me.nrules.Main;
import me.nrules.util.BlocksUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.pipeline.ForgeBlockModelRenderer;

import java.util.Collections;

public class NForgeBlockModelRenderer extends ForgeBlockModelRenderer {
    public NForgeBlockModelRenderer(BlockColors colors) {
        super(colors);
    }

    @Override
    public boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, BufferBuilder buffer, boolean checkSides) {
        if (Main.moduleManager.getModule(Xray.class).isToggled()) {
            if (!isVisible(blockStateIn.getBlock()))
                return false;
            blockStateIn.getBlock().setLightLevel(100.0F);
            checkSides = false;
        }
        return super.renderModel(blockAccessIn, modelIn, blockStateIn, blockPosIn, buffer, checkSides);
    }


    private boolean isVisible(Block block) {
        String name = BlocksUtils.getName(block);
        int index = Collections.binarySearch(Xray.blocks.getBlockNames(), name);
        return (index >= 0);
    }
}