package me.nrules.module.modules.combat;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FastBow extends Module {
    public FastBow() {
        super(FastBow.piska2() + FastBow.piska3() + FastBow.piska4() + FastBow.piska5(), Category.COMBAT);
        Main.settingsManager.rSetting(new Setting("Delay", this, 2.02, 0.1, 20, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Fa");
    }
    public static String piska3() {
        return piska2.replace("GHO", "st");
    }
    public static String piska4() {
        return piska3.replace("UIOL", "Bo");
    }
    public static String piska5() {
        return piska5.replace("NGHO", "w");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
            if (mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= Main.settingsManager.getSettingByName("Delay").getValDouble()) {
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
                mc.player.stopActiveHand();
            }
        }
    }

}
