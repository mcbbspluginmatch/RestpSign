package com.ayou.restpsign.listeners;

import com.ayou.restpsign.RestpSign;
import com.ayou.restpsign.config.ConfigVars;
import com.ayou.restpsign.signs.Tpsign;
import com.ayou.restpsign.signs.TpsignVault;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.api.ResidenceInterface;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;

/**
 * @Author: Ayou
 * @Date: 2019/7/2 12:20
 */
public class SignChangeListener extends BaseListener {
    @EventHandler
    public void onSignChange(SignChangeEvent event){
        String[] lines = event.getLines();
        if (lines.length >=2){
            String text = lines[0];
            if (text.startsWith("[") && text.contains("restp") & text.endsWith("]")){
                text = lines[1];
                if (text != null){
                    Player player = event.getPlayer();
                    ResidenceInterface residenceManager = ResidenceApi.getResidenceManager();
                    ClaimedResidence residence = residenceManager.getByName(text);
                    if (residence != null){
                        double money = ConfigVars.default_money * ConfigVars.default_discount;
                        TpsignVault tpsignVault = ConfigVars.checkVault(player);
                        if (tpsignVault != null){
                            money = tpsignVault.getMoney() * tpsignVault.getDiscount();
                        }
                        if (RestpSign.getInstance().getEconomy().has(player,money)){
                            event.setLine(0,ConfigVars.line_1);

                            if (!ConfigVars.tpSigns.containsKey(event.getBlock())){
                                Tpsign tpSign = new Tpsign(event.getBlock().getLocation(),text, (int) (System.currentTimeMillis()/1000));
                                RestpSign.getInstance().getConfigManger().getDataConfig().addSign(tpSign);
                                if (!(player.isOp() || player.hasPermission("restpsign.admin"))){
                                    RestpSign.getInstance().getEconomy().withdrawPlayer(player,money);
                                    player.sendMessage(ConfigVars.withdrawmoney.replace("%money%",String.valueOf(money)));
                                }else{
                                    player.sendMessage(ConfigVars.createDone);
                                }
                            }

                            return;
                        }else{
                            player.sendMessage(ConfigVars.noMoney.replace("%money%",String.valueOf(money)));
                        }
                    }
                }
            }
        }
    }
}
