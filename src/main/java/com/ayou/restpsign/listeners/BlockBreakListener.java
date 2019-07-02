package com.ayou.restpsign.listeners;

import com.ayou.restpsign.RestpSign;
import com.ayou.restpsign.config.ConfigVars;
import com.ayou.restpsign.signs.Tpsign;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
                Tpsign tpSign = ConfigVars.tpSigns.get(block);
                RestpSign.getInstance().getConfigManger().getDataConfig().remvoeSign(tpSign);
            }
        }
    }
}
