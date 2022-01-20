package com.matthew4man.crystalrunes.fileConfig;

import com.matthew4man.crystalrunes.CrystalRunes;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrystalsConfig {

    private static File file;
    private static FileConfiguration customFile;

    // Finds or generates custom config file
    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("CrystalRunes").getDataFolder(), "crystal_details.yml");

        // Checks to make sure the file already exists
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("!!! Crystal Details CONFIG COULD NOT BE CREATED");
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    // Returns the Custom File
    public static FileConfiguration get() {
        return customFile;
    }

    // Saves the customFile to normal file
    public static void save() {

        try {
            customFile.save(file);
        } catch (IOException e) {
            System.out.println("Could not save Crystal Details file");
        }
    }

    // Reloads the customFile
    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    // Adds crystal to config file
    public static void addCrystal(int primaryRune1, int primaryRune2, int primaryRune3, int secondaryRune1, int secondaryRune2,
                                  int typePrimary, int typeSecondary, String ownerUUID, String ownerName, int x, int y, int z,
                                  String worldName, double gold, String name, String uuid, int tpStatus, int buildStatus) {


        customFile.createSection(uuid);
        List<String> crystalList = customFile.getStringList(uuid);

        crystalList.add(Integer.toString(primaryRune1));
        crystalList.add(Integer.toString(primaryRune2));
        crystalList.add(Integer.toString(primaryRune3));
        crystalList.add(Integer.toString(secondaryRune1));
        crystalList.add(Integer.toString(secondaryRune2));
        crystalList.add(Integer.toString(typePrimary));
        crystalList.add(Integer.toString(typeSecondary));
        crystalList.add(ownerUUID);
        crystalList.add(ownerName);
        crystalList.add(Integer.toString(x));
        crystalList.add(Integer.toString(y));
        crystalList.add(Integer.toString(z));
        crystalList.add(worldName);
        crystalList.add(Double.toString(gold));
        crystalList.add(name);
        crystalList.add(uuid);
        crystalList.add(Integer.toString(tpStatus));
        crystalList.add(Integer.toString(buildStatus));

        customFile.set(uuid, crystalList);

    }

    // Updates the gold in a crystal
    public static void updateGold(String crystalUUID, Double gold) {
        List<String> crystalList = customFile.getStringList(crystalUUID);

        // Remove the last index for the gold, which is 8
        crystalList.remove(8);
        crystalList.add(String.valueOf(gold));

        customFile.set(crystalUUID, crystalList);
    }

    // Updates the name of a crystal
    public static void updateName(String crystalUUID, String name) {
        List<String> crystalList = customFile.getStringList(crystalUUID);

        crystalList.set(4, name);

        customFile.set(crystalUUID, crystalList);
    }

    // Returns a String list of every crystal's UUID
    public static List<String> getAllCrystals() {
        List<String> crystalList = new ArrayList<>();
        int counter = 0;

        for (String key : customFile.getKeys(false)) {
            crystalList.add(key);
            counter++;
        }

        return crystalList;
    }

    public static List<Integer> getPrimaryRunes(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);

        List<Integer> primaryRuneList = new ArrayList<>();

        primaryRuneList.add(Integer.valueOf(crystalList.get(0)));
        primaryRuneList.add(Integer.valueOf(crystalList.get(1)));
        primaryRuneList.add(Integer.valueOf(crystalList.get(2)));

        return primaryRuneList;
    }

    public static List<Integer> getSecondaryRunes(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);

        List<Integer> secondaryRuneList = new ArrayList<>();

        secondaryRuneList.add(Integer.valueOf(crystalList.get(3)));
        secondaryRuneList.add(Integer.valueOf(crystalList.get(4)));

        return secondaryRuneList;
    }

    public static int getTypePrimary(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);
        return Integer.parseInt(crystalList.get(5));
    }

    public static int getTypeSecondary(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);
        return Integer.parseInt(crystalList.get(6));
    }

    public static String getOwnerUUID(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);
        return crystalList.get(7);
    }

    public static String getOwnerName(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);
        return crystalList.get(8);
    }

    public static int getX(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);
        return Integer.parseInt(crystalList.get(9));
    }

    public static int getY(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);
        return Integer.parseInt(crystalList.get(10));
    }

    public static int getZ(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);
        return Integer.parseInt(crystalList.get(11));
    }

    public static String getWorld(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);
        return crystalList.get(12);
    }

    public static double getGold(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);
        return Double.parseDouble(crystalList.get(13));
    }

    public static String getName(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);
        return crystalList.get(14);
    }

    public static String getUUID(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);
        return crystalList.get(15);
    }

    public static int getTpStatus(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);
        return Integer.parseInt(crystalList.get(16));
    }

    public static int getBuildStatus(String crystalUUID) {
        List<String> crystalList = customFile.getStringList(crystalUUID);
        return Integer.parseInt(crystalList.get(17));
    }

    // Returns all the crystal data in String List form
    public static List<String> getContents(String crystalUUID) {
        List<String> crystalContents = customFile.getStringList(crystalUUID);

        return crystalContents;
    }

    public static void setPrimaryRune1(String crystalUUID, String value) {

        List<String> crystalList = customFile.getStringList(crystalUUID);
        crystalList.set(0, value);
        customFile.set(crystalUUID, crystalList);
    }

    public static void setPrimaryRune2(String crystalUUID, String value) {

        List<String> crystalList = customFile.getStringList(crystalUUID);
        crystalList.set(1, value);
        customFile.set(crystalUUID, crystalList);
    }

    public static void setPrimaryRune3(String crystalUUID, String value) {

        List<String> crystalList = customFile.getStringList(crystalUUID);
        crystalList.set(2, value);
        customFile.set(crystalUUID, crystalList);
    }

    public static void setSecondaryRune1(String crystalUUID, String value) {

        List<String> crystalList = customFile.getStringList(crystalUUID);
        crystalList.set(3, value);
        customFile.set(crystalUUID, crystalList);
    }

    public static void setSecondaryRune2(String crystalUUID, String value) {

        List<String> crystalList = customFile.getStringList(crystalUUID);
        crystalList.set(4, value);
        customFile.set(crystalUUID, crystalList);
    }

    public static void setTypePrimary(String crystalUUID, String value) {

        List<String> crystalList = customFile.getStringList(crystalUUID);
        crystalList.set(5, value);
        customFile.set(crystalUUID, crystalList);
    }

    public static void setTypeSecondary(String crystalUUID, String value) {

        List<String> crystalList = customFile.getStringList(crystalUUID);
        crystalList.set(6, value);
        customFile.set(crystalUUID, crystalList);
    }

    public static void setGold(String crystalUUID, String value) {

        List<String> crystalList = customFile.getStringList(crystalUUID);
        crystalList.set(13, value);
        customFile.set(crystalUUID, crystalList);
    }

    public static void setName(String crystalUUID, String value) {

        List<String> crystalList = customFile.getStringList(crystalUUID);
        crystalList.set(14, value);
        customFile.set(crystalUUID, crystalList);
    }

    public static void setTpStatus(String crystalUUID, String value) {

        List<String> crystalList = customFile.getStringList(crystalUUID);
        crystalList.set(16, value);
        customFile.set(crystalUUID, crystalList);
    }

    public static void setBuildStatus(String crystalUUID, String value) {

        List<String> crystalList = customFile.getStringList(crystalUUID);
        crystalList.set(17, value);
        customFile.set(crystalUUID, crystalList);
    }

    // Removes a crystal from config
    public static void removeCrystal(String crystalUUID) {
        customFile.set(crystalUUID, null);
    }

    // Delete all data
    public static void deleteData() {
        customFile.set("", null);
    }

}
