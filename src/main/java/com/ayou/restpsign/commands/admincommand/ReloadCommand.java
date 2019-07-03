package com.ayou.restpsign.commands.admincommand;

import com.ayou.restpsign.RestpSign;
import com.ayou.restpsign.commands.SubCommand;
import com.ayou.restpsign.config.ConfigVars;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * @Author: Ayou
 * @Date: 2019/7/3 20:37
 */
public class ReloadCommand extends SubCommand {
    @Override
    public String getName() {
        return "重载";
    }

    @Override
    public String getCommand() {
        return "reload";
    }

    @Override
    public String getPermission() {
        return "admin";
    }

    @Override
    public String getDescription() {
        return "重载配置文件";
    }

    @Override
    public String[] getArguments() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (!hasPermission(sender)){
            return false;
        }
        RestpSign.getInstance().getConfigManger().reloadConfig();
        sender.sendMessage(ConfigVars.reloadConfig);
        return true;
    }
}
