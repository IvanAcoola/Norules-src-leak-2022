package me.nrules.module.modules.fun;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.MathUtilsNR;
import me.nrules.util.TimerHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Random;

public class ChatSpammer extends Module {

    ArrayList<String> message;
    TimerHelper timerHelper = new TimerHelper();

    public ChatSpammer() {
        super(ChatSpammer.piska2() + ChatSpammer.piska3() + ChatSpammer.piska4() + ChatSpammer.piska5(), Category.FUN);
        message = new ArrayList<>();
        message.add("нр лучшее решение" + " [" + getRandomString(5 + MathUtilsNR.getRandomInRange(1, 3)) + "]");
        message.add("нр лучший клиент " + " [" + getRandomString(4 + MathUtilsNR.getRandomInRange(1, 3)) + "]");
        message.add("нр юзеры крутые!" + " [" + getRandomString(3 + MathUtilsNR.getRandomInRange(1, 3)) + "]");
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Cha");
    }
    public static String piska3() {
        return piska2.replace("GHO", "tSp");
    }
    public static String piska4() {
        return piska3.replace("UIOL", "am");
    }
    public static String piska5() {
        return piska5.replace("NGHO", "mer");
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (timerHelper.hasReached(5000)) {
            Random r = new Random();
            int index = r.nextInt(message.size());
            String messages = message.get(index);

            mc.player.sendChatMessage(messages);
            timerHelper.reset();
        }

    }

    public static String getRandomString(double d) {
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890?$#@()&^%$!?>:,";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < d; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
