package net.strangelyng.beneathoverhaul;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.strangelyng.beneathoverhaul.client.ClientEventHandler;
import net.strangelyng.beneathoverhaul.common.BeneathOverhaulCreativeTabs;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulBlocks;
import net.strangelyng.beneathoverhaul.common.items.BeneathOverhaulItems;
import net.strangelyng.beneathoverhaul.misc.BeneathOverhaulClimateModels;
import net.strangelyng.beneathoverhaul.world.BeneathOverhaulFeatures;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(BeneathOverhaul.MOD_ID)
public class BeneathOverhaul {
    public static final String MOD_ID = "beneathoverhaul";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public BeneathOverhaul(IEventBus bus, ModContainer mod) {
        bus.addListener(this::commonSetup);

        // Core Registries
        BeneathOverhaulBlocks.BLOCKS.register(bus);
        BeneathOverhaulItems.ITEMS.register(bus);
        BeneathOverhaulCreativeTabs.CREATIVE_TABS.register(bus);
        BeneathOverhaulFeatures.FEATURES.register(bus);
        BeneathOverhaulClimateModels.TYPES.register(bus);

        mod.registerConfig(ModConfig.Type.COMMON, BeneathOverhaulConfig.SPEC);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientEventHandler.init(bus, mod);
        }
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BeneathOverhaulClimateModels.registerModels();
        });
    }
}