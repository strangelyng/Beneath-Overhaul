package net.strangelyng.beneathoverhaul.mixin.common.minecraft.entities;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*
 * Gives piglin brutes some equipment
 */

@Mixin(PiglinBrute.class)
public abstract class PiglinBruteMixin extends AbstractPiglin {
    public PiglinBruteMixin(EntityType<? extends AbstractPiglin> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("HEAD"))
    private void beneathoverhaul$populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
    }
}
