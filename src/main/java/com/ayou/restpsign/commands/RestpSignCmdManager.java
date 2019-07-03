package com.ayou.restpsign.commands;

import com.ayou.restpsign.commands.admincommand.ReloadCommand;
import com.ayou.restpsign.commands.playercommands.EditCommand;
import com.ayou.restpsign.commands.playercommands.HelpCommand;
import com.ayou.restpsign.commands.playercommands.SubzonesCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ayou
 * @Date: 2019/7/3 20:33
 */
public class RestpSignCmdManager {
    private static List<SubCommand> subCommands;

    public RestpSignCmdManager() {
        subCommands = new ArrayList<>();
        this.onRegister();
    }

    private void onRegister(){
        subCommands.add(new HelpCommand());
        subCommands.add(new EditCommand());
        subCommands.add(new SubzonesCommand());
        subCommands.add(new ReloadCommand());
    }

    public static List<SubCommand> getSubCommands() {
        return subCommands;
    }
}
