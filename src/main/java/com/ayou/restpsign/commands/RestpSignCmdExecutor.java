package com.ayou.restpsign.commands;

import com.ayou.restpsign.config.ConfigVars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author: Ayou
 * @Date: 2019/7/3 20:26
 */
public class RestpSignCmdExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("restp")){
            return false;
        }
        if (args.length < 1){
            return false;
        }
        String subcmd = args[0];
        ArrayList<String> cmds = new ArrayList<String>(Arrays.asList(args));
        cmds.remove(0);

        for (SubCommand subCommand : RestpSignCmdManager.getSubCommands()){
            if (subCommand.getCommand().equalsIgnoreCase(subcmd)){
                if (subCommand.getArguments().length > cmds.size()){
                    sender.sendMessage(ConfigVars.error);
                    return false;
                }
                boolean b = subCommand.execute(sender,cmds);
                return b;
            }
        }
        return false;
    }
}
