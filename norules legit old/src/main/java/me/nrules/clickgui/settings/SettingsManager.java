package me.nrules.clickgui.settings;

import me.nrules.module.Module;

import java.util.ArrayList;


public class SettingsManager {

    private ArrayList<Setting> settings;

    public SettingsManager(){
        this.settings = new ArrayList<>();
    }

    public void rSetting(Setting in){
        this.settings.add(in);
    }

    public ArrayList<Setting> getSettings(){
        return this.settings;
    }

    public boolean hasSettings(Module mod) {
        ArrayList<Setting> out = new ArrayList<>();
        for(Setting s : getSettings()){
            if(s.getParentMod().equals(mod)){
                out.add(s);
            }
        }
        if(out.isEmpty()){
            return false;
        }
        return true;
    }

    public ArrayList<Setting> getSettingsByMod(Module mod){
        ArrayList<Setting> out = new ArrayList<>();
        for(Setting s : getSettings()){
            if(s.getParentMod().equals(mod)){
                out.add(s);
            }
        }
        if(out.isEmpty()){
            return null;
        }
        return out;
    }

    public Setting getSettingByName(String name){
        for(Setting set : getSettings()){
            if(set.getName().equalsIgnoreCase(name)){
                return set;
            }
        }
        return null;
    }

    public Setting getSettingByName(Module mod, String name){
        for(Setting set : getSettings()){
            if(set.getName().equalsIgnoreCase(name) && set.getParentMod() == mod){
                return set;
            }
        }
        System.err.println("[Artemis] Error Setting NOT found: '" + name +"'!");
        return null;
    }

}