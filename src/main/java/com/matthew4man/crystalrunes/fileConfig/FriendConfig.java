package com.matthew4man.crystalrunes.fileConfig;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FriendConfig {

    private static File file;
    private static FileConfiguration customFile;

    // Finds or generates custom config file
    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("CrystalRunes").getDataFolder(), "friends.yml");

        // Checks to make sure the file already exists
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("!!! CUSTOM CONFIG COULD NOT BE CREATED");
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    // Returns the Custom File
    public static FileConfiguration get() {
        return customFile;
    }

    // Adds user to friend config file
    public static void addUser(String userUUID) {
        customFile.createSection(userUUID);
    }

    // Adds friend UUID to user UUID field
    public static void addFriend(String userUUID, String friendUUID) {
        List<String> friendList = new ArrayList<>();
        friendList = customFile.getStringList(userUUID + ".friends");
        friendList.add(friendUUID);
        customFile.set(userUUID + ".friends", friendList);
    }

    // Removes friend UUID from user UUID field and vice versa
    public static void removeFriend(String userUUID, String friendUUID) {
        List<String> friendList = customFile.getStringList(userUUID + ".friends");
        friendList.remove(friendUUID);
        customFile.set(userUUID + ".friends", friendList);
    }

    // Saves the customFile to normal file
    public static void save() {

        try {
            customFile.save(file);
        } catch (IOException e) {
            System.out.println("Could not save file");
        }
    }

    public static List<String> getFriends(String userUUID) {
        List<String> friendList = customFile.getStringList(userUUID + ".friends");
        return friendList;
    }

    public static List<String> getAllFriends() {
        List<String> friendList = new ArrayList<>();
        int counter = 0;

        for (String key : customFile.getKeys(false)) {
            friendList.add(key);
            counter++;
        }
        System.out.println("Loaded " + counter + " friends");
        return friendList;

    }

    // Reloads the customFile
    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }


}
