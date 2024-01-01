package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Tracers extends Module {
    public Tracers() {
        super(Tracers.piska2() + Tracers.piska3() + Tracers.piska4() + Tracers.piska5(), Category.RENDER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Tra");
    }

    public static String piska3() {
        return piska2.replace("GHO", "ce");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "r");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "s");
    }

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        mc.world.loadedEntityList.stream().forEach(entity -> {
            if (entity != null && entity != mc.player && entity instanceof EntityPlayer) {
                try {
                    drawTracer(entity, event.getPartialTicks());
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    private void drawTracer(Entity entity, float f) {
        double d = mc.getRenderManager().viewerPosX;
        double d2 = mc.getRenderManager().viewerPosY;
        double d3 = mc.getRenderManager().viewerPosZ;
        double d4 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * f - d;
        double d5 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * f + (entity.height / 2.0f) - d2 + 0.3;
        double d6 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * f - d3;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2896);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(255, 255, 255, 255);
        Vec3d vec3d = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float) Math.toRadians(mc.player.rotationPitch)).rotateYaw(-(float) Math.toRadians(mc.player.rotationYaw));
        GL11.glBegin(1);
        GL11.glVertex3d(vec3d.x, (mc.player.getEyeHeight() + vec3d.y), vec3d.z);
        GL11.glVertex3d(d4, d5, d6);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GL11.glDepthMask(true);
    }
}
