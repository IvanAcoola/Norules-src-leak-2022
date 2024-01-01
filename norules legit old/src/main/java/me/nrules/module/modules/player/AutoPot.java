package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;
import java.util.Objects;

public class AutoPot extends Module {
    public AutoPot() {
        super(AutoPot.piska2() + AutoPot.piska3() + AutoPot.piska4() + AutoPot.piska5(), Category.PLAYER);
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
        return piska3.replace("UIOL", "toPo");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "tion");
    }

    private ItemStack held;

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        mc.player.rotationYaw += 0.0001;

        if (held == null) {
            held = mc.player.getHeldItemMainhand();
        }

        if (timerHelper.hasReached(250) && this.isPotionOnHotBar() && mc.player.moveForward > 0) {
            timerHelper.reset();

            if (!mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionById(1)))) {
                onSpeed();
            }
            if (!mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionById(5)))) {
                onStrenght();
            }
            if (!mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionById(12)))) {
                onFireRes();
            }
        }
    }


    private void onStrenght() {
        int potion = getPotionSlotStrength();
        mc.player.connection.sendPacket(new CPacketHeldItemChange(potion));
        mc.playerController.updateController();
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        mc.playerController.updateController();
        mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));

    }

    private void onSpeed() {
        int potion = getPotionSlotSpeed();
        mc.player.connection.sendPacket(new CPacketHeldItemChange(potion));
        mc.playerController.updateController();
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        mc.playerController.updateController();
        mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
    }

    private void onFireRes() {
        int potion = getPotionSlotFireS();
        mc.player.connection.sendPacket(new CPacketHeldItemChange(potion));
        mc.playerController.updateController();
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        mc.playerController.updateController();
        mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));

    }

    // try to find STRENGTH on hotbar player.
    public int getPotionSlotStrength() {
        int i = 0;
        while (i < 9) {
            if (this.isStackPotionStreng(mc.player.inventory.getStackInSlot(i), i)) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    // try to find FIRE RESISTANCE on hotbar player.
    public int getPotionSlotFireS() {
        int i = 0;
        while (i < 9) {
            if (this.isStackPotionFireS(mc.player.inventory.getStackInSlot(i), i)) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    // try to find SPEED on hotbar player.
    public int getPotionSlotSpeed() {
        int i = 0;
        while (i < 9) {
            if (this.isStackPotionSpeed(mc.player.inventory.getStackInSlot(i), i)) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    // check POTIONS on hotbar player.
    private boolean isPotionOnHotBar() {
        int i = 0;
        while (i < 9) {
            if (this.isStackPotionStreng(mc.player.inventory.getStackInSlot(i), i)) {
                return true;
            }
            if (this.isStackPotionSpeed(mc.player.inventory.getStackInSlot(i), i)) {
                return true;
            }
            if (this.isStackPotionFireS(mc.player.inventory.getStackInSlot(i), i)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    // check SPEED on hotbar player.
    private boolean isStackPotionSpeed(ItemStack stack, int index) {
        if (stack == null) {
            return false;
        }
        Item item = stack.getItem();
        if (item == Items.SPLASH_POTION) {
            for (PotionEffect effect : PotionUtils.getEffectsFromStack(stack)) {
                if (effect.getPotion() == Potion.getPotionById(1))
                    return true;
            }
        }
        return false;
    }

    // check FIRE RESISTANCE on hotbar player.
    private boolean isStackPotionFireS(ItemStack stack, int index) {
        if (stack == null) {
            return false;
        }
        Item item = stack.getItem();
        if (item == Items.SPLASH_POTION) {
            for (PotionEffect effect : PotionUtils.getEffectsFromStack(stack)) {
                if (effect.getPotion() == Potion.getPotionById(12))
                    return true;
            }
        }
        return false;
    }

    // check STRENGTH on hotbar player.
    private boolean isStackPotionStreng(ItemStack stack, int index) {
        if (stack == null) {
            return false;
        }
        Item item = stack.getItem();
        if (item == Items.SPLASH_POTION) {
            for (PotionEffect effect : PotionUtils.getEffectsFromStack(stack)) {
                if (effect.getPotion() == Potion.getPotionById(5))
                    return true;
            }
        }
        return false;
    }

    @Override
    public synchronized boolean onPacketSent(Packet<?> packet) {
        if (packet instanceof CPacketPlayer) {
            if (this.isPotionOnHotBar() && !mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionById(1))) && !mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionById(5))) && !mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionById(12)))) {
                try {
                    Field pitch = CPacketPlayer.class.getDeclaredFields()[4];
                    pitch.setAccessible(true);
                    pitch.setFloat(packet, 90);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
