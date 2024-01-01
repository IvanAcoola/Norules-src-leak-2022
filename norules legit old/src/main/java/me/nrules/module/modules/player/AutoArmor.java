package me.nrules.module.modules.player;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoArmor extends Module {
    public AutoArmor() {
        super(AutoArmor.piska2() + AutoArmor.piska3() + AutoArmor.piska4() + AutoArmor.piska5(), Category.PLAYER);
        Main.settingsManager.rSetting(new Setting("Inventory", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Aut");
    }

    public static String piska3() {
        return piska2.replace("GHO", "oAr");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "mo");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "r");
    }

    private int timer;
    private final Item NULL_ITEM = Item.getItemFromBlock(Blocks.AIR);

    @Override
    public void onEnable() {
        timer = 0;
        super.onEnable();
    }


    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (Main.settingsManager.getSettingByName("Inventory").getValBoolean()) {
            if (mc.currentScreen != null) {
                equip();
            }

        }

        if (!Main.settingsManager.getSettingByName("Inventory").getValBoolean()) {
            if (mc.currentScreen == null) {
                equip();
            }
        }
    }

    private void equip() {
        if (timer > 0) {
            timer--;
            return;
        }

        if (mc.currentScreen instanceof GuiContainer && !(mc.currentScreen instanceof InventoryEffectRenderer))
            return;

        int[] bestArmorSlots = new int[4];
        int[] bestArmorValues = new int[4];

        for (int armorType = 0; armorType < 4; armorType++) {
            ItemStack oldArmor = mc.player.inventory.armorItemInSlot(armorType);
            if (oldArmor != null && oldArmor.getItem() instanceof ItemArmor)
                bestArmorValues[armorType] = ((ItemArmor) oldArmor.getItem()).damageReduceAmount;

            bestArmorSlots[armorType] = -1;
        }

        for (int slot = 0; slot < 36; slot++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(slot);
            if (stack == null || !(stack.getItem() instanceof ItemArmor))
                continue;

            ItemArmor armor = (ItemArmor) stack.getItem();
            int armorType = this.getArmorType(armor);
            int armorValue = armor.damageReduceAmount;

            if (armorValue > bestArmorValues[armorType]) {
                bestArmorSlots[armorType] = slot;
                bestArmorValues[armorType] = armorValue;
            }
        }

        for (int armorType = 0; armorType < 4; armorType++) {
            int slot = bestArmorSlots[armorType];
            if (slot == -1)
                continue;

            ItemStack oldArmor = mc.player.inventory.armorItemInSlot(armorType);
            if (oldArmor == null || !this.isEmptySlot(oldArmor) || mc.player.inventory.getFirstEmptyStack() != -1) {
                if (slot < 9)
                    slot += 36;

                mc.playerController.windowClick(0, 8 - armorType, 0, ClickType.QUICK_MOVE, mc.player);
                mc.playerController.windowClick(0, slot, 0, ClickType.QUICK_MOVE, mc.player);

                break;
            }
        }

        timer = 4;
    }

    public int getArmorType(ItemArmor armor) {
        return armor.armorType.ordinal() - 2;
    }

    public boolean isEmptySlot(ItemStack slot) {
        return slot.getItem() == NULL_ITEM;
    }

}
