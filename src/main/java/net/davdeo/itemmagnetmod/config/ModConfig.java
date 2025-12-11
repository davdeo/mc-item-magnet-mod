package net.davdeo.itemmagnetmod.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntFieldBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ModConfig {

    private static final String CONFIG_FILE_PATH = "config/itemmagnetmod.properties";

    // Controls how far the magnet works
    public static int magnetDistance = 7;
    // Controls the durability of the magnet
    public static int magnetDurability = 1024;
    // Controls whether the magnet breaks when picking up items
    public static boolean isIndestructible = false;

    // Controls whether a Magnet Core can be found in structures
    public static boolean canFindInBastion = true;
    public static boolean canFindInAncientCity = true;
    public static boolean canFindInEndCity = true;
    public static boolean canFindInStrongholdLibrary = true;

    public static void loadConfig() {
        Properties properties = new Properties();
        File configFile = new File(CONFIG_FILE_PATH);

        if (configFile.exists()) {
            try (FileInputStream input = new FileInputStream(configFile)) {
                properties.load(input);

                magnetDistance = Integer.parseInt(properties.getProperty("magnetDistance", String.valueOf(magnetDistance)));
                magnetDurability = Integer.parseInt(properties.getProperty("magnetDurability", String.valueOf(magnetDurability)));
                isIndestructible = Boolean.parseBoolean(properties.getProperty("isIndestructible", String.valueOf(isIndestructible)));
                canFindInBastion = Boolean.parseBoolean(properties.getProperty("canFindInBastion", String.valueOf(canFindInBastion)));
                canFindInAncientCity = Boolean.parseBoolean(properties.getProperty("canFindInAncientCity", String.valueOf(canFindInAncientCity)));
                canFindInEndCity = Boolean.parseBoolean(properties.getProperty("canFindInEndCity", String.valueOf(canFindInEndCity)));
                canFindInStrongholdLibrary = Boolean.parseBoolean(properties.getProperty("canFindInStrongholdLibrary", String.valueOf(canFindInStrongholdLibrary)));

            } catch (IOException | NumberFormatException e) {
                System.err.println("Failed to load config file. Using defaults. " + e.getMessage());
                saveConfig();
            }
        } else {
            saveConfig();
        }
    }

    public static void saveConfig() {
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
            System.err.println("Failed to save config file: " + e.getMessage());
        }
    }

    public static Screen setupCloth(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setTitle(Component.translatable("title.itemmagnetmod.config"))
                .setParentScreen(parent)
                .setSavingRunnable(ModConfig::saveConfig);

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("category.itemmagnetmod.general"));
        ConfigCategory world = builder.getOrCreateCategory(Component.translatable("category.itemmagnetmod.world_gen"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        IntFieldBuilder magnetDistanceField = entryBuilder.startIntField(Component.translatable("option.itemmagnetmod.magnet_distance"), magnetDistance)
                .setDefaultValue(7)
                .setTooltip(Component.translatable("tooltip.itemmagnetmod.magnet_distance"))
                .setSaveConsumer(newValue -> magnetDistance = newValue)
                .setMin(1)
                .setMax(128);
        general.addEntry(magnetDistanceField.build());

        IntFieldBuilder magnetDurabilityField = entryBuilder.startIntField(Component.translatable("option.itemmagnetmod.magnet_durability"), magnetDurability)
                .setDefaultValue(1024)
                .setTooltip(Component.translatable("tooltip.itemmagnetmod.magnet_durability"))
                .setSaveConsumer(newValue -> magnetDurability = newValue)
                .setMin(1);
        general.addEntry(magnetDurabilityField.build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.itemmagnetmod.indestructible"), isIndestructible)
                .setDefaultValue(false)
                .setTooltip(Component.translatable("tooltip.itemmagnetmod.indestructible"))
                .setSaveConsumer(newValue -> isIndestructible = newValue)
                .build());

        world.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.itemmagnetmod.core_in_bastion"), canFindInBastion)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("tooltip.itemmagnetmod.core_in_bastion"))
                .setSaveConsumer(newValue -> canFindInBastion = newValue)
                .build());

        world.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.itemmagnetmod.core_in_ancient_city"), canFindInAncientCity)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("tooltip.itemmagnetmod.core_in_ancient_city"))
                .setSaveConsumer(newValue -> canFindInAncientCity = newValue)
                .build());

        world.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.itemmagnetmod.core_in_end_city"), canFindInEndCity)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("tooltip.itemmagnetmod.core_in_end_city"))
                .setSaveConsumer(newValue -> canFindInEndCity = newValue)
                .build());

        world.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.itemmagnetmod.core_in_stronghold_library"), canFindInStrongholdLibrary)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("tooltip.itemmagnetmod.core_in_stronghold_library"))
                .setSaveConsumer(newValue -> canFindInStrongholdLibrary = newValue)
                .build());
        return builder.build();
    }
}
