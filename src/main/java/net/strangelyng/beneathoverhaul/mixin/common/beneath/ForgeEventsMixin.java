package net.strangelyng.beneathoverhaul.mixin.common.beneath;

import com.eerussianguy.beneath.ForgeEvents;
import net.dries007.tfc.util.climate.ClimateModel;
import net.dries007.tfc.util.events.SelectClimateModelEvent;
import net.strangelyng.beneathoverhaul.misc.BeneathOverhaulClimateModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ForgeEvents.class, remap = false)
public class ForgeEventsMixin {
    @Redirect(method = "onSelectClimateModel", at = @At(value = "INVOKE", target = "Lnet/dries007/tfc/util/events/SelectClimateModelEvent;setModel(Lnet/dries007/tfc/util/climate/ClimateModel;)V"))
    private static void beneathoverhaul$redirectSelectClimateModel(SelectClimateModelEvent event, ClimateModel model) {
        event.setModel(BeneathOverhaulClimateModel.INSTANCE);
    }
}
