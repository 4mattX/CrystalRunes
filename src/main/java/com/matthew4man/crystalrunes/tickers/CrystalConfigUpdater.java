package com.matthew4man.crystalrunes.tickers;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.fileConfig.CrystalsConfig;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CrystalConfigUpdater {

    public void start() {

        new BukkitRunnable() {

            @Override
            public void run() {

                CrystalRunes.updateAllConfigCrystal();

//                // For every crystal on server
//                for (List<Crystal> playerCrystalList : CrystalRunes.getCrystalMap().values()) {
//                    for (Crystal crystal : playerCrystalList) {
//
//                        try {
//                            CrystalRunes.modifyCrystalConfig("primaryRune1", crystal.getUuid(), String.valueOf(crystal.getPrimaryRunes().get(0)));
//                        } catch (Exception e) {
//                        }
//
//                        try {
//                            CrystalRunes.modifyCrystalConfig("primaryRune2", crystal.getUuid(), String.valueOf(crystal.getPrimaryRunes().get(1)));
//                        } catch (Exception e) {
//                        }
//
//                        try {
//                            CrystalRunes.modifyCrystalConfig("primaryRune3", crystal.getUuid(), String.valueOf(crystal.getPrimaryRunes().get(2)));
//                        } catch (Exception e) {
//                        }
//
//                        try {
//                            CrystalRunes.modifyCrystalConfig("secondaryRune1", crystal.getUuid(), String.valueOf(crystal.getSecondaryRunes().get(0)));
//                        } catch (Exception e) {
//                        }
//
//                        try {
//                            CrystalRunes.modifyCrystalConfig("secondaryRune2", crystal.getUuid(), String.valueOf(crystal.getSecondaryRunes().get(1)));
//                        } catch (Exception e) {
//                        }
//
//                        try {
//                            CrystalRunes.modifyCrystalConfig("typePrimary", crystal.getUuid(), String.valueOf(crystal.getTypePrimary()));
//                        } catch (Exception e) {
//                        }
//
//                        try {
//                            CrystalRunes.modifyCrystalConfig("typeSecondary", crystal.getUuid(), String.valueOf(crystal.getTypeSecondary()));
//                        } catch (Exception e) {
//                        }
//
//                        try {
//                            CrystalRunes.modifyCrystalConfig("tpStatus", crystal.getUuid(), String.valueOf(crystal.getTpStatus()));
//                        } catch (Exception e) {
//                        }
//
//                        try {
//                            CrystalRunes.modifyCrystalConfig("buildStatus", crystal.getUuid(), String.valueOf(crystal.getBuildStatus()));
//                        } catch (Exception e) {
//                        }
//
//                    }
//                }
            }
        }.runTaskTimerAsynchronously(CrystalRunes.getPlugin(), 0, 1200);


    }

}
