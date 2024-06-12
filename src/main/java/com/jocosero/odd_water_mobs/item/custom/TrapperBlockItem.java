package com.jocosero.odd_water_mobs.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class TrapperBlockItem extends BlockItem {
    public TrapperBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("BlockEntityTag") && tag.getCompound("BlockEntityTag").contains("TrappedEntity")) {
            CompoundTag entityData = tag.getCompound("BlockEntityTag").getCompound("TrappedEntity");
            String entityName = entityData.getString("id");
            tooltip.add(Component.literal("Contains: " + entityName));
        }
    }
}
