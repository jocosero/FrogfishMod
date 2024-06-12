package com.jocosero.odd_water_mobs.block.custom;

import com.jocosero.odd_water_mobs.item.ModItems;
import com.jocosero.odd_water_mobs.util.ModTags;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;

public class ChumBarrelBlock extends Block implements WorldlyContainerHolder {
    public static final int READY = 8;
    public static final int MIN_LEVEL = 0;
    public static final int MAX_LEVEL = 7;
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_COMPOSTER;
    public static final Object2IntMap<TagKey<Item>> CHUM_INGREDIENT = new Object2IntOpenHashMap<>();
    private static final int AABB_SIDE_THICKNESS = 2;
    private static final VoxelShape OUTER_SHAPE = Shapes.block();
    private static final VoxelShape[] SHAPES = Util.make(new VoxelShape[9], (p_51967_) -> {
        for (int i = 0; i < 8; ++i) {
            p_51967_[i] = Shapes.join(OUTER_SHAPE, Block.box(2.0D, (double) Math.max(2, 1 + i * 2), 2.0D, 14.0D, 16.0D, 14.0D), BooleanOp.ONLY_FIRST);
        }

        p_51967_[8] = p_51967_[7];
    });

    public ChumBarrelBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(0)));
    }

    public static void chumIngredients() {
        CHUM_INGREDIENT.defaultReturnValue(-1);
        int fillOne = 1;
        int fillTwo = 2;
        int fillHalf = 4;
        int fillUp = 7;
        add(fillOne, ModTags.Items.CHUM_INGREDIENTS_ONE_LAYER);
        add(fillTwo, ModTags.Items.CHUM_INGREDIENTS_TWO_LAYERS);
        add(fillHalf, ModTags.Items.CHUM_INGREDIENTS_HALF_LAYERS);
        add(fillUp, ModTags.Items.CHUM_INGREDIENTS_FULL_LAYERS);
    }

    private static void add(int layers, TagKey<Item> tag) {
        CHUM_INGREDIENT.put(tag, layers);
    }

    @OnlyIn(Dist.CLIENT)
    public static void handleFill(Level pLevel, BlockPos pPos, boolean pSuccess) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        pLevel.playLocalSound(pPos, pSuccess ? SoundEvents.COMPOSTER_FILL_SUCCESS : SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 1.0F, 1.0F, false);
        double d0 = blockstate.getShape(pLevel, pPos).max(Direction.Axis.Y, 0.5D, 0.5D) + 0.03125D;
        double d1 = (double) 0.13125F;
        double d2 = (double) 0.7375F;
        RandomSource randomsource = pLevel.getRandom();

        for (int i = 0; i < 10; ++i) {
            double d3 = randomsource.nextGaussian() * 0.02D;
            double d4 = randomsource.nextGaussian() * 0.02D;
            double d5 = randomsource.nextGaussian() * 0.02D;
            pLevel.addParticle(ParticleTypes.COMPOSTER, (double) pPos.getX() + (double) 0.13125F + (double) 0.7375F * (double) randomsource.nextFloat(), (double) pPos.getY() + d0 + (double) randomsource.nextFloat() * (1.0D - d0), (double) pPos.getZ() + (double) 0.13125F + (double) 0.7375F * (double) randomsource.nextFloat(), d3, d4, d5);
        }

    }

    public static BlockState insertItem(Entity pEntity, BlockState pState, ServerLevel pLevel, ItemStack pStack, BlockPos pPos) {
        int i = pState.getValue(LEVEL);
        int layers = CHUM_INGREDIENT.entrySet().stream()
                .filter(entry -> pStack.is(entry.getKey()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(-1);
        if (i < 7 && layers != -1) {
            BlockState blockstate = addItem(pEntity, pState, pLevel, pPos, pStack);
            pStack.shrink(1);
            return blockstate;
        } else {
            return pState;
        }
    }

    public static BlockState extractProduce(Entity pEntity, BlockState pState, Level pLevel, BlockPos pPos) {
        if (!pLevel.isClientSide) {
            Vec3 vec3 = Vec3.atLowerCornerWithOffset(pPos, 0.5D, 1.01D, 0.5D).offsetRandom(pLevel.random, 0.7F);
            ItemEntity itementity = new ItemEntity(pLevel, vec3.x(), vec3.y(), vec3.z(), new ItemStack(ModItems.DEEP_SEA_FISH.get()));
            itementity.setDefaultPickUpDelay();
            pLevel.addFreshEntity(itementity);
        }

        BlockState blockstate = empty(pEntity, pState, pLevel, pPos);
        pLevel.playSound((Player) null, pPos, SoundEvents.COMPOSTER_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        return blockstate;
    }

    static BlockState empty(@Nullable Entity pEntity, BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
        BlockState blockstate = pState.setValue(LEVEL, Integer.valueOf(0));
        pLevel.setBlock(pPos, blockstate, 3);
        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pEntity, blockstate));
        return blockstate;
    }

    static BlockState addItem(@Nullable Entity pEntity, BlockState pState, LevelAccessor pLevel, BlockPos pPos, ItemStack pStack) {
        int i = pState.getValue(LEVEL);
        int layers = CHUM_INGREDIENT.entrySet().stream().filter(entry -> pStack.is(entry.getKey())).findFirst().map(Map.Entry::getValue).orElse(-1);
        if (i < 7 && layers != -1) {
            int j = Math.min(i + layers, 7);
            BlockState blockstate = pState.setValue(LEVEL, Integer.valueOf(j));
            pLevel.setBlock(pPos, blockstate, 3);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pEntity, blockstate));
            if (j == 7) {
                pLevel.scheduleTick(pPos, pState.getBlock(), 20);
            }

            return blockstate;
        } else {
            return pState;
        }
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES[pState.getValue(LEVEL)];
    }

    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return OUTER_SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES[0];
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (pState.getValue(LEVEL) == 7) {
            pLevel.scheduleTick(pPos, pState.getBlock(), 20);
        }

    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        int i = pState.getValue(LEVEL);
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        boolean isIngredient = CHUM_INGREDIENT.entrySet().stream().anyMatch(entry -> itemstack.is(entry.getKey()));
        if (i < 8 && isIngredient) {
            if (i < 7 && !pLevel.isClientSide) {
                BlockState blockstate = insertItem((Entity) pPlayer, pState, (ServerLevel) pLevel, itemstack, pPos);
                ChumBarrelBlock.handleFill(pLevel, pPos, blockstate != pState);
                pPlayer.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else if (i == 8) {
            extractProduce(pPlayer, pState, pLevel, pPos);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(LEVEL) == 7) {
            pLevel.setBlock(pPos, pState.cycle(LEVEL), 3);
            pLevel.playSound((Player) null, pPos, SoundEvents.COMPOSTER_READY, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

    }

    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return pBlockState.getValue(LEVEL);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LEVEL);
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    public WorldlyContainer getContainer(BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
        int i = pState.getValue(LEVEL);
        if (i == 8) {
            return new ChumBarrelBlock.OutputContainer(pState, pLevel, pPos, new ItemStack(ModItems.DEEP_SEA_FISH.get()));
        } else {
            return (WorldlyContainer) (i < 7 ? new ChumBarrelBlock.InputContainer(pState, pLevel, pPos) : new ChumBarrelBlock.EmptyContainer());
        }
    }

    static class EmptyContainer extends SimpleContainer implements WorldlyContainer {
        public EmptyContainer() {
            super(0);
        }

        public int[] getSlotsForFace(Direction pSide) {
            return new int[0];
        }

        public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
            return false;
        }

        public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
            return false;
        }
    }

    static class InputContainer extends SimpleContainer implements WorldlyContainer {
        private final BlockState state;
        private final LevelAccessor level;
        private final BlockPos pos;
        private boolean changed;

        public InputContainer(BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
            super(1);
            this.state = pState;
            this.level = pLevel;
            this.pos = pPos;
        }

        public int getMaxStackSize() {
            return 1;
        }

        public int[] getSlotsForFace(Direction pSide) {
            return pSide == Direction.UP ? new int[]{0} : new int[0];
        }

        public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
            return !this.changed && pDirection == Direction.UP && (
                            pItemStack.is(ModTags.Items.CHUM_INGREDIENTS_ONE_LAYER) ||
                            pItemStack.is(ModTags.Items.CHUM_INGREDIENTS_TWO_LAYERS) ||
                            pItemStack.is(ModTags.Items.CHUM_INGREDIENTS_HALF_LAYERS) ||
                            pItemStack.is(ModTags.Items.CHUM_INGREDIENTS_FULL_LAYERS)
            );
        }

        public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
            return false;
        }

        public void setChanged() {
            ItemStack itemstack = this.getItem(0);
            if (!itemstack.isEmpty()) {
                this.changed = true;
                BlockState blockstate = ChumBarrelBlock.addItem((Entity) null, this.state, this.level, this.pos, itemstack);
                this.removeItemNoUpdate(0);
            }

        }
    }

    static class OutputContainer extends SimpleContainer implements WorldlyContainer {
        private final BlockState state;
        private final LevelAccessor level;
        private final BlockPos pos;
        private boolean changed;

        public OutputContainer(BlockState pState, LevelAccessor pLevel, BlockPos pPos, ItemStack pStack) {
            super(pStack);
            this.state = pState;
            this.level = pLevel;
            this.pos = pPos;
        }

        public int getMaxStackSize() {
            return 1;
        }

        public int[] getSlotsForFace(Direction pSide) {
            return pSide == Direction.DOWN ? new int[]{0} : new int[0];
        }

        public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
            return false;
        }

        public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
            return !this.changed && pDirection == Direction.DOWN && pStack.is(ModItems.DEEP_SEA_FISH.get());
        }

        public void setChanged() {
            ChumBarrelBlock.empty((Entity) null, this.state, this.level, this.pos);
            this.changed = true;
        }
    }
}