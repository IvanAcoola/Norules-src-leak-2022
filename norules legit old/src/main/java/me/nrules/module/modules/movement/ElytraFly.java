package me.nrules.module.modules.movement;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.Utils;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ElytraFly extends Module {
    public ElytraFly() {
        super(ElytraFly.piska2() + ElytraFly.piska3() + ElytraFly.piska4() + ElytraFly.piska5(), Category.MOVEMENT);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "E");
    }

    public static String piska3() {
        return piska2.replace("GHO", "lyT");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "raF");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ly");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.player.isElytraFlying()) {
            float f = Utils.getDirection();
            mc.player.motionX -= (MathHelper.sin(f) * 0.0069F);
            mc.player.motionY -= 0.00001F;
            mc.player.motionZ += (MathHelper.cos(f) * 0.0069F);

            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                mc.player.motionY += 0.2F;
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                mc.player.motionY -= 0.2F;
            }
        }
    }
}
