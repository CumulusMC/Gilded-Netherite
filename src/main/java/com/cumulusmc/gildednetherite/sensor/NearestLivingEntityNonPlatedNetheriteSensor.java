package com.cumulusmc.gildednetherite.sensor;

import com.cumulusmc.gildednetherite.items.RegisterItems;
import com.cumulusmc.gildednetherite.mixin.SensorTypeMixin;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NearestLivingEntityNonPlatedNetheriteSensor extends Sensor<LivingEntity> {
    public static final SensorType<NearestLivingEntityNonPlatedNetheriteSensor> NEAREST_LIVING_ENTITY_NON_PLATED_NETHERITE =
            SensorTypeMixin.register(
                    // We need to specify the identifier as a single string, so we do it like this
                    "gildednetherite:nearest_living_entity_non_plated_netherite",
                    NearestLivingEntityNonPlatedNetheriteSensor::new
            );

    private boolean hasPlatedNetherite(LivingEntity entity) {
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

    @Override
    protected void sense(ServerWorld world, LivingEntity entity) {
        Box box = entity.getBoundingBox().expand(16.0D, 16.0D, 16.0D);

        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box, (livingEntity2) ->
                livingEntity2 != entity && livingEntity2.isAlive() && !hasPlatedNetherite(livingEntity2)
        );

        list.sort(Comparator.comparingDouble(entity::squaredDistanceTo));

        Brain<?> brain = entity.getBrain();

        brain.remember(MemoryModuleType.MOBS, list);
        brain.remember(MemoryModuleType.VISIBLE_MOBS, list.stream().filter((livingEntity2)
                -> method_30954(entity, livingEntity2)).collect(Collectors.toList()));
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS);
    }
}
