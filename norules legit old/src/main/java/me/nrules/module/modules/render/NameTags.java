package me.nrules.module.modules.render;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.helper.ReflectionHelper;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

public class NameTags extends Module {
    public NameTags() {
        super(NameTags.piska2() + NameTags.piska3() + NameTags.piska4() + NameTags.piska5(), Category.RENDER);
        Main.settingsManager.rSetting(new Setting("Items", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Na");
    }

    public static String piska3() {
        return piska2.replace("GHO", "me");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "Ta");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "gs");
    }

//    @SubscribeEvent
//    public void onNickRemove(PlayerEvent.NameFormat event) {
//        event.setCanceled(true);
//    }

    @SubscribeEvent
    public void onRenderArmor(RenderWorldLastEvent event) {
        List list = mc.world.playerEntities;
        Iterator var3 = list.iterator();

        if (mc.player == null && mc.world == null)
            return;

        while (var3.hasNext()) {
            EntityPlayer player = (EntityPlayer) var3.next();
            if (player != null && player != mc.player) {
                try {
                    double renderPosX = TileEntityRendererDispatcher.staticPlayerX;
                    double renderPosY = TileEntityRendererDispatcher.staticPlayerY;
                    double renderPosZ = TileEntityRendererDispatcher.staticPlayerZ;
                    double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) event.getPartialTicks() - renderPosX;
                    double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) event.getPartialTicks() - renderPosY;
                    double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) event.getPartialTicks() - renderPosZ;
                    this.renderNametag(player, x, y, z);
                } catch (Throwable var18) {
                    var18.printStackTrace();
                }
            }
        }
    }

    public void renderNametag(EntityPlayer player, double x, double y, double z) {
        double size = (double) this.getSize(player) * -0.0225D;
        FontRenderer var13 = mc.fontRenderer;
        GL11.glPushMatrix();
        boolean health = true;
        boolean armor = Main.settingsManager.getSettingByName("Items").getValBoolean();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glTranslated((double) ((float) x), (double) ((float) y + player.height) + 0.5D, (double) ((float) z));
        GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScaled(size, size, size);
        int var16 = health ? var13.getStringWidth(String.valueOf(this.getPlayerName(player)) + " " + this.getHealth(player)) / 2 : var13.getStringWidth(this.getPlayerName(player)) / 2;
        int bordercolor = 1879048192;
        int maincolor = 1879048192;
        RenderUtils.drawBorderedRect((float) (-var16 - 2), (float) (-(mc.fontRenderer.FONT_HEIGHT - 6)), (float) (var16 + 2), (float) ((double) mc.fontRenderer.FONT_HEIGHT + 0.5D), 1.0F, -1879048192, 1879048192);
        GL11.glDisable(2929);
        if (!health) {
            var13.drawStringWithShadow(this.getPlayerName(player), (float) var16, 0.0F, 15790320);
        } else if (health) {
            var13.drawStringWithShadow(this.getPlayerName(player), (float) (-var13.getStringWidth(String.valueOf(this.getPlayerName(player)) + " " + this.getHealth(player)) / 2), 0.0F, 15790320);
            var13.drawStringWithShadow(this.getHealth(player), (float) ((var13.getStringWidth(String.valueOf(this.getPlayerName(player)) + " " + this.getHealth(player)) - var13.getStringWidth(this.getHealth(player)) * 2) / 2), 0.0F, this.getHealthColorHEX(player));
        }

        if (armor) {
            this.renderArmor(player);
        }

        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    private int getHealthColorHEX(EntityPlayer e) {
        int health = Math.round(20.0F * (e.getHealth() / e.getMaxHealth()));
        int color = -1;
        if (health >= 20) {
            color = 5030935;
        } else if (health >= 18) {
            color = 9108247;
        } else if (health >= 16) {
            color = 10026904;
        } else if (health >= 14) {
            color = 12844472;
        } else if (health >= 12) {
            color = 16633879;
        } else if (health >= 10) {
            color = 15313687;
        } else if (health >= 8) {
            color = 16285719;
        } else if (health >= 6) {
            color = 16286040;
        } else if (health >= 4) {
            color = 15031100;
        } else if (health >= 2) {
            color = 16711680;
        } else if (health >= 0) {
            color = 16190746;
        }

        return color;
    }

    private String getHealth(EntityPlayer e) {
        String hp = "";
        double abs = (double) (2.0F * (e.getAbsorptionAmount() / 4.0F));
        double health = (10.0D + abs) * (double) (e.getHealth() / e.getMaxHealth());
        health = Double.valueOf(Math.abs(health));
        int ihealth = (int) health;
        if (health % 1.0D != 0.0D) {
            hp = String.valueOf(health);
        } else {
            hp = String.valueOf(ihealth);
        }

        float tmpFloat = Math.abs((float) health);
        String f0 = String.format("%.01f", tmpFloat);
        f0 = f0.replace(",", ".");
        return f0;
    }

    private String getPlayerName(EntityPlayer player) {
        String name = "";
        name = player.getDisplayName().getFormattedText();
        return name;
    }

    public boolean isFacingAtEntity(Entity cunt, double angleHowClose) {
        Entity ent = mc.player;
        float[] yawPitch = this.getYawAndPitch(cunt);
        angleHowClose /= 4.5D;
        float yaw = yawPitch[0];
        float pitch = yawPitch[1];
        return (double) this.AngleDistance(ent.rotationYaw, yaw) < angleHowClose && (double) this.AngleDistance(ent.rotationPitch, pitch) < angleHowClose;
    }

    private float AngleDistance(float par1, float par2) {
        float angle = Math.abs(par1 - par2) % 360.0F;
        if (angle > 180.0F) {
            angle = 360.0F - angle;
        }

        return angle;
    }

    public float[] getYawAndPitch(Entity target) {
        Entity ent = mc.player;
        double x = target.posX - ent.posX;
        double z = target.posZ - ent.posZ;
        double y = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0D - mc.player.posY;
        double helper = (double) MathHelper.sqrt(x * x + z * z);
        float newYaw = (float) (Math.atan2(z, x) * 180.0D / 3.141592653589793D) - 90.0F;
        float newPitch = (float) (Math.atan2(y * 1.0D, helper) * 180.0D / 3.141592653589793D);
        return new float[]{newYaw, newPitch};
    }

    private float getSize(EntityPlayer player) {
        Entity ent = mc.player;
        this.isFacingAtEntity(player, 22.0D);
        float dist = ent.getDistance(player) / 9.0F;
        float size = dist <= 1.3F ? 1.3F : dist;
        return size;
    }

    public static void renderArmor(EntityPlayer player) {
        int xOffset = 0;
        ItemStack[] arrayOfItemStack1;
        int j = (arrayOfItemStack1 = player.inventory.armorInventory.toArray(new ItemStack[0])).length;

        int index;
        ItemStack armourStack2;
        for (index = 0; index < j; ++index) {
            armourStack2 = arrayOfItemStack1[index];
            if (armourStack2 != null) {
                xOffset -= 8;
            }
        }

        if (player.getHeldItem(EnumHand.MAIN_HAND) != null) {
            xOffset -= 8;
            ItemStack stock = player.getHeldItem(EnumHand.MAIN_HAND).copy();
            if (stock.hasEffect() && (stock.getItem() instanceof ItemTool || stock.getItem() instanceof ItemArmor)) {
                Field object;
                object = ReflectionHelper.getField(ItemStack.class, "stackSize", "field_77994_a", "c");
                try {
                    object.setInt(stock, 1);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            renderItemStack(stock, xOffset, -25);
            xOffset += 16;
        }

        if (player.getHeldItem(EnumHand.OFF_HAND) != null) {
            xOffset -= 16;
            ItemStack stock = player.getHeldItem(EnumHand.OFF_HAND).copy();
            if (stock.hasEffect() && (stock.getItem() instanceof ItemTool || stock.getItem() instanceof ItemArmor)) {
                Field object;
                object = ReflectionHelper.getField(ItemStack.class, "stackSize", "field_77994_a", "c");
                try {
                    object.setInt(stock, 2);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            renderItemStack(stock, xOffset, -39);
            xOffset += 16;
        }

        ItemStack[] renderStack = player.inventory.armorInventory.toArray(new ItemStack[0]);

        for (index = 3; index >= 0; --index) {
            armourStack2 = renderStack[index];
            if (armourStack2 != null) {
                renderItemStack(armourStack2, xOffset, -19);
                xOffset += 16;
            }
        }

    }

    public static void renderItemStack(ItemStack stack, int x, int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        GlStateManager.disableLighting();
        mc.getRenderItem().zLevel = -150.0F;
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, x, y);
        mc.getRenderItem().zLevel = 0.0F;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.disableDepth();
        drawitemStackEnchants(stack, x * 2, y * 2);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GlStateManager.enableLighting();
        GL11.glPopMatrix();
    }

    public static void drawitemStackEnchants(ItemStack stak, int x, int y) {
        NBTTagList enchants = stak.getEnchantmentTagList();
        if (enchants != null) {
            int ency = 0;

            for (int index = 0; index < enchants.tagCount(); ++index) {
                short id = enchants.getCompoundTagAt(index).getShort("id");
                short level = enchants.getCompoundTagAt(index).getShort("lvl");
                Enchantment enc = Enchantment.getEnchantmentByID(id);
                if (enc != null) {
                    String encName = enc.getTranslatedName(level).substring(0, 2).toLowerCase();
                    String[] var10000 = new String[]{"Efficiency", "Unbreaking", "Sharpness", "FireAspect", ""};
                    mc.fontRenderer.drawStringWithShadow(String.valueOf(encName) + "\u00a7f" + level, (float) x, (float) (y + ency), -1);
                    ency += mc.fontRenderer.FONT_HEIGHT;
                    if (index > 4) {
                        mc.fontRenderer.drawStringWithShadow("\u00a7f+ others", (float) x, (float) (y + ency), -5592406);
                        break;
                    }
                }
            }
        }

    }


//    public void drawPotions(EntityPlayer player, double posX, double posY) {
//        Collection<PotionEffect> collection = mc.player.getActivePotionEffects();
//        List<PotionEffect> sorted = Lists.reverse(Ordering.natural().sortedCopy(collection));
//        for (PotionEffect potioneffect : sorted) {
//            Potion potion = potioneffect.getPotion();
//            int i = 0;
//            ++i;
//            String s1 = I18n.format(potion.getName(), new Object[0]);
//            if (potioneffect.getAmplifier() == 1) {
//                s1 = s1 + " " + I18n.format("enchantment.level.2", new Object[0]);
//            } else if (potioneffect.getAmplifier() == 2) {
//                s1 = s1 + " " + I18n.format("enchantment.level.3", new Object[0]);
//            } else if (potioneffect.getAmplifier() == 3) {
//                s1 = s1 + " " + I18n.format("enchantment.level.4", new Object[0]);
//            }
//            s1 = s1 + " \u00a7f" + Potion.getPotionDurationString(potioneffect, 1.0F);
//            GL11.glPushMatrix();
//            GL11.glScalef(0.5f, 0.5f, 0.5f);
//            mc.fontRenderer.drawStringWithShadow(s1,
//                    (float) ((posX - mc.fontRenderer.getStringWidth(I18n.format(potion.getName())
//                            + ChatFormatting.GRAY + s1 + ": "
//                            + s1) / 4) * 2),
//                    (float) (i * 2), potion.getLiquidColor());
//
//            GL11.glPopMatrix();
//            GL11.glScalef(1.0f, 1.0f, 1.0f);
//        }
//    }
}