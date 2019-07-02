package com.ayou.restpsign.listeners;

import com.ayou.restpsign.RestpSign;
import org.bukkit.event.Listener;

/**
 * @Author: Ayou
 * @Date: 2019/7/2 12:15
 */
public class BaseListener implements Listener {
    public BaseListener() {
        registerListener();
    }
    private void registerListener(){
        RestpSign.getInstance().getServer().getPluginManager().registerEvents(this,RestpSign.getInstance());
    }
}
