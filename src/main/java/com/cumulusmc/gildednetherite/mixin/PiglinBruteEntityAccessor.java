package com.cumulusmc.gildednetherite.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.mob.PiglinBruteEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PiglinBruteEntity.class)
public interface PiglinBruteEntityAccessor {
    @Accessor("SENSOR_TYPES")
    static ImmutableList<SensorType<? extends Sensor<? super PiglinBruteEntity>>> getSensorTypes() {
        return null;
    }

    @Accessor("SENSOR_TYPES")
    static void setSensorTypes(ImmutableList<SensorType<? extends Sensor<? super PiglinBruteEntity>>> sensorTypes) {

    }
}
