package net.davdeo.itemmagnetmod.datagen;

import net.davdeo.itemmagnetmod.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifier {
    private ModLootTableModifier() {
        super();
    }

    private static final String MINECRAFT_NAMESPACE = "minecraft";
    private static final Identifier BASTION_TREASURE_CHEST_ID = new Identifier(MINECRAFT_NAMESPACE, "chests/bastion_treasure");
    private static final Identifier BASTION_BRIDGE_CHEST_ID = new Identifier(MINECRAFT_NAMESPACE, "chests/bastion_bridge");
    private static final Identifier BASTION_HOGLIN_STABLE_CHEST_ID = new Identifier(MINECRAFT_NAMESPACE, "chests/bastion_hoglin_stable");
    private static final Identifier BASTION_OTHER_CHEST_ID = new Identifier(MINECRAFT_NAMESPACE, "chests/bastion_other");
    private static final Identifier ANCIENT_CITY_CHEST_ID = new Identifier(MINECRAFT_NAMESPACE, "chests/ancient_city");
    private static final Identifier END_CITY_TREASURE_CHEST_ID = new Identifier(MINECRAFT_NAMESPACE, "chests/end_city_treasure");
    private static final Identifier STRONGHOLD_LIBRARY_CHEST_ID = new Identifier(MINECRAFT_NAMESPACE, "chests/stronghold_library");


    private static void registerChestLoot(Identifier identifier, int numberOfRolls, float chance, float minAmount, float maxAmount, Item item) {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (identifier.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(numberOfRolls))
                        .conditionally(RandomChanceLootCondition.builder(chance))
//                        .conditionally(LootCondition) // Play round with different lootConditions
                        .with(ItemEntry.builder(item))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minAmount, maxAmount)).build());

                tableBuilder.pool(poolBuilder.build());
            }
        }));
    }

    public static void modifyLootTables() {
        registerChestLoot(BASTION_TREASURE_CHEST_ID, 2, 0.65f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
        registerChestLoot(BASTION_BRIDGE_CHEST_ID, 1, 0.35f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
        registerChestLoot(BASTION_HOGLIN_STABLE_CHEST_ID, 1, 0.35f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
        registerChestLoot(BASTION_OTHER_CHEST_ID, 1, 0.35f, 1.0f, 1.0f, ModItems.MAGNET_CORE);

        registerChestLoot(ANCIENT_CITY_CHEST_ID, 2, 0.35f, 1.0f, 1.0f, ModItems.MAGNET_CORE);

        registerChestLoot(END_CITY_TREASURE_CHEST_ID, 1, 0.35f, 1.0f, 1.0f, ModItems.MAGNET_CORE);

        registerChestLoot(STRONGHOLD_LIBRARY_CHEST_ID, 1, 0.25f, 1.0f, 1.0f, ModItems.MAGNET_CORE);
    }
}
