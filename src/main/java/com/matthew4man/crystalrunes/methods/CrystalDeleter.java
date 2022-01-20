package com.matthew4man.crystalrunes.methods;

import com.matthew4man.crystalrunes.CrystalRunes;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CrystalDeleter {

    public static void delete(Entity entity) {

        try {
            PersistentDataContainer crystalData = entity.getPersistentDataContainer();
            String crystalUUID = crystalData.get(new NamespacedKey(CrystalRunes.getPlugin(), "crystalUUID"), PersistentDataType.STRING);
            String playerUUID = crystalData.get(new NamespacedKey(CrystalRunes.getPlugin(), "playerUUID"), PersistentDataType.STRING);

            CrystalRunes.deleteCrystal(playerUUID, crystalUUID);
        } catch (Exception e) {

        }

        entity.remove();
    }
}
