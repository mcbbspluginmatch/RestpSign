package com.ayou.restpsign.config;

/**
 * @Author: Ayou
 * @Date: 2019/7/5 20:39
 */
public enum RestpSignPerms {
    //
    ADMIN("admin"),
    CREATE("create"),
    BYPASS_COOLDOWN("bypasscooldown"),
    BYPASS_MONEY("bypassmoney");

    private String perm;
    RestpSignPerms(String perm){
        this.perm = "restpsign."+perm;
    }

    public String getPerm() {
        return perm;
    }
}
