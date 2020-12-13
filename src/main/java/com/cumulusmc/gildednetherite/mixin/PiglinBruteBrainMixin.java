package com.cumulusmc.gildednetherite.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.PiglinBruteBrain;
import net.minecraft.entity.mob.PiglinBruteEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

import static com.cumulusmc.gildednetherite.GildedNetherite.hasFullPlatedNetherite;

@Mixin(PiglinBruteBrain.class)
public abstract class PiglinBruteBrainMixin {
    @Inject(method = "method_30245", at = @At(value = "RETURN"), cancellable = true)
    private static void shouldAttack(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        Logger logger = LogManager.getLogger("PiglinBruteBrainMixin");

        if (cir.getReturnValue() && hasFullPlatedNetherite(target)) {
            logger.info("Shouldn't attack: Wearing plated netherite");
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "method_30256", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PiglinBruteEntity;setAttacking(Z)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void method_30256(PiglinBruteEntity piglinBruteEntity, CallbackInfo ci, Brain<PiglinBruteEntity> brain) {
        if (brain.hasMemoryModule(MemoryModuleType.ATTACK_TARGET)) {
            Optional<LivingEntity> targetOptional = brain.getOptionalMemory(MemoryModuleType.ATTACK_TARGET);

            if (targetOptional.isPresent()) {
                LivingEntity target = targetOptional.get();

                if (hasFullPlatedNetherite(target)) {
                    brain.forget(MemoryModuleType.ATTACK_TARGET);
                    piglinBruteEntity.setAttacking(false);
                    ci.cancel();
                }
            }
        }
    }

    @Inject(method = "method_30256", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PiglinBruteBrain;method_30261(Lnet/minecraft/entity/mob/PiglinBruteEntity;)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void method_30256_2(PiglinBruteEntity piglinBruteEntity, CallbackInfo ci, Brain<PiglinBruteEntity> brain) {
        if (brain.hasMemoryModule(MemoryModuleType.ATTACK_TARGET)) {
            Optional<LivingEntity> targetOptional = brain.getOptionalMemory(MemoryModuleType.ATTACK_TARGET);

            if (targetOptional.isPresent()) {
                LivingEntity target = targetOptional.get();

                if (hasFullPlatedNetherite(target)) {
                    brain.forget(MemoryModuleType.ATTACK_TARGET);
                    piglinBruteEntity.setAttacking(false);
                    ci.cancel();
                }
            }
        }
    }
}
