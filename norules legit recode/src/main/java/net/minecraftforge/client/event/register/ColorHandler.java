package net.minecraftforge.client.event.register;

import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.clickgui.setting.Setting;
import net.minecraftforge.client.Category;
import net.minecraftforge.client.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ColorHandler extends Module {
    public ColorHandler() {
        super(ColorHandler.piska2() + ColorHandler.piska3() + ColorHandler.piska4() + ColorHandler.piska5(), Category.Combat, "Убирает задержку при выстреле из лука", 3);
        ForgeInternalHandler.settingsManager.rSetting(new Setting("Delay", this, 2.02, 0.1, 20, false));
    }

    private static final String piska = "FFFFFFFFFFFFFFFFF";
    private static final String piska2 = "AAAAAAAAAAAAAA";
    private static final String piska3 = "VVVVVVVVVVVVVVVVVVVVVVVVVVVVV";
    private static final String piska5 = "ZZZZZZZZZZZZZZZZZZ";

    public static String piska2() {
        return piska.replace("FFFFFFFFFFFFFFFFF", "Fas");
    }

    public static String piska3() {
        return piska2.replace("AAAAAAAAAAAAAA", "t");
    }

    public static String piska4() {
        return piska3.replace("VVVVVVVVVVVVVVVVVVVVVVVVVVVVV", "Bo");
    }

    public static String piska5() {
        return piska5.replace("ZZZZZZZZZZZZZZZZZZ", "w");
    }



    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
            if (mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= ForgeInternalHandler.settingsManager.getSettingByName("Delay").getValDouble()) {
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
                mc.player.stopActiveHand();
            }
        }
    }

}