package com.ayou.restpsign.listeners;

import com.ayou.restpsign.RestpSign;
import com.ayou.restpsign.config.ConfigUtil;
import com.ayou.restpsign.config.ConfigVars;
import com.ayou.restpsign.config.RestpSignPerms;
import com.ayou.restpsign.signs.Tpsign;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @Author: Ayou
 * @Date: 2019/7/2 15:47
 */
public class BlockBreakListener extends BaseListener {
    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST){
            if (ConfigVars.tpSigns.containsKey(block)){
                Player player = event.getPlayer();
                if (event.isCancelled()){
                    return;
                }
                if (!ConfigVars.enable){
                    player.sendMessage(ConfigVars.notexists);
                    event.setCancelled(true);
                    return;
                }
                Tpsign tpSign = ConfigVars.tpSigns.get(block);
                if (tpSign.getOwner() != null){
                    if (!(tpSign.getOwner().equalsIgnoreCase(player.getName()) || ConfigUtil.hasPerm(player,RestpSignPerms.ADMIN) || player.isOp())){
                        player.sendMessage(ConfigVars.notowner.replace("%owner%",tpSign.getOwner()));
                        event.setCancelled(true);
                        return;
                    }
                }
                RestpSign.getInstance().getConfigManger().getDataConfig().remvoeSign(tpSign);
            }
        }
    }
}
