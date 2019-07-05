package com.ayou.restpsign.signs;

import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ayou
 * @Date: 2019/7/2 13:07
 */
public class Tpsign implements ConfigurationSerializable {
    private Location location;
    private String residence;
    private String owner;
    private int id;

    public Tpsign(Location location, String residence,String owner, int id) {
        this.location = location;
        this.residence = residence;
        this.owner = owner;
        this.id = id;
    }
//    public TpSign(String world, int x,int y,int z,String residence) {
//        this.location = new Location(Bukkit.getWorld(world),x,y,z);
//        this.residence = residence;
//    }

    public Tpsign(Map<String,Object> map){
        String name = (String) map.get("world");
        double x = (Double) map.get("x");
        double y = (Double) map.get("y");
        double z = (Double) map.get("z");
        this.location = new Location(Bukkit.getWorld(name),x,y,z);
        this.residence = (String) map.get("residence");
        this.owner = (String) map.get("owner");
        this.id = (Integer)map.get("id");
    }

    public String getResidence() {
        return residence;
    }

    public Location getLocation() {
        return location;
    }

    public Block getSign(){
        return location.getBlock();
    }

    public ClaimedResidence getRes(){
        return ResidenceApi.getResidenceManager().getByName(this.residence);
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "Tpsign{" +
                "location=" + location +
                ", residence='" + residence + '\'' +
                ", owner='" + owner + '\'' +
                ", id=" + id +
                '}';
    }

    public Map<String, Object> serialize() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("world",location.getWorld().getName());
        map.put("x",location.getX());
        map.put("y",location.getY());
        map.put("z",location.getZ());
        map.put("residence",residence);
        map.put("owner",owner);
        map.put("id",id);
        return map;
    }

//    public static TpSign valueOf(Map<String,Object> map){
//        String name = (String) map.get("world");
//        Integer x = (Integer) map.get("x");
//        Integer y = (Integer) map.get("y");
//        Integer z = (Integer) map.get("z");
//        Location location = new Location(Bukkit.getWorld(name),x,y,z);
//        String residence = (String) map.get("residence");
//        return new TpSign(name,x,y,z,residence);
//    }
}
