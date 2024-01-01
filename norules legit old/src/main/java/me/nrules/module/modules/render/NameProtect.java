package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;

public class NameProtect extends Module {
    public NameProtect() {
        super(NameProtect.piska2() + NameProtect.piska3() + NameProtect.piska4() + NameProtect.piska5(), Category.RENDER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Na");
    }

    public static String piska3() {
        return piska2.replace("GHO", "me");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "Prote");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ct");
    }
}

