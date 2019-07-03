package com.ayou.restpsign.commands.playercommands;

import com.ayou.restpsign.commands.RestpSignCmdManager;
import com.ayou.restpsign.commands.SubCommand;
import com.ayou.restpsign.config.ConfigVars;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author: Ayou
 * @Date: 2019/7/3 20:35
 */
public class HelpCommand extends SubCommand {
    @Override
    public String getName() {
        return "指令帮助";
    }

    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String getPermission() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "指令帮助";
    }

    @Override
    public String[] getArguments() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (!hasPermission(sender)){
            return true;
        }
        sender.sendMessage("§6------------------§eRestpSign 指令帮助§6------------------");
        for (SubCommand subCommand : RestpSignCmdManager.getSubCommands()) {
            if (subCommand.hasPermission(sender,false)){
                String msg = (ConfigVars.replaceChatColor("§a/restp ")+subCommand.getCommand() +" " +Arrays.asList(subCommand.getArguments()) +" §6- §e"+subCommand.getDescription()).replace("[]","");
                sender.sendMessage(msg);
            }
        }
        sender.sendMessage("§6------------------§eRestpSign 指令帮助§6------------------");
        return true;
    }
}
