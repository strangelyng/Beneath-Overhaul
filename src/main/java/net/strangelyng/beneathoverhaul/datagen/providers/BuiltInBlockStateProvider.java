package net.strangelyng.beneathoverhaul.datagen.providers;

import net.dries007.tfc.common.blocks.rock.*;
import net.dries007.tfc.util.registry.RegistryRock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulBlocks;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulRock;
import net.strangelyng.beneathoverhaul.common.blocks.SandLayerBlock;
import net.strangelyng.beneathoverhaul.util.TextureUtils;
import net.neoforged.neoforge.client.model.generators.*;

import java.util.stream.Stream;

public class BuiltInBlockStateProvider extends BlockStateProvider {

    private final static ResourceLocation oreParent = ResourceLocation.fromNamespaceAndPath("tfc", "block/ore");
    private final static ResourceLocation aqueductBaseParent = ResourceLocation.fromNamespaceAndPath("tfc", "block/aqueduct/base");
    private final static ResourceLocation aqueductNorthParent = ResourceLocation.fromNamespaceAndPath("tfc", "block/aqueduct/north");
    private final static ResourceLocation aqueductSouthParent = ResourceLocation.fromNamespaceAndPath("tfc", "block/aqueduct/south");
    private final static ResourceLocation aqueductEastParent = ResourceLocation.fromNamespaceAndPath("tfc", "block/aqueduct/east");
    private final static ResourceLocation aqueductWestParent = ResourceLocation.fromNamespaceAndPath("tfc", "block/aqueduct/west");
    private final static ResourceLocation mossOverlay = ResourceLocation.parse(BeneathOverhaul.MOD_ID + ":block/rock/mossy_bricks/overlay");

    protected BuiltInBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BeneathOverhaul.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Misc Blocks
        layerBlock(BeneathOverhaulBlocks.ASH_LAYER_BLOCK.holder(), ResourceLocation.parse(BeneathOverhaul.MOD_ID + ":block/ash_block"));

        // Ore Blocks
        Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
            Stream.of(Ore.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock()) {
                    simpleOre(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_ORES.get(rock).get(ore).holder(), rock, ore);
                }

