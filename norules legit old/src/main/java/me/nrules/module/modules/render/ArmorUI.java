package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ArmorUI extends Module {
    public ArmorUI() {
        super(ArmorUI.piska2() + ArmorUI.piska3() + ArmorUI.piska4() + ArmorUI.piska5(), Category.RENDER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Arm");
    }

    public static String piska3() {
        return piska2.replace("GHO", "or");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "U");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "I");
    }

    private static final RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            GlStateManager.enableTexture2D();

            ScaledResolution resolution = new ScaledResolution(mc);
            int i = resolution.getScaledWidth() / 2;
            int iteration = 0;
            int y = resolution.getScaledHeight() - 55 - (mc.player.isInWater() ? 10 : 0);
            for (ItemStack is : mc.player.inventory.armorInventory) {
                iteration++;
                if (is.isEmpty())
                    continue;

                int x = i - 90 + (9 - iteration) * 24 - 25;
                GlStateManager.enableDepth();
                itemRender.zLevel = 200F;
                itemRender.renderItemAndEffectIntoGUI(is, x, y);
                itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, is, x, y, "");
                itemRender.zLevel = 0F;

                GlStateManager.enableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();

                String s = is.getCount() > 50 ? is.getCount() + "" : "";
                mc.fontRenderer.drawStringWithShadow(s, x + 14 - 2 - mc.fontRenderer.getStringWidth(s), y + 9, 0xffffffff);
                float green = ((float) is.getMaxDamage() - (float) is.getItemDamage()) / (float) is.getMaxDamage();
                float red = 1 - green;
                int dmg = 100 - (int) (red * 100);
//                mc.fontRenderer.drawStringWithShadow(dmg + "" + "%", x + 8 - mc.fontRenderer.getStringWidth(dmg + "" + "%") / 2, y - 8, 0xffffffff);
            }

            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }

}