package com.jocosero.odd_water_mobs.block.entity.renderer;

import com.jocosero.odd_water_mobs.block.entity.TrapperBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TrapperBlockEntityRenderer implements BlockEntityRenderer<TrapperBlockEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public TrapperBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = Minecraft.getInstance().getBlockRenderer();
    }

    @Override
    public void render(TrapperBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState blockState = Blocks.STONE.defaultBlockState(); // Example block state
        BlockPos blockPos = blockEntity.getBlockPos();
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        this.blockRenderer.renderSingleBlock(blockState, poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}
