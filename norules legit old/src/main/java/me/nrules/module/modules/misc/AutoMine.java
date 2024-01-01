package me.nrules.module.modules.misc;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.misc.AutoMine;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class AutoMine extends Module {
    public AutoMine() {
        super(AutoMine.piska2() + AutoMine.piska3() + AutoMine.piska4() + AutoMine.piska5(), Category.PLAYER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "A");
    }

    public static String piska3() {
        return piska2.replace("GHO", "u");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "toMi");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ne");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        int key = mc.gameSettings.keyBindAttack.getKeyCode();
        KeyBinding.setKeyBindState(key, true);
        if (Mouse.isButtonDown(0)) {
            if (mc.objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK) {
                return;
            }

            int key1 = mc.gameSettings.keyBindAttack.getKeyCode();
            KeyBinding.setKeyBindState(key1, false);
        }
    }

    @Override
    public void onDisable() {
        int key = mc.gameSettings.keyBindAttack.getKeyCode();
        KeyBinding.setKeyBindState(key, false);
        super.onDisable();
    }
}