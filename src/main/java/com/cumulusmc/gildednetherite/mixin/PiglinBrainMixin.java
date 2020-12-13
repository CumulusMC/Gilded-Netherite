package com.cumulusmc.gildednetherite.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cumulusmc.gildednetherite.GildedNetherite.hasFullPlatedNetherite;

@Mixin(PiglinBrain.class)
public abstract class PiglinBrainMixin {
    @Inject(method = "wearsGoldArmor", at = @At(value = "INVOKE"), cancellable = true)
    private static void wearsGoldArmor(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        Logger logger = LogManager.getLogger("PiglinBrainMixin");

        if (hasFullPlatedNetherite(entity)) {
            logger.info("Plated netherite");
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "shouldAttack", at = @At(value = "RETURN"), cancellable = true)
    private static void shouldAttack(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        Logger logger = LogManager.getLogger("PiglinBrainMixin");

        if (cir.getReturnValue() && hasFullPlatedNetherite(target)) {
            logger.info("Shouldn't attack: Wearing plated netherite");
            cir.setReturnValue(false);
        }
    }
}
