package com.jocosero.odd_water_mobs.entity.client.model;

import com.jocosero.odd_water_mobs.entity.animation.HorseshoeCrabAnimationDefinitions;
import com.jocosero.odd_water_mobs.entity.custom.HorseshoeCrabEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class HorseshoeCrabModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart horseshoe_crab;
    private final ModelPart body;
    private final ModelPart thorax;
    private final ModelPart tail;
    private final ModelPart legs;
    private final ModelPart leftlegs;
    private final ModelPart leftleg1;
    private final ModelPart leftleg2;
    private final ModelPart leftleg3;
    private final ModelPart leftleg4;
    private final ModelPart leftleg5;
    private final ModelPart rightlegs;
    private final ModelPart rightleg1;
    private final ModelPart rightleg2;
    private final ModelPart rightleg3;
    private final ModelPart rightleg4;
    private final ModelPart rightleg5;

    public HorseshoeCrabModel(ModelPart root) {
        this.horseshoe_crab = root.getChild("horseshoe_crab");
        this.body = horseshoe_crab.getChild("body");
        this.thorax = horseshoe_crab.getChild("thorax");
        this.tail = horseshoe_crab.getChild("thorax").getChild("tail");
        this.legs = horseshoe_crab.getChild("legs");
        this.leftlegs = horseshoe_crab.getChild("legs").getChild("leftlegs");
        this.leftleg1 = horseshoe_crab.getChild("legs").getChild("leftlegs").getChild("leftleg1");
        this.leftleg2 = horseshoe_crab.getChild("legs").getChild("leftlegs").getChild("leftleg2");
        this.leftleg3 = horseshoe_crab.getChild("legs").getChild("leftlegs").getChild("leftleg3");
        this.leftleg4 = horseshoe_crab.getChild("legs").getChild("leftlegs").getChild("leftleg4");
        this.leftleg5 = horseshoe_crab.getChild("legs").getChild("leftlegs").getChild("leftleg5");
        this.rightlegs = horseshoe_crab.getChild("legs").getChild("rightlegs");
        this.rightleg1 = horseshoe_crab.getChild("legs").getChild("rightlegs").getChild("rightleg1");
        this.rightleg2 = horseshoe_crab.getChild("legs").getChild("rightlegs").getChild("rightleg2");
        this.rightleg3 = horseshoe_crab.getChild("legs").getChild("rightlegs").getChild("rightleg3");
        this.rightleg4 = horseshoe_crab.getChild("legs").getChild("rightlegs").getChild("rightleg4");
        this.rightleg5 = horseshoe_crab.getChild("legs").getChild("rightlegs").getChild("rightleg5");

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition horseshoe_crab = partdefinition.addOrReplaceChild("horseshoe_crab", CubeListBuilder.create(), PartPose.offset(0.5F, 21.7923F, 0.5223F));

        PartDefinition body = horseshoe_crab.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.625F, -4.25F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(-3, 9).addBox(-4.0F, -0.825F, -4.25F, 8.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 9).addBox(-3.0F, 0.375F, -4.25F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(4.0F, -1.625F, 1.75F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).mirror().addBox(-4.0F, -1.625F, 1.75F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.3327F, -2.2723F));

        PartDefinition thorax = horseshoe_crab.addOrReplaceChild("thorax", CubeListBuilder.create(), PartPose.offset(0.0F, -1.2923F, -1.5223F));

        PartDefinition thorax_r1 = thorax.addOrReplaceChild("thorax_r1", CubeListBuilder.create().texOffs(0, 15).addBox(-3.0F, 1.0F, 0.0F, 7.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 14).addBox(-2.0F, 0.0F, 0.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, -0.5411F, 0.0F, 0.0F));

        PartDefinition tail = thorax.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 2.05F, 3.425F));

        PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-0.55F, 0.0F, 0.0F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.05F, 0.025F, 0.0F, -0.2051F, 0.0F, 0.0F));

        PartDefinition legs = horseshoe_crab.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 1.2077F, -3.0223F));

        PartDefinition leftlegs = legs.addOrReplaceChild("leftlegs", CubeListBuilder.create(), PartPose.offset(0.75F, -0.5F, 0.0F));

        PartDefinition leftleg1 = leftlegs.addOrReplaceChild("leftleg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.0F));

        PartDefinition leftleg1_r1 = leftleg1.addOrReplaceChild("leftleg1_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -1.0F, 0.0F, 0.0F, 0.7854F, 0.1745F));

        PartDefinition leftleg2 = leftlegs.addOrReplaceChild("leftleg2", CubeListBuilder.create(), PartPose.offset(0.05F, 0.0F, -0.5F));

        PartDefinition leftleg2_r1 = leftleg2.addOrReplaceChild("leftleg2_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, -1.0F, 0.0F, 0.0F, 0.2618F, 0.1745F));

        PartDefinition leftleg3 = leftlegs.addOrReplaceChild("leftleg3", CubeListBuilder.create(), PartPose.offset(0.05F, 0.0F, 0.0F));

        PartDefinition leftleg3_r1 = leftleg3.addOrReplaceChild("leftleg3_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, -1.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition leftleg4 = leftlegs.addOrReplaceChild("leftleg4", CubeListBuilder.create(), PartPose.offset(0.05F, 0.0F, 0.5F));

        PartDefinition leftleg4_r1 = leftleg4.addOrReplaceChild("leftleg4_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, -1.0F, 0.0F, 0.0F, -0.2618F, 0.1745F));

        PartDefinition leftleg5 = leftlegs.addOrReplaceChild("leftleg5", CubeListBuilder.create(), PartPose.offset(0.05F, 0.0F, 1.0F));

        PartDefinition leftleg5_r1 = leftleg5.addOrReplaceChild("leftleg5_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0165F, -0.0195F, -0.0165F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.1745F));

        PartDefinition rightlegs = legs.addOrReplaceChild("rightlegs", CubeListBuilder.create(), PartPose.offset(-0.75F, -0.5F, 0.0F));

        PartDefinition rightleg1 = rightlegs.addOrReplaceChild("rightleg1", CubeListBuilder.create(), PartPose.offset(-0.05F, 0.0F, -1.0F));

        PartDefinition rightleg1_r1 = rightleg1.addOrReplaceChild("rightleg1_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-3.0F, 1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2F, -1.0F, 0.0F, 0.0F, -0.7854F, -0.1745F));

        PartDefinition rightleg2 = rightlegs.addOrReplaceChild("rightleg2", CubeListBuilder.create(), PartPose.offset(-0.05F, 0.0F, -0.5F));

        PartDefinition rightleg2_r1 = rightleg2.addOrReplaceChild("rightleg2_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-3.0F, 1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2F, -1.0F, 0.0F, 0.0F, -0.2618F, -0.1745F));

        PartDefinition rightleg3 = rightlegs.addOrReplaceChild("rightleg3", CubeListBuilder.create(), PartPose.offset(-0.05F, 0.0F, 0.0F));

        PartDefinition rightleg3_r1 = rightleg3.addOrReplaceChild("rightleg3_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-3.0F, 1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2F, -1.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition rightleg4 = rightlegs.addOrReplaceChild("rightleg4", CubeListBuilder.create(), PartPose.offset(-0.05F, 0.0F, 0.5F));

        PartDefinition rightleg4_r1 = rightleg4.addOrReplaceChild("rightleg4_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-3.0F, 1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2F, -1.0F, 0.0F, 0.0F, 0.2618F, -0.1745F));

        PartDefinition rightleg5 = rightlegs.addOrReplaceChild("rightleg5", CubeListBuilder.create(), PartPose.offset(-0.05F, 0.0F, 1.0F));

        PartDefinition rightleg5_r1 = rightleg5.addOrReplaceChild("rightleg5_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-3.0F, 1.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2F, -1.0F, 0.0F, 0.0F, 0.7854F, -0.1745F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(HorseshoeCrabAnimationDefinitions.HORSESHOE_CRAB_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((HorseshoeCrabEntity) entity).flippedAnimationState, HorseshoeCrabAnimationDefinitions.HORSESHOE_CRAB_FLIP, ageInTicks, 1f);
        if (!((HorseshoeCrabEntity) entity).flippedAnimationState.isStarted()) {
            this.animate(((HorseshoeCrabEntity) entity).idleAnimationState, HorseshoeCrabAnimationDefinitions.HORSESHOE_CRAB_IDLE, ageInTicks, 1f);
        } else {
            this.horseshoe_crab.xRot = 3f;
            this.horseshoe_crab.yRot = 3f;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        horseshoe_crab.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return horseshoe_crab;
    }
}
