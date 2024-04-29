package dev.figboot;

import dev.figboot.enchant.SmeltingTouchEnchantment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.FurnaceSmeltLootFunction;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.predicate.item.ItemSubPredicateTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class SmeltingTouchMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("smeltingtouch");

    public static final SmeltingTouchEnchantment SMELTING_TOUCH = new SmeltingTouchEnchantment(
            Enchantment.properties(ItemTags.MINING_LOOT_ENCHANTABLE, 1, 1,
                    Enchantment.constantCost(15), Enchantment.constantCost(65), 8,
                    EquipmentSlot.MAINHAND));

    @Override
    public void onInitialize() {
        LOGGER.info("initializing mod>.....");
        Registry.register(Registries.ENCHANTMENT, new Identifier("smeltingtouch:smeltingtouch"), SMELTING_TOUCH);

        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            if (source.isBuiltin() && key.getValue().getPath().startsWith("blocks/")) {
                tableBuilder.apply(FurnaceSmeltLootFunction.builder()
                        .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().subPredicate(ItemSubPredicateTypes.ENCHANTMENTS,
                                EnchantmentsPredicate.enchantments(Collections.singletonList(new EnchantmentPredicate(SMELTING_TOUCH, NumberRange.IntRange.atLeast(1))))))));
            }
        });
    }
}
