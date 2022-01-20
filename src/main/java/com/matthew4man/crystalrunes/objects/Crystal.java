package com.matthew4man.crystalrunes.objects;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.fileConfig.CrystalsConfig;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class Crystal {

    private List<Integer> primaryRunes = new ArrayList<>();
    private List<Integer> secondaryRunes = new ArrayList<>();
    private int typePrimary;
    private int typeSecondary;
    private String ownerUUID = "";
    private String ownerName = "";
    private int x;
    private int y;
    private int z;
    private Location location;
    private World world;
    private double gold;
    private String name = "";
    private String uuid = "";
    private int tpStatus;
    private int buildStatus;

    public Crystal(String ownerUUID, String ownerName, String uuid, Location location) {

        this.ownerUUID = ownerUUID;
        this.ownerName = ownerName;
        this.x = (int) location.getX();
        this.y = (int) location.getY();
        this.z = (int) location.getZ();
        this.location = location;
        this.world = location.getWorld();
        this.uuid = uuid;
    }

    public List<Integer> getPrimaryRunes() {
        return primaryRunes;
    }

    public void setPrimaryRunes(List<Integer> primaryRunes) {
        this.primaryRunes = primaryRunes;
    }

    public void addPrimaryRune(int rune) {
        this.primaryRunes.add(rune);
    }

    public void addSecondaryRune(int rune) {
        this.secondaryRunes.add(rune);
    }

    public void removePrimaryRune(int rune) {
        this.primaryRunes.remove(rune);
    }

    public void removeSecondaryRune(int rune) {
        this.secondaryRunes.remove(rune);
    }

    public List<Integer> getSecondaryRunes() {
        return secondaryRunes;
    }

    public void setSecondaryRunes(List<Integer> secondaryRunes) {
        this.secondaryRunes = secondaryRunes;
    }

    public void clearSecondaryRunes() {
        List<Integer> emptyList = new ArrayList<>();
        this.setSecondaryRunes(emptyList);
    }

    public void clearPrimaryRunes() {
        List<Integer> emptyList = new ArrayList<>();
        this.setPrimaryRunes(emptyList);
    }

    public String getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(String ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public double getGold() {
        return gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getTypePrimary() {
        return typePrimary;
    }

    public void setTypePrimary(int typePrimary) {
        this.typePrimary = typePrimary;
    }

    public int getTypeSecondary() {
        return typeSecondary;
    }

    public void setTypeSecondary(int typeSecondary) {
        this.typeSecondary = typeSecondary;
    }

    public int getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(int buildStatus) {
        this.buildStatus = buildStatus;
    }

    public int getTpStatus() {
        return tpStatus;
    }

    public void setTpStatus(int tpStatus) {
        this.tpStatus = tpStatus;
    }
}
