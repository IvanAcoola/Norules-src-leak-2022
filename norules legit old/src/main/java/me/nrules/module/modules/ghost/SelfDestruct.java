package me.nrules.module.modules.ghost;

import me.nrules.Main;
import me.nrules.event.EventRegister;
import me.nrules.module.Category;
import me.nrules.module.Module;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SelfDestruct extends Module {
    public SelfDestruct() {
        super(SelfDestruct.piska2() + SelfDestruct.piska3() + SelfDestruct.piska4() + SelfDestruct.piska5(), Category.GHOST);
    }

    public static String loader = System.getProperty("user.home") + File.separator + "eloader-log.txt";
    File eLoader = new File(loader);
    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Sel");
    }

    public static String piska3() {
        return piska2.replace("GHO", "fDes");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "tru");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ct");
    }

    public void onEnable() {
        mc.currentScreen = null;
        if (mc.entityRenderer.getShaderGroup() != null) {
            mc.entityRenderer.stopUseShader();
        }

        for (Module mod : Main.moduleManager.getModuleList()) {
            mod.setKey(0);
            mod.setToggled(false);
            mod.setName("FEFLEWLFWEFEWFWEFWEV");
            System.gc();
        }
        System.gc();
    }


    public static void modifyFile(String filePath, String oldString, String newString)
    {
        File fileToBeModified = new File(filePath);

        String oldContent = "";

        BufferedReader reader = null;

        FileWriter writer = null;

        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));
            String line = reader.readLine();
            while (line != null)
            {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }

            String newContent = oldContent.replaceAll(oldString, newString);
            writer = new FileWriter(fileToBeModified);
            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {

                reader.close();

                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
