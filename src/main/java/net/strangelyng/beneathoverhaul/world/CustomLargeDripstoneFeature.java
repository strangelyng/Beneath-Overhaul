package net.strangelyng.beneathoverhaul.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;
import net.strangelyng.beneathoverhaul.mixin.common.minecraft.DripstoneUtilsAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CustomLargeDripstoneFeature extends Feature<CustomLargeDripstoneConfig> {
    public CustomLargeDripstoneFeature(Codec<CustomLargeDripstoneConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CustomLargeDripstoneConfig> ctx) {
        WorldGenLevel worldgenLevel = ctx.level();
        BlockPos blockPos = ctx.origin();
        CustomLargeDripstoneConfig config = ctx.config();
        RandomSource randomSource = ctx.random();
        if (!DripstoneUtilsAccessor.invokeIsEmptyOrWater(worldgenLevel, blockPos)) {
            return false;
        } else {
            Optional<Column> optional = Column.scan(
                    worldgenLevel, blockPos, config.floorToCeilingSearchRange(), DripstoneUtils::isEmptyOrWater, DripstoneUtils::isDripstoneBaseOrLava
            );
            if (!optional.isEmpty() && optional.get() instanceof Column.Range columnRange) {
                if (columnRange.height() < 4) {
                    return false;
                } else {
                    int i = (int) ((float) columnRange.height() * config.maxColumnRadiusToCaveHeightRatio());
                    int j = Mth.clamp(i, config.columnRadius().getMinValue(), config.columnRadius().getMaxValue());
                    int k = Mth.randomBetweenInclusive(randomSource, config.columnRadius().getMinValue(), j);
                    CustomLargeDripstone largeDripstone1 = makeDripstone(
                            blockPos.atY(columnRange.ceiling() - 1),
                            false,
                            randomSource,
                            k,
                            config.stalactiteBluntness(),
                            config.heightScale()
                    );
                    CustomLargeDripstone largeDripstone2 = makeDripstone(
                            blockPos.atY(columnRange.floor() + 1),
                            true,
                            randomSource,
                            k,
                            config.stalactiteBluntness(),
                            config.heightScale()
                    );
                    WindOffsetter windOffsetter;
                    if (largeDripstone1.isSuitableForWind(config) && largeDripstone2.isSuitableForWind(config)) {
                        windOffsetter = new WindOffsetter(blockPos.getY(), randomSource, config.windSpeed());
                    } else {
                        windOffsetter = WindOffsetter.noWind();
                    }

                    boolean flag1 = largeDripstone1.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(
                            worldgenLevel, windOffsetter
                    );
                    boolean flag2 = largeDripstone2.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(
                            worldgenLevel, windOffsetter
                    );

                    if (flag1) {
                        largeDripstone1.placeBlocks(worldgenLevel, randomSource, windOffsetter, config);
                    }

                    if (flag2) {
                        largeDripstone2.placeBlocks(worldgenLevel, randomSource, windOffsetter, config);
                    }

                    return true;
                }
            } else {
                return false;
            }
        }
    }

    private static CustomLargeDripstone makeDripstone(BlockPos root, boolean pointingUp, RandomSource random, int radius, FloatProvider bluntnessBase, FloatProvider scaleBase) {
        return new CustomLargeDripstone(root, pointingUp, radius, bluntnessBase.sample(random), scaleBase.sample(random));
    }

    static final class CustomLargeDripstone {
        private BlockPos root;
        private final boolean pointingUp;
        private int radius;
        private final double bluntness;
        private final double scale;

        CustomLargeDripstone(BlockPos root, boolean pointingUp, int radius, double bluntness, double scale) {
            this.root = root;
            this.pointingUp = pointingUp;
            this.radius = radius;
            this.bluntness = bluntness;
            this.scale = scale;
        }

        private int getHeight() { return this.getHeightAtRadius(0.0F);}

        boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel level, WindOffsetter windOffsetter) {
            while (this.radius > 1) {
                BlockPos.MutableBlockPos mutablePos = this.root.mutable();
                int attempts = Math.min(10, this.getHeight());

                for (int i = 0; i < attempts; i++) {
                    if (level.getBlockState(mutablePos).is(Blocks.LAVA)) {
                        return false;
                    }

                    if (DripstoneUtilsAccessor.invokeIsCircleMostlyEmbeddedInStone(level, windOffsetter.offset(mutablePos), this.radius)) {
                        this.root = mutablePos;
                        return true;
                    }

                    mutablePos.move(this.pointingUp ? Direction.DOWN : Direction.UP);
                }

                this.radius /= 2;
            }

            return false;
        }

        private int getHeightAtRadius(float radius) {
            return (int) DripstoneUtilsAccessor.invokeGetDripstoneHeight(radius, this.radius, this.scale, this.bluntness);
        }

        void placeBlocks(WorldGenLevel level, RandomSource random, WindOffsetter windOffsetter, CustomLargeDripstoneConfig config) {
            for (int i = -this.radius; i <= this.radius; i++) {
                for (int j = -this.radius; j <= this.radius; j++) {
                    float f = Mth.sqrt((float)(i * i + j * j));
                    if (!(f > (float)this.radius)) {
                        int k = this.getHeightAtRadius(f);
                        if (k > 0) {
                            if ((double)random.nextFloat() < 0.2) {
                                k = (int)((float)k * Mth.randomBetween(random, 0.8F, 1.0F));
                            }

                            BlockPos.MutableBlockPos mutablePos = this.root.offset(i, 0, j).mutable();
                            boolean flag = false;
                            int l = this.pointingUp
                                    ? level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, mutablePos.getX(), mutablePos.getZ())
                                    : Integer.MAX_VALUE;

                            for (int i1 = 0; i1 < k && mutablePos.getY() < l; i1++) {
                                BlockPos blockPos = windOffsetter.offset(mutablePos);
                                if (DripstoneUtilsAccessor.invokeIsEmptyOrWaterOrLava(level, blockPos)) {
                                    flag = true;
                                    Block block = config.block().getBlock(); // Allow Custom Block
                                    level.setBlock(blockPos, block.defaultBlockState(), 2);
                                } else if (flag && level.getBlockState(blockPos).is(BlockTags.BASE_STONE_OVERWORLD)) {
                                    break;
                                }

                                mutablePos.move(this.pointingUp ? Direction.UP : Direction.DOWN);
                            }
                        }
                    }
                }
            }
        }

        boolean isSuitableForWind(CustomLargeDripstoneConfig config) {
            return this.radius >= config.minRadiusForWind() && this.bluntness >= (double) config.minBluntnessForWind();
        }
    }

    static final class WindOffsetter {
        private final int originY;
        @Nullable
        private final Vec3 windSpeed;

        WindOffsetter(int originY, RandomSource random, FloatProvider magnitude) {
            this.originY = originY;
            float f = magnitude.sample(random);
            float f1 = Mth.randomBetween(random, 0.0F, (float) Math.PI);
            this.windSpeed = new Vec3((Mth.cos(f1) * f), 0.0, (Mth.sin(f1) * f));
        }

        private WindOffsetter() {
            this.originY = 0;
            this.windSpeed = null;
        }

        static WindOffsetter noWind() {
            return new WindOffsetter();
        }

        BlockPos offset(BlockPos pos) {
            if (this.windSpeed == null) {
                return pos;
            } else {
                int i = this.originY - pos.getY();
                Vec3 vec3 = this.windSpeed.scale(i);
                return pos.offset(Mth.floor(vec3.x), 0, Mth.floor(vec3.z));
            }
        }
    }

}
