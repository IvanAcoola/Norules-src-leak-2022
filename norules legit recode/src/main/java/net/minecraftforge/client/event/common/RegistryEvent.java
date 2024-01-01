package net.minecraftforge.client.event.common;

import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.font.FontRenderer;
import net.minecraftforge.client.Category;
import net.minecraftforge.client.Module;
import net.minecraftforge.client.ModuleManager;
import net.minecraftforge.utils.RenderUtil;
import net.minecraftforge.utils.TimerHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.clickgui.Clickgui;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RegistryEvent extends Module {
    public RegistryEvent() {
        super(RegistryEvent.piska2() + RegistryEvent.piska3() + RegistryEvent.piska4() + RegistryEvent.piska5(), Category.Render, "Показывает включенные функции.", 4);
    }

    private static final String piska = "FFFFFFFFFFFFFFFFF";
    private static final String piska2 = "AAAAAAAAAAAAAA";
    private static final String piska3 = "VVVVVVVVVVVVVVVVVVVVVVVVVVVVV";
    private static final String piska5 = "ZZZZZZZZZZZZZZZZZZ";

    public static String piska2() {
        return piska.replace("FFFFFFFFFFFFFFFFF", "H");
    }

    public static String piska3() {
        return piska2.replace("AAAAAAAAAAAAAA", "U");
    }

    public static String piska4() {
        return piska3.replace("VVVVVVVVVVVVVVVVVVVVVVVVVVVVV", "D");
    }

    public static String piska5() {
        return piska5.replace("ZZZZZZZZZZZZZZZZZZ", "");
    }

    public static int hudX = 5;
    public static int hudY = 70;
    public static ScheduledFuture<?> sf;
    public static TimerHelper aT;
    public static TimerHelper aL;
    public static TimerHelper aE;
    public static TimerHelper aR;
    private float currentValueAnimate = 0f;

    @SubscribeEvent
    public void RenderTick(TickEvent.RenderTickEvent event) {
        if (mc.gameSettings.showDebugInfo || mc.player == null || mc.world == null) return;

        int margin = 2;
        int y = hudY;
        int del = 0;


        if (hudX < 0) {
            hudX = margin;
        }
        if (hudY < 0) {
            {
                hudY = margin;
            }
        }

        int textBoxWidth = ForgeInternalHandler.moduleManager.getLongestActiveModule(ForgeInternalHandler.SANS_SMALL);
        int textBoxHeight = ForgeInternalHandler.moduleManager.getBoxHeight(ForgeInternalHandler.SANS_SMALL, margin);

        if (hudX + textBoxWidth > mc.displayWidth / 2) {
            hudX = mc.displayWidth / 2 - textBoxWidth - margin;
        }

        if (hudY + textBoxHeight > mc.displayHeight / 2) {
            hudY = mc.displayHeight / 2 - textBoxHeight;
        }

        ForgeInternalHandler.moduleManager.getModuleList().sort(Comparator.comparingInt((mx) -> (int) ForgeInternalHandler.SANS_SMALL.getStringWidth(((Module) mx).getName())).reversed());
        //            Gui.drawRect(hudX + 10, hudY + 10, hudX + 120, hudY + 50, new Color(55, 55, 55, 200).getRGB());
        //        for (Module m : Main.moduleManager.getModuleList()) {
////            Main.SANS_SMALL.drawString(m.isToggled() ? m.getName() + " включен!" : m.getName() + " выключен!", hudX + 20, hudY + 5 + offset, 0xFFF8F8);
//            offset += 11;
//        }


        for (Module m : ForgeInternalHandler.moduleManager.getModuleList()) {
            if (m.isToggled() && m != this) {

                currentValueAnimate = RenderUtil.moveUD(currentValueAnimate, textBoxWidth, 0.000012f);
                Gui.drawRect((int) (hudX + (currentValueAnimate - ForgeInternalHandler.SANS_SMALL.getStringWidth(m.getName())) - 2), (hudY - 11), (int) ((float) (hudX + ForgeInternalHandler.SANS_SMALL.getStringWidth(m.getName()))),  hudY - 9, new Color(204, 22, 69, 255).getRGB());
                RenderUtil.drawRoundedRect((int) (hudX + (textBoxWidth - ForgeInternalHandler.SANS_SMALL.getStringWidth(m.getName())) - 4), (int) (y + ForgeInternalHandler.SANS_SMALL.getFontHeight() - 12), (int) (textBoxWidth + hudX + 6), y + 15, 3, new Color(63, 62, 62, 255));
                ForgeInternalHandler.SANS_SMALL.drawStringWithShadow(m.getName(), hudX + (textBoxWidth - ForgeInternalHandler.SANS_SMALL.getStringWidth(m.getName())), y, -1, 1.05f);
                y += ForgeInternalHandler.SANS_SMALL.getFontHeight() + margin;
                del -= 120;
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
            mc.displayGuiScreen(new EditHudPositionScreen());
        }
    }

    static class EditHudPositionScreen extends GuiScreen {
        final String hudTextExample = "This is an-Example-HUD";
        GuiButtonExt resetPosButton;
        boolean mouseDown = false;
        int textBoxStartX = 0;
        int textBoxStartY = 0;
        ScaledResolution sr;
        int textBoxEndX = 0;
        int textBoxEndY = 0;
        int marginX = 5;
        int marginY = 70;
        int lastMousePosX = 0;
        int lastMousePosY = 0;
        int sessionMousePosX = 0;
        int sessionMousePosY = 0;

        public void initGui() {
            super.initGui();
            (aT = aE = aR = new TimerHelper(500.0F)).start();
            sf = Clickgui.getExecutor().schedule(() -> (
                    aL = new TimerHelper(650.0F)
            ).start(), 650L, TimeUnit.MILLISECONDS);
            this.buttonList.add(this.resetPosButton = new GuiButtonExt(1, this.width - 90, 5, 85, 20, "Сбросить настройки"));
            this.marginX = hudX;
            this.marginY = hudY;
            sr = new ScaledResolution(mc);
        }

        public void drawScreen(int mX, int mY, float pt) {
            RenderUtil.drawRect(0, 0, this.width, this.height, (int) (aR.getValueFloat(0.0F, 0.7F, 2) * 255.0F) << 24);
            int textBoxStartX = this.marginX;
            int textBoxStartY = this.marginY;
            int textBoxEndX = textBoxStartX + 50;
            int textBoxEndY = textBoxStartY + 32;
            this.drawArrayList(ForgeInternalHandler.SANS_SMALL, this.hudTextExample);
            this.drawDebugModule();
            this.textBoxStartX = textBoxStartX;
            this.textBoxStartY = textBoxStartY;
            this.textBoxEndX = textBoxEndX;
            this.textBoxEndY = textBoxEndY;
            RegistryEvent.hudX = textBoxStartX;
            RegistryEvent.hudY = textBoxStartY;
            ScaledResolution res = new ScaledResolution(this.mc);
            int descriptionOffsetX = res.getScaledWidth() / 2 - 84;
            int descriptionOffsetY = res.getScaledHeight() / 2 - 20;
            ForgeInternalHandler.SANS_SMALL.drawStringWithShadow("Передвигай элементы как тебе удобно", descriptionOffsetX, descriptionOffsetY, -1, 1.5f);


            super.drawScreen(mX, mY, pt);
        }


        private void drawDebugModule() {
            RenderUtil.drawRoundedRect(hudX + 10, hudY + 10, hudX + 110, hudY + 40,5, new Color(38, 38, 38, 255));
//            Gui.drawRect(hudX + 10, hudY + 10, hudX + 120, hudY + 50, new Color(55, 55, 55, 200).getRGB());
            int offset = 0;
            for (Module m : ForgeInternalHandler.moduleManager.getModuleList()) {
//                Main.SANS_SMALL.drawString(m.isToggled() ? m.getName() + " включен!" : m.getName() + " выключен!", hudX + 20, hudY + 5 + offset, 0xFFF8F8);
                offset += 11;
            }
        }

        private void drawArrayList(FontRenderer fr, String t) {
            int x = this.textBoxStartX;
            int y = this.textBoxStartY;
            double marginY = fr.getFontHeight() + 2;
            ArrayList<String> arrayList = new ArrayList();
            ModuleManager.getModules().stream().forEach(module -> {
                if (module.isToggled() && !module.getName().equals(ForgeInternalHandler.moduleManager.getModule(RegistryEvent.class).getName())) {
                    arrayList.add(String.valueOf(module.getName()));
                }
            });
            ForgeInternalHandler.moduleManager.getModuleList().sort(Comparator.comparingInt((mx) -> {
                return (int) fr.getStringWidth(((Module) mx).getName());
            }).reversed());
            arrayList.sort((string, string2) -> (int) (ForgeInternalHandler.SANS_SMALL.getStringWidth(string2) - ForgeInternalHandler.SANS_SMALL.getStringWidth(string)));

//            for (String s : arrayList) {
//                fr.drawString(s, x, y, -1);
//                y += marginY;
//            }
        }

        protected void mouseClickMove(int mousePosX, int mousePosY, int clickedMouseButton, long timeSinceLastClick) {
            super.mouseClickMove(mousePosX, mousePosY, clickedMouseButton, timeSinceLastClick);
            if (clickedMouseButton == 0) {
                if (this.mouseDown) {
                    this.marginX = this.lastMousePosX + (mousePosX - this.sessionMousePosX);
                    this.marginY = this.lastMousePosY + (mousePosY - this.sessionMousePosY);
                    sr = new ScaledResolution(mc);
//                    HUD.positionMode = Utils.HUD.getPostitionMode(marginX, marginY,sr.getScaledWidth(), sr.getScaledHeight());

                    //in the else if statement, we check if the mouse is clicked AND inside the "text box"
                } else if (mousePosX > this.textBoxStartX && mousePosX < this.textBoxEndX && mousePosY > this.textBoxStartY && mousePosY < this.textBoxEndY) {
                    this.mouseDown = true;
                    this.sessionMousePosX = mousePosX;
                    this.sessionMousePosY = mousePosY;
                    this.lastMousePosX = this.marginX;
                    this.lastMousePosY = this.marginY;
                }

         }        }


    public void actionPerformed(GuiButton b) {
            if (b == this.resetPosButton) {
                this.marginX = RegistryEvent.hudX = 5;
                this.marginY = RegistryEvent.hudY = 70;
            }
        }
    }
}
