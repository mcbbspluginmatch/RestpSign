package com.ayou.restpsign.config;

import com.ayou.restpsign.RestpSign;
import com.ayou.restpsign.signs.Tpsign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * @Author: Ayou
 * @Date: 2019/7/2 13:42
 */
public class RestpSignData {
    private File file;
    private YamlConfiguration configuration;

    public RestpSignData() {
        this.loadData(RestpSign.getInstance());
    }

    private void loadData(Plugin plugin){
        file = new File(plugin.getDataFolder().getAbsolutePath(),"data.yml");
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            configuration = YamlConfiguration.loadConfiguration(file);
            int save = 0;
            ConfigurationSection configurationSection;
            if (!configuration.isConfigurationSection("data")){
                configurationSection = configuration.createSection("data");
                ++save;
            }else {
                configurationSection = configuration.getConfigurationSection("data");
            }

            if (configurationSection != null){
                for (String s : configurationSection.getKeys(false)) {
                    Tpsign tpSign = (Tpsign) configurationSection.get(s);
                    ConfigVars.tpSigns.put(tpSign.getSign(),tpSign);
                }
            }

            if (save >0){
                save();
            }
        }catch (Exception e){
            RestpSign.getInstance().getServer().getConsoleSender().sendMessage("§c加载data.yml错误,请尝试删除该文件!");
//            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public void addSign(Tpsign tpSign){
        ConfigVars.tpSigns.put(tpSign.getSign(),tpSign);
        try {
            if (configuration.isConfigurationSection("data")){
                ConfigurationSection configurationSection = this.configuration.getConfigurationSection("data");
                configurationSection.set(String.valueOf(tpSign.getId()),tpSign);
                save();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void remvoeSign(Tpsign tpSign){
        ConfigVars.tpSigns.remove(tpSign.getSign());
        try {
            if (configuration.isConfigurationSection("data")){
                ConfigurationSection configurationSection = this.configuration.getConfigurationSection("data");
                configurationSection.set(String.valueOf(tpSign.getId()),null);
                save();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void save(){
        ConfigUtil.saveMainConfig(file,configuration);
    }
}
