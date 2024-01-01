package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class WallHack extends Module {
    public WallHack() {
        super(WallHack.piska2() + WallHack.piska3() + WallHack.piska4() + WallHack.piska5(), Category.RENDER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Wal");
    }

    public static String piska3() {
        return piska2.replace("GHO", "lHa");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "c");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "k");
    }

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent event) {
        if (mc.player == null || mc.world == null)
            return;

        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        RenderHelper.enableStandardItemLighting();

        for (Entity entity : mc.world.getLoadedEntityList()) {
            if ((entity == mc.getRenderViewEntity() && mc.gameSettings.thirdPersonView == 0)) {
                continue;
            }

            mc.entityRenderer.disableLightmap();
            mc.getRenderManager().renderEntityStatic(entity, event.getPartialTicks(), false);
            mc.entityRenderer.enableLightmap();
        }

    }
}

