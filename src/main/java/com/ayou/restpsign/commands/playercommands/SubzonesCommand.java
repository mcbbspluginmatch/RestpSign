package com.ayou.restpsign.commands.playercommands;

import com.ayou.restpsign.commands.SubCommand;
import com.ayou.restpsign.config.ConfigVars;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * @Author: Ayou
 * @Date: 2019/7/3 20:37
 */
public class SubzonesCommand extends SubCommand {
    @Override
    public String getName() {
        return "子领地";
    }

    @Override
    public String getCommand() {
        return "subzones";
    }

    @Override
    public String getPermission() {
        return "subzones";
    }

    @Override
    public String getDescription() {
        return "显示领地中的子领地";
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
        ClaimedResidence residence;
        if (args.size() >0 && args.get(0) != null){
            String resName = args.get(0);
            residence = ResidenceApi.getResidenceManager().getByName(resName);
            if (residence == null){
                sender.sendMessage(ConfigVars.notexists);
                sender.sendMessage(ConfigVars.replaceChatColor("&a/restp subzones [领地] &6- &e显示特定领地子领地"));
                return true;
            }
            sendSubzones(sender,residence);
            return true;
        }
        if (!(sender instanceof Player)){
            return true;
        }
        Player player = (Player) sender;
        residence = ResidenceApi.getResidenceManager().getByLoc(player.getLocation());
        if (residence == null){
            sender.sendMessage(ConfigVars.notexists);
            return true;
        }
        sendSubzones(sender,residence);
        return true;
    }

    private void sendSubzones(CommandSender sender,ClaimedResidence residence){
        sender.sendMessage(ConfigVars.replaceChatColor("&6领地: &e"+residence.getName() +" &6子领地数目: &e"+residence.getSubzones().size()));
        StringBuilder stringBuilder = new StringBuilder("§6[");
        String subzones = StringUtils.join(residence.getSubzoneList(),",");
        stringBuilder.append("§e"+subzones).append("&6]");
        sender.sendMessage(ConfigVars.replaceChatColor(stringBuilder.toString()));
    }
}