                Stream.of(Ore.Grade.values()).forEach(grade -> {
                    if (ore.isGraded()) {
                        simpleOre(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES.get(rock).get(ore).get(grade).holder(), rock, ore, grade);
                    }
                });
            });
        });

        // Rock Blocks
        Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
            Stream.of(Rock.BlockType.values()).forEach(type -> {
                if (type.hasVariants()) {
                    if (isMossyVariant(type, rock)) {
                        ResourceLocation texture = TextureUtils.getRockTexture(rock, type);
                        cubeMossOverlayAll(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).holder(), texture);
                        stairsMossOverlayBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).stair().holder(), texture);
                        slabMossOverlayBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).slab().holder(), texture,
                                getBlockModelLocation(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).getId()));
                        wallMossOverlayBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).wall().holder(),
                                getBlockModelString(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).getId()), texture);
                    } else {
                        ResourceLocation texture = TextureUtils.getRockTexture(rock, type);
                        cubeAll(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).holder(), texture);
                        stairsBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).stair().holder(), texture);
                        slabBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).slab().holder(), texture,
                                getBlockModelLocation(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).getId()));
                        wallBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).wall().holder(),
                                getBlockModelString(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).getId()), texture);
                    }
                }
            });

            cubeAll(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL).holder(), TextureUtils.getRockTexture(rock, Rock.BlockType.GRAVEL));
            cubeAll(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.HARDENED).holder(), TextureUtils.getRockTexture(rock, Rock.BlockType.HARDENED));
            aqueductBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.AQUEDUCT).holder(), TextureUtils.getRockTexture(rock, Rock.BlockType.AQUEDUCT));
            looseRockBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).holder(), TextureUtils.getRockTexture(rock, Rock.BlockType.LOOSE), rock);
            looseRockBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_LOOSE).holder(), TextureUtils.getRockTexture(rock, Rock.BlockType.MOSSY_LOOSE), rock);
            rockSpikeBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.SPIKE).holder(), TextureUtils.getRockTexture(rock, Rock.BlockType.SPIKE));

            cubeAll(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.CHISELED).holder(), TextureUtils.getRockTexture(rock, Rock.BlockType.CHISELED));
            pressurePlateBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.PRESSURE_PLATE).holder(), TextureUtils.getRockTexture(rock, Rock.BlockType.PRESSURE_PLATE));
            buttonBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BUTTON).holder(), TextureUtils.getRockTexture(rock, Rock.BlockType.BUTTON));
        });
    }

    private void simpleOre(DeferredHolder<Block, Block> block, RegistryRock rock, Ore ore) {
        String allTexture = TextureUtils.getRawRockTexture(rock);
        String oreTexture = TextureUtils.getOreTexture(ore);
        simpleBlock(block.get(), ConfiguredModel.builder().modelFile(createOverlayModel(block.getId().getPath(), allTexture, oreTexture)).buildLast());
    }

    private void simpleOre(DeferredHolder<Block, Block> block, RegistryRock rock, Ore ore, Ore.Grade grade) {
        String allTexture = TextureUtils.getRawRockTexture(rock);
        String oreTexture = TextureUtils.getOreTexture(ore, grade);
        simpleBlock(block.get(), ConfiguredModel.builder().modelFile(createOverlayModel(block.getId().getPath(), allTexture, oreTexture)).buildLast());
    }

    private BlockModelBuilder createOverlayModel(String name, String allTexture, String overlayTexture) {
        return models().withExistingParent("block/" + name, oreParent).texture("all", allTexture).texture("overlay", overlayTexture);
    }

    private void cubeAll(DeferredHolder<Block, Block> block, ResourceLocation texture) {
        simpleBlock(block.get(), this.models().cubeAll(block.getId().getNamespace() + ":block/" + block.getId().getPath(), texture));
    }

    private void cubeMossOverlayAll(DeferredHolder<Block, Block> block, ResourceLocation texture) {
        simpleBlock(block.get(), ConfiguredModel.builder().modelFile(createOverlayModel(block.getId().getPath(), texture.toString(), mossOverlay.toString())).buildLast());
    }

    private void stairsBlock(DeferredHolder<Block, ? extends Block> block, ResourceLocation texture) {
        ModelFile stairs = this.models().stairs(getBlockModelString(block.getId()), texture, texture, texture);
        ModelFile stairsInner = this.models().stairsInner(getBlockModelString(block.getId()) + "_inner", texture, texture, texture);
        ModelFile stairsOuter = this.models().stairsOuter(getBlockModelString(block.getId()) + "_outer", texture, texture, texture);

        getVariantBuilder(block.get())
                .forAllStatesExcept(state -> {
                    Direction facing = state.getValue(StairBlock.FACING);
                    Half half = state.getValue(StairBlock.HALF);
                    StairsShape shape = state.getValue(StairBlock.SHAPE);
                    int yRot = (int) facing.getClockWise().toYRot(); // Stairs model is rotated 90 degrees clockwise for some reason
                    if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) {
                        yRot += 270; // Left facing stairs are rotated 90 degrees clockwise
                    }
                    if (shape != StairsShape.STRAIGHT && half == Half.TOP) {
                        yRot += 90; // Top stairs are rotated 90 degrees clockwise
                    }
                    yRot %= 360;
                    boolean uvlock = yRot != 0 || half == Half.TOP; // Don't set uvlock for states that have no rotation
                    return ConfiguredModel.builder()
                            .modelFile(shape == StairsShape.STRAIGHT ? stairs : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? stairsInner : stairsOuter)
                            .rotationX(half == Half.BOTTOM ? 0 : 180)
                            .rotationY(yRot)
                            .uvLock(uvlock)
                            .build();
                }, StairBlock.WATERLOGGED);
    }

    private void stairsMossOverlayBlock(DeferredHolder<Block, ? extends Block> block, ResourceLocation texture) {
        ModelFile stairs = createModel(getBlockModelString(block.getId()), "beneathoverhaul:block/overlay_stairs")
                .texture("bottom", texture)
                .texture("side", texture)
                .texture("top", texture)
                .texture("particle", texture)
                .texture("overlay", mossOverlay);
        ModelFile stairsInner = createModel(getBlockModelString(block.getId()) + "_inner", "beneathoverhaul:block/overlay_inner_stairs")
                .texture("bottom", texture)
                .texture("side", texture)
                .texture("top", texture)
                .texture("article", texture)
                .texture("overlay", mossOverlay);
        ModelFile stairsOuter = createModel(getBlockModelString(block.getId()) + "_outer", "beneathoverhaul:block/overlay_outer_stairs")
                .texture("bottom", texture)
                .texture("side", texture)
                .texture("top", texture)
                .texture("particle", texture)
                .texture("overlay", mossOverlay);

        getVariantBuilder(block.get())
                .forAllStatesExcept(state -> {
                    Direction facing = state.getValue(StairBlock.FACING);
                    Half half = state.getValue(StairBlock.HALF);
                    StairsShape shape = state.getValue(StairBlock.SHAPE);
                    int yRot = (int) facing.getClockWise().toYRot(); // Stairs model is rotated 90 degrees clockwise for some reason
                    if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) {
                        yRot += 270; // Left facing stairs are rotated 90 degrees clockwise
                    }
                    if (shape != StairsShape.STRAIGHT && half == Half.TOP) {
                        yRot += 90; // Top stairs are rotated 90 degrees clockwise
                    }
                    yRot %= 360;
                    boolean uvlock = yRot != 0 || half == Half.TOP; // Don't set uvlock for states that have no rotation
                    return ConfiguredModel.builder()
                            .modelFile(shape == StairsShape.STRAIGHT ? stairs : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? stairsInner : stairsOuter)
                            .rotationX(half == Half.BOTTOM ? 0 : 180)
                            .rotationY(yRot)
                            .uvLock(uvlock)
                            .build();
                }, StairBlock.WATERLOGGED);
    }

    private void slabBlock(DeferredHolder<Block, ? extends Block> block, ResourceLocation texture, ResourceLocation doubleSlab) {
        ModelFile slabBottom = this.models().slab(getBlockModelString(block.getId()), texture, texture, texture);
        ModelFile slabTop = this.models().slabTop(getBlockModelString(block.getId()) + "_top", texture, texture, texture);
        ModelFile slabDouble = this.models().getExistingFile(doubleSlab);

        this.getVariantBuilder(block.get())
                .partialState().with(SlabBlock.TYPE, SlabType.BOTTOM).addModels(new ConfiguredModel(slabBottom))
                .partialState().with(SlabBlock.TYPE, SlabType.TOP).addModels(new ConfiguredModel(slabTop))
                .partialState().with(SlabBlock.TYPE, SlabType.DOUBLE).addModels(new ConfiguredModel(slabDouble));
    }

    private void slabMossOverlayBlock(DeferredHolder<Block, ? extends Block> block, ResourceLocation texture, ResourceLocation doubleSlab) {
        ModelFile slabBottom = createModel(getBlockModelString(block.getId()), "beneathoverhaul:block/overlay_slab")
                .texture("bottom", texture)
                .texture("side", texture)
                .texture("top", texture)
                .texture("particle", texture)
                .texture("overlay", mossOverlay);
        ModelFile slabTop = createModel(getBlockModelString(block.getId()) + "_top", "beneathoverhaul:block/overlay_slab_top")
                .texture("bottom", texture)
                .texture("side", texture)
                .texture("top", texture)
                .texture("particle", texture)
                .texture("overlay", mossOverlay);

        ModelFile slabDouble = this.models().getExistingFile(doubleSlab);

        this.getVariantBuilder(block.get())
                .partialState().with(SlabBlock.TYPE, SlabType.BOTTOM).addModels(new ConfiguredModel(slabBottom))
                .partialState().with(SlabBlock.TYPE, SlabType.TOP).addModels(new ConfiguredModel(slabTop))
                .partialState().with(SlabBlock.TYPE, SlabType.DOUBLE).addModels(new ConfiguredModel(slabDouble));
    }

    private void aqueductBlock(DeferredHolder<Block, Block> block, ResourceLocation texture) {
        ModelFile modelBase = createModel(getBlockModelString(block.getId()) + "/base", aqueductBaseParent).texture("texture", texture).texture("particle", texture);
        ModelFile modelNorth = createModel(getBlockModelString(block.getId()) + "/north", aqueductNorthParent).texture("texture", texture).texture("particle", texture);
        ModelFile modelSouth = createModel(getBlockModelString(block.getId()) + "/south", aqueductSouthParent).texture("texture", texture).texture("particle", texture);
        ModelFile modelEast = createModel(getBlockModelString(block.getId()) + "/east", aqueductEastParent).texture("texture", texture).texture("particle", texture);
        ModelFile modelWest = createModel(getBlockModelString(block.getId()) + "/west", aqueductWestParent).texture("texture", texture).texture("particle", texture);

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block.get());

        builder.part().modelFile(modelBase).addModel();
        builder.part().modelFile(modelNorth).addModel().condition(AqueductBlock.NORTH, false);
        builder.part().modelFile(modelSouth).addModel().condition(AqueductBlock.SOUTH, false);
        builder.part().modelFile(modelEast).addModel().condition(AqueductBlock.EAST, false);
        builder.part().modelFile(modelWest).addModel().condition(AqueductBlock.WEST, false);
    }

    private void rockSpikeBlock(DeferredHolder<Block, Block> block, ResourceLocation texture) {
        ModelFile modelBase = createModel(getBlockModelString(block.getId()) + "_base", "tfc:block/rock/spike_base").texture("texture", texture).texture("particle", texture);
        ModelFile modelMiddle = createModel(getBlockModelString(block.getId()) + "_middle", "tfc:block/rock/spike_middle").texture("texture", texture).texture("particle", texture);
        ModelFile modelTip = createModel(getBlockModelString(block.getId()) + "_tip", "tfc:block/rock/spike_tip").texture("texture", texture).texture("particle", texture);

        VariantBlockStateBuilder builder = getVariantBuilder(block.get());

        builder
                .partialState().with(RockSpikeBlock.PART, RockSpikeBlock.Part.BASE).modelForState().modelFile(modelBase).addModel()
                .partialState().with(RockSpikeBlock.PART, RockSpikeBlock.Part.MIDDLE).modelForState().modelFile(modelMiddle).addModel()
                .partialState().with(RockSpikeBlock.PART, RockSpikeBlock.Part.TIP).modelForState().modelFile(modelTip).addModel();
    }

    private void looseRockBlock(DeferredHolder<Block, Block> block, ResourceLocation texture, BeneathOverhaulRock rock) {
        ModelFile model1 = createModel(getBlockModelString(block.getId()) + "_1", getLooseRockModelParent(rock, 1)).texture("texture", texture).texture("particle", texture);
        ModelFile model2 = createModel(getBlockModelString(block.getId()) + "_2", getLooseRockModelParent(rock, 2)).texture("texture", texture).texture("particle", texture);
        ModelFile model3 = createModel(getBlockModelString(block.getId()) + "_3", getLooseRockModelParent(rock, 3)).texture("texture", texture).texture("particle", texture);

        VariantBlockStateBuilder builder = getVariantBuilder(block.get());

        builder
                .partialState().with(LooseRockBlock.COUNT, 1).modelForState()
                    .modelFile(model1).rotationY(0).nextModel()
                    .modelFile(model1).rotationY(90).nextModel()
                    .modelFile(model1).rotationY(180).nextModel()
                    .modelFile(model1).rotationY(270).addModel()
                .partialState().with(LooseRockBlock.COUNT, 2).modelForState()
                    .modelFile(model2).rotationY(0).nextModel()
                    .modelFile(model2).rotationY(90).nextModel()
                    .modelFile(model2).rotationY(180).nextModel()
                    .modelFile(model2).rotationY(270).addModel()
                .partialState().with(LooseRockBlock.COUNT, 3).modelForState()
                    .modelFile(model3).rotationY(0).nextModel()
                    .modelFile(model3).rotationY(90).nextModel()
                    .modelFile(model3).rotationY(180).nextModel()
                    .modelFile(model3).rotationY(270).addModel();
    }

    private void pressurePlateBlock(DeferredHolder<Block, Block> block, ResourceLocation texture) {
        ModelFile pressurePlate = this.models().pressurePlate(getBlockModelString(block.getId()), texture);
        ModelFile pressurePlateDown = this.models().pressurePlateDown(getBlockModelString(block.getId()) + "_down", texture);

        this.getVariantBuilder(block.get()).partialState()
                .with(PressurePlateBlock.POWERED, true).addModels(new ConfiguredModel(pressurePlateDown)).partialState()
                .with(PressurePlateBlock.POWERED, false).addModels(new ConfiguredModel(pressurePlate));
    }

    private void buttonBlock(DeferredHolder<Block, Block> block, ResourceLocation texture) {
        ModelFile button = this.models().button(getBlockModelString(block.getId()), texture);
        ModelFile buttonPressed = this.models().buttonPressed(getBlockModelString(block.getId()) + "_pressed", texture);

        this.getVariantBuilder(block.get()).forAllStates((state) -> {
            Direction facing = state.getValue(ButtonBlock.FACING);
            AttachFace face = state.getValue(ButtonBlock.FACE);
            boolean powered = state.getValue(ButtonBlock.POWERED);
            return ConfiguredModel.builder()
                    .modelFile(powered ? buttonPressed : button)
                    .rotationX(face == AttachFace.FLOOR ? 0 : (face == AttachFace.WALL ? 90 : 180))
                    .rotationY((int) (face == AttachFace.CEILING ? facing : facing.getOpposite()).toYRot())
                    .uvLock(face == AttachFace.WALL).build();
        });
    }

    private void wallBlock(DeferredHolder<Block, ? extends WallBlock> block, String baseName, ResourceLocation texture) {
        this.wallBlock(block.get(), baseName, texture);
    }

    private void wallMossOverlayBlock(DeferredHolder<Block, ? extends WallBlock> block, String baseName, ResourceLocation texture) {
        this.wallBlock(block.get(),
                createModel(baseName + "_post", "beneathoverhaul:block/overlay_template_wall_post")
                        .texture("wall", texture)
                        .texture("particle", texture)
                        .texture("overlay", mossOverlay),
                createModel(baseName + "_side", "beneathoverhaul:block/overlay_template_wall_side")
                        .texture("wall", texture)
                        .texture("particle", texture)
                        .texture("overlay", mossOverlay),
                createModel(baseName + "_side_tall", "beneathoverhaul:block/overlay_template_wall_side_tall")
                        .texture("wall", texture)
                        .texture("particle", texture)
                        .texture("overlay", mossOverlay)
        );
    }

    private String getBlockModelString(ResourceLocation block) {
        return block.getNamespace() + ":block/" + block.getPath();
    }

    private String getLooseRockModelParent(BeneathOverhaulRock rock, int count) {
        RockCategory category = rock.displayCategory().category();

        switch (category) {
            case METAMORPHIC -> {
                return "tfc:block/rock/loose_metamorphic_" + count;
            }
            case SEDIMENTARY -> {
                return "tfc:block/rock/loose_sedimentary_" + count;
            }
            case IGNEOUS_EXTRUSIVE -> {
                return "tfc:block/rock/loose_igneous_extrusive_" + count;
            }
            case IGNEOUS_INTRUSIVE -> {
                return "tfc:block/rock/loose_igneous_intrusive_" + count;
            }
            default -> throw new AssertionError("No category found for rock: " + rock.getSerializedName());
        }
    }

    private String getLayerBlockModelParent(int height) {
        switch (height) {
            case 1 -> {
                return "beneathoverhaul:block/layer_block/template_height2";
            }
            case 2 -> {
                return "beneathoverhaul:block/layer_block/template_height4";
            }
            case 3 -> {
                return "beneathoverhaul:block/layer_block/template_height6";
            }
            case 4 -> {
                return "beneathoverhaul:block/layer_block/template_height8";
            }
            case 5 -> {
                return "beneathoverhaul:block/layer_block/template_height10";
            }
            case 6 -> {
                return "beneathoverhaul:block/layer_block/template_height12";
            }
            case 7 -> {
                return "beneathoverhaul:block/layer_block/template_height14";
            }
            case 8 -> {
                return "minecraft:block/cube_all";
            }
            default -> throw new AssertionError("No height found for layer count: " + height);
        }
    }

    private void layerBlock(DeferredHolder<Block, ? extends SandLayerBlock> block, ResourceLocation texture) {
        ModelFile model1 = createModel(getBlockModelString(block.getId()) + "_height2", getLayerBlockModelParent(1)).texture("texture", texture);
        ModelFile model2 = createModel(getBlockModelString(block.getId()) + "_height4", getLayerBlockModelParent(2)).texture("texture", texture);
        ModelFile model3 = createModel(getBlockModelString(block.getId()) + "_height6", getLayerBlockModelParent(3)).texture("texture", texture);
        ModelFile model4 = createModel(getBlockModelString(block.getId()) + "_height8", getLayerBlockModelParent(4)).texture("texture", texture);
        ModelFile model5 = createModel(getBlockModelString(block.getId()) + "_height10", getLayerBlockModelParent(5)).texture("texture", texture);
        ModelFile model6 = createModel(getBlockModelString(block.getId()) + "_height12", getLayerBlockModelParent(6)).texture("texture", texture);
        ModelFile model7 = createModel(getBlockModelString(block.getId()) + "_height14", getLayerBlockModelParent(7)).texture("texture", texture);
        ModelFile model8 = createModel(getBlockModelString(block.getId()), getLayerBlockModelParent(8)).texture("all", texture);

        VariantBlockStateBuilder builder = getVariantBuilder(block.get());

        builder
                .partialState().with(SandLayerBlock.LAYERS, 1).modelForState()
                .modelFile(model1).addModel()
                .partialState().with(SandLayerBlock.LAYERS, 2).modelForState()
                .modelFile(model2).addModel()
                .partialState().with(SandLayerBlock.LAYERS, 3).modelForState()
                .modelFile(model3).addModel()
                .partialState().with(SandLayerBlock.LAYERS, 4).modelForState()
                .modelFile(model4).addModel()
                .partialState().with(SandLayerBlock.LAYERS, 5).modelForState()
                .modelFile(model5).addModel()
                .partialState().with(SandLayerBlock.LAYERS, 6).modelForState()
                .modelFile(model6).addModel()
                .partialState().with(SandLayerBlock.LAYERS, 7).modelForState()
                .modelFile(model7).addModel()
                .partialState().with(SandLayerBlock.LAYERS, 8).modelForState()
                .modelFile(model8).addModel();
    }

    private ResourceLocation getBlockModelLocation(ResourceLocation block) {
        return ResourceLocation.fromNamespaceAndPath(block.getNamespace(), "block/" + block.getPath());
    }

    private ModelBuilder<BlockModelBuilder> createModel(String name, ResourceLocation parent) {
        return this.models().withExistingParent(name, parent);
    }

    private ModelBuilder<BlockModelBuilder> createModel(String name, String parent) {
        return this.models().withExistingParent(name, parent);
    }

    private boolean isMossyVariant(Rock.BlockType type, BeneathOverhaulRock rock) {
        return type == Rock.BlockType.MOSSY_BRICKS || type == Rock.BlockType.MOSSY_COBBLE || type == Rock.BlockType.MOSSY_LOOSE;
    }
}
