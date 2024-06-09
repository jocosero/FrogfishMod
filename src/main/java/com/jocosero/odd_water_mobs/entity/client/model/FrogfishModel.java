package com.jocosero.odd_water_mobs.entity.client.model;

import com.jocosero.odd_water_mobs.entity.animation.FrogfishAnimationDefinitions;
import com.jocosero.odd_water_mobs.entity.custom.FrogfishEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class FrogfishModel<T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart frogfish;
    private final ModelPart body;
    private final ModelPart left_fin;
    private final ModelPart right_fin;
    private final ModelPart tail;
    private final ModelPart tail_fin;
    private final ModelPart jaw;

    public FrogfishModel(ModelPart root) {

        this.frogfish = root.getChild("frogfish");
        this.body = frogfish.getChild("body");
        this.jaw = frogfish.getChild("body").getChild("jaw");
        this.left_fin = frogfish.getChild("left_fin");
        this.right_fin = frogfish.getChild("right_fin");
        this.tail = frogfish.getChild("tail");
        this.tail_fin = frogfish.getChild("tail").getChild("tail_fin");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition frogfish = partdefinition.addOrReplaceChild("frogfish", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = frogfish.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -5.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 9).addBox(0.0F, -6.0F, -5.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.5F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.45F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, 0.2182F, 0.0F, 0.0F));

        PartDefinition jaw = body.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -5.0F));

        PartDefinition cube_r2 = jaw.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(10, 14).addBox(-1.5F, -3.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition left_fin = frogfish.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(13, 0).mirror().addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, -2.0F, -3.5F));

        PartDefinition right_fin = frogfish.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(13, 0).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -2.0F, -3.5F));

        PartDefinition tail = frogfish.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(10, 9).addBox(-1.5F, -1.25F, 0.25F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.75F, 0.25F));

        PartDefinition tail_fin = tail.addOrReplaceChild("tail_fin", CubeListBuilder.create().texOffs(0, 4).addBox(0.0F, -3.5F, 0.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.001F, 0.25F, 0.25F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (entity.onGround()) {
            this.animateWalk(FrogfishAnimationDefinitions.FROGFISH_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        } else {
            this.animateWalk(FrogfishAnimationDefinitions.FROGFISH_SWIM, limbSwing, limbSwingAmount, 2f, 2.5f);
        }
        this.animate(((FrogfishEntity) entity).swimmingAnimationState, FrogfishAnimationDefinitions.FROGFISH_SWIM, ageInTicks, 1f);
        if (!((FrogfishEntity) entity).swimmingAnimationState.isStarted()) {
            this.animate(((FrogfishEntity) entity).idleAnimationState, FrogfishAnimationDefinitions.FROGFISH_IDLE, ageInTicks, 1f);
        }
        float f = entity.isInWater() ? 1.5F : 0.0F;
        float f1 = !entity.isInWater() ? 1.5F : 0.0F;
        float f2 = (Mth.cos(limbSwing * 0.01F)) * 0.2F * limbSwingAmount;
        float f3 = (Mth.cos(limbSwing * 0.2F)) * 0.8F * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        frogfish.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return frogfish;
    }
}