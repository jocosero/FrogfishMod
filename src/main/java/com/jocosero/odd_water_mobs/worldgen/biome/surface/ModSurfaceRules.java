package com.jocosero.odd_water_mobs.worldgen.biome.surface;

import com.jocosero.odd_water_mobs.worldgen.biome.ModBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
    private static final SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isBelowWaterLevel = SurfaceRules.waterBlockCheck(0, 1699);

        SurfaceRules.RuleSource waterSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isBelowWaterLevel, WATER), STONE);

        return SurfaceRules.sequence(
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.DEEP_OCEAN_DUNES),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, waterSurface))));
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}



