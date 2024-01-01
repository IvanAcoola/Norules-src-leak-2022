package net.minecraftforge.hooks;

import com.google.common.collect.Lists;
import net.minecraftforge.ForgeInternalHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class GuiNewChatHook extends GuiNewChat {

    private final Minecraft mc;
    private final List<String> sentMessages = Lists.newArrayList();
    private final List<ChatLine> chatLines = Lists.newArrayList();
    private final List<ChatLine> drawnChatLines = Lists.newArrayList();
    private int scrollPos;
    private boolean isScrolled;

    public GuiNewChatHook(Minecraft p_i1022_1_) {
        super(p_i1022_1_);

        this.mc = p_i1022_1_;
    }


    @Override
    public void drawChat(int p_146230_1_) {
        System.out.println("1");
        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            int lvt_2_1_ = this.getLineCount();
            int lvt_3_1_ = this.drawnChatLines.size();
            float lvt_4_1_ = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
            if (lvt_3_1_ > 0) {
                boolean lvt_5_1_ = false;
                if (this.getChatOpen()) {
                    lvt_5_1_ = true;
                }

                float lvt_6_1_ = this.getChatScale();
                int lvt_7_1_ = MathHelper.ceil((float)this.getChatWidth() / lvt_6_1_);
                GlStateManager.pushMatrix();
                GlStateManager.translate(2.0F, 8.0F, 0.0F);
                GlStateManager.scale(lvt_6_1_, lvt_6_1_, 1.0F);
                int lvt_8_1_ = 0;

                int lvt_9_1_;
                int lvt_11_1_;
                int lvt_14_1_;
                for(lvt_9_1_ = 0; lvt_9_1_ + this.scrollPos < this.drawnChatLines.size() && lvt_9_1_ < lvt_2_1_; ++lvt_9_1_) {
                    ChatLine lvt_10_1_ = (ChatLine)this.drawnChatLines.get(lvt_9_1_ + this.scrollPos);
                    if (lvt_10_1_ != null) {
                        lvt_11_1_ = p_146230_1_ - lvt_10_1_.getUpdatedCounter();
                        if (lvt_11_1_ < 200 || lvt_5_1_) {
                            double lvt_12_1_ = (double)lvt_11_1_ / 200.0D;
                            lvt_12_1_ = 1.0D - lvt_12_1_;
                            lvt_12_1_ *= 10.0D;
                            lvt_12_1_ = MathHelper.clamp(lvt_12_1_, 0.0D, 1.0D);
                            lvt_12_1_ *= lvt_12_1_;
                            lvt_14_1_ = (int)(255.0D * lvt_12_1_);
                            if (lvt_5_1_) {
                                lvt_14_1_ = 255;
                            }

                            lvt_14_1_ = (int)((float)lvt_14_1_ * lvt_4_1_);
                            ++lvt_8_1_;
                            if (lvt_14_1_ > 3) {
                                int lvt_16_1_ = -lvt_9_1_ * 9;
                                drawRect(-2, lvt_16_1_ - 9, 0 + lvt_7_1_ + 4, lvt_16_1_, lvt_14_1_ / 2 << 24);
                                String lvt_17_1_ = lvt_10_1_.getChatComponent().getFormattedText();
                                GlStateManager.enableBlend();
                                ForgeInternalHandler.SANS_SMALL.drawStringWithShadow(lvt_17_1_, 0.0F, (float)(lvt_16_1_ - 8), 16777215 + (lvt_14_1_ << 24));
                                GlStateManager.disableAlpha();
                                GlStateManager.disableBlend();
                            }
                        }
                    }
                }

                if (lvt_5_1_) {
                    lvt_9_1_ = (int) ForgeInternalHandler.SANS_SMALL.getFontHeight();
                    GlStateManager.translate(-3.0F, 0.0F, 0.0F);
                    int lvt_10_2_ = lvt_3_1_ * lvt_9_1_ + lvt_3_1_;
                    lvt_11_1_ = lvt_8_1_ * lvt_9_1_ + lvt_8_1_;
                    int lvt_12_2_ = this.scrollPos * lvt_11_1_ / lvt_3_1_;
                    int lvt_13_1_ = lvt_11_1_ * lvt_11_1_ / lvt_10_2_;
                    if (lvt_10_2_ != lvt_11_1_) {
                        lvt_14_1_ = lvt_12_2_ > 0 ? 170 : 96;
                        int lvt_15_2_ = this.isScrolled ? 13382451 : 3355562;
                        drawRect(0, -lvt_12_2_, 2, -lvt_12_2_ - lvt_13_1_, lvt_15_2_ + (lvt_14_1_ << 24));
                        drawRect(2, -lvt_12_2_, 1, -lvt_12_2_ - lvt_13_1_, 13421772 + (lvt_14_1_ << 24));
                    }
                }

                GlStateManager.popMatrix();
            }
        }
    }
}
