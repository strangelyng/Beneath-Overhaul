package net.strangelyng.beneathoverhaul.datagen.providers;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulBlocks;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulRock;
import net.strangelyng.beneathoverhaul.common.items.BeneathOverhaulItems;
import net.strangelyng.beneathoverhaul.util.TextureUtils;

import java.util.stream.Stream;

/*
 * Special thanks to Gourmandd, much of the datagen code is based on their work for On-Ancient-Ground-Core
 * https://github.com/Gourmandd/On-Ancient-Ground-Core/blob/main/src/main/java/net/gourmand/core/datagen/providers/BuiltinItemModels.java
 */

public class BuiltInItemModelProvider extends ItemModelProvider {

    protected BuiltInItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BeneathOverhaul.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Misc Blocks
        simpleItem(BeneathOverhaulBlocks.MUSHROOM_ROOTS.asItem(), ResourceLocation.parse(BeneathOverhaul.MOD_ID + ":item/mushroom_roots"));
        simpleItem(BeneathOverhaulBlocks.MUSHROOM_SPROUTS.asItem(), ResourceLocation.parse(BeneathOverhaul.MOD_ID + ":item/mushroom_sprouts"));

        simpleBlock(BeneathOverhaulBlocks.CHARRED_LOG.holder());

        // Ore Blocks
        Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
            Stream.of(Ore.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock()) {
                    simpleBlock(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_ORES.get(rock).get(ore).holder());
                }

                Stream.of(Ore.Grade.values()).forEach(grade -> {
                    if (ore.isGraded()) {
                        simpleBlock(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES.get(rock).get(ore).get(grade).holder());
                    }
                });
            });
        });

        // Rock Blocks
        Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
            Stream.of(Rock.BlockType.values()).forEach(type -> {
                if (type.hasVariants()) {
                    simpleBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).holder());
                    simpleBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).stair().holder());
                    simpleBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).slab().holder());
                    if (isMossyVariant(type, rock)) {
                        mossyWallInventory(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).wall().holder(),
                                getItemModelString(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).getId()), TextureUtils.getRockTexture(rock, type));
                    } else {
                        wallInventory(getItemModelString(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).wall().getId()), TextureUtils.getRockTexture(rock, type));
                    }
                }
            });

            simpleBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.HARDENED).holder());
            simpleBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL).holder());
            simpleBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.AQUEDUCT).holder(),
                    ResourceLocation.parse(getBlockModelString(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.AQUEDUCT).getId()) + "/base"));
            simpleItem(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).get().asItem(),
                    getItemModelLocation(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).getId()));
            mossyLooseItem(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_LOOSE).holder(), rock);
            simpleBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.SPIKE).holder(),
                    ResourceLocation.parse(getBlockModelString(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.SPIKE).getId()) + "_base"));

            simpleBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.CHISELED).holder());
            pressurePlate(getItemModelString(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.PRESSURE_PLATE).getId()),
                    TextureUtils.getRockTexture(rock, Rock.BlockType.PRESSURE_PLATE));
            buttonInventory(getItemModelString(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BUTTON).getId()),
                    TextureUtils.getRockTexture(rock, Rock.BlockType.BUTTON));

            simpleItem(BeneathOverhaulItems.BRICKS.get(rock).get(), getItemModelLocation(BeneathOverhaulItems.BRICKS.get(rock).getId()));
        });
    }

    // Helper Functions
    private void simpleBlock(DeferredHolder<Block, ? extends Block> block) {
        withExistingParent(getItemModelString(block.getId()), getBlockModelLocation(block.getId()));
    }

    private void simpleBlock(DeferredHolder<Block, ? extends Block> block, ResourceLocation location) {
        withExistingParent(getItemModelString(block.getId()), location);
    }

    private void simpleItem(Item item, ResourceLocation texture) {
        this.getBuilder(getItemModelString(item)).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", texture);
    }

    private void itemModel(Item item, ResourceLocation texture, String parent) {
        itemModel(item, texture, "layer0", parent);
    }

    private void itemModel(Item item, ResourceLocation texture, String texturePath, String parent) {
        this.getBuilder(getItemModelString(item)).parent(new ModelFile.UncheckedModelFile(parent)).texture(texturePath, texture);
    }

    private ResourceLocation itemTexture(DeferredHolder<Item, Item> item) {
        return ResourceLocation.fromNamespaceAndPath(item.getId().getNamespace(), "item/" + item.getId().getPath());
    }

    private void mossyLooseItem(DeferredHolder<Block, ? extends Block> block, BeneathOverhaulRock rock) {
        String textureBase = getItemModelString(block.getId()).replace("mossy_", "");
        String textureMossy = (TerraFirmaCraft.MOD_ID + ":item/loose_rock/moss");
        this.getBuilder(getItemModelString(block.getId())).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", textureBase).texture("layer1", textureMossy);
    }

    private void mossyWallInventory(DeferredHolder<Block, ? extends WallBlock> block, String baseName, ResourceLocation texture) {
        ResourceLocation mossyBrickOverlay = ResourceLocation.parse(BeneathOverhaul.MOD_ID + ":block/rock/mossy_bricks/overlay");
        ResourceLocation mossyCobbleOverlay = ResourceLocation.parse(BeneathOverhaul.MOD_ID + ":block/rock/mossy_cobble/overlay");

        ResourceLocation mossyOverlay = (block.getId().getPath().contains("bricks/") ? mossyBrickOverlay : mossyCobbleOverlay);

        this.getBuilder(getItemModelString(block.getId())).parent(new ModelFile.UncheckedModelFile("beneathoverhaul:block/overlay_template_wall_inventory")).texture("wall", texture).texture("overlay", mossyOverlay);
    }

    private String getItemModelString(ResourceLocation block) {
        return block.getNamespace() + ":item/" + block.getPath();
    }

    private ResourceLocation getItemModelLocation(ResourceLocation block) {
        return ResourceLocation.fromNamespaceAndPath(block.getNamespace(), "item/" + block.getPath());
    }

    private String getItemModelString(Item item) {
        return item.toString().replace(":", ":item/");
    }

    private ResourceLocation getBlockModelLocation(ResourceLocation block) {
        return ResourceLocation.fromNamespaceAndPath(block.getNamespace(), "block/" + block.getPath());
    }

    private ResourceLocation getBlockModelLocation(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, "block/" + path);
    }

    private String getBlockModelString(ResourceLocation block) {
        return block.getNamespace() + ":block/" + block.getPath();
    }

    private boolean isMossyVariant(Rock.BlockType type, BeneathOverhaulRock rock) {
        return type == Rock.BlockType.MOSSY_BRICKS || type == Rock.BlockType.MOSSY_COBBLE || type == Rock.BlockType.MOSSY_LOOSE;
    }
}
