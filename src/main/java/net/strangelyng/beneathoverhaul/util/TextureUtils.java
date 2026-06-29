package net.strangelyng.beneathoverhaul.util;

import com.google.common.collect.ImmutableMap;
import com.nmagpie.tfc_ie_addon.TFC_IE_Addon;
import com.nmagpie.tfc_ie_addon.util.IEOre;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.registry.RegistryRock;
import net.minecraft.resources.ResourceLocation;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulOres;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulRock;

import java.util.Locale;
import java.util.Map;

/*
 * Special thanks to Gourmandd, much of this utility function library is based on their work for On-Ancient-Ground-Core
 * https://github.com/Gourmandd/On-Ancient-Ground-Core/blob/main/src/main/java/net/gourmand/core/util/TextureUtil.java
 */

public class TextureUtils {
    public static String getRawRockTexture(RegistryRock rock) {
        if (rock instanceof Rock) {
            return getRawRockTexture((Rock) rock);
        }

        if (rock instanceof BeneathOverhaulRock) {
            return GET_RAW_ROCK_TEXTURE.get((BeneathOverhaulRock) rock);
        }

        throw new IllegalArgumentException("RockType: " + rock.getSerializedName() + " not found");
    }

    public static final Map<BeneathOverhaulRock, String> GET_RAW_ROCK_TEXTURE = ImmutableMap.<BeneathOverhaulRock, String>builder()
            .put(BeneathOverhaulRock.DEEPSLATE, "minecraft:block/deepslate")
            .put(BeneathOverhaulRock.BLACKSTONE, "minecraft:block/blackstone")
            .put(BeneathOverhaulRock.DRIPSTONE, "minecraft:block/dripstone_block")
            .put(BeneathOverhaulRock.NETHERRACK, "beneathoverhaul:block/rock/raw/netherrack")
            .build();

    private static String getCobbleTexture(BeneathOverhaulRock rock) {
        switch (rock) {
            case DEEPSLATE -> {
                return "minecraft:block/cobbled_deepslate";
            }
            case BLACKSTONE, DRIPSTONE, NETHERRACK -> {
                return (BeneathOverhaul.MOD_ID + ":block/rock/cobble/" + rock.getSerializedName());
            }
            case null, default -> throw new AssertionError("Invalid Rock to get texture for");
        }
    }

    private static String getMossyCobbleTexture(BeneathOverhaulRock rock) {
        switch (rock) {
            case DEEPSLATE -> {
                return "minecraft:block/cobbled_deepslate";
            }
            case BLACKSTONE, DRIPSTONE, NETHERRACK -> {
                return (BeneathOverhaul.MOD_ID + ":block/rock/cobble/" + rock.getSerializedName());
            }
            case null, default -> throw new AssertionError("Invalid Rock to get texture for");
        }
    }

    private static String getBricksTexture(BeneathOverhaulRock rock) {
        switch (rock) {
            case DEEPSLATE -> {
                return "minecraft:block/deepslate_bricks";
            }
            case BLACKSTONE -> {
                return "minecraft:block/polished_blackstone_bricks";
            }
            case DRIPSTONE, NETHERRACK -> {
                return (BeneathOverhaul.MOD_ID + ":block/rock/bricks/" + rock.getSerializedName());
            }
            case null, default -> throw new AssertionError("Invalid Rock to get texture for");
        }
    }

    private static String getMossyBricksTexture(BeneathOverhaulRock rock) {
        switch (rock) {
            case DEEPSLATE -> {
                return "minecraft:block/deepslate_bricks";
            }
            case BLACKSTONE -> {
                return "minecraft:block/polished_blackstone_bricks";
            }
            case DRIPSTONE, NETHERRACK -> {
                return (BeneathOverhaul.MOD_ID + ":block/rock/bricks/" + rock.getSerializedName());
            }
            case null, default -> throw new AssertionError("Invalid Rock to get texture for");
        }
    }

