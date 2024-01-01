package me.nrules;

import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;

import java.util.ArrayList;

public class AttackManager {

    public static ArrayList<Friend> enemies = new ArrayList();

    public static void addFriend(String name, String alias) {
        enemies.add(new Friend(name, alias));
    }

    public static String getAliasName(final String name) {
        String alias = "";
        for (final Friend enemies : AttackManager.enemies) {
            if (enemies.name.equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
                alias = enemies.alias;
                break;
            }
        }
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.getGameProfile().getName() == name) {
            return name;
        }
        return alias;
    }

    public static void deleteFriend(String name) {
        for (Friend friend : enemies) {
            if (!friend.getName().equalsIgnoreCase(name) && !friend.getAlias().equalsIgnoreCase(StringUtils.stripControlCodes(name)))
                continue;

            enemies.remove(friend);
            break;
        }
    }

    public static boolean isFriend(String name) {
        boolean isFriend = false;
        for (Friend friend : enemies) {
            if (!friend.getName().equalsIgnoreCase(StringUtils.stripControlCodes(name)) && !friend.getAlias().equalsIgnoreCase(StringUtils.stripControlCodes(name))) continue;
            isFriend = true;
            break;
        }
        return isFriend;
    }

    public static class Friend {
        private String name;
        private String alias;

        public Friend(String name, String alias) {
            this.name = name;
            this.alias = alias;
        }

        public String getName() {
            return this.name;
        }
        public String getAlias() {
            return this.alias;
        }
        public void setName(String s) {
            this.name = s;
        }
    }
}