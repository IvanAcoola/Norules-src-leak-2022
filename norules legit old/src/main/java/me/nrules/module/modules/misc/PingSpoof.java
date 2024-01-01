package me.nrules.module.modules.misc;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.TimerHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketKeepAlive;

import java.util.ArrayList;


public class PingSpoof extends Module {
    public PingSpoof() {
        super(PingSpoof.piska2() + PingSpoof.piska3() + PingSpoof.piska4() + PingSpoof.piska5(), Category.MISC);
        Main.settingsManager.rSetting(new Setting("Ping", this, 100, 1.0D, 25000, true));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Pin");
    }

    public static String piska3() {
        return piska2.replace("GHO", "gS");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "poo");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "f");
    }

    public static TimerHelper timerHelper = new TimerHelper();
    ArrayList<CPacketKeepAlive> packets = new ArrayList<CPacketKeepAlive>();
    boolean save = true;

    @Override
    public void onEnable() {
        save = true;
        packets.clear();
        super.onEnable();
    }

    public boolean onPacketSent(Packet<?> packet) {
        if (this.isToggled()) {
            int ping = (int) Main.settingsManager.getSettingByName("Ping").getValDouble();
            if (packet instanceof CPacketKeepAlive && save) {
                packets.add((CPacketKeepAlive) packet);
                return false;
            }
            if (save && !packets.isEmpty()) {
                save = false;
                Runnable run = () -> {
                    try {
                        for (CPacketKeepAlive packetAye : packets) {
                            Thread.sleep(ping);
                            mc.getConnection().sendPacket(packetAye);
                            packets.remove(packetAye);
                        }
                        packets.clear();
                        save = true;
                    } catch (Exception e) {
                    }
                };
                Thread thread = new Thread(run, "ping");
                thread.start();
            }
            if (packets.isEmpty() && !save) {
                Runnable run = () -> {
                    try {
                        Thread.sleep(20);
                        save = true;
                    } catch (Exception e) {
                    }
                };
                Thread thread = new Thread(run, "toSave");
                thread.start();
                return true;
            }
        }
        return true;
    }
}
