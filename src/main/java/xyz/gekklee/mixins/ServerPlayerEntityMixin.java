package xyz.gekklee.mixins;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.damage.DamageSource;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(method = "onDeath(Lnet/minecraft/entity/damage/DamageSource;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageTracker;getDeathMessage()Lnet/minecraft/text/Text;"), cancellable = true)
    private void cancelDefaultDeathMessage(DamageSource source, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        DamageTracker damageTracker = player.getDamageTracker();
        Text message = damageTracker.getDeathMessage();
        if (message.getString().contains("fell") || message.getString().contains("hit the ground")) {
            player.getServer().getPlayerManager().broadcast(Text.of(player.getNameForScoreboard() + " failed their water bucket release"), false);
            ci.cancel();
        } else if (message.getString().contains("lava")) {
            player.getServer().getPlayerManager().broadcast(Text.of(player.getNameForScoreboard() + " visited Steve's Lava Chicken"), false);
            ci.cancel();
        }
    }
}