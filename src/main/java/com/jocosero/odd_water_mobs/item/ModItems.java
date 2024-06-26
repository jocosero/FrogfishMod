package com.jocosero.odd_water_mobs.item;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import com.jocosero.odd_water_mobs.block.ModBlocks;
import com.jocosero.odd_water_mobs.entity.ModEntities;
import com.jocosero.odd_water_mobs.item.custom.FrogfishBucketItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OddWaterMobs.MOD_ID);

    public static final RegistryObject<Item> RAW_ANGLERFISH = ITEMS.register("raw_anglerfish",
            () -> new Item(new Item.Properties().food(ModFoods.RAW_ANGLERFISH)));

    public static final RegistryObject<Item> RAW_ISOPOD = ITEMS.register("raw_isopod",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COOKED_ISOPOD = ITEMS.register("cooked_isopod",
            () -> new Item(new Item.Properties().food(ModFoods.COOKED_ISOPOD)));

    public static final RegistryObject<Item> HORSESHOE_CRAB_THORAX = ITEMS.register("horseshoe_crab_thorax",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COELACANTH = ITEMS.register("coelacanth",
            () -> new Item(new Item.Properties().food(ModFoods.COELACANTH)));

    public static final RegistryObject<Item> RAW_SPIDER_CRAB_LEG = ITEMS.register("raw_spider_crab_leg",
            () -> new Item(new Item.Properties().food(ModFoods.RAW_SPIDER_CRAB_LEG)));

    public static final RegistryObject<Item> COOKED_SPIDER_CRAB_LEG = ITEMS.register("cooked_spider_crab_leg",
            () -> new Item(new Item.Properties().food(ModFoods.COOKED_SPIDER_CRAB_LEG)));

    public static final RegistryObject<Item> RAW_GHOST_SHARK = ITEMS.register("raw_ghost_shark",
            () -> new Item(new Item.Properties().food(ModFoods.RAW_GHOST_SHARK)));

    public static final RegistryObject<Item> GHOST_SHARK_STING = ITEMS.register("ghost_shark_sting",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_MUDSKIPPER = ITEMS.register("raw_mudskipper",
            () -> new Item(new Item.Properties().food(ModFoods.RAW_MUDSKIPPER)));

    public static final RegistryObject<Item> COOKED_MUDSKIPPER = ITEMS.register("cooked_mudskipper",
            () -> new Item(new Item.Properties().food(ModFoods.COOKED_MUDSKIPPER)));

    public static final RegistryObject<Item> MUDSKIPPER_FIN = ITEMS.register("mudskipper_fin",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DEEP_SEA_FISH = ITEMS.register("deep_sea_fish",
            () -> new Item(new Item.Properties().food(ModFoods.DEEP_SEA_FISH)));

    public static final RegistryObject<Item> RAW_SQUAT_LOBSTER_TAIL = ITEMS.register("raw_squat_lobster_tail",
            () -> new Item(new Item.Properties().food(ModFoods.RAW_SQUAT_LOBSTER_TAIL)));

    public static final RegistryObject<Item> COOKED_SQUAT_LOBSTER_TAIL = ITEMS.register("cooked_squat_lobster_tail",
            () -> new Item(new Item.Properties().food(ModFoods.COOKED_SQUAT_LOBSTER_TAIL)));

    public static final RegistryObject<Item> SEA_PIG = ITEMS.register("sea_pig",
            () -> new Item(new Item.Properties().food(ModFoods.SEA_PIG)));

    public static final RegistryObject<Item> FROGFISH = ITEMS.register("frogfish",
            () -> new Item(new Item.Properties().food(ModFoods.FROGFISH)));

    public static final RegistryObject<Item> ANGLER_LURE = ITEMS.register("angler_lure",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FISH_BAIT = ITEMS.register("fish_bait",
            () -> new Item(new Item.Properties().food(ModFoods.FISH_BAIT)));

    public static final RegistryObject<SolidBucketItem> CHUM_BUCKET = ITEMS.register("chum_bucket",
            () -> new SolidBucketItem(ModBlocks.CHUM.get(), SoundEvents.WART_BLOCK_PLACE, (new Item.Properties()).stacksTo(1)));

    // Spawn Eggs

    public static final RegistryObject<Item> ANGLERFISH_SPAWN_EGG = ITEMS.register("anglerfish_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.ANGLERFISH, -11781057, -14082015, (new Item.Properties().stacksTo(1))));

    public static final RegistryObject<Item> ISOPOD_SPAWN_EGG = ITEMS.register("isopod_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.ISOPOD, 0xEDDCC2, 0xA8938C, (new Item.Properties().stacksTo(1))));

    public static final RegistryObject<Item> HORSESHOE_CRAB_SPAWN_EGG = ITEMS.register("horseshoe_crab_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.HORSESHOE_CRAB, 0x6A5740, 0x2B2817, (new Item.Properties().stacksTo(1))));

//    public static final RegistryObject<Item> COELACANTH_SPAWN_EGG = ITEMS.register("coelacanth_spawn_egg",
//            () -> new ForgeSpawnEggItem(ModEntities.COELACANTH, -15461851, -13618085, (new Item.Properties().stacksTo(1))));

    public static final RegistryObject<Item> FROGFISH_SPAWN_EGG = ITEMS.register("frogfish_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.FROGFISH, 0xF4EFED, 0xFC5E47, new Item.Properties()));

    // Bucket Items

    public static final RegistryObject<Item> ANGLERFISH_BUCKET = ITEMS.register("anglerfish_bucket",
            () -> new MobBucketItem(ModEntities.ANGLERFISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties().stacksTo(1))));

    public static final RegistryObject<Item> ISOPOD_BUCKET = ITEMS.register("isopod_bucket",
            () -> new MobBucketItem(ModEntities.ISOPOD, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties().stacksTo(1))));

    public static final RegistryObject<Item> HORSESHOE_CRAB_BUCKET = ITEMS.register("horseshoe_crab_bucket",
            () -> new MobBucketItem(ModEntities.HORSESHOE_CRAB, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties().stacksTo(1))));

//    public static final RegistryObject<Item> COELACANTH_BUCKET = ITEMS.register("coelacanth_bucket",
//            () -> new MobBucketItem(ModEntities.COELACANTH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FROGFISH_BUCKET = ITEMS.register("frogfish_bucket",
            () -> new FrogfishBucketItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
