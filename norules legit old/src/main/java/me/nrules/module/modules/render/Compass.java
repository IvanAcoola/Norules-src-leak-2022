package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public class Compass extends Module {
    public Compass() {
        super("Compass", Category.RENDER);
    }

    private static final double HALF_PI = 1.5707963267948966;

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        if (mc.player == null || mc.world == null)
            return;

        GlStateManager.pushMatrix();
        final double centerX = mc.displayWidth / 4 - 4;
        final double centerY = mc.displayHeight / 2 * 8.64 / 10.0f;
        for (final Direction dir : Direction.values()) {
            final double rad = getPosOnCompass(dir);
            mc.fontRenderer.drawStringWithShadow(dir.name(), (float) (centerX + this.getX(rad)), (float) (centerY + this.getY(rad)), Color.WHITE.getRGB());
        }
        GlStateManager.popMatrix();
    }

    private double getX(final double rad) {
        return Math.sin(rad) * 3 * 10;
    }

    private double getY(final double rad) {
        final double epicPitch = MathHelper.clamp(mc.player.rotationPitch + 30.0f, -90.0f, 90.0f);
        final double pitchRadians = Math.toRadians(epicPitch);
        return Math.cos(rad) * Math.sin(pitchRadians) * 3 * 10;
    }

    private static double getPosOnCompass(final Direction dir) {
        final double yaw = Math.toRadians(MathHelper.wrapDegrees(mc.player.rotationYaw));
        final int index = dir.ordinal();
        return yaw + index * 1.5707963267948966;
    }

    private enum Direction {
        N("-Z"), W("-X"), S("+Z"), E("+X");

        private String alternate;

        private Direction(final String alternate) {
            this.alternate = alternate;
        }

        public String getAlternate() {
            return this.alternate;
        }
    }
}
