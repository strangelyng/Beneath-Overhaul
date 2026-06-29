package net.strangelyng.beneathoverhaul.common;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;

public class BeneathOverhaulEvents {
    public static void init(IEventBus bus, ModContainer mod) {
        bus.addListener(BeneathOverhaulEvents::setupModCompat);
    }

    public static void setupModCompat(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA && ModList.get().isLoaded("firmalife")) {
            event.addPackFinders(
                    ResourceLocation.fromNamespaceAndPath(BeneathOverhaul.MOD_ID, "compat/firmalife"),
                    PackType.SERVER_DATA,
                    Component.literal("Firmalife Compat"),
                    PackSource.BUILT_IN,
                    true,
                    Pack.Position.TOP
            );
        }

        if (event.getPackType() == PackType.SERVER_DATA && ModList.get().isLoaded("tfc_ie_addon")) {
            event.addPackFinders(
                    ResourceLocation.fromNamespaceAndPath(BeneathOverhaul.MOD_ID, "compat/tfc_ie_addon"),
                    PackType.SERVER_DATA,
                    Component.literal("TFC-IE-Crossover Compat"),
                    PackSource.BUILT_IN,
                    true,
                    Pack.Position.TOP
            );
        }

        if (event.getPackType() == PackType.CLIENT_RESOURCES && ModList.get().isLoaded("create")) {
            event.addPackFinders(
                    ResourceLocation.fromNamespaceAndPath(BeneathOverhaul.MOD_ID, "compat/create"),
                    PackType.CLIENT_RESOURCES,
                    Component.literal("Create Compat"),
                    PackSource.BUILT_IN,
                    false,
                    Pack.Position.TOP
            );
        }
    }
}
