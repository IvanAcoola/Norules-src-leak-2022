package me.nrules.module.modules.misc;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketCloseWindow;

import java.lang.reflect.Field;

public class XCarry extends Module {
    public XCarry() {
        super(XCarry.piska2() + XCarry.piska3() + XCarry.piska4() + XCarry.piska5(), Category.MISC);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "X");
    }

    public static String piska3() {
        return piska2.replace("GHO", "Car");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "r");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "y");
    }

    public void onDisable() {
        if (mc.world != null) {
            mc.player.connection.sendPacket(new CPacketCloseWindow(mc.player.inventoryContainer.windowId));
        }
        super.onDisable();
    }

    @Override
    public boolean onPacketSent(Packet<?> packet) {
        if (this.isToggled() && packet instanceof CPacketCloseWindow && mc.player != null && mc.world != null) {
            try {
                Field windowId = CPacketCloseWindow.class.getDeclaredField("field_149554_a");
                windowId.setAccessible(true);
                windowId.setInt(windowId, mc.player.inventoryContainer.windowId);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }
}
