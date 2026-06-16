package net.strangelyng.beneathoverhaul.mixin.common.beneath;

import com.eerussianguy.beneath.common.entities.prey.NetherPrey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/*
 * Special thanks to the TerraFirmaGreg Team, this Class is based on their work for the TerraFirmaGreg modpack
 * https://github.com/TerraFirmaGreg-Team/Core-Modern/blob/dev/src/main/java/su/terrafirmagreg/core/mixins/common/beneath/NetherPreyMixin.java
 */

@Mixin(value = NetherPrey.class, remap = false)
public class NetherPreyMixin {

    /**
     * @author Strangelyng
     * @reason Makes red elk not take damage from water, now that it spawns naturally in the Beneath
     */
    @Overwrite(remap = true)
    public boolean isSensitiveToWater() {
        return false;
    }
}
