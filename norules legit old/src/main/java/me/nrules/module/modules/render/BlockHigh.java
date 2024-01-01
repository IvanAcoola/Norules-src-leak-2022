package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.ColorUtils;
import me.nrules.util.MathUtilsNR;
import me.nrules.util.RenderUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockHigh extends Module {
    public BlockHigh() {
        super(BlockHigh.piska2() + BlockHigh.piska3() + BlockHigh.piska4() + BlockHigh.piska5(), Category.RENDER);

    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "RGR";
    private static final String piska5 = "FEW";

    public static String piska2() {
        return piska.replace("GOGA", "Block");
    }

    public static String piska3() {
        return piska2.replace("GHO", "Hi");
    }

    public static String piska4() {
        return piska3.replace("RGR", "gh");
    }

    public static String piska5() {
        return piska5.replace("FEW", "light");
    }

    @SubscribeEvent
    public void render3D(RenderWorldLastEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.objectMouseOver != null) {
            final RayTraceResult ray = mc.objectMouseOver;
            if (ray.typeOfHit == RayTraceResult.Type.BLOCK) {
                this.drawHighlight(ray, mc);
            }
        }
    }

    public void drawHighlight(final RayTraceResult ray, final Minecraft mc) {
        final BlockPos blockpos = ray.getBlockPos();
        final IBlockState iblockstate = mc.world.getBlockState(blockpos);
        if (iblockstate.getMaterial() != Material.AIR && mc.world.getWorldBorder().contains(blockpos)) {
            float currentDamage = 0.0f;

            GlStateManager.color(1.0f, 1.0f, 1.0f);
            RenderUtils.enable3D();
            final Vec3d interp = MathUtilsNR.interpolateEntity(mc.player);
            final AxisAlignedBB bb = iblockstate.getSelectedBoundingBox(mc.world, blockpos).shrink(currentDamage / 2.0f).offset(-interp.x, -interp.y, -interp.z);
            final int color = ColorUtils.changeAlpha(0xf2ebeb, 200);
            RenderUtils.drawFilledBox(bb, ColorUtils.changeAlpha(color, 200 / 2));
            RenderUtils.drawBoundingBox(bb, 0.1f, color);
            RenderUtils.disable3D();
        }
    }

}