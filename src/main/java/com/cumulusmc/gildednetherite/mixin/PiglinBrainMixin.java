package com.cumulusmc.gildednetherite.mixin;

import com.cumulusmc.gildednetherite.items.RegisterItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
    @Inject(method = "wearsGoldArmor", at = @At(value = "RETURN"), cancellable = true)
    private static void wearsGoldArmor(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            return;  // They have gold, no point in iterating again
        }

        Iterable<ItemStack> iterable = entity.getArmorItems();
        Iterator<ItemStack> iterator = iterable.iterator();

        Item item;

        do {
            if (!iterator.hasNext()) {
                cir.setReturnValue(false);  // It's not plated netherite
                return;
            }

            item = iterator.next().getItem();
        } while (!(item instanceof ArmorItem) || ((ArmorItem) item).getMaterial() != RegisterItems.platedNetheriteArmorMaterial);

        cir.setReturnValue(true);  // It's plated netherite, treat it like gold
    }
}
