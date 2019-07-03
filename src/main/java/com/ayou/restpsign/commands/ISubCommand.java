package com.ayou.restpsign.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * @Author: Ayou
 * @Date: 2019/7/3 20:25
 */
public interface ISubCommand {
    public String getCommand();

    public String getPermission();

    public String getName();

    public String getDescription();

    public boolean hasPermission(CommandSender sender);


    public String[] getArguments();

    public boolean execute(CommandSender sender, ArrayList<String> args);
}
