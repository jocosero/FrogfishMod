package com.jocosero.odd_water_mobs.entity.client.renderer.layers;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import com.jocosero.odd_water_mobs.entity.client.model.AnglerfishModel;
import com.jocosero.odd_water_mobs.entity.custom.AnglerfishEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class AnglerfishEmissiveLayer extends RenderLayer<AnglerfishEntity, AnglerfishModel<AnglerfishEntity>> {

    private static final ResourceLocation EMISSIVE_TEXTURE = new ResourceLocation(OddWaterMobs.MOD_ID, "textures/entity/anglerfish/anglerfish_e.png");

    public AnglerfishEmissiveLayer(RenderLayerParent<AnglerfishEntity, AnglerfishModel<AnglerfishEntity>> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AnglerfishEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.eyes(EMISSIVE_TEXTURE));
        this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.WHITE_OVERLAY_V, limbSwing, limbSwingAmount, ageInTicks, headPitch);
    }
}
