package net.strangelyng.beneathoverhaul.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;

import net.dries007.tfc.world.feature.TFCFeatures.Id;

import java.util.function.Function;

public class BeneathOverhaulFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, BeneathOverhaul.MOD_ID);

    public static final Id<CustomLargeDripstoneFeature> CUSTOM_LARGE_DRIPSTONE = register("custom_large_dripstone", CustomLargeDripstoneFeature::new, CustomLargeDripstoneConfig.CODEC);

    private static <C extends FeatureConfiguration, F extends Feature<C>> Id<F> register(String name, Function<Codec<C>, F> factory, Codec<C> codec)
    {
        return new Id<>(FEATURES.register(name, () -> factory.apply(codec)));
    }
}
