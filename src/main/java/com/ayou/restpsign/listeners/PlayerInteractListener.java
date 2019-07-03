package com.ayou.restpsign.listeners;

import com.ayou.restpsign.config.ConfigVars;
import com.ayou.restpsign.signs.Tpsign;
import com.bekvon.bukkit.residence.Residence;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

/**
 * @Author: Ayou
 * @Date: 2019/7/2 12:15
 */
public class PlayerInteractListener extends BaseListener {
    private HashMap<UUID,Long> cooldown = new HashMap<UUID, Long>();
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock() != null && (event.getClickedBlock().getType() == Material.WALL_SIGN || event.getClickedBlock().getType() == Material.SIGN_POST)) {
                Player player = event.getPlayer();
                if (ConfigVars.tpSigns.containsKey(event.getClickedBlock())){
                    if (!ConfigVars.enable){
                        event.getPlayer().sendMessage(ConfigVars.notexists);
                        event.setCancelled(true);
                        return;
                    }
                    if (cooldown.containsKey(player.getUniqueId())){
                        long time = ((cooldown.get(player.getUniqueId()) /1000 )+ ConfigVars.cooldown) - (System.currentTimeMillis() / 1000);
                        if (time <= 0){
                            cooldown.remove(player.getUniqueId());
                        }else{
                            player.sendMessage(ConfigVars.cooldownMsg.replace("%time%",String.valueOf(time)));
                            event.setCancelled(true);
                            return;
                        }
                    }
                    Tpsign tpSign = ConfigVars.tpSigns.get(event.getClickedBlock());
                    tpSign.getRes().tpToResidence(player,player,Residence.getInstance().isResAdminOn(player));
                    if (!(player.isOp() || player.hasPermission("restpsign.admin") || player.hasPermission("restpsign.bypasscooldown"))){
                        if (!cooldown.containsKey(player.getUniqueId())){
                            cooldown.put(player.getUniqueId(),System.currentTimeMillis());
                        }
                    }
                }
            }
        }
    }
}
