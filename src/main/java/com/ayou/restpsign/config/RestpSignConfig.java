package com.ayou.restpsign.config;

import com.ayou.restpsign.RestpSign;
import com.ayou.restpsign.signs.TpsignVault;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: Ayou
 * @Date: 2019/7/2 13:42
 */
public class RestpSignConfig {
    private File file;
    private YamlConfiguration configuration;

    public RestpSignConfig() {
        this.loadConfig(RestpSign.getInstance());
    }

    private void loadConfig(Plugin plugin){
        file = new File(plugin.getDataFolder().getAbsolutePath());
        if (!file.exists() || !file.isDirectory()){
            file.mkdir();
        }
        file = new File(plugin.getDataFolder().getAbsolutePath(),"config.yml");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            configuration = YamlConfiguration.loadConfiguration(file);
            int save = 0;

            save += ConfigUtil.setDefaultIfNotSet(configuration,"version",plugin.getDescription().getVersion());
            save += ConfigUtil.setDefaultIfNotSet(configuration,"hookVault",true);
            save += ConfigUtil.setDefaultIfNotSet(configuration,"enable",true);
            save += ConfigUtil.setDefaultIfNotSet(configuration,"stats",true);

            ConfigurationSection lang;
            if (!configuration.isConfigurationSection("Lang")){
                lang = configuration.createSection("Lang");
                ++save;
            }else{
                lang = configuration.getConfigurationSection("Lang");
            }
            if (lang != null){
                save += ConfigUtil.setDefaultIfNotSet(lang,"prefix","&e[RestpSign] >>");
                save += ConfigUtil.setDefaultIfNotSet(lang,"cooldown","&c冷却中...%time%");
                save += ConfigUtil.setDefaultIfNotSet(lang,"noMoney","&c你没有足够的金钱,至少需要%money%.");
                save += ConfigUtil.setDefaultIfNotSet(lang,"withdrawmoney","&a成功创建传送牌子,扣除%money%.");
                save += ConfigUtil.setDefaultIfNotSet(lang,"needText","&c正确使用方法 &e/restp edit <领地名称>");
                save += ConfigUtil.setDefaultIfNotSet(lang,"reloadConfig","&a成功重载配置文件!");
                save += ConfigUtil.setDefaultIfNotSet(lang,"noPerm","&c你没有足够的权限!");
                save += ConfigUtil.setDefaultIfNotSet(lang,"editDone","&a成功编辑领地牌子!");
                save += ConfigUtil.setDefaultIfNotSet(lang,"createDone","&a成功创建领地牌子!");
                save += ConfigUtil.setDefaultIfNotSet(lang,"notexists","&c该领地不存在!");
                save += ConfigUtil.setDefaultIfNotSet(lang,"error","&c错误的参数!");
                save += ConfigUtil.setDefaultIfNotSet(lang,"notenable","&c插件功能未被启用,请联系服主咨询!");
                save += ConfigUtil.setDefaultIfNotSet(lang,"notowner","&c你不是该牌子的主人,请联系%owner%!");
            }

            ConfigurationSection settings;
            if (!configuration.isConfigurationSection("Settings")){
                settings = configuration.createSection("Settings");
                ++save;
            }else{
                settings = configuration.getConfigurationSection("Settings");
            }
            if (settings != null){
                save += ConfigUtil.setDefaultIfNotSet(settings,"cooldown",3);
            }

            if (RestpSign.getInstance().getEconomy() != null){
                ConfigurationSection vaults;
                if (!configuration.isConfigurationSection("Vault")){
                    vaults = configuration.createSection("Vault");
                    vaults.set("default.permission","restpsign.default");
                    vaults.set("default.money",500.0d);
                    vaults.set("default.editmoney",300.0d);
                    vaults.set("default.discount",1.0d);
                    ++save;
                }else{
                    vaults = configuration.getConfigurationSection("Vault");
                }
                if (vaults != null){
                    for (String s : vaults.getKeys(false)) {
                        ConfigurationSection section = vaults.getConfigurationSection(s);
                        String perm = section.getString("permission");
                        double money = section.getDouble("money");
                        double editMoney = section.getDouble("editmoney");
                        double discount = section.getDouble("discount");
                        TpsignVault vault = new TpsignVault(perm,money,editMoney,discount);
                        ConfigVars.tpsignVaults.add(vault);
                    }
                    // Collections.reverse() —— 754503921
                    Collections.sort(ConfigVars.tpsignVaults, new Comparator<TpsignVault>() {
                        @Override
                        public int compare(TpsignVault o1, TpsignVault o2) {
                            return -1;
                        }
                    });
                }
            }

            ConfigurationSection lines;
            if (!configuration.isConfigurationSection("Lines")){
                lines = configuration.createSection("Lines");
                lines.set("Line-1","&9[ResTP]");
                ++save;
            }
//            else{
//                lines = configuration.getConfigurationSection("Lines");
//            }

            if (save > 0){
                ConfigUtil.saveMainConfig(file,configuration);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }
}
