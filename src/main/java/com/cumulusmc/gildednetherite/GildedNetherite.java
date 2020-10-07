package com.cumulusmc.gildednetherite;

import com.cumulusmc.gildednetherite.items.RegisterItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class GildedNetherite implements ModInitializer {

    public static final ItemGroup GILDED_NETHERITE_GROUP = FabricItemGroupBuilder.create(
            new Identifier("gildednetherite", "gilded_netherite_group"))
            .icon(() -> new ItemStack(RegisterItems.GILDED_NETHERITE))
            .build();

    @Override
    public void onInitialize() {
        RegisterItems.register();
    }
}
