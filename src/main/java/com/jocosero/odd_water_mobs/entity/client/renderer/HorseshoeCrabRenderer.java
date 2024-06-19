package com.jocosero.odd_water_mobs.entity.client.renderer;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import com.jocosero.odd_water_mobs.entity.client.renderer.layers.ModModelLayers;
import com.jocosero.odd_water_mobs.entity.client.model.HorseshoeCrabModel;
import com.jocosero.odd_water_mobs.entity.custom.HorseshoeCrabEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HorseshoeCrabRenderer extends MobRenderer<HorseshoeCrabEntity, HorseshoeCrabModel<HorseshoeCrabEntity>> {

    public HorseshoeCrabRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new HorseshoeCrabModel<>(pContext.bakeLayer(ModModelLayers.HORSESHOE_CRAB_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(HorseshoeCrabEntity pEntity) {
        return new ResourceLocation(OddWaterMobs.MOD_ID, "textures/entity/horseshoe_crab.png");
    }

    @Override
    public void render(HorseshoeCrabEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
