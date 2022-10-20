package isensehostility.enchantable_staffs.util;

import isensehostility.enchantable_staffs.enums.EWeather;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class EnchantmentUtils {
    public static <T extends ParticleOptions> List<LivingEntity> summonBreath(Player user, double range, double thickness, T particle) {
        ModUtils.spawnParticlesInLine(user, range, particle);

        return ModUtils.getEntitiesAtPositions(user.getLevel(), ModUtils.getPosLine(user, range), thickness);
    }

    public static void invokeCriticalEffects(Player player) {
        ModUtils.spawnParticleCloud(ParticleTypes.CRIT, player.getLevel(), new BlockPos(player.getEyePosition()));
        ModUtils.spawnParticleCloud(ParticleTypes.END_ROD, player.getLevel(), new BlockPos(player.getEyePosition()));
        player.getLevel().playSound(null, new BlockPos(player.getEyePosition()), SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 100.0F, 1.0F);
    }

    public static void invokeWeatherEffects(EWeather weather, BlockPos pos, Level level) {
        switch (weather) {
            case CLEAR -> {
                ModUtils.spawnParticleCloud(ParticleTypes.LAVA, level, pos.getX(), pos.getY() + 1.0D, pos.getZ());
                level.playSound(null, pos, SoundEvents.LAVA_POP, SoundSource.PLAYERS, 100.0F, 1.0F);
            }
            case RAINING -> {
                ModUtils.spawnParticleCloud(ParticleTypes.DRIPPING_WATER, level, pos.getX(), pos.getY() + 1.0D, pos.getZ());
                level.playSound(null, pos, SoundEvents.WEATHER_RAIN, SoundSource.PLAYERS, 100.0F, 1.0F);
            }
            case THUNDERING -> {
                ModUtils.spawnParticleCloud(ParticleTypes.DRIPPING_WATER, level, pos.getX(), pos.getY() + 1.0D, pos.getZ());
                ModUtils.spawnParticleCloud(ParticleTypes.FIREWORK, level, pos.getX(), pos.getY() + 1.0D, pos.getZ());
                level.playSound(null, pos, SoundEvents.WEATHER_RAIN, SoundSource.PLAYERS, 100.0F, 1.0F);
                level.playSound(null, pos, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 100.0F, 1.0F);
            }
        }
    }

    public static void summonFang(LivingEntity summoner, Level level, Vec3 pos) {
        EvokerFangs fangs = new EvokerFangs(EntityType.EVOKER_FANGS, level);
        fangs.setOwner(summoner);
        fangs.setYRot(summoner.getYRot());
        fangs.setPos(pos.x(), pos.y(), pos.z());
        level.addFreshEntity(fangs);
    }

    public static void summonFang(LivingEntity summoner, Level level, BlockPos pos, @Nullable int offset) {
        EvokerFangs fangs = new EvokerFangs(level, pos.getX(), pos.getY(), pos.getZ(), 0, offset, summoner);
        fangs.setYRot(summoner.getYRot());
        level.addFreshEntity(fangs);
    }

}
