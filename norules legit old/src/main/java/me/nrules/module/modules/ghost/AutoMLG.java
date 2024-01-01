package me.nrules.module.modules.ghost;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;


public class AutoMLG extends Module {
    public AutoMLG() {
        super(AutoMLG.piska2() + AutoMLG.piska3() + AutoMLG.piska4() + AutoMLG.piska5(), Category.PLAYER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Au");
    }

    public static String piska3() {
        return piska2.replace("GHO", "to");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "ML");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "G");
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent event) {
        if (mc.player != null && mc.world != null) {
            mc.player.rotationYaw += 0.0001f;
        }
    }

    @SubscribeEvent
    public void onUpdate(EntityViewRenderEvent.CameraSetup event) {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.player.fallDistance > 2.8F) {
            for (int i = 0; i < 9; i++) {
                ItemStack itemStack = mc.player.inventory.getStackInSlot(i);

                if (itemStack.getItem() == Items.WATER_BUCKET) {
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(i));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));

                }
            }
        }
    }

    @Override
    public boolean onPacketSent(Packet<?> packet) {
        if (mc.player != null || mc.world != null) {
            try {
                Field pitchRot = CPacketPlayer.class.getDeclaredFields()[4];
                pitchRot.setAccessible(true);
                pitchRot.setFloat(packet, 90);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


}
