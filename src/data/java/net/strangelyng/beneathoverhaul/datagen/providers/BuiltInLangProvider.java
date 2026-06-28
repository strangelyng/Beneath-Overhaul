package net.strangelyng.beneathoverhaul.datagen.providers;

import com.nmagpie.tfc_ie_addon.util.IEOre;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.minecraft.data.PackOutput;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulBlocks;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulOres;
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
        // Creative Tabs
        add("beneathoverhaul.creative_tab.ores", "Beneath Overhaul Ores");
        add("beneathoverhaul.creative_tab.rock", "Beneath Overhaul Rock Stuff");
        add("beneathoverhaul.creative_tab.misc", "Beneath Overhaul Miscellaneous");

        // Biomes
        add("biome.beneathoverhaul.nether/ash_forest", "Ash Forest");
        add("biome.beneathoverhaul.nether/basalt_deltas", "Basalt Deltas");
        add("biome.beneathoverhaul.nether/decaying_caverns", "Decaying Caverns");
        add("biome.beneathoverhaul.nether/diorite_caves", "Diorite Caves");
        add("biome.beneathoverhaul.nether/gabbro_caves", "Gabbro Caves");
        add("biome.beneathoverhaul.nether/gneiss_caves", "Gneiss Caves");
        add("biome.beneathoverhaul.nether/granite_caves", "Granite Caves");
        add("biome.beneathoverhaul.nether/lava_floes", "Lava Floes");
        add("biome.beneathoverhaul.nether/lush_hollow", "Lush Hollow");
        add("biome.beneathoverhaul.nether/schist_caves", "Schist Caves");
        add("biome.beneathoverhaul.nether/webbed_lair", "Webbed Lair");

        // Blocks & Items
        addBlock(BeneathOverhaulBlocks.CHARRED_LOG, "Charred Log");
        addBlock(BeneathOverhaulBlocks.MUSHROOM_ROOTS, "Mushroom Roots");
        addBlock(BeneathOverhaulBlocks.MUSHROOM_SPROUTS, "Mushroom Sprouts");
        addBlock(BeneathOverhaulBlocks.FLY_AGARIC, "Fly Agaric");
        addBlock(BeneathOverhaulBlocks.ASH_LAYER_BLOCK, "Ash Pile");

        addBlock(BeneathOverhaulBlocks.PIGLIN_MASK, "Piglin Mask");

        addBlock(BeneathOverhaulBlocks.BASTION_LAMP, "Black Bronze Lamp");

        addItem(BeneathOverhaulItems.FLY_AGARIC, "Fly Agaric Mushroom");

        // Tooltips
        add("beneathoverhaul.tooltip.hold_shift", "Hold (Shift) for Item Info");
        add("beneathoverhaul.tooltip.piglin_mask", "Makes piglins neutral and reduces \nthe detection range of piglin brutes");

        // Ore Blocks
        Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
            Stream.of(Ore.values()).forEach(ore -> {
                if (ore.hasBlock()) {
                    if (ore.isGraded()) {
                        Stream.of(Ore.Grade.values()).forEach(grade -> {
                            createOreKey(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES.get(rock).get(ore).get(grade), getName(grade.name()) + " " + getName(rock), getName(ore));
                        });
                    } else {
                        createOreKey(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_ORES.get(rock).get(ore), getName(rock), getName(ore));
                    }
                }
            });

            Stream.of(BeneathOverhaulOres.values()).forEach(ore -> {
                if (ore.hasBlock()) {
                    if (ore.isGraded()) {
                        // TODO: Custom Graded Ores
                    } else {
                        createOreKey(BeneathOverhaulBlocks.BENEATH_ROCK_CUSTOM_ORES.get(rock).get(ore), getName(rock), getName(ore));
                    }
                }
            });

            // Compat Ores
            Stream.of(Ore.Grade.values()).forEach(grade -> {
                createOreKey(BeneathOverhaulBlocks.BENEATH_ROCK_CHROMITE_ORES.get(rock).get(grade), getName(grade.name()) + " " + getName(rock), "Chromite");

                createOreKey(BeneathOverhaulBlocks.BENEATH_ROCK_BAUXITE_ORES.get(rock).get(grade), getName(grade.name()) + " " + getName(rock), "Bauxite");
                createOreKey(BeneathOverhaulBlocks.BENEATH_ROCK_GALENA_ORES.get(rock).get(grade), getName(grade.name()) + " " + getName(rock), "Galena");
                createOreKey(BeneathOverhaulBlocks.BENEATH_ROCK_URANINITE_ORES.get(rock).get(grade), getName(grade.name()) + " " + getName(rock), "Uraninite");
            });
        });

        Stream.of(BeneathOverhaulOres.values()).forEach(ore -> {
            if (ore.isGraded()) {

            } else {
                addItem(BeneathOverhaulItems.ORES.get(ore), getName(ore));
            }

            if (ore.isGem()) {
                addItem(BeneathOverhaulItems.GEMS.get(ore), "Cut " + getName(ore));
            }

            if (ore.hasPowder()) {
                addItem(BeneathOverhaulItems.ORE_POWDERS.get(ore), getName(ore) + " Powder");
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

    private String getName(Ore ore) {
        // Using switch in case it needs expanding for anything
        switch (ore) {
            case DIAMOND -> {
                return "Kimberlite";
            }
            default -> {
                return getName(ore.name());
            }
        }
    }

    private String getName(BeneathOverhaulOres ore) {
        return getName(ore.name());
    }

    private String getName(BeneathOverhaulRock rock) {
        switch (rock) {
            case BeneathOverhaulRock.BLACKSTONE -> {
                return "Pyroxenite";
            }
            case BeneathOverhaulRock.DEEPSLATE -> {
                return "Migmatite";
            }
            case BeneathOverhaulRock.DRIPSTONE -> {
                return "Travertine";
            }
            default -> {
                return getName(rock.getSerializedName());
            }
        }
    }

    private String getName(IEOre ore) {
        return getName(ore.name());
    }

    private String getName(StringRepresentable entry){
        return TextUtils.getName(entry.getSerializedName());
    }

    private String getName(String string){
        return TextUtils.getName(string);
    }
}
