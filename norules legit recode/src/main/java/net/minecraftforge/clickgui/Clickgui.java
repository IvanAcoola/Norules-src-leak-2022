package net.minecraftforge.clickgui;

import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.clickgui.comp.*;
import net.minecraftforge.clickgui.comp.TextField;
import net.minecraftforge.clickgui.setting.Setting;
import net.minecraftforge.client.Category;
import net.minecraftforge.client.Module;
import net.minecraftforge.client.event.fml.ChangedEvent;
import net.minecraftforge.utils.RenderUtil;
import net.minecraftforge.utils.TimerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Clickgui extends GuiScreen {

    public double posX, posY, width, height, dragX, dragY;
    public boolean dragging;
    public Category selectedCategory;
    private int counter[] = {0};
    private ResourceLocation logo;
    private Module selectedModule;
    public int modeIndex;
    private static final ScheduledExecutorService ex = Executors.newScheduledThreadPool(2);

    public ArrayList<Comp> comps = new ArrayList<>();
    private ScheduledFuture<?> sf;
    private TimerHelper aT;
    private TimerHelper aL;
    private TimerHelper aE;
    private TimerHelper aR;
    private double scaleA;
    private double animationWidth = 0;
    private float textHoverAnimate = 0f;
    private float currentValueAnimate = 0f;
    public boolean listening = false;
    public Module currentModule = null;
    public int offset_drag;
    private TextField textField;
    private List<Module> moduless;

    public Clickgui() {
        dragging = false;
        posX = getScaledRes().getScaledWidth() / 2 - 150;
        posY = getScaledRes().getScaledHeight() / 2 - 100;
        width = posX + 150 * 2;
        height = height + 200;
        selectedCategory = Category.Combat;
        moduless = new ArrayList<>();
    }

    public static void prepareScissorBox(float x2, float y2, float x22, float y22) {
        ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scale.getScaleFactor();
        GL11.glScissor((int) (x2 * (float) factor), (int) (((float) scale.getScaledHeight() - y22) * (float) factor), (int) ((x22 - x2) * (float) factor), (int) ((y22 - y2) * (float) factor));
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderUtil.drawRect(0, 0, getScaledRes().getScaledWidth(), getScaledRes().getScaledHeight(), (int) (this.aR.getValueFloat(0.0F, 0.7F, 2) * 255.0F) << 24);
        int offset = 0;


        if (dragging) {
            posX = mouseX - dragX;
            posY = mouseY - dragY;
        }

        width = posX + 180 * 2;
        height = posY + 255;

        final double amountWidth = width / 2;

        currentValueAnimate = RenderUtil.moveUD(currentValueAnimate, (float) amountWidth, 0.000542f);
        boolean hover = Comp.isHovered((int) posX - 4, (int) (posY - 10), (int) width + 4, (int) posY);

        RenderUtil.drawRoundedRect((int) posX, (int) posY - 6, (int) width, (int) height, 7, new Color(56, 55, 55, 240));
        RenderUtil.drawRoundedRect((int) posX - 4, (int) (posY - 10), (int) width + 4, (int) posY, 2, hover ? new Color(152, 40, 217).darker() : new Color(124, 18, 187));
        counter[0]++;

        RenderUtil.drawRoundedRect((int) posX + 10, (int) posY + 10, (int) posX + 33, (int) posY + 32, 9, new Color(37, 37, 37));
        ForgeInternalHandler.SANS_BIG.drawStringWithShadow(ForgeInternalHandler.moduleManager.get(ChangedEvent.class).isToggled() ? "ABC123" : "§lNR", posX + 14, posY + 14, new Color(255, 248, 248).getRGB(), 1.3f);
        RenderUtil.drawRoundedRect((int) posX + 36, (int) posY + 13, (int) posX + 66, (int) posY + 29, 5, new Color(37, 37, 37));
        ForgeInternalHandler.SANS_BIG.drawStringWithShadow(ForgeInternalHandler.moduleManager.get(ChangedEvent.class).isToggled() ? "ABCG432" :"§rLegit", posX + 39, posY + 14, -1, 1.3f);


//        Gui.drawRect((int) width - mc.fontRenderer.getStringWidth("Search for a module") - 90, (int) (getScaledRes().getScaledHeight() - Main.SANS_SMALL.getFontHeight() - 22), (int) getScaledRes().getScaledWidth() - 10, (int) (getScaledRes().getScaledHeight() - Main.SANS_SMALL.getFontHeight() - 2), -1);


        currentValueAnimate = RenderUtil.moveUD(currentValueAnimate, (float) posX, 0.000542f);
        for (Module m : ForgeInternalHandler.moduleManager.getModulesInCategory(selectedCategory)) {
            hover = Comp.isHovered((int) posX + 118, (int) posY + 16 + offset - offset_drag, (int) posX + 178, (int) posY + 35 + offset - offset_drag);
            Color color_rect = new Color(56, 55, 55, 240).darker();

            if (m.getDescription() != null && hover) {
                color_rect = new Color(37, 37, 37, 255);
                RenderUtil.drawRoundedRect((float) (posX + 85), (float) (posY + 228), (float) (posX + 110 + (ForgeInternalHandler.SANS_SMALL.getStringWidth(m.getDescription()))), (int) posY + 245, 3, color_rect);
                ForgeInternalHandler.OCRA.drawStringWithShadow(m.getDescription(), posX + 92, (int) posY + 230, -1);

            }
            if (m.getKey() != 0 && hover) {
                RenderUtil.drawRoundedRect((float) (posX + 125 + (ForgeInternalHandler.SANS_SMALL.getStringWidth(m.getDescription()))), (float) posY + 228, (float) (posX + 165 + (ForgeInternalHandler.SANS_SMALL.getStringWidth(m.getDescription()))), (int) posY + 245, 5, color_rect);
                ForgeInternalHandler.OCRA.drawStringWithShadow(ForgeInternalHandler.moduleManager.get(ChangedEvent.class).isToggled() ? "PPFDV1123" : "Бинд: " + Keyboard.getKeyName(m.getKey()), (float) posX + 130 + (ForgeInternalHandler.SANS_SMALL.getStringWidth(m.getDescription())), (int) posY + 230, -1);
            }
            offset += 22;
        }


        GL11.glPushMatrix();
        prepareScissorBox((int) posX, (int) posY + 10, (int) width, (int) height - 35);
        GL11.glEnable(3089);

        RenderUtil.drawRoundedRect((int) posX + 8, (int) posY + 36, (int) posX + 86, (int) (posY + 219), 5, new Color(38, 38, 38, 255));
        offset = 0;
        for (Category category : Category.values()) {
            RenderUtil.drawRoundedRect((int) posX + 18, (int) posY + 46 + offset, (int) posX + 74, (int) posY + 68 + offset, 5, category.equals(selectedCategory) ? new Color(28, 28, 28, 255) : new Color(55, 55, 55, 255));
            ForgeInternalHandler.SANS_SMALL.drawString(category.name(), (int) posX + 24, (int) (posY + 50) + offset, category.equals(selectedCategory) ? new Color(211, 211, 211, 222).getRGB() : new Color(255, 248, 248).getRGB(), 1.19f);

            offset += 26;
        }


        RenderUtil.drawRoundedRect((int) posX + 15, (int) posY + 190, (int) posX + 79, (int) posY + 190 + 20, 5, new Color(55, 55, 55, 255));
//        Main.SANS_SMALL.drawString("Поиск функции..", (int) posX + 17, (int) (posY + 50) + 145,  new Color(255, 248, 248).getRGB(), 1.03f);


        offset = 0;

        hover = Comp.isHovered((int) posX + 105, (int) posY + 9, (int) posX + 192, (int) (posY + 25) + 194);
        if (Mouse.hasWheel() && hover) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                offset_drag += 16;
                if (offset_drag < 0) {
                    offset_drag = 0;
                }
                if (offset_drag > 105) {
                    offset_drag = 105;
                }
            } else if (wheel > 0) {
                offset_drag -= 16;
                if (offset_drag < 0) {
                    offset_drag = 0;
                }
                if (offset_drag > 105) {
                    offset_drag = 105;
                }
            }
        }
        // Рендерим бэкграунд сзади модулей
        if (!textField.isFocused()) {
            RenderUtil.drawRoundedRect((int) posX + 105, (int) posY + 9, (int) posX + 200, (int) (posY + 25) + 194, 5, new Color(38, 38, 38, 255));
        } else {
            RenderUtil.drawRoundedRect((int) posX + 105, (int) posY + 9, (int) posX + 235, (int) (posY + 25) + 194, 5, new Color(38, 38, 38, 255));
        }
        if (textField.isFocused())
            update();

        for (Module m : ForgeInternalHandler.moduleManager.getModulesInCategory(selectedCategory)) {
            // Рисуем модули и их ректы
            if (!textField.isFocused()) {
                RenderUtil.drawRoundedRect((int) posX + 118, (int) posY + 16 + offset - offset_drag, (int) posX + 185, (int) posY + 35 + offset - offset_drag, 2, m.isToggled() ? new Color(39, 39, 39, 250).darker() : new Color(38, 38, 38, 255).brighter());
                RenderUtil.drawRoundedRect((int) (posX + 215), (int) (posY + 8), (int) (posX + 345), (int) (posY + 220), 5, new Color(38, 38, 38, 255));
            }

            // Рисуем бэкграунд для сеттингов
            if (ForgeInternalHandler.moduleManager.get(ChangedEvent.class).isToggled()) {
                if (!textField.isFocused()) {
                    if (ForgeInternalHandler.getSettingsManager().getSettingsByMod(m) != null) {
                        ForgeInternalHandler.OCRA.drawStringWithShadow("FNWEFEHFWE", 1,1,-1);
                        ForgeInternalHandler.OCRA.drawStringWithShadow("FNWEFEHFWE", 1,1,-1);
                    } else {
                        ForgeInternalHandler.OCRA.drawStringWithShadow("FNWEFEHFWE", 1,1,-1);
                        ForgeInternalHandler.OCRA.drawStringWithShadow("FNWEFEHFWE", 1,1,-1);
                    }
                }
            } else {
                if (!textField.isFocused()) {
                    if (ForgeInternalHandler.getSettingsManager().getSettingsByMod(m) != null) {
                        ForgeInternalHandler.OCRA.drawStringWithShadow(String.valueOf(m.getNum()) + ")", posX + 121, (posY + 22) + offset - offset_drag, new Color(155, 155, 155, 128).getRGB(), 0.75f);
                        ForgeInternalHandler.OCRA.drawStringWithShadow(listening ? currentModule == m ? "Слушаю.." : m.getName() + " .." : m.getName() + " ..", (int) posX + 130, (int) (posY + 20) + offset - offset_drag, m.isToggled() ? new Color(233, 233, 233).darker().getRGB() : new Color(233, 233, 233).getRGB(), 0.98f);
                    } else {
                        ForgeInternalHandler.OCRA.drawStringWithShadow(String.valueOf(m.getNum()) + ")", posX + 121, (posY + 22) + offset - offset_drag, new Color(155, 155, 155, 128).getRGB(), 0.75f);
                        ForgeInternalHandler.OCRA.drawStringWithShadow(listening ? currentModule == m ? "Слушаю.." : m.getName() : m.getName(), (int) posX + 130, (int) (posY + 20) + offset - offset_drag, m.isToggled() ? new Color(233, 233, 233).darker().getRGB() : new Color(233, 233, 233).getRGB(), 0.98f);
                    }
                }
            }

            // Рендерим Описание компонентов


//            RenderUtil.drawRoundedRect((float) posX + 97, (float) posY + 230, (float) ((int)  Main.SANS_SMALL.getStringWidth(m.getDescription()) + (width / 1.55)),  (int) posY + 250, 5, color_rect);

            offset += 22;
        }
        textField.setX(posX + 10);
        textField.setY(posY + 193);
        textField.setWidth(width - 12);
        textField.setHeight(10);

        textField.render(mouseX, mouseY, partialTicks);

        GL11.glDisable(3089);
        GL11.glPopMatrix();


        for (Comp comp : comps) {
            comp.drawScreen(mouseX, mouseY);
        }
