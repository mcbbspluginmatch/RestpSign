package com.ayou.restpsign.commands;

import com.ayou.restpsign.config.ConfigVars;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * @Author: Ayou
 * @Date: 2019/7/3 20:26
 */
public abstract class SubCommand implements ISubCommand {
    @Override
    public abstract String getName();

    @Override
    public abstract String getCommand();

    @Override
    public abstract String getPermission();

    @Override
    public abstract String getDescription();
    @Override
    public boolean hasPermission(CommandSender sender) {
        if (!ConfigVars.enable){
            sender.sendMessage(ConfigVars.notexists);
            return false;
        }
        if (!sender.hasPermission("restpsign."+this.getPermission())){
            sender.sendMessage(ConfigVars.noPerm);
            return false;
        }
        return true;
    }

    public boolean hasPermission(CommandSender sender,boolean sendMsg) {
        if (!sender.hasPermission("restpsign."+this.getPermission())){
           if (sendMsg){
               sender.sendMessage(ConfigVars.noPerm);
           }
            return false;
        }
        return true;
    }

    @Override
    public abstract String[] getArguments();

    @Override
    public abstract boolean execute(CommandSender sender, ArrayList<String> args);
}
