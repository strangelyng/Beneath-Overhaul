package net.strangelyng.beneathoverhaul.datagen.providers;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.registry.RegistryRock;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulBlocks;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulRock;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class BuiltInItemTagProvider extends TagsProvider<Item> {
    private final ExistingFileHelper.IResourceType resourceType;

    protected BuiltInItemTagProvider(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(event.getGenerator().getPackOutput(), Registries.ITEM, lookupProvider, BeneathOverhaul.MOD_ID, event.getExistingFileHelper());
        this.resourceType = new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json", Registries.tagsDirPath(registryKey));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Ore Block Items
        Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
            Stream.of(Ore.values()).forEach(ore -> {
                if (ore.hasBlock()) {
                    if (ore.isGraded()) {
                        addGradedOreTags(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES, ore, rock);
                    } else {
                        addOreTags(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_ORES, ore, rock);
                    }
                }
            });
        });

        // Rock Block Items
        Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
            this.tag(Tags.Items.COBBLESTONES_NORMAL).add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.COBBLE).get()));
            this.tag(Tags.Items.COBBLESTONES_MOSSY).add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).get()));

            this.tag(TFCTags.Items.STONES_HARDENED).add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.HARDENED).get()));

            this.tag(Tags.Items.STONES).add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.RAW).get()));

            this.tag(Tags.Items.COBBLESTONES)
                    .add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.COBBLE).get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).get()));

            this.tag(ItemTags.STAIRS)
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.COBBLE).stair().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).stair().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).stair().get()));

            this.tag(ItemTags.SLABS)
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.COBBLE).slab().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).slab().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).slab().get()));

            this.tag(ItemTags.WALLS)
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.COBBLE).wall().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).wall().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).wall().get()));

            this.tag(ItemTags.STONE_BRICKS).add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).get()));
            this.tag(TFCTags.Items.AQUEDUCTS).add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.AQUEDUCT).get()));
            this.tag(Tags.Items.GRAVELS).add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL).get()));

            this.tag(TFCTags.Items.STONES_LOOSE)
                    .add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_LOOSE).get()));

            this.tag(ItemTags.STONE_BRICKS)
                    .add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BRICKS).get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.CHISELED).get()));

            this.tag(TFCTags.Items.STONES_SMOOTH).add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.SMOOTH).get()));

            this.tag(ItemTags.STAIRS)
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.RAW).stair().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.BRICKS).stair().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).stair().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.SMOOTH).stair().get()));

            this.tag(ItemTags.SLABS)
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.RAW).slab().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.BRICKS).slab().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).slab().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.SMOOTH).slab().get()));

            this.tag(ItemTags.WALLS)
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.RAW).wall().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.BRICKS).wall().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).wall().get()))
                    .add(getKey(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.SMOOTH).wall().get()));

            this.tag(TFCTags.Items.STONES_PRESSURE_PLATES).add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.PRESSURE_PLATE).get()));

            this.tag(ItemTags.STONE_BUTTONS).add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BUTTON).get()));
            this.tag(ItemTags.BUTTONS).add(getKey(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BUTTON).get()));
        });
    }

    // Helper Functions

    protected ResourceKey<Item> getKey(DeferredHolder<Block, ? extends Block> block ){
        return block.get().asItem().builtInRegistryHolder().key();
    }

    protected ResourceKey<Item> getKey(Block block){
        return block.asItem().builtInRegistryHolder().key();
    }

    protected ResourceKey<Item> getKey(Item item){
        return item.builtInRegistryHolder().key();
    }

    private <T1 extends RegistryRock, T2> void addOreTags(Map<T1, Map<T2, BeneathOverhaulBlocks.Id<Block>>> map, T2 ore, T1 rock) {
        DeferredHolder<Block, Block> block = map.get(rock).get(ore).holder();
        this.tag(Tags.Items.ORES).add(getKey(block));
    }

    private <T1 extends RegistryRock, T2, T3 extends Ore.Grade> void addGradedOreTags(Map<T1, Map<T2, Map<T3, BeneathOverhaulBlocks.Id<Block>>>> map, T2 ore, T1 rock) {
        for (Ore.Grade grade : Ore.Grade.values()) {
            DeferredHolder<Block, Block> block = map.get(rock).get(ore).get(grade).holder();
            this.tag(Tags.Items.ORES).add(getKey(block));
        }
    }
}