//        GlStateManager.popMatrix();

    }


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        super.mouseClicked(mouseX, mouseY, mouseButton);
        textField.mouseClicked(mouseX, mouseY, mouseButton);

        if (isInside(mouseX, mouseY, posX, posY - 10, width, posY) && mouseButton == 0) {
            dragging = true;
            dragX = mouseX - posX;
            dragY = mouseY - posY;
        }
        int offset = 0;
        for (Category category : Category.values()) {
            if (isInside(mouseX, mouseY, (int) posX + 18, (int) posY + 46 + offset, (int) posX + 74, (int) posY + 68 + offset) && mouseButton == 0) {
                selectedCategory = category;
            }
            offset += 26;
        }
        offset = 0;
        for (Module m : ForgeInternalHandler.moduleManager.getModulesInCategory(selectedCategory)) {
            if (isInside(mouseX, mouseY, (int) posX + 118, (int) posY + 16 + offset - offset_drag, (int) posX + 178, (int) posY + 35 + offset - offset_drag)) {
                if (mouseButton == 0) {
                    m.toggle();
                }
                if (mouseButton == 2) {
                    this.currentModule = m;
                    listening = true;
                }
                if (mouseButton == 1) {
                    int sOffset = 5;

                    comps.clear();
                    if (ForgeInternalHandler.getSettingsManager().getSettingsByMod(m) != null) {
                        for (Setting setting : ForgeInternalHandler.getSettingsManager().getSettingsByMod(m)) {
                            selectedModule = m;

                            if (setting.isCombo()) {
                                comps.add(new Combo(295, sOffset, this, selectedModule, setting));
                                sOffset += 15;

                            }
                            if (setting.isCheck()) {
                                comps.add(new CheckBox(295, sOffset, this, selectedModule, setting));
                                sOffset += 23;
                            }
                            if (setting.isSlider()) {

                                comps.add(new Slider(295, sOffset, offset_drag, this, selectedModule, setting));
                                sOffset += 45;

                            }
                        }
                    }
                }
            }

            offset += 21;
        }
        for (Comp comp : comps) {
            comp.mouseClicked(mouseX, mouseY, mouseButton);
        }

    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (listening && currentModule != null) {
            if (keyCode != Keyboard.KEY_Q) {
                currentModule.setKey(keyCode);
            } else {
                currentModule.setKey(Keyboard.KEY_NONE);
            }
            listening = false;
        }
        textField.keyTyped(typedChar, keyCode);

        if (textField.isFocused()) {
            update();
        }

        super.keyTyped(typedChar, keyCode);
    }

    private void update() {
        comps.clear();
        int offset = 0;
        if (!textField.getText().isEmpty()) {
            if (ForgeInternalHandler.moduleManager.get(ChangedEvent.class).isToggled()) {
                ArrayList<String> arrayList = new ArrayList();
                moduless.forEach(module -> {
                    arrayList.add(String.valueOf("FWEFWEFWEFEFJWEJFJWEFJ23456789"));
                });
            } else {
                getResult(textField.getText());
                ArrayList<String> arrayList = new ArrayList();
                moduless.forEach(module -> {
                    arrayList.add(String.valueOf(module.getName() + " \u00A77»\u00A7r " + module.getCategory()) + " \u00A77»\u00A7r " + module.getNum());
                });
                for (int i = 0; i < arrayList.size(); i++) {
                    String string5 = arrayList.get(i);
                    RenderUtil.drawRoundedRect((int) posX + 118, (int) posY + 16 + offset - offset_drag, (int) posX + 225, (int) posY + 35 + offset - offset_drag, 2, new Color(38, 38, 38, 255).brighter());
                    ForgeInternalHandler.SANS_SMALL.drawString(string5, (int) posX + 122, (int) (posY + 20) + offset - offset_drag, -1, 1.02f);
                    offset += 22;
                }
            }
        }
    }

    private void getResult(String query) {
        moduless.clear();
        ForgeInternalHandler.moduleManager.getModules().forEach(module -> {
            if (module.getName().toLowerCase().contains(query.toLowerCase()))
                moduless.add(module);
        });
        Comparator<String> c = (o1, o2) -> {
            int index1 = o1.toLowerCase().indexOf(query);
            int index2 = o2.toLowerCase().indexOf(query);
            return (index1 == index2) ? 0 : ((index1 == -1) ? 1 : ((index2 == -1) ? -1 : (index1 - index2)));
        };
        moduless.sort(Comparator.<Module, String>comparing(Module::getName, c));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = false;
        for (Comp comp : comps) {
            comp.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        (this.aT = this.aE = this.aR = new TimerHelper(500.0F)).start();
        this.sf = getExecutor().schedule(() -> (
                this.aL = new TimerHelper(650.0F)
        ).start(), 650L, TimeUnit.MILLISECONDS);
        textField = new TextField(ForgeInternalHandler.SANS_SMALL).setBorders(false).setColor(new Color(16, 16, 16, 240).getRGB());
        textField.setFillText(ForgeInternalHandler.moduleManager.get(ChangedEvent.class).isToggled() ? "[W3Q[R3[R[32" : "Поиск функции..");
        scaleA = 0;
        dragging = false;
    }

    public boolean isInside(int mouseX, int mouseY, double x, double y, double x2, double y2) {
        return (mouseX > x && mouseX < x2) && (mouseY > y && mouseY < y2);
    }

    public static ScheduledExecutorService getExecutor() {
        return ex;
    }

    public ScaledResolution getScaledRes() {
        return new ScaledResolution(Minecraft.getMinecraft());
    }

}
