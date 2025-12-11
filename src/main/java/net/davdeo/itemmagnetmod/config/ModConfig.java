package net.davdeo.itemmagnetmod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/item_magnet.json");

    // Controls how far the magnet works
    public int magnetDistance = 7;
    // Controls the durability of the magnet
    public int magnetDurability = 1024;
    // Controls whether the magnet breaks when picking up items
    public boolean isIndestructible = false;

    // Controls whether a Magnet Core can be found in structures
    public boolean canFindInBastion = true;
    public boolean canFindInAncientCity = true;
    public boolean canFindInEndCity = true;
    public boolean canFindInStrongholdLibrary = true;

    private static ModConfig INSTANCE;

    public static ModConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }

    private static ModConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                ModConfig config = GSON.fromJson(reader, ModConfig.class);
                if (config != null) {
                    return config;
                }
            } catch (IOException e) {
                System.err.println("Failed to read config file: " + e.getMessage());
            }
        }

        ModConfig defaultConfig = new ModConfig();
        defaultConfig.save();
        return defaultConfig;
    }

    public void save() {
        CONFIG_FILE.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            System.err.println("Failed to write config file: " + e.getMessage());
        }
    }
}
