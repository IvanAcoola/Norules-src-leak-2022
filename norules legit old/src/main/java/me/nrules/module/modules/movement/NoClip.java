package me.nrules.module.modules.movement;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;

public class NoClip extends Module {
    public NoClip() {
        super(NoClip.piska2() + NoClip.piska3() + NoClip.piska4() + NoClip.piska5(), Category.MOVEMENT);
        Main.settingsManager.rSetting(new Setting("AutoPerl", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "No");
    }

    public static String piska3() {
        return piska2.replace("GHO", "C");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "li");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "p");
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        mc.player.noClip = true;
        mc.player.fallDistance = 0;
        mc.player.onGround = true;

        mc.player.capabilities.isFlying = false;
        mc.player.motionX = 0;
        mc.player.motionY = 0;
        mc.player.motionZ = 0;

        float speed = 0.4f;
        mc.player.jumpMovementFactor = 0.05f;

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY += 0.2;
        }

        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY -= 0.2;
        }

    }

    public void onDisable() {
        mc.player.noClip = false;
        super.onDisable();
    }

    @Override
    public boolean onPacketSent(Packet<?> packet) {
        if (packet instanceof CPacketPlayer) {
            if (mc.player != null && mc.world != null && Main.settingsManager.getSettingByName("AutoPerl").getValBoolean()) {
                for (int j = 0; j < 1; j++) {
                    try {
                        Field pitch = CPacketPlayer.class.getDeclaredFields()[4];
                        pitch.setAccessible(true);
                        pitch.setFloat(packet, 85);

                        for (int i = 0; i < 9; i++) {
                            if (mc.player.rotationPitch == 85) {
                                ItemStack itemStack = mc.player.inventory.getStackInSlot(i);

                                if (itemStack.getItem() == Items.ENDER_PEARL) {
                                    mc.player.connection.sendPacket(new CPacketHeldItemChange(i));
                                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                                    mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
                                }
                            }
                        }

                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            }
        }
        return true;
    }
}
