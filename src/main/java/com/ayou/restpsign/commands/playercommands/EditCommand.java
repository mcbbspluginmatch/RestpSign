package com.ayou.restpsign.commands.playercommands;

import com.ayou.restpsign.RestpSign;
import com.ayou.restpsign.commands.SubCommand;
import com.ayou.restpsign.config.ConfigUtil;
import com.ayou.restpsign.config.ConfigVars;
import com.ayou.restpsign.config.RestpSignPerms;
import com.ayou.restpsign.signs.Tpsign;
import com.ayou.restpsign.signs.TpsignVault;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Ayou
 * @Date: 2019/7/3 20:37
 */
public class EditCommand extends SubCommand {
    @Override
    public String getName() {
        return "编辑牌子";
    }

    @Override
    public String getCommand() {
        return "edit";
    }

    @Override
    public String getPermission() {
        return "edit";
    }

    @Override
    public String getDescription() {
        return "编辑领地传送牌子";
    }

    @Override
    public String[] getArguments() {
        return new String[]{"领地名称"};
    }

    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (!hasPermission(sender)){
            return true;
        }
        if (!(sender instanceof Player)){
            return true;
        }
        Player player = (Player)sender;
        Block block = player.getTargetBlock((Set<Material>) null,8);
        if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST){
            Sign sign = (Sign)block.getState();
            String resName = args.get(0);
            ClaimedResidence residence = ResidenceApi.getResidenceManager().getByName(resName);
            if (ConfigVars.tpSigns.containsKey(block)){
                if (residence != null){
                    Tpsign tpsign = ConfigVars.tpSigns.get(block);
                    if (tpsign.getOwner() != null){
                        if (! (tpsign.getOwner().equalsIgnoreCase(player.getName()) || ConfigUtil.hasPerm(player,RestpSignPerms.ADMIN) || player.isOp())){
                            player.sendMessage(ConfigVars.notowner.replace("%owner%",tpsign.getOwner()));
                            return true;
                        }
                    }
                    if (ConfigVars.hookVault){
                        double money = ConfigVars.default_editmoney * ConfigVars.default_discount;
                        TpsignVault tpsignVault = ConfigVars.checkVault(player);
                        if (tpsignVault != null){
                            money = tpsignVault.getEditmoney() * tpsignVault.getDiscount();
                        }
                        if (!(player.isOp()) || ConfigUtil.hasPerm(player,RestpSignPerms.ADMIN) || ConfigUtil.hasPerm(player,RestpSignPerms.BYPASS_MONEY)){
                            if (!RestpSign.getInstance().getEconomy().has(player,money)){
                            player.sendMessage(ConfigVars.noMoney.replace("%money%",String.valueOf(money)));
                            return true;
                           }
                            RestpSign.getInstance().getEconomy().withdrawPlayer(player, money);
                            player.sendMessage(ConfigVars.withdrawmoney.replace("%money%", String.valueOf(money)));
                        }
                    }
                    sign.setLine(0,ConfigVars.line_1);
                    sign.setLine(1,resName.length()>=12?resName.substring(0,12):resName);
                    sign.update();
                    tpsign.setResidence(resName);
                    RestpSign.getInstance().getConfigManger().getDataConfig().save();
                    player.sendMessage(ConfigVars.editDone);
                }else{
                    player.sendMessage(ConfigVars.notexists);
                }
            }
        }
        return true;
    }
}
