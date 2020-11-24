package com.cumulusmc.gildednetherite.sensor;

import com.cumulusmc.gildednetherite.items.RegisterItems;
import com.cumulusmc.gildednetherite.mixin.SensorTypeMixin;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

import java.util.Iterator;
import java.util.Set;

public class HurtByNonPlatedNetheriteSensor extends Sensor<LivingEntity> {
    public static final SensorType<HurtByNonPlatedNetheriteSensor> HURT_BY_NON_PLATED_NETHERITE =
            SensorTypeMixin.register(
                    // We need to specify the identifier as a single string, so we do it like this
                    "gildednetherite:hurt_by_non_plated_netherite",
                    HurtByNonPlatedNetheriteSensor::new
            );

    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY);
    }

    private boolean hasPlatedNetherite(Entity entity) {
        Iterable<ItemStack> iterable = entity.getArmorItems();
        Iterator<ItemStack> iterator = iterable.iterator();

        Item item;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            item = iterator.next().getItem();
        } while (!(item instanceof ArmorItem) || ((ArmorItem) item).getMaterial() != RegisterItems.platedNetheriteArmorMaterial);

        return true;
    }

    protected void sense(ServerWorld world, LivingEntity entity) {
        Brain<?> brain = entity.getBrain();
        DamageSource damageSource = entity.getRecentDamageSource();

        if (damageSource != null) {
            if (damageSource.getAttacker() != null && hasPlatedNetherite(damageSource.getAttacker())) {
                return;
            }

            brain.remember(MemoryModuleType.HURT_BY, entity.getRecentDamageSource());
            Entity entity2 = damageSource.getAttacker();
            if (entity2 instanceof LivingEntity) {
                brain.remember(MemoryModuleType.HURT_BY_ENTITY, (LivingEntity) entity2);
            }
        } else {
            brain.forget(MemoryModuleType.HURT_BY);
        }

        brain.getOptionalMemory(MemoryModuleType.HURT_BY_ENTITY).ifPresent((livingEntity) -> {
            if (!livingEntity.isAlive() || livingEntity.world != world) {
                brain.forget(MemoryModuleType.HURT_BY_ENTITY);
            }
        });
    }
}
