package com.cumulusmc.gildednetherite.items;

import com.cumulusmc.gildednetherite.GildedNetherite;
import com.cumulusmc.gildednetherite.materials.GildedNetheriteArmorMaterial;
import com.cumulusmc.gildednetherite.materials.PlatedNetheriteArmorMaterial;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterItems {

    public static final ArmorMaterial gildedNetheriteArmorMaterial = new GildedNetheriteArmorMaterial();
    public static final ArmorMaterial platedNetheriteArmorMaterial = new PlatedNetheriteArmorMaterial();

    public static final Item GILDED_NETHERITE = new GildedNetheriteItem(new Item.Settings().group(GildedNetherite.GILDED_NETHERITE_GROUP).maxDamage(20));  // TODO: REMOVE DAMAGE
    public static final Item GILDED_NETHERITE_HELMET = new ArmorItem(gildedNetheriteArmorMaterial, EquipmentSlot.HEAD, new Item.Settings().group(GildedNetherite.GILDED_NETHERITE_GROUP).fireproof());
    public static final Item GILDED_NETHERITE_CHESTPLATE = new ArmorItem(gildedNetheriteArmorMaterial, EquipmentSlot.CHEST, new Item.Settings().group(GildedNetherite.GILDED_NETHERITE_GROUP).fireproof());
    public static final Item GILDED_NETHERITE_LEGGINGS = new ArmorItem(gildedNetheriteArmorMaterial, EquipmentSlot.LEGS, new Item.Settings().group(GildedNetherite.GILDED_NETHERITE_GROUP).fireproof());
    public static final Item GILDED_NETHERITE_BOOTS = new ArmorItem(gildedNetheriteArmorMaterial, EquipmentSlot.FEET, new Item.Settings().group(GildedNetherite.GILDED_NETHERITE_GROUP).fireproof());

    public static final Item PLATED_NETHERITE_HELMET = new ArmorItem(platedNetheriteArmorMaterial, EquipmentSlot.HEAD, new Item.Settings().group(GildedNetherite.GILDED_NETHERITE_GROUP).fireproof());
    public static final Item PLATED_NETHERITE_CHESTPLATE = new ArmorItem(platedNetheriteArmorMaterial, EquipmentSlot.CHEST, new Item.Settings().group(GildedNetherite.GILDED_NETHERITE_GROUP).fireproof());
    public static final Item PLATED_NETHERITE_LEGGINGS = new ArmorItem(platedNetheriteArmorMaterial, EquipmentSlot.LEGS, new Item.Settings().group(GildedNetherite.GILDED_NETHERITE_GROUP).fireproof());
    public static final Item PLATED_NETHERITE_BOOTS = new ArmorItem(platedNetheriteArmorMaterial, EquipmentSlot.FEET, new Item.Settings().group(GildedNetherite.GILDED_NETHERITE_GROUP).fireproof());

    public static final Item GOLDEN_PORKCHOP = new GoldenPorkChopItem(new Item.Settings().group(GildedNetherite.GILDED_NETHERITE_GROUP).food(FoodComponents.COOKED_PORKCHOP));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier("gildednetherite", "gilded_netherite"), GILDED_NETHERITE);
        Registry.register(Registry.ITEM, new Identifier("gildednetherite", "gilded_netherite_helmet"), GILDED_NETHERITE_HELMET);
        Registry.register(Registry.ITEM, new Identifier("gildednetherite", "gilded_netherite_chestplate"), GILDED_NETHERITE_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier("gildednetherite", "gilded_netherite_leggings"), GILDED_NETHERITE_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier("gildednetherite", "gilded_netherite_boots"), GILDED_NETHERITE_BOOTS);

        Registry.register(Registry.ITEM, new Identifier("gildednetherite", "plated_netherite_helmet"), PLATED_NETHERITE_HELMET);
        Registry.register(Registry.ITEM, new Identifier("gildednetherite", "plated_netherite_chestplate"), PLATED_NETHERITE_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier("gildednetherite", "plated_netherite_leggings"), PLATED_NETHERITE_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier("gildednetherite", "plated_netherite_boots"), PLATED_NETHERITE_BOOTS);

        Registry.register(Registry.ITEM, new Identifier("gildednetherite", "golden_porkchop"), GOLDEN_PORKCHOP);
    }
}
