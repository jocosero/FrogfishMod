package com.jocosero.odd_water_mobs.event;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import com.jocosero.odd_water_mobs.block.ModBlocks;
import com.jocosero.odd_water_mobs.effect.ModEffects;
import com.jocosero.odd_water_mobs.item.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = OddWaterMobs.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entityWithEffect = event.getEntity();
        if (entityWithEffect != null && entityWithEffect.hasEffect(ModEffects.ALLURING.get())) {
            List<LivingEntity> entitiesInRange = entityWithEffect.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, entityWithEffect, entityWithEffect.getBoundingBox().inflate(10));
            for (LivingEntity entity : entitiesInRange) {
                if (entity instanceof Animal animal) {
                    if (!alreadyFollowingPlayer(animal)) {
                        animal.goalSelector.addGoal(-1, new TemptGoal(animal, 0.4D, Ingredient.EMPTY, false));
                    }
                }
                if (entity instanceof Villager villager) {
                    if (!alreadyFollowingPlayer(villager)) {
                        villager.goalSelector.addGoal(-1, new TemptGoal(villager, 0.4D, Ingredient.EMPTY, false));
                    }
                }
            }
        }
    }

    private static boolean alreadyFollowingPlayer(PathfinderMob entity) {
        for (WrappedGoal wrappedGoal : entity.goalSelector.getAvailableGoals()) {
            Goal goal = wrappedGoal.getGoal();
            if (goal instanceof TemptGoal) {
                return true;
            }
        }
        return false;
    }


    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.FISHERMAN) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            ItemStack raw_anglerfish = new ItemStack(ModItems.RAW_ANGLERFISH.get(), 1);
            ItemStack horseshoe_crab_thorax = new ItemStack(ModItems.HORSESHOE_CRAB_THORAX.get(), 2);
            ItemStack deep_sand_sell = new ItemStack(ModBlocks.DEEP_SAND.get(), 1);
            ItemStack spider_crab_leg_sell = new ItemStack(ModItems.RAW_SPIDER_CRAB_LEG.get(), 1);
            ItemStack coelacanth_bucket_sell = new ItemStack(Items.EMERALD, 45);

            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    raw_anglerfish, 16, 5, 0.05F));

            trades.get(3).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 7),
                    horseshoe_crab_thorax, 12, 10, 0.05F));

            trades.get(3).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3),
                    deep_sand_sell, 32, 2, 0.05F));

            trades.get(4).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 6),
                    spider_crab_leg_sell, 8, 5, 0.05F));
        }
    }

}
