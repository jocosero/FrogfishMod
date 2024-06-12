package com.jocosero.odd_water_mobs.item;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import com.jocosero.odd_water_mobs.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAGS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OddWaterMobs.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ODD_WATER_MOBS = CREATIVE_MODE_TAGS.register("odd_water_mobs",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RAW_ANGLERFISH.get()))
                    .title(Component.translatable("creativetab.odd_water_mobs"))
                    .displayItems((pParameters, pOutput) -> {

                        //Items
                        pOutput.accept(ModItems.RAW_ANGLERFISH.get());
                        pOutput.accept(ModItems.RAW_ISOPOD.get());
                        pOutput.accept(ModItems.COOKED_ISOPOD.get());
                        pOutput.accept(ModItems.HORSESHOE_CRAB_THORAX.get());
                        pOutput.accept(ModItems.COELACANTH.get());
                        pOutput.accept(ModItems.RAW_SPIDER_CRAB_LEG.get());
                        pOutput.accept(ModItems.COOKED_SPIDER_CRAB_LEG.get());
                        pOutput.accept(ModItems.RAW_GHOST_SHARK.get());
                        pOutput.accept(ModItems.GHOST_SHARK_STING.get());
                        pOutput.accept(ModItems.RAW_MUDSKIPPER.get());
                        pOutput.accept(ModItems.COOKED_MUDSKIPPER.get());
                        pOutput.accept(ModItems.MUDSKIPPER_FIN.get());
                        pOutput.accept(ModItems.DEEP_SEA_FISH.get());
                        pOutput.accept(ModItems.RAW_SQUAT_LOBSTER_TAIL.get());
                        pOutput.accept(ModItems.COOKED_SQUAT_LOBSTER_TAIL.get());
                        pOutput.accept(ModItems.SEA_PIG.get());
                        pOutput.accept(ModItems.FROGFISH.get());
                        pOutput.accept(ModItems.ANGLER_LURE.get());
                        pOutput.accept(ModItems.FISH_BAIT.get());
                        pOutput.accept(ModItems.CHUM_BUCKET.get());

                        //Buckets
                        pOutput.accept(ModItems.ANGLERFISH_BUCKET.get());
                        pOutput.accept(ModItems.ISOPOD_BUCKET.get());
                        pOutput.accept(ModItems.HORSESHOE_CRAB_BUCKET.get());
//                        pOutput.accept(ModItems.COELACANTH_BUCKET.get());
                        pOutput.accept(ModItems.FROGFISH_BUCKET.get());;

                        //Spawn eggs
                        pOutput.accept(ModItems.ANGLERFISH_SPAWN_EGG.get());
                        pOutput.accept(ModItems.ISOPOD_SPAWN_EGG.get());
                        pOutput.accept(ModItems.HORSESHOE_CRAB_SPAWN_EGG.get());
//                        pOutput.accept(ModItems.COELACANTH_SPAWN_EGG.get());
                        pOutput.accept(ModItems.FROGFISH_SPAWN_EGG.get());

                        //Deep sand variants
                        pOutput.accept(ModBlocks.DEEP_SAND.get());
                        pOutput.accept(ModBlocks.CHISELED_DEEP_SANDSTONE.get());
                        pOutput.accept(ModBlocks.CUT_DEEP_SANDSTONE.get());
                        pOutput.accept(ModBlocks.DEEP_SANDSTONE_SLAB.get());
                        pOutput.accept(ModBlocks.CUT_DEEP_SANDSTONE_SLAB.get());
                        pOutput.accept(ModBlocks.SMOOTH_DEEP_SANDSTONE.get());
                        pOutput.accept(ModBlocks.DEEP_SANDSTONE_STAIRS.get());
                        pOutput.accept(ModBlocks.DEEP_SANDSTONE_WALL.get());
                        pOutput.accept(ModBlocks.SMOOTH_DEEP_SANDSTONE_STAIRS.get());
                        pOutput.accept(ModBlocks.SMOOTH_DEEP_SANDSTONE_SLAB.get());

                        //Blocks
                        pOutput.accept(ModBlocks.BIOLUMINESCENT_SCALES.get());
                        pOutput.accept(ModBlocks.BIOLUMINESCENT_BLOCK.get());
                        pOutput.accept(ModBlocks.GIANT_TUBE_WORM.get());
                        pOutput.accept(ModBlocks.GLOWING_ANEMONE.get());
                        pOutput.accept(ModBlocks.HYDROTHERMAL_VENT.get());
                        pOutput.accept(ModBlocks.CHUM_BARREL.get());
                        pOutput.accept(ModBlocks.TRAPPER.get());

                        //Potions

                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAGS.register(eventBus);
    }

}



