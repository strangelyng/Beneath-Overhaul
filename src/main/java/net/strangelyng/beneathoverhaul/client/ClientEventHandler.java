package net.strangelyng.beneathoverhaul.client;

import net.dries007.tfc.common.blocks.rock.Rock;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulBlocks;

public class ClientEventHandler {
    public static void init(IEventBus bus, ModContainer mod) {
        bus.addListener(ClientEventHandler::clientSetup);
    }

    @SuppressWarnings("deprecation")
    public static void clientSetup(FMLClientSetupEvent event) {
        final RenderType cutout = RenderType.cutout();
        final RenderType cutoutMipped = RenderType.cutoutMipped();

        BeneathOverhaulBlocks.ROCK_BLOCKS.values().forEach(map -> {
            ItemBlockRenderTypes.setRenderLayer(map.get(Rock.BlockType.MOSSY_LOOSE).get(), cutout);
            ItemBlockRenderTypes.setRenderLayer(map.get(Rock.BlockType.MOSSY_BRICKS).get(), cutout);
            ItemBlockRenderTypes.setRenderLayer(map.get(Rock.BlockType.MOSSY_COBBLE).get(), cutout);
        });

        BeneathOverhaulBlocks.ROCK_DECORATIONS.values().forEach(map -> {
            ItemBlockRenderTypes.setRenderLayer(map.get(Rock.BlockType.MOSSY_BRICKS).stair().get(), cutout);
            ItemBlockRenderTypes.setRenderLayer(map.get(Rock.BlockType.MOSSY_BRICKS).slab().get(), cutout);
            ItemBlockRenderTypes.setRenderLayer(map.get(Rock.BlockType.MOSSY_BRICKS).wall().get(), cutout);
            ItemBlockRenderTypes.setRenderLayer(map.get(Rock.BlockType.MOSSY_COBBLE).stair().get(), cutout);
            ItemBlockRenderTypes.setRenderLayer(map.get(Rock.BlockType.MOSSY_COBBLE).slab().get(), cutout);
            ItemBlockRenderTypes.setRenderLayer(map.get(Rock.BlockType.MOSSY_COBBLE).wall().get(), cutout);
        });

        BeneathOverhaulBlocks.BENEATH_ROCK_TFC_ORES.values().forEach(map -> map.values().forEach(reg ->
                ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout)));
        BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES.values().forEach(map -> map.values().forEach(inner ->
                inner.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout))));
    }
}
