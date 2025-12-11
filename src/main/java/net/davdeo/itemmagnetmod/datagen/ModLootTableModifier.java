package net.davdeo.itemmagnetmod.datagen;

import net.davdeo.itemmagnetmod.item.ModItems;
import net.davdeo.itemmagnetmod.config.ModConfig; // ДОБАВЛЕН ИМПОРТ КОНФИГА
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ModLootTableModifier {
    private ModLootTableModifier() {
        super();
    }

    private static void registerChestLoot(ResourceKey<LootTable> structure, int numberOfRolls, float chance, float minAmount, float maxAmount, Item item) {
        LootTableEvents.MODIFY.register(((id, tableBuilder, source, x) -> {
            if (source.isBuiltin() && structure.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(numberOfRolls))
                        .when(LootItemRandomChanceCondition.randomChance(chance))
                        .add(LootItem.lootTableItem(item))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minAmount, maxAmount)).build());

                tableBuilder.withPool(poolBuilder);
            }
        }));
    }

    public static void modifyLootTables() {
        if (ModConfig.canFindInBastion) {
            registerChestLoot(BuiltInLootTables.BASTION_TREASURE, 2, 0.35f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
        }

        if (ModConfig.canFindInBastion) {
            registerChestLoot(BuiltInLootTables.BASTION_BRIDGE, 1, 0.15f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
        }

        if (ModConfig.canFindInBastion) {
            registerChestLoot(BuiltInLootTables.BASTION_HOGLIN_STABLE, 1, 0.15f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
        }

        if (ModConfig.canFindInBastion) {
            registerChestLoot(BuiltInLootTables.BASTION_OTHER, 1, 0.15f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
        }

        if (ModConfig.canFindInAncientCity) {
            registerChestLoot(BuiltInLootTables.ANCIENT_CITY, 1, 0.35f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
        }

        if (ModConfig.canFindInEndCity) {
            registerChestLoot(BuiltInLootTables.END_CITY_TREASURE, 1, 0.15f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
        }

        if (ModConfig.canFindInStrongholdLibrary) {
            registerChestLoot(BuiltInLootTables.STRONGHOLD_LIBRARY, 1, 0.1f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
        }
    }
}
