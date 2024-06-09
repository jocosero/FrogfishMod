package com.jocosero.odd_water_mobs.worldgen.biome;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes() {
        Regions.register(new ModOverworldRegion(new ResourceLocation(OddWaterMobs.MOD_ID, "overworld"), 5));
    }
}
