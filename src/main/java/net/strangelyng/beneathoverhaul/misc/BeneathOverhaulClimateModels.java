package net.strangelyng.beneathoverhaul.misc;

import io.netty.buffer.ByteBuf;
import net.dries007.tfc.util.climate.ClimateModel;
import net.dries007.tfc.util.climate.ClimateModelType;
import net.dries007.tfc.util.climate.ClimateModels;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;

public class BeneathOverhaulClimateModels {
    public static final DeferredRegister<ClimateModelType<?>> TYPES = DeferredRegister.create(ClimateModels.KEY, BeneathOverhaul.MOD_ID);

    public static final ClimateModels.Id<BeneathOverhaulClimateModel> BENEATH = register("beneath", StreamCodec.unit(BeneathOverhaulClimateModel.INSTANCE));

    public static void registerModels() {
        BENEATH.get();
    }

    private static <T extends ClimateModel> ClimateModels.Id<T> register(String id, StreamCodec<ByteBuf, T> codec) {
        return new ClimateModels.Id<>(TYPES.register(id, () -> new ClimateModelType<>(codec)));
    }
}
