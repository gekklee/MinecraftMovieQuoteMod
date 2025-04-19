package xyz.gekklee;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinecraftMovieQuoteMod implements ModInitializer {
	public static final String MOD_ID = "minecraftmoviequotemod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
			if (entity instanceof ServerPlayerEntity player) {
				if (damageSource.isOf(DamageTypes.FALL)) {
					String message = player.getNameForScoreboard() + " failed their water bucket release";
					player.getServer().getPlayerManager().broadcast(Text.literal(message), false);
				}
			}
		});
		LOGGER.info("MinecraftMovieQuoteMod initialized");
	}
}