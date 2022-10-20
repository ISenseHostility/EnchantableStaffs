package isensehostility.enchantable_staffs.util;

import isensehostility.enchantable_staffs.enums.EWeather;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ModUtils {
    public static BlockHitResult rayTrace(Level level, Player player, ClipContext.Fluid fluids, double range) {
        float f = player.getXRot();
        float f1 = player.getYRot();
        Vec3 vec3d = player.getEyePosition(1.0F);
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vec3d1 = vec3d.add((double)f6 * range, (double)f5 * range, (double)f7 * range);
        return level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, fluids, player));
    }

    public static <T extends ParticleOptions> void spawnParticleCloud(T type, Level level, double x, double y, double z) {
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(type, x, y, z, 20, 0.5D, 0.5D, 0.5D, 0.1D);
        }
    }

    public static <T extends ParticleOptions> void spawnParticleCloud(T type, Level level, BlockPos pos) {
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(type, pos.getX(), pos.getY(), pos.getZ(), 20, 0.5D, 0.5D, 0.5D, 0.1D);
        }
    }

    public static boolean posIsAir(Level level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() == Blocks.AIR;
    }

    public static Vec3 getDirection(LivingEntity entity) {
        double directionX = -Math.sin(Math.toRadians(entity.getYRot())) * Math.cos(Math.toRadians(entity.getXRot()));
        double directionY = -Math.sin(Math.toRadians(entity.getXRot()));
        double directionZ = Math.cos(Math.toRadians(entity.getYRot())) * Math.cos(Math.toRadians(entity.getXRot()));

        return new Vec3(directionX, directionY, directionZ);
    }

    public static Vec3 getPosFromDirection(Vec3 direction, LivingEntity entity) {
        return new Vec3(entity.getX() + direction.x() * 2, entity.getY() + 1.5 + direction.y(), entity.getZ() + direction.z() * 2);
    }

    public static AABB createBoundingBox(BlockPos pos, double radius) {
        return new AABB(pos.getX() - radius, pos.getY() - radius, pos.getZ() + radius, pos.getX() + radius, pos.getY() + radius, pos.getZ() - radius);
    }

    public static List<BlockPos> getPosLine(Player player, double range) {
        List<BlockPos> line = new ArrayList<>();

        BlockPos lineStartPos = new BlockPos(player.getEyePosition());
        BlockPos lineEndPos = rayTrace(player.getLevel(), player, ClipContext.Fluid.ANY, range).getBlockPos();

        line.add(lineStartPos);
        line.add(lineEndPos);

        double interval = 1 / range;

        for (int i = 1; i < range; i++) {
            double intervalAtRange = interval * i;

            BlockPos linePos = new BlockPos(
                    getLineCoordinate(lineStartPos.getX(), lineEndPos.getX(), intervalAtRange),
                    getLineCoordinate(lineStartPos.getY(), lineEndPos.getY(), intervalAtRange),
                    getLineCoordinate(lineStartPos.getZ(), lineEndPos.getZ(), intervalAtRange)
            );

            System.out.println("line pos " + i + ": " + linePos);

            line.add(linePos);
        }

        return line;
    }

    public static <T extends ParticleOptions> void spawnParticlesInLine(Player player, double range, T type) {
        BlockPos lineStartPos = new BlockPos(player.getEyePosition());
        BlockPos lineEndPos = rayTrace(player.getLevel(), player, ClipContext.Fluid.ANY, range).getBlockPos();
        System.out.println("player pos: " + lineStartPos);
        System.out.println("raytrace pos: " + lineEndPos);

        double interval = 1 / range;

        for (int i = 1; i < range; i++) {
            double intervalAtRange = interval * i;

            spawnParticleCloud(type,
                    player.getLevel(),
                    getLineCoordinate(lineStartPos.getX(), lineEndPos.getX(), intervalAtRange),
                    getLineCoordinate(lineStartPos.getY(), lineEndPos.getY(), intervalAtRange),
                    getLineCoordinate(lineStartPos.getZ(), lineEndPos.getZ(), intervalAtRange)
            );
        }
    }

    public static double getLineCoordinate(double startPos, double endPos, double intervalAtRange) {
        return ((startPos + 0.5) * intervalAtRange) + ((endPos + 0.5) * (1 - intervalAtRange));
    }

    public static List<LivingEntity> getEntitiesAtPositions(Level level, List<BlockPos> positions, double radius) {
        List<LivingEntity> entities = new ArrayList<>();

        for (BlockPos pos : positions) {
            entities.addAll(getEntitiesAtPosition(level, pos, radius));
        }

        return entities;
    }

    public static List<LivingEntity> getEntitiesAtPosition(Level level, BlockPos pos, double radius) {
        return level.getEntitiesOfClass(LivingEntity.class, createBoundingBox(pos, radius));
    }

    public static EWeather getWeather(Level level) {
        if (level.isRaining()) {
            if (level.isThundering()) {
                return EWeather.THUNDERING;
            } else {
                return EWeather.RAINING;
            }
        } else {
            return EWeather.CLEAR;
        }
    }

    public static void setWeatherClear(ServerLevel server) {
        server.setWeatherParameters(6000, 0, false, false);
    }

    public static void setWeatherRaining(ServerLevel server) {
        server.setWeatherParameters(0, 6000, true, false);
    }

    public static void setWeatherThundering(ServerLevel server) {
        server.setWeatherParameters(0, 6000, true, true);
    }

    public static BlockPos getHighestBlock(Level level, double x, double z) {
        BlockHitResult result = level.clip(new ClipContext(new Vec3(x, 319, z), new Vec3(x, -64, z), ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, null));

        return result.getBlockPos();
    }

    public static LightningBolt createLightningBolt(Level level) {
        return new LightningBolt(EntityType.LIGHTNING_BOLT, level);
    }

    public static boolean isMeleeWeapon(ItemStack stack) {
        return (stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem || stack.getItem() instanceof TridentItem);
    }

    public static boolean isInTemperatureRange(LivingEntity entity, float minTemp, float maxTemp) {
        float temperature = entity.level.getBiome(entity.getOnPos()).get().getBaseTemperature();

        return temperature >= minTemp && temperature <= maxTemp;
    }

}
