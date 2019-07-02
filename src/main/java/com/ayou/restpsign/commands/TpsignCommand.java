package com.ayou.restpsign.commands;

import com.ayou.restpsign.RestpSign;
import com.ayou.restpsign.config.ConfigVars;
import com.ayou.restpsign.signs.Tpsign;
import com.ayou.restpsign.signs.TpsignVault;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @Author: Ayou
 * @Date: 2019/7/2 19:34
 */
public class TpsignCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("restp")){
            if (args.length >0){
                 if (args[0].equalsIgnoreCase("reload")){
                     if (sender.isOp() || sender.hasPermission("restpsign.admin")){
                         RestpSign.getInstance().getConfigManger().reloadConfig();
                         sender.sendMessage(ConfigVars.reloadConfig);
                     }else {
                         sender.sendMessage(ConfigVars.noPerm);
                         return true;
                     }
                }else if (args[0].equalsIgnoreCase("help")){
                     sender.sendMessage("§6--------------§eRestpSign 指令帮助§6--------------");
                     sender.sendMessage(ConfigVars.replaceChatColor(" &a/restp edit <领地名称> &6- &e修改传送领地!"));
                     sender.sendMessage(ConfigVars.replaceChatColor(" &a/restp reload &6- &e重载配置文件!"));
                     sender.sendMessage("§6--------------§eRestpSign 指令帮助§6--------------");
                 } else if (args[0].equalsIgnoreCase("edit")){
                     if (sender instanceof Player){
                         Player player = (Player)sender;
                         if (!player.hasPermission("restpsign.create")){
                             player.sendMessage(ConfigVars.noPerm);
                             return true;
                         }
                         if (args.length <= 1){
                            player.sendMessage(ConfigVars.needText);
                         }else{
                                 Block block = player.getTargetBlock(null,8);
                                 if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST){
                                         Sign sign = (Sign)block.getState();
                                         ClaimedResidence residence = ResidenceApi.getResidenceManager().getByName(args[1]);
                                         if (ConfigVars.tpSigns.containsKey(block)){
                                             if (residence != null){
                                                 double money = ConfigVars.default_editmoney * ConfigVars.default_discount;
                                                 TpsignVault tpsignVault = ConfigVars.checkVault(player);
                                                 if (tpsignVault != null){
                                                     money = tpsignVault.getEditmoney() * tpsignVault.getDiscount();
                                                 }
                                                 if (RestpSign.getInstance().getEconomy().has(player,money) || player.isOp() || player.hasPermission("restpsign.admin")) {
                                                     sign.setLine(0,ConfigVars.line_1);
                                                     sign.setLine(1,args[1].length()>=12?args[1].substring(0,12):args[1]);
                                                     sign.update();

                                                     Tpsign tpsign = ConfigVars.tpSigns.get(block);
                                                     tpsign.setResidence(args[1]);
                                                     RestpSign.getInstance().getConfigManger().getDataConfig().save();
                                                     if (!(player.isOp() || player.hasPermission("restpsign.admin"))){
                                                         RestpSign.getInstance().getEconomy().withdrawPlayer(player, money);
                                                         player.sendMessage(ConfigVars.withdrawmoney.replace("%money%", String.valueOf(money)));
                                                     }else{
                                                         player.sendMessage(ConfigVars.editDone);
                                                     }
                                                 }else{
                                                     player.sendMessage(ConfigVars.noMoney.replace("%money%",String.valueOf(money)));
                                                 }
                                             }else{
                                                 player.sendMessage(ConfigVars.notexists);
                                             }
                                         }
                             }
                         }
                     }
                 }
            }else{
                return false;
            }
        }
        return true;
    }
}
