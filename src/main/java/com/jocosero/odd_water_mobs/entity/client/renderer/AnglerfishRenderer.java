package com.jocosero.odd_water_mobs.entity.client.renderer;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import com.jocosero.odd_water_mobs.entity.client.ModModelLayers;
import com.jocosero.odd_water_mobs.entity.client.model.AnglerfishModel;
import com.jocosero.odd_water_mobs.entity.custom.AnglerfishEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AnglerfishRenderer extends MobRenderer<AnglerfishEntity, AnglerfishModel<AnglerfishEntity>> {

    public AnglerfishRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AnglerfishModel<>(pContext.bakeLayer(ModModelLayers.ANGLERFISH_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(AnglerfishEntity pEntity) {
        if (pEntity.isPuffed()) {
            return new ResourceLocation(OddWaterMobs.MOD_ID, "textures/entity/anglerfish/anglerfish_puffed.png");
        }
        return switch (pEntity.getTypeVariant()) {
            case 0 -> // DEFAULT
                    new ResourceLocation(OddWaterMobs.MOD_ID, "textures/entity/anglerfish/anglerfish.png");
            case 1 -> // DEFAULT PUFFED
                    new ResourceLocation(OddWaterMobs.MOD_ID, "textures/entity/anglerfish/anglerfish_puffed.png");
//            case 2: // DEEP
//                return new ResourceLocation(OddWaterMobs.MOD_ID, "textures/entity/anglerfish/anglerfish_deep.png");
//            case 3: // DEEP PUFFED
//                return new ResourceLocation(OddWaterMobs.MOD_ID, "textures/entity/anglerfish/anglerfish_deep_puffed.png");
            default -> new ResourceLocation(OddWaterMobs.MOD_ID, "textures/entity/anglerfish/anglerfish.png");
        };
    }

    @Override
    public void render(AnglerfishEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }


}
