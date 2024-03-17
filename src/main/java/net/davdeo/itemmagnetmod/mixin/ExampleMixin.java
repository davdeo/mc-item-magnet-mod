package net.davdeo.itemmagnetmod.mixin;

import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void loadWorld(CallbackInfo info) {
		ItemMagnetMod.LOGGER.info("Hello load world");
	}

}