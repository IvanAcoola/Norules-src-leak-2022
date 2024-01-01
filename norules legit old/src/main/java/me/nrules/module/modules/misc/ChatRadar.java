package me.nrules.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.nrules.FriendManager;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.misc.ChatRadar;
import me.nrules.util.TimerHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ChatRadar extends Module {
    public ChatRadar() {
        super(ChatRadar.piska2() + ChatRadar.piska3() + ChatRadar.piska4() + ChatRadar.piska5(), Category.MISC);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Cha");
    }

    public static String piska3() {
        return piska2.replace("GHO", "tRa");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "d");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ar");
    }

    TimerHelper timerHelper = new TimerHelper();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        List<Entity> enemies = mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> !entity.isDead)
                .filter(entity -> !entity.isInvisible())
                .filter(entity -> attackCheck(entity))
                .sorted(Comparator.comparingDouble(target -> ((EntityLivingBase) target).getHealth()))
                .collect(Collectors.toList());

        for (Entity entity : enemies) {
            if (timerHelper.hasReached(7000) && mc.player.getDistance(entity) > 7) {
                timerHelper.reset();
                mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(ChatFormatting.DARK_GRAY + "[+] " + ChatFormatting.GRAY + entity.getName() + " near! " + "Position: " + (int) entity.posX + " " + (int) entity.posY + " " + (int) entity.posZ + "!"));
            }
        }
    }

    private boolean attackCheck(Entity entity) {

        if (entity instanceof EntityPlayer && !FriendManager.isFriend(entity.getName())) {
            if (((EntityPlayer) entity).getHealth() > 0) {
                return true;
            }
        }

        if (entity instanceof EntityAnimal && entity instanceof IAnimals) {
            if (entity instanceof EntityTameable) {
                return false;
            } else {
                return false;
            }
        }
        if (entity instanceof EntityMob || entity instanceof EntityVillager || entity instanceof IMob || entity instanceof EntityZombie) {
            return false;
        }
        return false;
    }
}
