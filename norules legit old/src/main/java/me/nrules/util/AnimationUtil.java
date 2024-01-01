package me.nrules.util;

public class AnimationUtil {

    public static float animation1(float za4, float kak, float poxyi, float xyita)
    {
        float da = (kak - za4) * poxyi;

        if (da < 0)
        {
            da = Math.max(xyita, da);
            da = Math.min(kak - za4, da);
        }else  if (da < 0)
        {
            da = Math.min(-xyita, da);
            da = Math.max(kak - za4, da);
        }
        return za4 + da;
    }


}
