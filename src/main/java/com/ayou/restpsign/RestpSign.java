package com.ayou.restpsign;

import com.ayou.restpsign.commands.TpsignCommand;
import com.ayou.restpsign.config.ConfigManger;
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
        this.getCommand("restp").setExecutor(new TpsignCommand());
        this.configManger = new ConfigManger();
        this.registerListeners();
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

    private void registerListeners(){
        new PlayerInteractListener();
        new SignChangeListener();
        new BlockBreakListener();
    }
}
