package com.ayou.restpsign.config;

import com.ayou.restpsign.signs.Tpsign;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

/**
 * @Author: Ayou
 * @Date: 2019/7/2 13:42
 */
public class ConfigManger {
    private static RestpSignConfig config;
    private static RestpSignData dataConfig;

    public ConfigManger() {
        initSerializa();
        config = new RestpSignConfig();
        dataConfig = new RestpSignData();
        ConfigVars.loadConfigVars(config.getConfiguration());
    }

    public RestpSignConfig getConfig() {
        return config;
    }

    public RestpSignData getDataConfig() {
        return dataConfig;
    }

    private void initSerializa(){
        ConfigurationSerialization.registerClass(Tpsign.class);
    }

    public void reloadConfig(){
        config = new RestpSignConfig();
        ConfigVars.loadConfigVars(config.getConfiguration());
    }
}
