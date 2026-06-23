package net.strangelyng.beneathoverhaul.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PiglinMaskItem extends BlockItem {

    public PiglinMaskItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @Nullable EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return stack.is(ItemTags.PIGLIN_LOVED);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        if (!Screen.hasShiftDown()){
            tooltipComponents.add(Component.translatable("beneathoverhaul.tooltip.hold_shift").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        } else {
            tooltipComponents.add(Component.translatable("beneathoverhaul.tooltip.piglin_mask").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
