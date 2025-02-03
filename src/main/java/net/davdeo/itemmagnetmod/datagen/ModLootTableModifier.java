package net.davdeo.itemmagnetmod.datagen;

import net.davdeo.itemmagnetmod.item.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;

public class ModLootTableModifier {
    private ModLootTableModifier() {
        super();
    }

    private static void registerChestLoot(RegistryKey<LootTable> structure, int numberOfRolls, float chance, float minAmount, float maxAmount, Item item) {
        LootTableEvents.MODIFY.register(((id, tableBuilder, source, x) -> {
            if (source.isBuiltin() && structure.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(numberOfRolls))
                        .conditionally(RandomChanceLootCondition.builder(chance))
                        .with(ItemEntry.builder(item))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minAmount, maxAmount)).build());

                tableBuilder.pool(poolBuilder);
            }
        }));
    }

    public static void modifyLootTables() {
        registerChestLoot(LootTables.BASTION_TREASURE_CHEST, 2, 0.35f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
        registerChestLoot(LootTables.BASTION_BRIDGE_CHEST, 1, 0.15f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
        registerChestLoot(LootTables.BASTION_HOGLIN_STABLE_CHEST, 1, 0.15f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
        registerChestLoot(LootTables.BASTION_OTHER_CHEST, 1, 0.15f, 1.0f, 1.0f, ModItems.MAGNET_CORE);

        registerChestLoot(LootTables.ANCIENT_CITY_CHEST, 1, 0.35f, 1.0f, 1.0f, ModItems.MAGNET_CORE);

        registerChestLoot(LootTables.END_CITY_TREASURE_CHEST, 1, 0.15f, 1.0f, 1.0f, ModItems.MAGNET_CORE);

        registerChestLoot(LootTables.STRONGHOLD_LIBRARY_CHEST, 1, 0.1f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
    }
}
