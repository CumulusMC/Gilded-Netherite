package com.cumulusmc.gildednetherite.sensor;

import com.cumulusmc.gildednetherite.mixin.SensorTypeMixin;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cumulusmc.gildednetherite.GildedNetherite.hasFullPlatedNetherite;

public class NearestLivingEntityNonPlatedNetheriteSensor extends Sensor<LivingEntity> {
    public static final SensorType<NearestLivingEntityNonPlatedNetheriteSensor> NEAREST_LIVING_ENTITY_NON_PLATED_NETHERITE =
            SensorTypeMixin.register(
                    // We need to specify the identifier as a single string, so we do it like this
                    "gildednetherite:nearest_living_entity_non_plated_netherite",
                    NearestLivingEntityNonPlatedNetheriteSensor::new
            );

    @Override
    protected void sense(ServerWorld world, LivingEntity entity) {
        Box box = entity.getBoundingBox().expand(16.0D, 16.0D, 16.0D);

        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box, (livingEntity2) ->
                livingEntity2 != entity && livingEntity2.isAlive() && !hasFullPlatedNetherite(livingEntity2)
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
