package com.cumulusmc.gildednetherite;

import com.cumulusmc.gildednetherite.items.RegisterItems;
import com.cumulusmc.gildednetherite.mixin.PiglinBruteEntityAccessor;
import com.cumulusmc.gildednetherite.mixin.PiglinEntityAccessor;
import com.cumulusmc.gildednetherite.sensor.HurtByNonPlatedNetheriteSensor;
import com.cumulusmc.gildednetherite.sensor.NearestLivingEntityNonPlatedNetheriteSensor;
import com.cumulusmc.gildednetherite.sensor.NearestPlayerNonPlatedNetheriteSensor;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static net.minecraft.entity.ai.brain.sensor.SensorType.*;

public class GildedNetherite implements ModInitializer {
    private Logger logger = LogManager.getLogger("GildedNetherite");

    public static final ArrayList<DamageSource> DAMAGE_SOURCES = Lists.newArrayList(
            // All the damage sources we should be immune to

            DamageSource.HOT_FLOOR,
            DamageSource.IN_FIRE,
            DamageSource.LAVA,
            DamageSource.LIGHTNING_BOLT,
            DamageSource.ON_FIRE
    );

    public static final ItemGroup GILDED_NETHERITE_GROUP = FabricItemGroupBuilder.create(
            new Identifier("gildednetherite", "gilded_netherite_group"))
            .icon(() -> new ItemStack(RegisterItems.GILDED_NETHERITE))
            .build();

    @Override
    public void onInitialize() {
        RegisterItems.register();

        setupBrutes();
        setupPiglins();
    }

    private void setupBrutes() {
        ImmutableList<SensorType<? extends Sensor<? super PiglinBruteEntity>>> immutableSensors = PiglinBruteEntityAccessor.getSensorTypes();

        if (immutableSensors.contains(NEAREST_PLAYERS) && immutableSensors.contains(NEAREST_LIVING_ENTITIES) && immutableSensors.contains(HURT_BY)) {
            logger.info("Brutes: Replacing sensors.");

            ArrayList<SensorType<? extends Sensor<? super PiglinBruteEntity>>> sensors = new ArrayList<>(immutableSensors);

            sensors.remove(NEAREST_PLAYERS);
            sensors.remove(NEAREST_LIVING_ENTITIES);
            sensors.remove(HURT_BY);

            sensors.add(NearestPlayerNonPlatedNetheriteSensor.NEAREST_PLAYER_NON_PLATED_NETHERITE);
            sensors.add(NearestLivingEntityNonPlatedNetheriteSensor.NEAREST_LIVING_ENTITY_NON_PLATED_NETHERITE);
            sensors.add(HurtByNonPlatedNetheriteSensor.HURT_BY_NON_PLATED_NETHERITE);

            immutableSensors = ImmutableList.copyOf(sensors);

            PiglinBruteEntityAccessor.setSensorTypes(immutableSensors);
        } else {
            logger.info("Brutes: Not replacing sensors - some sensors are missing.");
        }
    }

    private void setupPiglins() {
        ImmutableList<SensorType<? extends Sensor<? super PiglinEntity>>> immutableSensors = PiglinEntityAccessor.getSensorTypes();

        if (immutableSensors.contains(NEAREST_PLAYERS) && immutableSensors.contains(NEAREST_LIVING_ENTITIES) && immutableSensors.contains(HURT_BY)) {
            logger.info("Piglins: Replacing sensors.");

            ArrayList<SensorType<? extends Sensor<? super PiglinEntity>>> sensors = new ArrayList<>(immutableSensors);

            sensors.remove(NEAREST_PLAYERS);
            sensors.remove(NEAREST_LIVING_ENTITIES);
            sensors.remove(HURT_BY);

            sensors.add(NearestPlayerNonPlatedNetheriteSensor.NEAREST_PLAYER_NON_PLATED_NETHERITE);
            sensors.add(NearestLivingEntityNonPlatedNetheriteSensor.NEAREST_LIVING_ENTITY_NON_PLATED_NETHERITE);
            sensors.add(HurtByNonPlatedNetheriteSensor.HURT_BY_NON_PLATED_NETHERITE);

            immutableSensors = ImmutableList.copyOf(sensors);

            PiglinEntityAccessor.setSensorTypes(immutableSensors);
        } else {
            logger.info("Piglins: Not replacing sensors - some sensors are missing.");
        }
    }
}
