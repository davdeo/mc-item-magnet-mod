package net.davdeo.itemmagnetmod.config;

import net.minecraft.client.gui.screens.Screen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ModConfig {
    private static final String CONFIG_FILE_PATH = "config/itemmagnetmod.properties";

    public static int magnetDistance = 7;
    public static int magnetDurability = 1024;
    public static boolean isIndestructible = false;
    public static boolean canFindInBastion = true;
    public static boolean canFindInAncientCity = true;
    public static boolean canFindInEndCity = true;
    public static boolean canFindInStrongholdLibrary = true;

    public static void load() {
        Properties properties = new Properties();
        File configFile = new File(CONFIG_FILE_PATH);

        if (configFile.exists()) {
            try (FileInputStream input = new FileInputStream(configFile)) {
                properties.load(input);

                magnetDistance = getInt(properties, "magnetDistance", magnetDistance);
                magnetDurability = getInt(properties, "magnetDurability", magnetDurability);
                isIndestructible = getBool(properties, "isIndestructible", isIndestructible);
                canFindInBastion = getBool(properties, "canFindInBastion", canFindInBastion);
                canFindInAncientCity = getBool(properties, "canFindInAncientCity", canFindInAncientCity);
                canFindInEndCity = getBool(properties, "canFindInEndCity", canFindInEndCity);
                canFindInStrongholdLibrary = getBool(properties, "canFindInStrongholdLibrary", canFindInStrongholdLibrary);

            } catch (IOException e) {
                System.err.println("[ItemMagnet] Failed to load config, using defaults.");
            }
        } else {
            save();
        }
    }

    public static void save() {
        Properties properties = new Properties();
        properties.setProperty("magnetDistance", String.valueOf(magnetDistance));
        properties.setProperty("magnetDurability", String.valueOf(magnetDurability));
        properties.setProperty("isIndestructible", String.valueOf(isIndestructible));
        properties.setProperty("canFindInBastion", String.valueOf(canFindInBastion));
        properties.setProperty("canFindInAncientCity", String.valueOf(canFindInAncientCity));
        properties.setProperty("canFindInEndCity", String.valueOf(canFindInEndCity));
        properties.setProperty("canFindInStrongholdLibrary", String.valueOf(canFindInStrongholdLibrary));

        File configFile = new File(CONFIG_FILE_PATH);
        configFile.getParentFile().mkdirs();

        try (FileOutputStream output = new FileOutputStream(configFile)) {
            properties.store(output, "Item Magnet Mod Configuration");
        } catch (IOException e) {
            System.err.println("[ItemMagnet] Failed to save config!");
        }
    }

    private static int getInt(Properties props, String key, int def) {
        try { return Integer.parseInt(props.getProperty(key, String.valueOf(def))); }
        catch (NumberFormatException e) { return def; }
    }

    private static boolean getBool(Properties props, String key, boolean def) {
        return Boolean.parseBoolean(props.getProperty(key, String.valueOf(def)));
    }
}
