package com.jocosero.odd_water_mobs;

import com.jocosero.odd_water_mobs.block.ModBlocks;
import com.jocosero.odd_water_mobs.block.custom.ChumBarrelBlock;
import com.jocosero.odd_water_mobs.block.entity.ModBlockEntities;
import com.jocosero.odd_water_mobs.effect.ModEffects;
import com.jocosero.odd_water_mobs.entity.ModEntities;
import com.jocosero.odd_water_mobs.entity.client.renderer.AnglerfishRenderer;
import com.jocosero.odd_water_mobs.entity.client.renderer.FrogfishRenderer;
import com.jocosero.odd_water_mobs.entity.client.renderer.HorseshoeCrabRenderer;
import com.jocosero.odd_water_mobs.entity.client.renderer.IsopodRenderer;
import com.jocosero.odd_water_mobs.item.ModCreativeModeTab;
import com.jocosero.odd_water_mobs.item.ModItems;
import com.jocosero.odd_water_mobs.painting.ModPaintings;
import com.jocosero.odd_water_mobs.particle.ModParticles;
import com.jocosero.odd_water_mobs.potion.ModPotions;
import com.jocosero.odd_water_mobs.sound.ModSounds;
import com.jocosero.odd_water_mobs.util.PotionBrewingRecipe;
import com.jocosero.odd_water_mobs.worldgen.biome.ModTerrablender;
import com.jocosero.odd_water_mobs.worldgen.biome.surface.ModSurfaceRules;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import terrablender.api.SurfaceRuleManager;

@Mod(OddWaterMobs.MOD_ID)
public class OddWaterMobs {
    public static final String MOD_ID = "odd_water_mobs";
    private static final Logger LOGGER = LogUtils.getLogger();

    public OddWaterMobs() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTab.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModEntities.register(modEventBus);
        ModSounds.register(modEventBus);

        ModPaintings.register(modEventBus);
        ModParticles.register(modEventBus);

        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModTerrablender.registerBiomes();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModSurfaceRules.makeRules());
            //Items that serve as chum ingredients
            ChumBarrelBlock.chumIngredients();
            //Potion brewing recipes
            BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(Potions.AWKWARD, ModItems.FROGFISH.get(), ModPotions.ALLURING_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(Potions.AWKWARD, ModItems.RAW_ANGLERFISH.get(), ModPotions.ALLURING_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(ModPotions.ALLURING_POTION.get(), Items.REDSTONE, ModPotions.LONG_ALLURING_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(Potions.AWKWARD, ModItems.HORSESHOE_CRAB_THORAX.get(), ModPotions.HEALING_VIGOR_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(Potions.LEAPING, ModItems.MUDSKIPPER_FIN.get(), ModPotions.MUDSKIPPING_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(ModPotions.MUDSKIPPING_POTION.get(), Items.REDSTONE, ModPotions.LONG_MUDSKIPPING_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(Potions.AWKWARD, ModItems.GHOST_SHARK_STING.get(), Potions.LONG_POISON));
            BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(Potions.LONG_POISON, ModItems.SEA_PIG.get(), ModPotions.POISON_PROTECTION_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(ModPotions.POISON_PROTECTION_POTION.get(), Items.REDSTONE, ModPotions.LONG_POISON_PROTECTION_POTION.get()));
        });
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("OddWaterMobs initialized!");
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.ANGLERFISH.get(), AnglerfishRenderer::new);
            EntityRenderers.register(ModEntities.ISOPOD.get(), IsopodRenderer::new);
            EntityRenderers.register(ModEntities.HORSESHOE_CRAB.get(), HorseshoeCrabRenderer::new);
//            EntityRenderers.register(ModEntities.COELACANTH.get(), CoelacanthRenderer::new);
            EntityRenderers.register(ModEntities.FROGFISH.get(), FrogfishRenderer::new);
        }
    }
}
