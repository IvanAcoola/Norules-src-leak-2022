package me.nrules.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReflectUtils {
    private static List fields = new ArrayList();
    private static List methods = new ArrayList();

    public static Method getMethod(Class cl, int ParameterCount, String... names) {
        String[] var3 = names;
        int var4 = names.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            ReflectUtils.HashedMethod hashedMethod = findMethod(cl, s + ParameterCount);
            if (hashedMethod != null) {
                return hashedMethod.getMethod();
            }

            Method[] var8 = cl.getDeclaredMethods();
            int var9 = var8.length;

            for (int var10 = 0; var10 < var9; ++var10) {
                Method m = var8[var10];
                m.setAccessible(true);
                if (m.getName().equals(s) && m.getParameterCount() == ParameterCount) {
                    methods.add(new ReflectUtils.HashedMethod(m, cl, m.getName() + ParameterCount));
                    return m;
                }
            }
        }

        return null;
    }

    public static Method getMethod(Class target, String... names) {
        String[] var2 = names;
        int var3 = names.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            ReflectUtils.HashedMethod hashedMethod = findMethod(target, s);
            if (hashedMethod != null) {
                return hashedMethod.getMethod();
            }

            Method[] var7 = target.getDeclaredMethods();
            int var8 = var7.length;

            for (int var9 = 0; var9 < var8; ++var9) {
                Method method = var7[var9];
                method.setAccessible(true);
                if (method.getName().equals(s)) {
                    methods.add(new ReflectUtils.HashedMethod(method, target, s));
                    return method;
                }
            }
        }

        return null;
    }

    public static Field getField(Class target, String... names) {
        String[] var2 = names;
        int var3 = names.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            ReflectUtils.HashedField hashedField = findField(target, s);
            if (hashedField != null) {
                return hashedField.getField();
            }

            Field[] var7 = target.getDeclaredFields();
            int var8 = var7.length;

            for (int var9 = 0; var9 < var8; ++var9) {
                Field f = var7[var9];
                f.setAccessible(true);
                if (f.getName().equals(s)) {
                    fields.add(new ReflectUtils.HashedField(target, f, s));
                    return f;
                }
            }
        }

        return null;
    }

    private static ReflectUtils.HashedMethod findMethod(Class declared, String name) {
        Iterator var2 = methods.iterator();

        ReflectUtils.HashedMethod hashedMethod;
        int hash;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            hashedMethod = (ReflectUtils.HashedMethod) var2.next();
            hash = 0;
            char[] var5 = name.toCharArray();
            int var6 = var5.length;

            int var7;
            char c;
            for (var7 = 0; var7 < var6; ++var7) {
                c = var5[var7];
                hash += c ^ 16;
            }

            var5 = declared.getName().toCharArray();
            var6 = var5.length;

            for (var7 = 0; var7 < var6; ++var7) {
                c = var5[var7];
                hash += c ^ 16;
            }
        } while (hashedMethod.getHash() != hash);

        return hashedMethod;
    }

    private static ReflectUtils.HashedField findField(Class declared, String name) {
        Iterator var2 = fields.iterator();

        ReflectUtils.HashedField hashedField;
        int hash;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            hashedField = (ReflectUtils.HashedField) var2.next();
            hash = 0;
            char[] var5 = name.toCharArray();
            int var6 = var5.length;

            int var7;
            char c;
            for (var7 = 0; var7 < var6; ++var7) {
                c = var5[var7];
                hash += c ^ 16;
            }

            var5 = declared.getName().toCharArray();
            var6 = var5.length;

            for (var7 = 0; var7 < var6; ++var7) {
                c = var5[var7];
                hash += c ^ 16;
            }
        } while (hashedField.getHash() != hash);

        return hashedField;
    }

    public static class HashedField {
        private int hash;
        private Field field;

        public HashedField(Class declared, Field field, String name) {
            char[] var4 = name.toCharArray();
            int var5 = var4.length;

            int var6;
            char c;
            for (var6 = 0; var6 < var5; ++var6) {
                c = var4[var6];
                this.hash += c ^ 16;
            }

            var4 = declared.getName().toCharArray();
            var5 = var4.length;

            for (var6 = 0; var6 < var5; ++var6) {
                c = var4[var6];
                this.hash += c ^ 16;
            }

            this.field = field;
        }

        public int getHash() {
            return this.hash;
        }

        public Field getField() {
            return this.field;
        }
    }

    public static class HashedMethod {
        private int hash;
        private Method method;

        public HashedMethod(Method method, Class declared, String name) {
            char[] var4 = name.toCharArray();
            int var5 = var4.length;

            int var6;
            char c;
            for (var6 = 0; var6 < var5; ++var6) {
                c = var4[var6];
                this.hash += c ^ 16;
            }

            var4 = declared.getName().toCharArray();
            var5 = var4.length;

            for (var6 = 0; var6 < var5; ++var6) {
                c = var4[var6];
                this.hash += c ^ 16;
            }

            this.method = method;
        }

        public int getHash() {
            return this.hash;
        }

        public Method getMethod() {
            return this.method;
        }
    }

}