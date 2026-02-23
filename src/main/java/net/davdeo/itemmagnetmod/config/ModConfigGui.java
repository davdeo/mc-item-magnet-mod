package net.davdeo.itemmagnetmod.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModConfigGui {

    public static Screen setupCloth(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setTitle(Component.translatable("title.itemmagnetmod.config"))
                .setParentScreen(parent)
                .setSavingRunnable(ModConfig::save);

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("category.itemmagnetmod.general"));
        ConfigCategory world = builder.getOrCreateCategory(Component.translatable("category.itemmagnetmod.world_gen"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder.startIntField(Component.translatable("option.itemmagnetmod.magnet_distance"), ModConfig.magnetDistance)
                .setDefaultValue(7)
                .setTooltip(Component.translatable("tooltip.itemmagnetmod.magnet_distance"))
                .setSaveConsumer(newValue -> ModConfig.magnetDistance = newValue)
                .setMin(1)
                .setMax(128)
                .build());

        general.addEntry(entryBuilder.startIntField(Component.translatable("option.itemmagnetmod.magnet_durability"), ModConfig.magnetDurability)
                .setDefaultValue(1024)
                .setTooltip(Component.translatable("tooltip.itemmagnetmod.magnet_durability"))
                .setSaveConsumer(newValue -> ModConfig.magnetDurability = newValue)
                .setMin(1)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.itemmagnetmod.indestructible"), ModConfig.isIndestructible)
                .setDefaultValue(false)
                .setTooltip(Component.translatable("tooltip.itemmagnetmod.indestructible"))
                .setSaveConsumer(newValue -> ModConfig.isIndestructible = newValue)
                .build());

        world.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.itemmagnetmod.core_in_bastion"), ModConfig.canFindInBastion)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("tooltip.itemmagnetmod.core_in_bastion"))
                .setSaveConsumer(newValue -> ModConfig.canFindInBastion = newValue)
                .build());

        world.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.itemmagnetmod.core_in_ancient_city"), ModConfig.canFindInAncientCity)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("tooltip.itemmagnetmod.core_in_ancient_city"))
                .setSaveConsumer(newValue -> ModConfig.canFindInAncientCity = newValue)
                .build());

        world.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.itemmagnetmod.core_in_end_city"), ModConfig.canFindInEndCity)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("tooltip.itemmagnetmod.core_in_end_city"))
                .setSaveConsumer(newValue -> ModConfig.canFindInEndCity = newValue)
                .build());

        world.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.itemmagnetmod.core_in_stronghold_library"), ModConfig.canFindInStrongholdLibrary)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("tooltip.itemmagnetmod.core_in_stronghold_library"))
                .setSaveConsumer(newValue -> ModConfig.canFindInStrongholdLibrary = newValue)
                .build());

        return builder.build();
    }

    public static Screen create(Screen screen) {
        return null;
    }
}
