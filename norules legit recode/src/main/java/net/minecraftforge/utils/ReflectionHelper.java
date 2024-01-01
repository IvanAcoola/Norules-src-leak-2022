package net.minecraftforge.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class ReflectionHelper {
    public static MethodHandles.Lookup lookup;

    public static MethodHandles.Lookup lookup() {
        return lookup;
    }

    public static Field getField(Class class_, String... arrstring) {
        for (Field field : class_.getDeclaredFields()) {
            field.setAccessible(true);
            for (String string : arrstring) {
                if (!field.getName().equals(string)) {
                    continue;
                }
                return field;
            }
        }
        return null;
    }

    public static double getRenderPosX() throws Throwable, IllegalAccessException {
        return (double) ReflectionHelper.lookup().unreflectGetter(ReflectionHelper.getField(RenderManager.class, "renderPosX", "field_78725_b", "o")).invoke(Minecraft.getMinecraft().getRenderManager());
    }

    public static double getRenderPosY() throws IllegalAccessException, Throwable {
        return (double) ReflectionHelper.lookup().unreflectGetter(ReflectionHelper.getField(RenderManager.class, "renderPosY", "field_78726_c", "p")).invoke(Minecraft.getMinecraft().getRenderManager());
    }

    public static double getRenderPosZ() throws IllegalAccessException, Throwable {
        return (double) ReflectionHelper.lookup().unreflectGetter(ReflectionHelper.getField(RenderManager.class, "renderPosZ", "field_78723_d", "q")).invoke(Minecraft.getMinecraft().getRenderManager());
    }

    public static void hookField(Field field, Object object, Object object2) throws IllegalAccessException, Throwable {
        ReflectionHelper.lookup().unreflectSetter(field).invoke(object, object2);
    }


}
