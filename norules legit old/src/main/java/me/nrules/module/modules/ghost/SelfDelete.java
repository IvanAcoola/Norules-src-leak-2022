package me.nrules.module.modules.ghost;

import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.ModuleManager;
import me.nrules.module.modules.render.ClickGUI;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class SelfDelete extends Module {
    public SelfDelete() {
        super(SelfDelete.piska2() + SelfDelete.piska3() + SelfDelete.piska4() + SelfDelete.piska5(), Category.MISC);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "P");
    }

    public static String piska3() {
        return piska2.replace("GHO", "a");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "n");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ic");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        for (Module m : ModuleManager.getModules()) {
            m.setToggled(false);
            if (!m.getName().contains("ClickGUI")) {
                m.setKey(0);
            }
        }
    }
}
