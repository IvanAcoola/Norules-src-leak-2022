package me.nrules.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class Reflections {

    public static MethodHandles.Lookup lookup;

    public static boolean exists(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    public static final Field pressed = findField(KeyBinding.class, "field_74513_e", "pressed");

    static {
        pressed.setAccessible(true);
    }

    public static Field findField(Class<?> clazz, String... fieldNames) {
        Exception failed = null;
        for (String fieldName : fieldNames) {
            try {
                Field f = clazz.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } catch (Exception e) {
                failed = e;
            }
        }
        throw new IllegalArgumentException(Arrays.toString(fieldNames) + " failed: " + failed);
    }

    public static <T, E> void setPrivateValue(Class<? super T> classToAccess, @Nullable T instance, @Nullable E value, String... fieldName) {
        try {
            findField(classToAccess, fieldName).set(instance, value);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T, E> E getPrivateValue(Class<? super T> classToAccess, @Nullable T instance, String... fieldName) {
        try {
            return (E)findField(classToAccess, fieldName).get(instance);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
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

    public static MethodHandles.Lookup lookup() {
        return lookup;
    }

    public static double getRenderPosX() throws Throwable, IllegalAccessException {
        return (double) lookup().unreflectGetter(Objects.requireNonNull(getField(RenderManager.class, "renderPosX", "field_78725_b", "o"))).invoke(Minecraft.getMinecraft().getRenderManager());
    }

    public static double getRenderPosY() throws IllegalAccessException, Throwable {
        return (double) lookup().unreflectGetter(Objects.requireNonNull(getField(RenderManager.class, "renderPosY", "field_78726_c", "p"))).invoke(Minecraft.getMinecraft().getRenderManager());
    }

    public static double getRenderPosZ() throws IllegalAccessException, Throwable {
        return (double) lookup().unreflectGetter(Objects.requireNonNull(getField(RenderManager.class, "renderPosZ", "field_78723_d", "q"))).invoke(Minecraft.getMinecraft().getRenderManager());
    }
}