package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.render.NoOverlay;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

public class NoOverlay extends Module {
    public NoOverlay() {
        super(NoOverlay.piska2() + NoOverlay.piska3() + NoOverlay.piska4() + NoOverlay.piska5(), Category.RENDER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "NoO");
    }

    public static String piska3() {
        return piska2.replace("GHO", "ver");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "la");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "y");
    }

    @SubscribeEvent
    public void onUpdate(EntityViewRenderEvent.FogDensity event) {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.effectRenderer.equals(EnumParticleTypes.TOTEM)) {
            mc.effectRenderer.clearEffects(mc.world);
        }

        if (mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionById(15)))) {
            mc.player.removePotionEffect(Objects.requireNonNull(Potion.getPotionById(15)));
        }

        if (mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionById(9)))) {
            mc.player.removePotionEffect(Objects.requireNonNull(Potion.getPotionById(9)));
        }

        mc.entityRenderer.displayItemActivation(null);

        if (mc.world != null) {
            mc.world.setRainStrength(0.0f);
        }

        event.setDensity(0);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockOverlayEvent event) {
        event.setCanceled(true);
    }
}
