package net.minecraftforge.client;

import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.clickgui.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.utils.TimerHelper;
import net.minecraftforge.utils.TimerUtil;

public class Module {
    public static final Minecraft mc = Minecraft.getMinecraft();
    private String name;
    private String description;
    private int key;
    private int num;
    private Category category;
    public boolean toggled;
    private int color;
    private int keybind = 0;
    public int a;
    public double width;
    public double height;
    public double x;
    public double y;
    public static ForgeInternalHandler forgeInternalHandler;
    protected static TimerUtil timerHelper = new TimerUtil();
    public Module(String name, Category category, String description, int num) {
        super();
        this.name = name;
        this.category = category;
        this.description = description;
        this.key = 0;
        this.num = num;
        this.toggled = false;
        this.setup();
    }


    public int getKey() {
        return key;
    }

    public int getKeybind() {
        return this.keybind;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;

        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void toggle() {
        this.toggled = !this.toggled;

        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        return isHovered(mouseX, mouseY);
    }

    public boolean isHovered(int mouseX, int mouseY) {

        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void setup() {

    }

    protected final void rSetting(Setting setting) {
        ForgeInternalHandler.getSettingsManager().rSetting(setting);
    }

    protected final Setting getSetting(String name) {
        return ForgeInternalHandler.getSettingsManager().getSettingByName(name);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public Category getCategory() {
        return this.category;
    }

    public String getName() {
        return this.name;
    }

    public boolean onPacketSent(Packet<?> packet) {
        return true;
    }

    public boolean onPacketReceive(Packet<?> packet) {
        return true;
    }
}
