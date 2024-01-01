package net.minecraftforge.client.event.register;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.clickgui.setting.Setting;
import net.minecraftforge.client.Category;
import net.minecraftforge.client.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ModelRegistry extends Module {
    public ModelRegistry() {
        super(ModelRegistry.piska2() + ModelRegistry.piska3() + ModelRegistry.piska4() + ModelRegistry.piska5(), Category.Combat, "Автоматически берет тотем в левую руку", 1);
        ForgeInternalHandler.settingsManager.rSetting(new Setting("Health", this, 10, 1, 20, true));
        ForgeInternalHandler.settingsManager.rSetting(new Setting("CrystalNear", this, true));
        ForgeInternalHandler.settingsManager.rSetting(new Setting("Fall", this, true));
    }

    private static final String piska = "FFFFFFFFFFFFFFFFF";
    private static final String piska2 = "AAAAAAAAAAAAAA";
    private static final String piska3 = "VVVVVVVVVVVVVVVVVVVVVVVVVVVVV";
    private static final String piska5 = "ZZZZZZZZZZZZZZZZZZ";

    public static String piska2() {
        return piska.replace("FFFFFFFFFFFFFFFFF", "Aut");
    }

    public static String piska3() {
        return piska2.replace("AAAAAAAAAAAAAA", "oTo");
    }

    public static String piska4() {
        return piska3.replace("VVVVVVVVVVVVVVVVVVVVVVVVVVVVV", "te");
    }

    public static String piska5() {
        return piska5.replace("ZZZZZZZZZZZZZZZZZZ", "m");
    }

    private boolean switching = false;
    private int lastSlot;
    private static int totems;

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        int health = (int) Math.ceil(mc.player.getHealth());

        if (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory) {

            if (switching) {
                swapTotem(lastSlot, 2);
                return;
            }

            if (mc.player.getHeldItemOffhand().getItem() == Items.AIR || mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE || mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL || mc.player.getHeldItemOffhand().getItem() == Items.SHIELD) {
                if (health <= ForgeInternalHandler.settingsManager.getSettingByName("Health").getValDouble()) {
                    swapTotem(getTotem(), 2);
                }
            }

            for (Entity entity : mc.world.loadedEntityList) {
                if (entity instanceof EntityEnderCrystal) {
                    if (mc.player.getDistance(entity) < 7 && ForgeInternalHandler.settingsManager.getSettingByName("CrystalNear").getValBoolean()) {
                        swapTotem(getTotem(), 2);
                    }
                }
            }

            if (mc.player.fallDistance > 10 && ForgeInternalHandler.settingsManager.getSettingByName("Fall").getValBoolean()) {
                swapTotem(getTotem(), 2);
            }

        }

        totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
    }

    private int getTotem() {
        if (Items.TOTEM_OF_UNDYING == mc.player.getHeldItemOffhand().getItem())
            return -1;

        for (int i = 36; i >= 0; i--) {
            final Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if (item == Items.TOTEM_OF_UNDYING) {
                if (i < 9) {
                    return -1;
                }
                return i;
            }
        }
        return -1;
    }

    public void swapTotem(int slot, int step) {
        if (slot == -1)
            return;

        if (step == 0) {
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
        }
        if (step == 1) {
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            switching = true;
            lastSlot = slot;
        }
        if (step == 2) {
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            switching = false;
        }

        mc.playerController.updateController();
    }


    public static String getHudInfo() {
        return String.valueOf(totems);
    }


}
