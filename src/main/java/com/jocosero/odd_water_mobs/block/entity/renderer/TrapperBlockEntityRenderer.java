package com.jocosero.odd_water_mobs.block.entity.renderer;

import com.jocosero.odd_water_mobs.block.custom.TrapperBlock;
import com.jocosero.odd_water_mobs.block.entity.TrapperBlockEntity;
import com.jocosero.odd_water_mobs.entity.client.renderer.layers.ModModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TrapperBlockEntityRenderer implements BlockEntityRenderer<TrapperBlockEntity> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("odd_water_mobs:textures/entity/trapper_block_entity.png");
    private final ModelPart lid;

    public TrapperBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart model = context.bakeLayer(ModModelLayers.TRAPPER_LAYER);
        this.lid = model;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition lid = partdefinition.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -3.0F, -16.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(52, 0).addBox(-3.0F, -5.0F, -7.5F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 8.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void render(TrapperBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState blockState = blockEntity.getBlockState();
        BlockPos blockPos = blockEntity.getBlockPos();

        poseStack.pushPose();
        poseStack.translate(0.5, -0.5, 0.5);
        Direction direction = blockState.getValue(TrapperBlock.FACING);

        switch (direction) {
            case NORTH:
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                break;
            case SOUTH:
                poseStack.mulPose(Axis.YP.rotationDegrees(0F));
                break;
            case WEST:
                poseStack.mulPose(Axis.YP.rotationDegrees(270.0F));
                break;
            case EAST:
                poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                break;
            default:
                break;
        }
        boolean hasEntity = blockEntity.getBlockState().getValue(TrapperBlock.HAS_ENTITY);
        boolean isPowered = blockEntity.getBlockState().getValue(TrapperBlock.POWERED);

        if (hasEntity && !isPowered) {
            long ticks = blockEntity.getLevel().getGameTime();
            float shakeFrequency = 2.0F * (float) Math.PI / 20.0F;
            float maxShakeIntensity = 3.0F;

            float elapsedTicks = (ticks + partialTick) % 20;
            float intensity = maxShakeIntensity * (float) Math.exp(-elapsedTicks / 10.0);
            float rotationAngle = intensity * Mth.sin(elapsedTicks * shakeFrequency);

            poseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle));
            poseStack.translate(0, 0.01 + (Mth.cos(elapsedTicks) * 0.01), 0);
        }

        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE_LOCATION));
        this.lid.render(poseStack, vertexconsumer, packedLight, packedOverlay);
        poseStack.popPose();
    }
}