    private static String getCrackedBricksTexture(BeneathOverhaulRock rock) {
        switch (rock) {
            case DEEPSLATE -> {
                return "minecraft:block/cracked_deepslate_bricks";
            }
            case BLACKSTONE -> {
                return "minecraft:block/cracked_polished_blackstone_bricks";
            }
            case DRIPSTONE, NETHERRACK -> {
                return (BeneathOverhaul.MOD_ID + ":block/rock/cracked_bricks/" + rock.getSerializedName());
            }
            case null, default -> throw new AssertionError("Invalid Rock to get texture for");
        }
    }

    private static String getGravelTexture(BeneathOverhaulRock rock) {
        switch (rock) {
            default -> {
                return  (BeneathOverhaul.MOD_ID + ":block/rock/gravel/" + rock.getSerializedName());
            }
        }
    }

    private static String getSmoothTexture(BeneathOverhaulRock rock) {
        switch (rock) {
            case DEEPSLATE -> {
                return "minecraft:block/polished_deepslate";
            }
            case BLACKSTONE -> {
                return "minecraft:block/polished_blackstone";
            }
            default -> {
                return  (BeneathOverhaul.MOD_ID + ":block/rock/smooth/" + rock.getSerializedName());
            }
        }
    }

    private static String getChiseledTexture(BeneathOverhaulRock rock) {
        switch (rock) {
            case DEEPSLATE -> {
                return "minecraft:block/chiseled_deepslate";
            }
            case BLACKSTONE -> {
                return "minecraft:block/chiseled_polished_blackstone";
            }
            default -> {
                return  (BeneathOverhaul.MOD_ID + ":block/rock/chiseled/" + rock.getSerializedName());
            }
        }
    }

    private static String getRawRockTexture(Rock rock) {
        return TerraFirmaCraft.MOD_ID + ":block/rock/raw/" + rock.getSerializedName();
    }

    public static String getOreTexture(Ore ore) {
        return TerraFirmaCraft.MOD_ID + ":block/ore/" + ore.name().toLowerCase(Locale.ROOT);
    }

    public static String getOreTexture(BeneathOverhaulOres ore) {
        return BeneathOverhaul.MOD_ID + ":block/ore/" + ore.name().toLowerCase(Locale.ROOT);
    }

    public static String getOreTexture(Ore ore, Ore.Grade grade) {
        switch (grade) {
            case RICH -> {
                return TerraFirmaCraft.MOD_ID + ":block/ore/rich_" + ore.name().toLowerCase(Locale.ROOT);
            }
            case NORMAL -> {
                return TerraFirmaCraft.MOD_ID + ":block/ore/normal_" + ore.name().toLowerCase(Locale.ROOT);
            }
            case POOR -> {
                return TerraFirmaCraft.MOD_ID + ":block/ore/poor_" + ore.name().toLowerCase(Locale.ROOT);
            }
            case null -> throw new AssertionError("Invalid grade");
        }
    }

    public static String getOreTexture(BeneathOverhaulOres ore, Ore.Grade grade) {
        switch (grade) {
            case RICH -> {
                return BeneathOverhaul.MOD_ID + ":block/ore/rich_" + ore.name().toLowerCase(Locale.ROOT);
            }
            case NORMAL -> {
                return BeneathOverhaul.MOD_ID + ":block/ore/normal_" + ore.name().toLowerCase(Locale.ROOT);
            }
            case POOR -> {
                return BeneathOverhaul.MOD_ID + ":block/ore/poor_" + ore.name().toLowerCase(Locale.ROOT);
            }
            case null -> throw new AssertionError("Invalid grade");
        }
    }

    public static String getOreTexture(IEOre ore, Ore.Grade grade) {
        switch (grade) {
            case RICH -> {
                return TFC_IE_Addon.MOD_ID + ":block/ore/rich_" + ore.name().toLowerCase(Locale.ROOT);
            }
            case NORMAL -> {
                return TFC_IE_Addon.MOD_ID + ":block/ore/normal_" + ore.name().toLowerCase(Locale.ROOT);
            }
            case POOR -> {
                return TFC_IE_Addon.MOD_ID + ":block/ore/poor_" + ore.name().toLowerCase(Locale.ROOT);
            }
            case null -> throw new AssertionError("Invalid grade");
        }
    }

