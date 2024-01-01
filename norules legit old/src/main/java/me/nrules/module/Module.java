package me.nrules.module;

import me.nrules.Main;
import me.nrules.clickgui.settings.SettingsManager;
import me.nrules.util.TimerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;

public class Module {
    public static final Minecraft mc = Minecraft.getMinecraft();
    protected static SettingsManager setmgr;
    public String name;
    private int key;
    private Category category;
    public boolean toggled;
    private int color;
    private int keybind = 0;
    public int a;
    protected static TimerHelper timerHelper = new TimerHelper();
    public double width;
    public double height;
    public double x;
    public double y;
    public static Main main;

    public Module(String name, Category category) {
        super();
        this.name = name;
        this.category = category;
        this.key = 0;
        this.toggled = false;
        setmgr = Main.settingsManager;
    }


    public int getKey() {
        return key;
    }

    public int getKeybind() {
        return this.keybind;
    }

    public void setKey(int key) {
/*        if (main.saver != null)
        {
            main.saver.save();
        }*/
        this.key = key;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;

        if(this.toggled) {
            this.onEnable();
        }else {
            this.onDisable();
        }
    }

    public void setName(String name) {
        this.name = name;
    }
    public void toggle() {
        this.toggled = !this.toggled;

        if(this.toggled) {
            this.onEnable();
        }else {
            this.onDisable();
        }
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        return isHovered(mouseX, mouseY);
    }

    public boolean isHovered(int mouseX, int mouseY)
    {

        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void onEnable()
    {

        MinecraftForge.EVENT_BUS.register(this);
    }
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
    public String getName() {
        return this.name;
    }
    public Category getCategory() {
        return this.category;
    }
    public boolean onPacketSent(Packet<?> packet) {
        return true;
    }
    public boolean onPacketReceive(Packet<?> packet) {
        return true;
    }

}
