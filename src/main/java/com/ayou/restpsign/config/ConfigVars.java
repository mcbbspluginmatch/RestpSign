package com.ayou.restpsign.config;

import com.ayou.restpsign.signs.Tpsign;
import com.ayou.restpsign.signs.TpsignVault;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ayou
 * @Date: 2019/7/2 12:10
 */
public class ConfigVars {
    static {
        tpSigns = new HashMap<Block, Tpsign>();
        tpsignVaults = new ArrayList<>();
    }
    public static Map<Block,Tpsign> tpSigns;

    public static int version;
    public static String prefix;
    public static String cooldownMsg;
    public static String noMoney;
    public static String withdrawmoney;
    public static String needText;
    public static String reloadConfig;
    public static String noPerm;
    public static String editDone;
    public static String createDone;
    public static String notexists;
    public static String error;
    public static String notenable;
    public static String notowner;

    public static boolean hookVault;
    public static boolean enable;

    public static String line_1;

    public static int cooldown;
    public static List<TpsignVault> tpsignVaults;

    public static double default_money;
    public static double default_editmoney;
    public static double default_discount;

    public static void loadConfigVars(ConfigurationSection config){
        try {
            if (config != null){
                version = config.getInt("version");
                hookVault = config.getBoolean("hookVault");
                enable = config.getBoolean("enable");

                ConfigurationSection lang = config.getConfigurationSection("Lang");
                if (lang != null){
                    prefix = ChatColor.translateAlternateColorCodes('&',lang.getString("prefix"));
                    cooldownMsg = replaceChatColor(lang.getString("cooldown"));
                    noMoney = replaceChatColor(lang.getString("noMoney"));
                    withdrawmoney = replaceChatColor(lang.getString("withdrawmoney"));
                    needText = replaceChatColor(lang.getString("needText"));
                    reloadConfig = replaceChatColor(lang.getString("reloadConfig"));
                    noPerm = replaceChatColor(lang.getString("noPerm"));
                    editDone = replaceChatColor(lang.getString("editDone"));
                    createDone = replaceChatColor(lang.getString("createDone"));
                    notexists = replaceChatColor(lang.getString("notexists"));
                    error = replaceChatColor(lang.getString("error"));
                    notenable = replaceChatColor(lang.getString("notenable"));
                    notowner = replaceChatColor(lang.getString("notowner"));
                }

                ConfigurationSection settings = config.getConfigurationSection("Settings");
                if (settings != null){
                    cooldown = settings.getInt("cooldown");
                }

                ConfigurationSection vaults = config.getConfigurationSection("Vault");
                if (vaults != null){
                    default_money = vaults.getDouble("default.money");
                    default_editmoney = vaults.getDouble("default.editmoney");
                    default_discount = vaults.getDouble("default.discount");
                }

                ConfigurationSection lines = config.getConfigurationSection("Lines");
                if (lines != null){
                    line_1 = ChatColor.translateAlternateColorCodes('&',lines.getString("Line-1"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static TpsignVault checkVault(Player player){
        if (tpsignVaults.size() >0){
            for (TpsignVault tpsignVault : tpsignVaults) {
                if (player.hasPermission(tpsignVault.getPerm())){
                    return tpsignVault;
                }
            }
        }
        return null;
    }

    public static String replaceChatColor(String msg){
        return prefix + ChatColor.translateAlternateColorCodes('&',msg);
    }
}
