package com.ayou.restpsign;

import com.ayou.restpsign.commands.RestpSignCmdExecutor;
import com.ayou.restpsign.commands.RestpSignCmdManager;
import com.ayou.restpsign.config.ConfigManger;
import com.ayou.restpsign.config.ConfigVars;
import com.ayou.restpsign.listeners.BlockBreakListener;
import com.ayou.restpsign.listeners.PlayerInteractListener;
import com.ayou.restpsign.listeners.SignChangeListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @Author: Ayou
 * @Date: 2019/7/2 12:06
 */
public class RestpSign extends JavaPlugin {
    private static RestpSign instance;
    private ConfigManger configManger;
    private Economy economy;
    @Override
    public void onEnable() {
        if (hookVault()){
            this.getLogger().info("Hooked Vault!");
        }
        instance = this;
        this.configManger = new ConfigManger();
//        this.getCommand("restp").setExecutor(new TpsignCommand());
        this.registerCommands();
        this.registerListeners();

        try {
            if (ConfigVars.stats){
                String packageName = Bukkit.getServer().getClass().getPackage().getName();
                String version = packageName.substring(packageName.lastIndexOf(".") + 1);
                if (!version.contains("v1_7")){
                    new MetricsLite(this);
                }
            }
        }catch (Exception e){
            getLogger().info("统计插件信息出错!");
        }
    }

    public static RestpSign getInstance() {
        return instance;
    }

    public ConfigManger getConfigManger() {
        return configManger;
    }

    public Economy getEconomy() {
        return economy;
    }

    private boolean hookVault(){
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null){
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null){
            return false;
        }
        this.economy = rsp.getProvider();
        return this.economy != null;
    }

    private void registerCommands(){
        new RestpSignCmdManager();
        this.getCommand("restp").setExecutor(new RestpSignCmdExecutor());
    }


    private void registerListeners(){
        new PlayerInteractListener();
        new SignChangeListener();
        new BlockBreakListener();
    }
}
