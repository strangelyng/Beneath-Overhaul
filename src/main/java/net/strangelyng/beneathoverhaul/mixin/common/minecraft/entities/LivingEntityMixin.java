package net.strangelyng.beneathoverhaul.mixin.common.minecraft.entities;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.strangelyng.beneathoverhaul.common.items.BeneathOverhaulItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/*
 * Makes the piglin mask reduce detection range on piglin brutes, like the vanilla Piglin Head
 */

@Mixin(value = LivingEntity.class, remap = false)
public class LivingEntityMixin {
    @WrapOperation(method = "getVisibilityPercent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 3))
    private boolean beneathoverhaul$piglinMaskVisibility(ItemStack itemStack, Item item, Operation<Boolean> original) {
        if (itemStack.is(BeneathOverhaulItems.PIGLIN_MASK.get())) {
            return true;
        } else return original.call(itemStack, item);
    }
}
