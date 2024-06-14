package com.jocosero.odd_water_mobs.event;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import com.jocosero.odd_water_mobs.block.entity.ModBlockEntities;
import com.jocosero.odd_water_mobs.block.entity.renderer.TrapperBlockEntityRenderer;
import com.jocosero.odd_water_mobs.entity.client.model.AnglerfishModel;
import com.jocosero.odd_water_mobs.entity.client.model.FrogfishModel;
import com.jocosero.odd_water_mobs.entity.client.ModModelLayers;
import com.jocosero.odd_water_mobs.entity.client.model.HorseshoeCrabModel;
import com.jocosero.odd_water_mobs.entity.client.model.IsopodModel;
import com.jocosero.odd_water_mobs.particle.ModParticles;
import com.jocosero.odd_water_mobs.particle.custom.HydrothermalSmoke;
import com.jocosero.odd_water_mobs.particle.custom.ChumChunks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = OddWaterMobs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    static {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModEventBusClientEvents::setupParticles);
    }

    public static void setupParticles(RegisterParticleProvidersEvent registry) {
        registry.registerSpriteSet(ModParticles.HYDROTHERMAL_SMOKE.get(), HydrothermalSmoke.Factory::new);
        registry.registerSpriteSet(ModParticles.CHUM_CHUNKS.get(), ChumChunks.Factory::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.ANGLERFISH_LAYER, AnglerfishModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ISOPOD_LAYER, IsopodModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.HORSESHOE_CRAB_LAYER, HorseshoeCrabModel::createBodyLayer);
//        event.registerLayerDefinition(ModModelLayers.COELACANTH_LAYER, CoelacanthModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.FROGFISH_LAYER, FrogfishModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.TRAPPER_BLOCK_ENTITY.get(), TrapperBlockEntityRenderer::new);
    }
}
