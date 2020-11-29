package com.cumulusmc.gildednetherite.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GildedNetheriteItem extends Item {
    public GildedNetheriteItem(Settings settings) {
        super(settings);
    }

    // TODO: REMOVE
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    // TODO: REMOVE
    @Override
    public int getEnchantability() {
        return 3;
    }
}
