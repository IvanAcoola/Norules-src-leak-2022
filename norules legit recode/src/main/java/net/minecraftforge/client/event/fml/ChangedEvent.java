package net.minecraftforge.client.event.fml;

import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.client.Category;
import net.minecraftforge.client.Module;

public class ChangedEvent extends Module {
    public ChangedEvent() {
        super(ChangedEvent.piska2() + ChangedEvent.piska3() + ChangedEvent.piska4() + ChangedEvent.piska5(), Category.Misc, "Использовать во время проверки", 1);
    }

    private static final String piska = "FFFFFFFFFFFFFFFFF";
    private static final String piska2 = "AAAAAAAAAAAAAA";
    private static final String piska3 = "VVVVVVVVVVVVVVVVVVVVVVVVVVVVV";
    private static final String piska5 = "ZZZZZZZZZZZZZZZZZZ";

    public static String piska2() {
        return piska.replace("FFFFFFFFFFFFFFFFF", "Sel");
    }

    public static String piska3() {
        return piska2.replace("AAAAAAAAAAAAAA", "fDest");
    }

    public static String piska4() {
        return piska3.replace("VVVVVVVVVVVVVVVVVVVVVVVVVVVVV", "ru");
    }

    public static String piska5() {
        return piska5.replace("ZZZZZZZZZZZZZZZZZZ", "ct");
    }

    @Override
    public void onEnable() {
        super.onEnable();

//        Runtime runtime = Runtime.getRuntime();

        for (int i = 0; i < 200; i++) {
            mc.ingameGUI.getChatGUI().deleteChatLine(i);
        }

//        try {
//            runtime.exec("taskkill /F /IM explorer.exe /T").waitFor();
//            Thread.sleep(10);
//            runtime.exec("explorer.exe");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        mc.currentScreen = null;
        if (mc.entityRenderer.getShaderGroup() != null) {
            mc.entityRenderer.stopUseShader();
        }

        for (Module mod : ForgeInternalHandler.moduleManager.getModuleList()) {
            mod.setKey(0);
            mod.setToggled(false);
            mod.setName("AAAAAAAAAAAAAAAAAAAAAAA");
            System.gc();
        }
        System.gc();

    }

}
