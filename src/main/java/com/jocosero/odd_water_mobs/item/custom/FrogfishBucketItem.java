package com.jocosero.odd_water_mobs.item.custom;

import com.jocosero.odd_water_mobs.entity.ModEntities;
import com.jocosero.odd_water_mobs.entity.custom.variant.FrogfishVariant;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.Locale;

public class FrogfishBucketItem extends MobBucketItem {
    public FrogfishBucketItem(Properties properties) {
        super(ModEntities.FROGFISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        CompoundTag compoundnbt = stack.getTag();
        if (compoundnbt != null && compoundnbt.contains("Variant", 3)) {
            FrogfishVariant type = FrogfishVariant.byId(compoundnbt.getInt("Variant"));
            tooltip.add((Component.translatable(String.format("tooltip.odd_water_mobs.%s_frogfish", type.name().toLowerCase(Locale.ROOT))).withStyle(ChatFormatting.ITALIC)));
        }
    }
}
