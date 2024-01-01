package me.nrules.module.modules.combat;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoTotems extends Module {
    public AutoTotems() {
        super(AutoTotems.piska2() + AutoTotems.piska3() + AutoTotems.piska4() + AutoTotems.piska5(), Category.COMBAT);
        Main.settingsManager.rSetting(new Setting("Health", this, 10, 1, 20, true));
        Main.settingsManager.rSetting(new Setting("CrystalNear", this, true));
        Main.settingsManager.rSetting(new Setting("Fall", this, true));
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
        return piska3.replace("UIOL", "Tote");
    }
    public static String piska5() {
        return piska5.replace("NGHO", "ms");
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
                if (health <= Main.settingsManager.getSettingByName("Health").getValDouble()) {
                    swapTotem(getTotem(), 2);
                }
            }

            for (Entity entity : mc.world.loadedEntityList) {
                if (entity instanceof EntityEnderCrystal) {
                    if (mc.player.getDistance(entity) < 7 && Main.settingsManager.getSettingByName("CrystalNear").getValBoolean()) {
                        swapTotem(getTotem(), 2);
                    }
                }
            }

            if (mc.player.fallDistance > 10 && Main.settingsManager.getSettingByName("Fall").getValBoolean()) {
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