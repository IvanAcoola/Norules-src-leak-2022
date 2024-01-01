package me.nrules.util;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;

public class PlayerControllerUtil {
    public static float getCurBlockDamageMP() {
		float getCurBlockDamageMP = 0;
		try {
			Field field = ReflectionFields.curBlockDamageMP;
			field.setAccessible(true);
			getCurBlockDamageMP =  field.getFloat(Minecraft.getMinecraft().playerController);	
		} catch (Exception ignored) {}
		return getCurBlockDamageMP;
	}

    public static void setBlockHitDelay(final int blockHitDelay) {
    	try {
    		Field field = ReflectionFields.blockHitDelay;
        	field.setAccessible(true);
        	field.setInt(Minecraft.getMinecraft().playerController, blockHitDelay);
    	} catch (Exception ignored) {}
    }
}
