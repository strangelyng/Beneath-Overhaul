package net.strangelyng.beneathoverhaul.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = BeneathOverhaul.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(BuiltInBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));

        generator.addProvider(true, new BuiltInLangProvider(packOutput));
        generator.addProvider(event.includeClient(), new BuiltInBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new BuiltInItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new BuiltInBlockTagProvider(event, lookupProvider));
        generator.addProvider(event.includeServer(), new BuiltInItemTagProvider(event, lookupProvider));
    }
}
