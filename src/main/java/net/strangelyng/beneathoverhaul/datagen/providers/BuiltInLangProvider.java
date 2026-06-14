package net.strangelyng.beneathoverhaul.datagen.providers;

import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.minecraft.data.PackOutput;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulBlocks;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulRock;
import net.strangelyng.beneathoverhaul.common.items.BeneathOverhaulItems;
import net.strangelyng.beneathoverhaul.util.TextUtils;

import java.util.function.Supplier;
import java.util.stream.Stream;

/*
 * Special thanks to Gourmandd, much of the datagen code is based on their work for On-Ancient-Ground-Core
 * https://github.com/Gourmandd/On-Ancient-Ground-Core/blob/main/src/main/java/net/gourmand/core/datagen/providers/CoreLanguageProvider.java
 */

public class BuiltInLangProvider extends LanguageProvider {
    protected BuiltInLangProvider(PackOutput output) {
        super(output, BeneathOverhaul.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Misc Lang
        addBlock(BeneathOverhaulBlocks.CHARRED_LOG, "Charred Log");
        addBlock(BeneathOverhaulBlocks.MUSHROOM_ROOTS, "Mushroom Roots");
        addBlock(BeneathOverhaulBlocks.MUSHROOM_SPROUTS, "Mushroom Sprouts");
        addBlock(BeneathOverhaulBlocks.ASH_LAYER_BLOCK, "Ash Pile");

        // Ore Blocks
        Stream.of(Ore.values()).forEach(ore -> {
            if (ore.hasBlock()) {
                Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
                    if (ore.isGraded()) {
                        Stream.of(Ore.Grade.values()).forEach(grade -> {
                            createOreKey(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES.get(rock).get(ore).get(grade), getName(grade.name()) + " " + getName(rock), getName(ore.name()));
                        });
                    } else {
                        createOreKey(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_ORES.get(rock).get(ore), getName(rock), getName(ore.name()));
                    }
                });
            }
        });

        // Rock Blocks
        Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
            Stream.of(Rock.BlockType.values()).forEach(type -> {
                if (type.hasVariants()) {
                    switch (type) {
                        case HARDENED, RAW, SMOOTH -> {
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).stair(), getName(type) + " " + getName(rock) + " Stairs");
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).slab(), getName(type) + " " + getName(rock) + " Slab");
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).wall(), getName(type) + " " + getName(rock) + " Wall");
                        }
                        case COBBLE -> {
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).stair(), getName(rock) + " Cobblestone Stairs");
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).slab(), getName(rock) + " Cobblestone Slab");
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).wall(), getName(rock) + " Cobblestone Wall");
                        }
                        case MOSSY_COBBLE -> {
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).stair(), getName(rock) + " Mossy Cobblestone Stairs");
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).slab(), getName(rock) + " Mossy Cobblestone Slab");
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).wall(), getName(rock) + " Mossy Cobblestone Wall");
                        }
                        case BRICKS -> {
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).stair(), getName(rock) + " Brick Stairs");
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).slab(), getName(rock) + " Brick Slab");
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).wall(), getName(rock) + " Brick Wall");
                        }
                        case MOSSY_BRICKS -> {
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).stair(), getName(rock) + " Mossy Brick Stairs");
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).slab(), getName(rock) + " Mossy Brick Slab");
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).wall(), getName(rock) + " Mossy Brick Wall");
                        }
                        case CRACKED_BRICKS -> {
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).stair(), getName(rock) + " Cracked Brick Stairs");
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).slab(), getName(rock) + " Cracked Brick Slab");
                            addBlock(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).wall(), getName(rock) + " Cracked Brick Wall");
                        }
                        default -> addBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type), getName(rock) + " " + getName(type));
                    }
                }

                switch (type) {
                    case HARDENED, RAW, SMOOTH -> addBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type), getName(type) + " " + getName(rock));
                    case LOOSE -> addBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type), "Loose " + getName(rock) + " Rock");
                    case MOSSY_LOOSE -> addBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type), "Mossy Loose " + getName(rock) + " Rock");
                    case COBBLE -> addBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type), getName(rock) + " Cobblestone");
                    case MOSSY_COBBLE -> addBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type), getName(rock) + " Mossy Cobblestone");
                    case CHISELED -> addBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type), getName(rock) + " Chiseled Bricks");
                    default -> addBlock(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type), getName(rock) + " " + getName(type));
                }
            });

            addItem(BeneathOverhaulItems.BRICKS.get(rock), getName(rock) + " Brick");
        });
    }

    private void createOreKey(Supplier<Block> block, String rock, String ore) {
        addBlock(block, rock + " " + ore);

        if (ore.equals("Pyrite")) {
            ore = "Native Gold?";
        }

        add(block.get().getDescriptionId() + ".prospected", ore);
    }

    private void createOreKey(Supplier<Block> block, String ore) {
        addBlock(block, ore);

        add(block.get().getDescriptionId() + ".prospected", ore);
    }

    private String getName(String string){
        return TextUtils.getName(string);
    }

    private String getName(StringRepresentable entry){
        return TextUtils.getName(entry.getSerializedName());
    }
}
