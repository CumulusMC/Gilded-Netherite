package com.cumulusmc.gildednetherite.mixin;

import com.cumulusmc.gildednetherite.items.RegisterItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinBruteBrain;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(PiglinBruteBrain.class)
public abstract class PiglinBruteBrainMixin {
    @Inject(method = "method_30245", at = @At(value = "RETURN"), cancellable = true)
    private static void shouldAttack(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        Logger logger = LogManager.getLogger("PiglinBruteBrainMixin");

        if (cir.getReturnValue() && hasPlatedNetherite(target)) {
            logger.info("Shouldn't attack: Wearing plated netherite");
            cir.setReturnValue(false);
        }
    }

    private static boolean hasPlatedNetherite(LivingEntity entity) {
        Iterable<ItemStack> iterable = entity.getArmorItems();

        for (ItemStack itemStack : iterable) {
            Item item = itemStack.getItem();

            if (item instanceof ArmorItem && ((ArmorItem) item).getMaterial() == RegisterItems.platedNetheriteArmorMaterial) {
                return true;
            }
        }

        return false;
    }
}
