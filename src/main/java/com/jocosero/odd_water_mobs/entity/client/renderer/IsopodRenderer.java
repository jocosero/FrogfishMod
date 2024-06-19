package com.jocosero.odd_water_mobs.entity.client.renderer;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import com.jocosero.odd_water_mobs.entity.client.renderer.layers.ModModelLayers;
import com.jocosero.odd_water_mobs.entity.client.model.IsopodModel;
import com.jocosero.odd_water_mobs.entity.custom.IsopodEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;


public class IsopodRenderer extends MobRenderer<IsopodEntity, IsopodModel<IsopodEntity>> {

    public IsopodRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new IsopodModel(pContext.bakeLayer(ModModelLayers.ISOPOD_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(IsopodEntity pEntity) {
        return new ResourceLocation(OddWaterMobs.MOD_ID, "textures/entity/isopod.png");
    }

    @Override
    public void render(IsopodEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}

