package me.nrules.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

public class MathUtilsNR {

    public static double easeInOutQuad(double x) {
        return x < 0.5 ? (2 * x * x) : 1 - Math.pow(-2 * x + 2, 2) / 2;
    }

    public static int ceilToPOT(int valueIn) {
        int i = valueIn;
        i = (--i | i >> 1);
        i |= i >> 2;
        i |= i >> 4;
        i |= i >> 8;
        i |= i >> 16;
        return ++i;
    }


    public static double random(double min, double max) {
        return Math.random() * (max - min) + min;
    }


    public static double roundToDecimalPlace(double value, double inc) {
        double halfOfInc = inc / 2.0D;
        double floored = StrictMath.floor(value / inc) * inc;
        if (value >= floored + halfOfInc)
            return (new BigDecimal(StrictMath.ceil(value / inc) * inc, MathContext.DECIMAL64))
                    .stripTrailingZeros()
                    .doubleValue();
        return (new BigDecimal(floored, MathContext.DECIMAL64))
                .stripTrailingZeros()
                .doubleValue();
    }

    public static Vec3d interpolateEntity(Entity entity) {
        double partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks,
                entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks,
                entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks);
    }

    public static Vec3d interpolateVec3d(Vec3d current, Vec3d last, float partialTicks) {
        return current.subtract(last).scale(partialTicks).add(last);
    }


    public static float getRandomInRange(float min, float max) {
        Random random = new Random();
        float range = max - min;
        float scaled = random.nextFloat() * range;
        float shifted = scaled + min;
        return shifted;
    }

    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }


}
