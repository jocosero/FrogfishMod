package com.jocosero.odd_water_mobs.entity.client.renderer;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import com.jocosero.odd_water_mobs.entity.client.renderer.layers.ModModelLayers;
import com.jocosero.odd_water_mobs.entity.client.model.FrogfishModel;
import com.jocosero.odd_water_mobs.entity.custom.FrogfishEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FrogfishRenderer extends MobRenderer<FrogfishEntity, FrogfishModel<FrogfishEntity>> {
    public FrogfishRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FrogfishModel<>(pContext.bakeLayer(ModModelLayers.FROGFISH_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(FrogfishEntity pEntity) {
        switch (pEntity.getTypeVariant()) {
            case 0: // WHITE
                return new ResourceLocation(OddWaterMobs.MOD_ID, "textures/entity/frogfish/frogfish_white.png");
            case 1: // ORANGE
                return new ResourceLocation(OddWaterMobs.MOD_ID, "textures/entity/frogfish/frogfish_orange.png");
            case 2: // YELLOW
                return new ResourceLocation(OddWaterMobs.MOD_ID, "textures/entity/frogfish/frogfish_yellow.png");
            default:
                return new ResourceLocation(OddWaterMobs.MOD_ID, "textures/entity/frogfish/frogfish.png");
        }
    }

    @Override
    public void render(FrogfishEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
