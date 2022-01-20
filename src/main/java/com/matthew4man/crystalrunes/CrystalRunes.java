package com.matthew4man.crystalrunes;

import com.github.yannicklamprecht.worldborder.api.BorderAPI;
import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import com.matthew4man.crystalrunes.commands.FriendCommands;
import com.matthew4man.crystalrunes.commands.PublicCrystalMaker;
import com.matthew4man.crystalrunes.commands.TestHashMap;
import com.matthew4man.crystalrunes.commands.TestMenuCommand;
import com.matthew4man.crystalrunes.fileConfig.CrystalsConfig;
import com.matthew4man.crystalrunes.fileConfig.FriendConfig;
import com.matthew4man.crystalrunes.listeners.MenuInteract;
import com.matthew4man.crystalrunes.listeners.crystalRuneListeners.*;
import com.matthew4man.crystalrunes.listeners.playerListeners.*;
import com.matthew4man.crystalrunes.listeners.crystalListeners.CrystalBorderVisualizer;
import com.matthew4man.crystalrunes.listeners.crystalListeners.CrystalInteract;
import com.matthew4man.crystalrunes.listeners.crystalListeners.CrystalPlace;
import com.matthew4man.crystalrunes.listeners.crystalListeners.PreventCrystalExplosion;
import com.matthew4man.crystalrunes.objects.Crystal;
import com.matthew4man.crystalrunes.objects.CrystalChunk;
import com.matthew4man.crystalrunes.tickers.CrystalConfigUpdater;
import com.matthew4man.crystalrunes.tickers.CrystalParticles;
import com.matthew4man.crystalrunes.tickers.PlayerInventoryTicker;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public final class CrystalRunes extends JavaPlugin {

    private static CrystalRunes plugin;
    private static HashMap<String, Inventory> inventoryHashMap = new HashMap<>();
    private static HashMap<String, List<Crystal>> crystalMap = new HashMap<>();
    private static HashMap<String, List<CrystalChunk>> chunkMap = new HashMap<>();
    private static HashMap<String, List<String>> friendMap = new HashMap<>();

    private static WorldBorderApi worldBorderApi = BorderAPI.getApi();

    @Override
    public void onEnable() {
        plugin = this;

        MenuManager.setup(getServer(), this);

        loadConfig();
        loadEvents();
        loadCommands();
        loadTickers();
        loadWorldBorderAPI();
        copyConfigToServer();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        updateAllConfigCrystal();
    }

    private void loadCommands() {
        getCommand("menutester").setExecutor(new TestMenuCommand());
        getCommand("hmtester").setExecutor(new TestHashMap());
        getCommand("friend").setExecutor(new FriendCommands());
        getCommand("unfriend").setExecutor(new FriendCommands());
        getCommand("makecrystalpublic").setExecutor(new PublicCrystalMaker());
    }

    private void loadEvents() {
        getServer().getPluginManager().registerEvents(new PlayerInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new CrystalPlace(), this);
        getServer().getPluginManager().registerEvents(new PreventCrystalExplosion(), this);
        getServer().getPluginManager().registerEvents(new CrystalInteract(), this);
        getServer().getPluginManager().registerEvents(new MenuInteract(), this);
        getServer().getPluginManager().registerEvents(new CrystalBorderVisualizer(), this);
        getServer().getPluginManager().registerEvents(new TeleportPlayerLogic(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new FriendInteract(), this);
        getServer().getPluginManager().registerEvents(new AntiTNT(), this);
        getServer().getPluginManager().registerEvents(new AntiCreeper(), this);
        getServer().getPluginManager().registerEvents(new NoFallDamage(), this);
        getServer().getPluginManager().registerEvents(new AntiPVP(), this);
        getServer().getPluginManager().registerEvents(new IncreaseXP(), this);
        getServer().getPluginManager().registerEvents(new IncreaseFarmDrops(), this);
        getServer().getPluginManager().registerEvents(new BlockNaturalChecker(), this);
        getServer().getPluginManager().registerEvents(new IncreaseOreDrops(), this);
        getServer().getPluginManager().registerEvents(new IncreaseMobDrops(), this);
        getServer().getPluginManager().registerEvents(new GoldDropFarm(), this);
        getServer().getPluginManager().registerEvents(new GoldDropMob(), this);
        getServer().getPluginManager().registerEvents(new MonsterHealth(), this);
        getServer().getPluginManager().registerEvents(new AnimalHealth(), this);
        getServer().getPluginManager().registerEvents(new BottleXP(), this);
        getServer().getPluginManager().registerEvents(new InstantGoldTool(), this);
        getServer().getPluginManager().registerEvents(new HasteRune(), this);
        getServer().getPluginManager().registerEvents(new SpawnerUse(), this);
        getServer().getPluginManager().registerEvents(new CobbleGenerator(), this);
        getServer().getPluginManager().registerEvents(new FurnaceSpeed(), this);
        getServer().getPluginManager().registerEvents(new DoubleJump(), this);
    }

    private void loadConfig() {
        // Setup normal config file
        getConfig().options().copyDefaults(); // This also makes plugin create the data folder
        saveDefaultConfig();

        // Setups the crystals config file
        CrystalsConfig.setup();
        CrystalsConfig.get().options().copyDefaults(true);
        CrystalsConfig.save();

        FriendConfig.setup();
        FriendConfig.get().options().copyDefaults(true);
        FriendConfig.save();
    }

    // This only occurs at the start up of the server
    // No need to create Async Tasks
    private void copyConfigToServer() {
        List<String> crystalUUIDs = CrystalsConfig.getAllCrystals();

        for (String crystalUUID : crystalUUIDs) {
            List<Integer> primaryRuneList = CrystalsConfig.getPrimaryRunes(crystalUUID);
            List<Integer> secondaryRuneList = CrystalsConfig.getSecondaryRunes(crystalUUID);
            int typePrimary = CrystalsConfig.getTypePrimary(crystalUUID);
            int typeSecondary = CrystalsConfig.getTypeSecondary(crystalUUID);
            String ownerUUID = CrystalsConfig.getOwnerUUID(crystalUUID);
            String ownerName = CrystalsConfig.getOwnerName(crystalUUID);
            int x = CrystalsConfig.getX(crystalUUID);
            int y = CrystalsConfig.getY(crystalUUID);
            int z = CrystalsConfig.getZ(crystalUUID);
            World world = Bukkit.getWorld(CrystalsConfig.getWorld(crystalUUID));
            double gold = CrystalsConfig.getGold(crystalUUID);
            String name = CrystalsConfig.getName(crystalUUID);
            String uuid = CrystalsConfig.getUUID(crystalUUID);
            int tpStatus = CrystalsConfig.getTpStatus(crystalUUID);
            int buildStatus = CrystalsConfig.getBuildStatus(crystalUUID);
            Location location = new Location(world, x, y, z);

            Crystal crystal = new Crystal(ownerUUID, ownerName, uuid, location);
            crystal.setPrimaryRunes(primaryRuneList);
            crystal.setSecondaryRunes(secondaryRuneList);
            crystal.setTypePrimary(typePrimary);
            crystal.setTypeSecondary(typeSecondary);
            crystal.setGold(gold);
            crystal.setName(name);
            crystal.setUuid(uuid);
            crystal.setTpStatus(tpStatus);
            crystal.setBuildStatus(buildStatus);

            // Crystal Map
            addCrystal(ownerUUID, crystal);

            // Crystal Chunk Map
            int[] offset = {-1, 0, 1};

            for (int xOffset : offset) {
                for (int zOffset : offset) {

                    Chunk chunk = location.getWorld().getChunkAt((int) (location.getX() / 16) + xOffset, (int) (location.getZ() / 16) + zOffset);
                    CrystalChunk crystalChunk = new CrystalChunk(chunk, crystal);
                    CrystalRunes.addCrystalChunk(ownerUUID, crystalChunk);
                }
            }

        }

        List<String> playerUUIDs = FriendConfig.getAllFriends();

        for (String playerUUID : playerUUIDs) {
            List<String> friends = FriendConfig.getFriends(playerUUID);
            friendMap.put(playerUUID, friends);
        }
    }

    private static void configAddFriend(String playerUUID, String friendUUID) {
        new BukkitRunnable() {

            @Override
            public void run() {
                FriendConfig.addFriend(playerUUID, friendUUID);
                FriendConfig.addFriend(friendUUID, playerUUID);

                FriendConfig.save();
                FriendConfig.reload();
            }
        }.runTaskAsynchronously(getPlugin());
    }

    private static void configRemoveFriend(String playerUUID, String friendUUID) {
        new BukkitRunnable() {

            @Override
            public void run() {
                FriendConfig.removeFriend(playerUUID, friendUUID);
                FriendConfig.removeFriend(friendUUID, playerUUID);

                FriendConfig.save();
                FriendConfig.reload();
            }
        }.runTaskAsynchronously(getPlugin());
    }

    public static void updateAllConfigCrystal() {
        List<String> crystalUUIDList = CrystalsConfig.getAllCrystals();

        for (String crystalUUID : crystalUUIDList) {
            CrystalsConfig.removeCrystal(crystalUUID);
        }

        CrystalsConfig.save();
        CrystalsConfig.reload();

        for (List<Crystal> playerCrystalList : CrystalRunes.getCrystalMap().values()) {
            for (Crystal crystal : playerCrystalList) {
                CrystalRunes.configAddCrystal(crystal);
            }
        }

        CrystalsConfig.save();
        CrystalsConfig.reload();

    }

    public static void modifyCrystalConfig(String section, String crystalUUID, String value) {

                switch (section) {
                    case "primaryRune1":
                        CrystalsConfig.setPrimaryRune1(crystalUUID, value);
                        break;
                    case "primaryRune2":
                        CrystalsConfig.setPrimaryRune2(crystalUUID, value);
                        break;
                    case "primaryRune3":
                        CrystalsConfig.setPrimaryRune3(crystalUUID, value);
                        break;
                    case "secondaryRune1":
                        CrystalsConfig.setSecondaryRune1(crystalUUID, value);
                        break;
                    case "secondaryRune2":
                        CrystalsConfig.setSecondaryRune2(crystalUUID, value);
                        break;
                    case "typePrimary":
                        CrystalsConfig.setTypePrimary(crystalUUID, value);
                        break;
                    case "typeSecondary":
                        CrystalsConfig.setTypeSecondary(crystalUUID, value);
                        break;
                    case "tpStatus":
                        CrystalsConfig.setTpStatus(crystalUUID, value);
                        break;
                    case "buildStatus":
                        CrystalsConfig.setBuildStatus(crystalUUID, value);
                        break;
                }

                CrystalsConfig.save();
                CrystalsConfig.reload();

    }

    private void loadWorldBorderAPI() {
        RegisteredServiceProvider<WorldBorderApi> worldBorderApiRegisteredServiceProvider = getServer().getServicesManager().getRegistration(WorldBorderApi.class);

        if (worldBorderApiRegisteredServiceProvider == null) {
            getLogger().info("API not found");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        worldBorderApi = worldBorderApiRegisteredServiceProvider.getProvider();
    }

    public static WorldBorderApi getWorldBorderApi() {
        return worldBorderApi;
    }

    private void loadTickers() {
        PlayerInventoryTicker playerInventoryTicker = new PlayerInventoryTicker();
        playerInventoryTicker.start();

        CrystalParticles crystalParticles = new CrystalParticles();
        crystalParticles.start();

        CrystalConfigUpdater crystalConfigUpdater = new CrystalConfigUpdater();
        crystalConfigUpdater.start();
    }

    public static void addFriend(String playerUUID, String friendUUID) {

        if (!friendMap.containsKey(playerUUID)) {
            List<String> playerFriends = new ArrayList<>();
            friendMap.put(playerUUID, playerFriends);
        }

        if (!friendMap.containsKey(friendUUID)) {
            List<String> playerFriends = new ArrayList<>();
            friendMap.put(friendUUID, playerFriends);
        }

        List<String> playerFriends = getFriends(playerUUID);
        List<String> friendFriends = getFriends(friendUUID);
        playerFriends.add(friendUUID);
        friendFriends.add(playerUUID);

        friendMap.put(playerUUID, playerFriends);
        friendMap.put(friendUUID, friendFriends);
        configAddFriend(playerUUID, friendUUID);
    }

    public static void removeFriend(String playerUUID, String friendUUID) {
        List<String> playerFriends = getFriends(playerUUID);
        List<String> friendFriends = getFriends(friendUUID);
        playerFriends.remove(friendUUID);
        friendFriends.remove(playerUUID);

        friendMap.put(playerUUID, playerFriends);
        friendMap.put(friendUUID, friendFriends);
        configRemoveFriend(playerUUID, friendUUID);
    }

    public static boolean isFriends(String playerUUID, String friendUUID) {

        if (friendMap.isEmpty()) {
            return false;
        }

        List<String> friendList = getFriends(playerUUID);
        if (friendList.contains(friendUUID)) {
            return true;
        }
        return false;
    }

    public static List<String> getFriends(String playerUUID) {
        return friendMap.get(playerUUID);
    }

    public static void addCrystalChunk(String playerUUID, CrystalChunk crystalChunk) {

        List<CrystalChunk> crystalChunkList = new ArrayList<>();

        if (!(chunkMap.containsKey(playerUUID))) {
            chunkMap.put(playerUUID, crystalChunkList);
        }
        chunkMap.get(playerUUID).forEach(x -> {
            crystalChunkList.add(x);
        });
        crystalChunkList.add(crystalChunk);

        chunkMap.put(playerUUID, crystalChunkList);
    }

    public static void removeCrystalChunk(String playerUUID, String crystalUUID) {

        List<CrystalChunk> crystalChunkList = new ArrayList<>();

        chunkMap.get(playerUUID).forEach(x -> {
            if (!(x.getCrystal().getUuid().equals(crystalUUID))) {
                crystalChunkList.add(x);
            }
        });
        chunkMap.put(playerUUID, crystalChunkList);

    }

    public static HashMap<String, List<CrystalChunk>> getCrystalChunkMap() {
        return chunkMap;
    }

    public static void addCrystal(String playerUUID, Crystal crystal) {

        List<Crystal> crystalList = new ArrayList<>();
        crystalList.add(crystal);

        if (crystalMap.containsKey(playerUUID)) {
            crystalMap.get(playerUUID).forEach(x -> {
                crystalList.add(x);
            });
        }
        crystalMap.put(playerUUID, crystalList);

//        configAddCrystal(crystal);
    }

    public static void configAddCrystal(Crystal crystal) {
//        new BukkitRunnable() {
//
//            @Override
//            public void run() {

                int primaryRune1;
                int primaryRune2;
                int primaryRune3;
                int secondaryRune1;
                int secondaryRune2;
                int typePrimary;
                int typeSecondary;

                try {
                    primaryRune1 = crystal.getPrimaryRunes().get(0);
                } catch (Exception e) {
                    primaryRune1 = -1;
                }

                try {
                    primaryRune2 = crystal.getPrimaryRunes().get(1);
                } catch (Exception e) {
                    primaryRune2 = -1;
                }

                try {
                    primaryRune3 = crystal.getPrimaryRunes().get(2);
                } catch (Exception e) {
                    primaryRune3 = -1;
                }

                try {
                    secondaryRune1 = crystal.getSecondaryRunes().get(0);
                } catch (Exception e) {
                    secondaryRune1 = -1;
                }

                try {
                    secondaryRune2 = crystal.getSecondaryRunes().get(1);
                } catch (Exception e) {
                    secondaryRune2 = -1;
                }

                try {
                    typePrimary = crystal.getTypePrimary();
                } catch (Exception e) {
                    typePrimary = -1;
                }

                try {
                    typeSecondary = crystal.getTypeSecondary();
                } catch (Exception e) {
                    typeSecondary = -1;
                }

                String ownerUUID = crystal.getOwnerUUID();
                String ownerName = crystal.getOwnerName();
                int x = crystal.getX();
                int y = crystal.getY();
                int z = crystal.getZ();
                String world = crystal.getWorld().getName();
                double gold = crystal.getGold();
                String name = crystal.getName();
                String uuid = crystal.getUuid();
                int tpStatus = crystal.getTpStatus();
                int buildStatus = crystal.getBuildStatus();

                CrystalsConfig.addCrystal(primaryRune1, primaryRune2, primaryRune3, secondaryRune1, secondaryRune2,
                                          typePrimary, typeSecondary, ownerUUID, ownerName, x, y, z, world, gold,
                                          name, uuid, tpStatus, buildStatus);

                CrystalsConfig.save();
                CrystalsConfig.reload();
//            }
//        }.runTaskAsynchronously(getPlugin());
    }

    public static Crystal getCrystal(String playerUUID, String crystalUUID) {

        final Crystal[] crystal = {null};

        crystalMap.get(playerUUID).forEach(x -> {
            if (x.getUuid().equals(crystalUUID)) {
                crystal[0] = x;
            }
        });

        return crystal[0];
    }

    public static Crystal getCrystal(String crystalUUID) {

        for (List<Crystal> crystalList : crystalMap.values()) {

            for (Crystal crystal : crystalList) {
                if (crystal.getUuid().equals(crystalUUID)) {
                    return crystal;
                }
            }

        }

        return null;

    }

    public static void deleteCrystal(String playerUUID, String crystalUUID) {

        ListIterator<Crystal> iterator = crystalMap.get(playerUUID).listIterator();

        while (iterator.hasNext()) {
            if (iterator.next().getUuid().equals(crystalUUID)) {
                iterator.remove();
            }
        }

        removeCrystalChunk(playerUUID, crystalUUID);
//        configDeleteCrystal(crystalUUID);
    }

    private static void configDeleteCrystal(String crystalUUID) {
        new BukkitRunnable() {

            @Override
            public void run() {
                CrystalsConfig.removeCrystal(crystalUUID);
            }
        }.runTaskAsynchronously(getPlugin());
    }

    public static HashMap<String, List<Crystal>> getCrystalMap() {
        return crystalMap;
    }

    public static void addInventory(String uuid, Inventory inventory) {
        inventoryHashMap.put(uuid, inventory);
    }

    public static Inventory getPlayerInventory(String uuid) {
        return inventoryHashMap.get(uuid);
    }

    public static HashMap<String, Inventory> getInventoryHashMap() {
        return inventoryHashMap;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

}
