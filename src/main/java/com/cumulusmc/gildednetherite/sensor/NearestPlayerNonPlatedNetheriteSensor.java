package com.cumulusmc.gildednetherite.sensor;

import com.cumulusmc.gildednetherite.mixin.SensorTypeMixin;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.cumulusmc.gildednetherite.GildedNetherite.hasFullPlatedNetherite;

public class NearestPlayerNonPlatedNetheriteSensor extends Sensor<LivingEntity> {
    public static final SensorType<NearestPlayerNonPlatedNetheriteSensor> NEAREST_PLAYER_NON_PLATED_NETHERITE =
            SensorTypeMixin.register(
                    // We need to specify the identifier as a single string, so we do it like this
                    "gildednetherite:nearest_players_non_plated_netherite",
                    NearestPlayerNonPlatedNetheriteSensor::new
            );

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
    }

    @Override
    protected void sense(ServerWorld world, LivingEntity entity) {
        Stream<ServerPlayerEntity> players = world.getPlayers().stream().filter(EntityPredicates.EXCEPT_SPECTATOR).filter((serverPlayerEntity) ->
                entity.isInRange(serverPlayerEntity, 16.0D) && !hasFullPlatedNetherite(entity)
        );

        List<PlayerEntity> nearestPlayers = players
                .sorted(Comparator.comparingDouble(entity::squaredDistanceTo))
                .collect(Collectors.toList());

        List<PlayerEntity> nearestVisiblePlayer = nearestPlayers
                .stream()
                .filter((playerEntity) -> method_30954(entity, playerEntity))
                .collect(Collectors.toList());

        Optional<PlayerEntity> nearestTargetablePlayer = nearestVisiblePlayer
                .stream()
                .filter(EntityPredicates.EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL)
                .findFirst();

        Brain<?> brain = entity.getBrain();

        brain.remember(MemoryModuleType.NEAREST_PLAYERS, nearestPlayers);
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_PLAYER, (nearestVisiblePlayer.isEmpty() ? null : nearestVisiblePlayer.get(0)));
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, nearestTargetablePlayer);
    }
}
