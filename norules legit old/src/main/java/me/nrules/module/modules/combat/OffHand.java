package me.nrules.module.modules.combat;

import java.util.ArrayList;
import java.util.Arrays;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.nrules.AttackManager;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.*;
import me.nrules.util.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.*;

public class OffHand extends Module {
    private final Setting health = new Setting("Health", this, 11, 0, 20, true);

    private final Setting mode = new Setting("Mode", this, "Totem", new ArrayList<>(Arrays.asList("Totem", "Crystal", "Gapple", "Pearl", "Chorus", "Strength", "Shield")));
    private final Setting fallBackMode = new Setting("FallBackMode", this, "Crystal", new ArrayList<>(Arrays.asList("Totem", "Crystal", "Gapple", "Pearl", "Chorus", "Strength", "Shield")));
    private final Setting fallBackDistance = new Setting("FallBackDistance", this, 15, 0, 100, true);
    private final Setting totemOnElytra = new Setting("TotemOnElytra", this, true);
    private final Setting offhandGapOnSword = new Setting("GapOnSword", this, true);
    private final Setting hotbarFirst = new Setting("HotbarFirst", this, false);
    private final Setting useUpdateController = new Setting("Use UpdateController", this, true);
    
    public OffHand() {
        super("OffHand", Category.COMBAT);

        setmgr.rSetting(health);
        setmgr.rSetting(mode);
        setmgr.rSetting(fallBackMode);
        setmgr.rSetting(fallBackDistance);
        setmgr.rSetting(totemOnElytra);
        setmgr.rSetting(offhandGapOnSword);
        setmgr.rSetting(hotbarFirst);
        setmgr.rSetting(useUpdateController);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (mc.currentScreen != null && (!(mc.currentScreen instanceof GuiInventory))) return;

        if (!mc.player.getHeldItemMainhand().isEmpty()) {
            if (health.getValDouble() <= (mc.player.getHealth() + mc.player.getAbsorptionAmount()) && mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && offhandGapOnSword.getValBoolean()) {
                switchOffHandIfNeed("Gap");
                return;
            }
        }

        if (health.getValDouble() > (mc.player.getHealth() + mc.player.getAbsorptionAmount()) || mode.getValString().equalsIgnoreCase("Totem") || (totemOnElytra.getValBoolean() && mc.player.isElytraFlying()) || (mc.player.fallDistance >= fallBackDistance.getValDouble() && !mc.player.isElytraFlying()) || noNearbyPlayers()) {
            switchOffHandIfNeed("Totem");
            return;
        }
        switchOffHandIfNeed(mode.getValString());
    }

    private void switchOffHandIfNeed(String mode) {
        Item item = getItemFromModeVal(mode);

        if (mc.player.getHeldItemOffhand().getItem() != item) {
            int slot = hotbarFirst.getValBoolean() ? GetRecursiveItemSlot(item) : GetItemSlot(item);

            Item fallback = getItemFromModeVal(fallBackMode.getValString());

            String display = getItemNameFromModeVal(mode);

            if (slot == -1 && item != fallback && mc.player.getHeldItemOffhand().getItem() != fallback) {
                slot = GetRecursiveItemSlot(fallback);
                display = getItemNameFromModeVal(fallBackMode.getValString());

                if (slot == -1 && fallback != Items.TOTEM_OF_UNDYING) {
                    fallback = Items.TOTEM_OF_UNDYING;

                    if (item != fallback && mc.player.getHeldItemOffhand().getItem() != fallback) {
                        slot = GetRecursiveItemSlot(fallback);
                        display = "Emergency Totem";
                    }
                }
            }

            if (slot != -1) {
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
                if(useUpdateController.getValBoolean()) mc.playerController.updateController();

                Utils.printMessage(ChatFormatting.BLUE + "Offhand now has a " + display);
            }
        }
    }

    private int GetItemSlot(Item input) {
        if (mc.player == null) return 0;

        for (int i = 0; i < mc.player.inventoryContainer.getInventory().size(); ++i) {
            if (i == 0 || i == 5 || i == 6 || i == 7 || i == 8) continue;

            ItemStack s = mc.player.inventoryContainer.getInventory().get(i);

            if (s.isEmpty()) continue;
            if (s.getItem() == input) return i;
        }
        return -1;
    }

    private int GetRecursiveItemSlot(Item input) {
        if (mc.player == null) return 0;

        for (int i = mc.player.inventoryContainer.getInventory().size() - 1; i > 0; --i) {
            if (i == 5 || i == 6 || i == 7 || i == 8) continue;

            ItemStack s = mc.player.inventoryContainer.getInventory().get(i);

            if (s.isEmpty()) continue;
            if (s.getItem() == input) return i;
        }
        return -1;
    }

    private boolean isValidTarget(EntityPlayer player) {
        if (player == mc.player) return false;
        if (mc.player.getDistance(player) > 15) return false;
        return !AttackManager.isFriend(name);
    }

    public Item getItemFromModeVal(String mode) {
        switch (mode) {
            case "Crystal": return Items.END_CRYSTAL;
            case "Gap": return Items.GOLDEN_APPLE;
            case "Pearl": return Items.ENDER_PEARL;
            case "Chorus": return Items.CHORUS_FRUIT;
            case "Strength": return Items.POTIONITEM;
            case "Shield": return Items.SHIELD;
            default: return Items.TOTEM_OF_UNDYING;
        }
    }

    private String getItemNameFromModeVal(String mode) {
        switch (mode) {
            case "Crystal": return "End Crystal";
            case "Gap": return "Gap";
            case "Pearl": return "Pearl";
            case "Chorus": return "Chorus";
            case "Strength": return "Strength";
            case "Shield": return "Shield";
            default: return "Totem";
        }
    }

    private boolean noNearbyPlayers() {return mode.getValString().equalsIgnoreCase("Crystal") && mc.world.playerEntities.stream().noneMatch(e -> e != mc.player && isValidTarget(e));}
}
