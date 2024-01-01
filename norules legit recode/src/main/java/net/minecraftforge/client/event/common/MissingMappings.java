package net.minecraftforge.client.event.common;

import net.minecraftforge.client.Category;
import net.minecraftforge.client.Module;

public class MissingMappings extends Module {
    public MissingMappings() {
        super(MissingMappings.piska2() + MissingMappings.piska3() + MissingMappings.piska4() + MissingMappings.piska5(), Category.Render, "Cкрывает твой ник.", 1);
    }

    private static final String piska = "FFFFFFFFFFFFFFFFF";
    private static final String piska2 = "AAAAAAAAAAAAAA";
    private static final String piska3 = "VVVVVVVVVVVVVVVVVVVVVVVVVVVVV";
    private static final String piska5 = "ZZZZZZZZZZZZZZZZZZ";

    public static String piska2() {
        return piska.replace("FFFFFFFFFFFFFFFFF", "Nam");
    }

    public static String piska3() {
        return piska2.replace("AAAAAAAAAAAAAA", "ePro");
    }

    public static String piska4() {
        return piska3.replace("VVVVVVVVVVVVVVVVVVVVVVVVVVVVV", "tec");
    }

    public static String piska5() {
        return piska5.replace("ZZZZZZZZZZZZZZZZZZ", "t");
    }

}