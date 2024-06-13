package com.jocosero.odd_water_mobs.block.entity.renderer;

import com.jocosero.odd_water_mobs.block.ModBlocks;
import com.jocosero.odd_water_mobs.block.custom.TrapperBlock;
import com.jocosero.odd_water_mobs.block.entity.TrapperBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class TrapperBlockEntityRenderer implements BlockEntityRenderer<TrapperBlockEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public TrapperBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = Minecraft.getInstance().getBlockRenderer();
    }

    @Override
    public void render(TrapperBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState blockState = ModBlocks.TRAPPER.get().defaultBlockState();
        BlockPos blockPos = blockEntity.getBlockPos();

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        Direction direction = blockEntity.getBlockState().getValue(TrapperBlock.FACING);

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

        if (blockEntity.hasEntity()) {
            Random random = new Random();
            long time = blockEntity.getLevel().getGameTime();
            float shakeFrequency = 20.0F + random.nextFloat() * (180.0F - 20.0F);
            float maxShakeIntensity = 8.0F; // Maximum intensity of the shake

            float elapsedTicks = (time + partialTick) % 40;

            float intensity = maxShakeIntensity * (float) Math.exp(-elapsedTicks / 10.0);

            float rotationAngle = intensity * Mth.sin(elapsedTicks * shakeFrequency * 0.1F);

            poseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle));
            poseStack.translate(0, 0.01 + (Mth.cos(time + partialTick) * 0.01), 0);

        }

        poseStack.translate(-0.5, -0.5, -0.5);

        this.blockRenderer.renderSingleBlock(blockState, poseStack, bufferSource, packedLight, packedOverlay);
        poseStack.popPose();
    }
}