package com.jocosero.odd_water_mobs.entity.client.model;

import com.jocosero.odd_water_mobs.entity.animation.AnglerfishAnimationDefinitions;
import com.jocosero.odd_water_mobs.entity.animation.FrogfishAnimationDefinitions;
import com.jocosero.odd_water_mobs.entity.custom.AnglerfishEntity;
import com.jocosero.odd_water_mobs.entity.custom.FrogfishEntity;
import com.jocosero.odd_water_mobs.entity.custom.HorseshoeCrabEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class AnglerfishModel<T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart anglerfish;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart antenna;
    private final ModelPart left_fin;
    private final ModelPart right_fin;
    private final ModelPart tail_fin;


    public AnglerfishModel(ModelPart root) {
        this.anglerfish = root.getChild("anglerfish");
        this.body = anglerfish.getChild("body");
        this.head = anglerfish.getChild("body").getChild("head");
        this.jaw = anglerfish.getChild("body").getChild("head").getChild("jaw");
        this.antenna = anglerfish.getChild("body").getChild("head").getChild("antenna");
        this.left_fin = anglerfish.getChild("body").getChild("left_fin");
        this.right_fin = anglerfish.getChild("body").getChild("right_fin");
        this.tail_fin = anglerfish.getChild("body").getChild("tail_fin");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition anglerfish = partdefinition.addOrReplaceChild("anglerfish", CubeListBuilder.create(), PartPose.offset(0.0F, 21.5F, 0.5F));

        PartDefinition body = anglerfish.addOrReplaceChild("body", CubeListBuilder.create().texOffs(11, 11).addBox(-2.0F, -5.0F, -1.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(1, 5).addBox(0.0F, -6.0F, 0.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 0.5F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(16, 0).addBox(-2.5F, -3.0F, -4.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(0, 28).addBox(-2.5F, 0.0F, -4.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -1.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 4).addBox(-2.5F, -1.0F, -2.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.5F, -3.0F, -2.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -4.0F));

        PartDefinition antenna = head.addOrReplaceChild("antenna", CubeListBuilder.create().texOffs(0, 0).addBox(0.0436F, -3.0F, -6.001F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -2.0F, 0.0F, -0.0436F, 0.0F));

        PartDefinition left_fin = body.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(6, 13).mirror().addBox(0.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -1.0F, 1.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition right_fin = body.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(6, 13).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -1.0F, 1.0F, 0.0F, 0.0F, -0.6109F));

        PartDefinition tail_fin = body.addOrReplaceChild("tail_fin", CubeListBuilder.create().texOffs(0, 11).addBox(0.0F, -2.0F, 0.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 4.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
//        this.animate(((AnglerfishEntity) entity).swimAnimationState, AnglerfishAnimationDefinitions.ANGLERFISH_SWIM, ageInTicks, 1f);
        this.animateWalk(AnglerfishAnimationDefinitions.ANGLERFISH_SWIM, limbSwing, limbSwingAmount, 1.5f, 2f);
        this.animate(((AnglerfishEntity) entity).idleAnimationState, AnglerfishAnimationDefinitions.ANGLERFISH_IDLE, ageInTicks, 1f);
        this.animate(((AnglerfishEntity) entity).puffedAnimationState, AnglerfishAnimationDefinitions.ANGLERFISH_PUFFED, ageInTicks, 1f);
        this.animate(((AnglerfishEntity) entity).attackAnimationState, AnglerfishAnimationDefinitions.ANGLERFISH_ATTACK, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        anglerfish.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return anglerfish;
    }
}