    public static String getOreTexture(String modid, String oreName, Ore.Grade grade) {
        switch (grade) {
            case RICH -> {
                return modid + ":block/ore/rich_" + oreName.toLowerCase(Locale.ROOT);
            }
            case NORMAL -> {
                return modid + ":block/ore/normal_" + oreName.toLowerCase(Locale.ROOT);
            }
            case POOR -> {
                return modid + ":block/ore/poor_" + oreName.toLowerCase(Locale.ROOT);
            }
            case null -> throw new AssertionError("Invalid grade");
        }
    }

    public static ResourceLocation getRockTexture(BeneathOverhaulRock rock, Rock.BlockType type) {
        switch (type) {
            case RAW, HARDENED, SPIKE -> {
                return ResourceLocation.parse(getRawRockTexture(rock));
            }
            case LOOSE, COBBLE -> {
                return ResourceLocation.parse(getCobbleTexture(rock));
            }
            case MOSSY_LOOSE, MOSSY_COBBLE -> {
                return ResourceLocation.parse(getMossyCobbleTexture(rock));
            }
            case GRAVEL -> {
                return ResourceLocation.parse(getGravelTexture(rock));
            }
            case SMOOTH, BUTTON, PRESSURE_PLATE -> {
                return ResourceLocation.parse(getSmoothTexture(rock));
            }
            case CHISELED -> {
                return ResourceLocation.parse(getChiseledTexture(rock));
            }
            case BRICKS, AQUEDUCT -> {
                return ResourceLocation.parse(getBricksTexture(rock));
            }
            case MOSSY_BRICKS -> {
                return ResourceLocation.parse(getMossyBricksTexture(rock));
            }
            case CRACKED_BRICKS -> {
                return ResourceLocation.parse(getCrackedBricksTexture(rock));
            }
        }
        return null;
    }

    public static ResourceLocation getRockTexture(Rock rock, Rock.BlockType type) {
        switch (type) {
            case RAW, HARDENED, SPIKE -> {
                return ResourceLocation.parse(TerraFirmaCraft.MOD_ID + ":block/rock/raw/" + rock.getSerializedName());
            }
            case LOOSE, COBBLE -> {
                return ResourceLocation.parse(TerraFirmaCraft.MOD_ID + ":block/rock/cobble/" + rock.getSerializedName());
            }
            case MOSSY_LOOSE, MOSSY_COBBLE -> {
                return ResourceLocation.parse(TerraFirmaCraft.MOD_ID + ":block/rock/mossy_cobble/" + rock.getSerializedName());
            }
            case GRAVEL -> {
                return ResourceLocation.parse(TerraFirmaCraft.MOD_ID + ":block/rock/gravel/" + rock.getSerializedName());
            }
            case SMOOTH, BUTTON, PRESSURE_PLATE -> {
                return ResourceLocation.parse(TerraFirmaCraft.MOD_ID + ":block/rock/smooth/" + rock.getSerializedName());
            }
            case CHISELED -> {
                return ResourceLocation.parse(TerraFirmaCraft.MOD_ID + ":block/rock/chiseled/" + rock.getSerializedName());
            }
            case BRICKS, AQUEDUCT -> {
                return ResourceLocation.parse(TerraFirmaCraft.MOD_ID + ":block/rock/bricks/" + rock.getSerializedName());
            }
            case MOSSY_BRICKS -> {
                return ResourceLocation.parse(TerraFirmaCraft.MOD_ID + ":block/rock/mossy_bricks/" + rock.getSerializedName());
            }
            case CRACKED_BRICKS -> {
                return ResourceLocation.parse(TerraFirmaCraft.MOD_ID + ":block/rock/cracked_bricks/" + rock.getSerializedName());
            }
        }
        return null;
    }
}
