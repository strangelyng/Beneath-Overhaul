package net.strangelyng.beneathoverhaul.datagen.providers;

import net.dries007.tfc.common.blocks.rock.Rock;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulBlocks;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulRock;
import net.strangelyng.beneathoverhaul.datagen.builders.LootTableBuilders;

import java.util.Set;
import java.util.stream.Stream;

public class BuiltInBlockLootTableProvider extends BlockLootSubProvider {

    protected BuiltInBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BeneathOverhaulBlocks.BLOCKS.getEntries()
                .stream().map(e -> (Block) e.value()).toList();
    }

    private void addRockBlockLootTable(BeneathOverhaulRock rock, Rock.BlockType type) {
        switch (type) {
            case LOOSE, MOSSY_LOOSE -> this.add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).get(), LootTableBuilders.createLooseRockDropTable(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).get()));
            case SPIKE -> this.add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).get(), LootTableBuilders.createRockDropTable(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).get(), 1, 2));
            case RAW, HARDENED ->
                    this.add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).get(), LootTableBuilders.createRawRockDropTable(
                        BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).get(),
                        BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).get()
            ));
            default -> this.dropSelf(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).get());
        }
    }

    @Override
    protected void generate() {
        generateRocks();
    }

    private void generateRocks(){
        Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
            Stream.of(Rock.BlockType.values()).forEach(type -> {
                addRockBlockLootTable(rock, type);
                if ((type.hasVariants() || type == Rock.BlockType.MOSSY_COBBLE || type == Rock.BlockType.MOSSY_BRICKS)) {
                    this.dropSelf(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).slab().get());
                    this.dropSelf(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).stair().get());
                    this.dropSelf(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).wall().get());
                }
            });
        });
    }
}
