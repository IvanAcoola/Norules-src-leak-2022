package me.nrules;

import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;

import java.util.ArrayList;

public class FriendManager {

    public static ArrayList<Friend> friends = new ArrayList();

    public static void addFriend(String name, String alias) {
        friends.add(new Friend(name, alias));
    }

    public static String getAliasName(final String name) {
        String alias = "";
        for (final Friend friend : FriendManager.friends) {
            if (friend.name.equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
                alias = friend.alias;
                break;
            }
        }
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.getGameProfile().getName() == name) {
            return name;
        }
        return alias;
    }

    public static void deleteFriend(String name) {
        for (Friend friend : friends) {
            if (!friend.getName().equalsIgnoreCase(name) && !friend.getAlias().equalsIgnoreCase(StringUtils.stripControlCodes(name))) 
		continue;

            friends.remove(friend);
            break;
        }
    }

    public static boolean isFriend(String name) {
        boolean isFriend = false;
        for (Friend friend : friends) {
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