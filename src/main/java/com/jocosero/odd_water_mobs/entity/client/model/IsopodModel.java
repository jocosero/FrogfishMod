package com.jocosero.odd_water_mobs.entity.client.model;

import com.jocosero.odd_water_mobs.entity.animation.FrogfishAnimationDefinitions;
import com.jocosero.odd_water_mobs.entity.animation.HorseshoeCrabAnimationDefinitions;
import com.jocosero.odd_water_mobs.entity.animation.IsopodAnimationDefinitions;
import com.jocosero.odd_water_mobs.entity.custom.HorseshoeCrabEntity;
import com.jocosero.odd_water_mobs.entity.custom.IsopodEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;


public class IsopodModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart head;
    private final ModelPart leftantenna;
    private final ModelPart rightantenna;
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

    public IsopodModel(ModelPart root) {
        this.body = root.getChild("body");
        this.tail = body.getChild("tail");
        this.head = body.getChild("head");
        this.leftantenna = body.getChild("head").getChild("leftantenna");
        this.rightantenna = body.getChild("head").getChild("rightantenna");
        this.legs = body.getChild("legs");
        this.leftlegs = body.getChild("legs").getChild("leftlegs");
        this.leftleg1 = body.getChild("legs").getChild("leftlegs").getChild("leftleg1");
        this.leftleg2 = body.getChild("legs").getChild("leftlegs").getChild("leftleg2");
        this.leftleg3 = body.getChild("legs").getChild("leftlegs").getChild("leftleg3");
        this.leftleg4 = body.getChild("legs").getChild("leftlegs").getChild("leftleg4");
        this.leftleg5 = body.getChild("legs").getChild("leftlegs").getChild("leftleg5");
        this.rightlegs = body.getChild("legs").getChild("rightlegs");
        this.rightleg1 = body.getChild("legs").getChild("rightlegs").getChild("rightleg1");
        this.rightleg2 = body.getChild("legs").getChild("rightlegs").getChild("rightleg2");
        this.rightleg3 = body.getChild("legs").getChild("rightlegs").getChild("rightleg3");
        this.rightleg4 = body.getChild("legs").getChild("rightlegs").getChild("rightleg4");
        this.rightleg5 = body.getChild("legs").getChild("rightlegs").getChild("rightleg5");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -5.0F, 7.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-3.0F, -3.0F, -5.0F, 5.0F, 2.0F, 9.0F, new CubeDeformation(0.01F)), PartPose.offset(0.5F, 23.0F, 0.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(20, 14).addBox(-3.5F, -1.0F, 0.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 19).addBox(-3.5F, 0.0F, 2.0F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 4.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -1.5F, -2.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -5.0F));

        PartDefinition leftantenna = head.addOrReplaceChild("leftantenna", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -2.0F));

        PartDefinition tailfin_r1 = leftantenna.addOrReplaceChild("tailfin_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -4.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, -0.7854F, 0.0F));

        PartDefinition rightantenna = head.addOrReplaceChild("rightantenna", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.5F, -2.0F));

        PartDefinition tailfin_r2 = rightantenna.addOrReplaceChild("tailfin_r2", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -4.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, 0.7854F, 0.0F));

        PartDefinition legs = body.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(-0.5F, -1.5F, -0.5F));

        PartDefinition leftlegs = legs.addOrReplaceChild("leftlegs", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 0.0F));

        PartDefinition leftleg1 = leftlegs.addOrReplaceChild("leftleg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -4.0F));

        PartDefinition leftleg1_r1 = leftleg1.addOrReplaceChild("leftleg1_r1", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.1745F));

        PartDefinition leftleg2 = leftlegs.addOrReplaceChild("leftleg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.0F));

        PartDefinition leftleg2_r1 = leftleg2.addOrReplaceChild("leftleg2_r1", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.1745F));

        PartDefinition leftleg3 = leftlegs.addOrReplaceChild("leftleg3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftleg3_r1 = leftleg3.addOrReplaceChild("leftleg3_r1", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition leftleg4 = leftlegs.addOrReplaceChild("leftleg4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition leftleg4_r1 = leftleg4.addOrReplaceChild("leftleg4_r1", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.1745F));

        PartDefinition leftleg5 = leftlegs.addOrReplaceChild("leftleg5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0F));

        PartDefinition leftleg5_r1 = leftleg5.addOrReplaceChild("leftleg5_r1", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.1745F));

        PartDefinition rightlegs = legs.addOrReplaceChild("rightlegs", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 0.0F));

        PartDefinition rightleg1 = rightlegs.addOrReplaceChild("rightleg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -4.0F));

        PartDefinition rightleg1_r1 = rightleg1.addOrReplaceChild("rightleg1_r1", CubeListBuilder.create().texOffs(0, 6).mirror().addBox(-3.0F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, -0.1745F));

        PartDefinition rightleg2 = rightlegs.addOrReplaceChild("rightleg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.0F));

        PartDefinition rightleg2_r1 = rightleg2.addOrReplaceChild("rightleg2_r1", CubeListBuilder.create().texOffs(0, 6).mirror().addBox(-3.0F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, -0.1745F));

        PartDefinition rightleg3 = rightlegs.addOrReplaceChild("rightleg3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightleg3_r1 = rightleg3.addOrReplaceChild("rightleg3_r1", CubeListBuilder.create().texOffs(0, 6).mirror().addBox(-3.0F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition rightleg4 = rightlegs.addOrReplaceChild("rightleg4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition rightleg4_r1 = rightleg4.addOrReplaceChild("rightleg4_r1", CubeListBuilder.create().texOffs(0, 6).mirror().addBox(-3.0F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, -0.1745F));

        PartDefinition rightleg5 = rightlegs.addOrReplaceChild("rightleg5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0F));

        PartDefinition rightleg5_r1 = rightleg5.addOrReplaceChild("rightleg5_r1", CubeListBuilder.create().texOffs(0, 6).mirror().addBox(-3.0F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, -0.1745F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public ModelPart root() {
        return body;
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(IsopodAnimationDefinitions.ISOPOD_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((IsopodEntity) entity).swimmingAnimationState, IsopodAnimationDefinitions.ISOPOD_SWIM, ageInTicks, 1f);
        if (!((IsopodEntity) entity).swimmingAnimationState.isStarted()) {
            this.animate(((IsopodEntity) entity).idleAnimationState, IsopodAnimationDefinitions.ISOPOD_IDLE, ageInTicks, 1f);
        }

//        body.yRot = (Mth.cos(limbSwing * 0.6662F) * -0.4F * limbSwingAmount * 0.3F);
//        tail.xRot = (Mth.cos(limbSwing * 0.6662F) * -0.4F * limbSwingAmount * 0.3F);

    }
}
