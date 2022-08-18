package isensehostility.enchantable_staffs;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.effect.StaffEffects;
import isensehostility.enchantable_staffs.enchantment.IStaffEnchantment;
import isensehostility.enchantable_staffs.enums.EChargeTextColors;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.enums.EStaffModifiers;
import isensehostility.enchantable_staffs.enums.EWeather;
import isensehostility.enchantable_staffs.item.Staff;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static isensehostility.enchantable_staffs.StaffUtils.getCharge;
import static isensehostility.enchantable_staffs.StaffUtils.getMaxCharge;

public class StaffUtils {
    private static final Random random = new Random();

    private static final String TAG_STAFF_CHARGE = "enchantable_staffs:staff_charge";
    private static final String TAG_STAFF_MAX_CHARGE = "enchantable_staffs:staff_max_charge";
    private static final String TAG_STAFF_HAS_CHARGE_DATA = "enchantable_staffs:staff_has_charge_data";
    private static final String TAG_HURT_BY_STAFF = "enchantable_staffs:hurt_by_staff";
    private static final String TAG_FROM_STAFF = "enchantable_staffs:from_staff";
    private static final String TAG_ENCHANTMENT_LEVEL = "enchantable_staffs:enchantment_level_";
    private static final String TAG_DIRECTION = "enchantable_staffs:direction";
    private static final String TAG_WARP_DIMENSION = "enchantable_staffs:warp_dimension";
    private static final String TAG_WARP_POSITION = "enchantable_staffs:warp_position";
    private static final String TAG_WARP_OWNER = "enchantable_staffs:warp_owner";
    private static final String TAG_MAGICAL_STRENGTHENING_TIME = "enchantable_staffs:magical_strengthening_time";
    private static final String TAG_SPECTRAL_WINGS_TIME = "enchantable_staffs:spectral_wings_time";
    private static final String TAG_IS_SPECTRAL_WINGS = "enchantable_staffs:is_spectral_wings";
    private static final String TAG_CHESTPLATE_DATA = "enchantable_staffs:chestplate_data";
    private static final String TAG_FRIENDLY = "enchantable_staffs:friendly";

    public static String getTagElementalEfficiency() {
        return TAG_ELEMENTAL_EFFICIENCY;
    }

    private static final String TAG_ELEMENTAL_EFFICIENCY = "enchantable_staffs:elemental_efficiency";

    public static void setCharge(LivingEntity entity, int charge) {
        entity.getPersistentData().putInt(TAG_STAFF_CHARGE, charge);
    }

    public static void addCharge(LivingEntity entity) {
        if (getCharge(entity) < getMaxCharge(entity)) {
            setCharge(entity, entity.getPersistentData().getInt(TAG_STAFF_CHARGE) + 1);
        }
    }

    public static void addCharge(LivingEntity entity, int amount) {
        if (getCharge(entity) < getMaxCharge(entity)) {
            setCharge(entity, entity.getPersistentData().getInt(TAG_STAFF_CHARGE) + amount);
        }
    }

    public static void reduceCharge(LivingEntity entity, int cost) {
        setCharge(entity, StaffUtils.getCharge(entity) - cost);
    }

    public static int getCharge(LivingEntity entity) {
        return entity.getPersistentData().getInt(TAG_STAFF_CHARGE);
    }

    public static void setMaxCharge(LivingEntity entity, int charge) {
        entity.getPersistentData().putInt(TAG_STAFF_MAX_CHARGE, charge);
    }

    public static int getMaxCharge(LivingEntity entity) {
        return entity.getPersistentData().getInt(TAG_STAFF_MAX_CHARGE);
    }

    public static void generateChargeData(LivingEntity entity) {
        entity.getPersistentData().putBoolean(TAG_STAFF_HAS_CHARGE_DATA, true);
    }

    public static boolean hasChargeData(LivingEntity entity) {
        return entity.getPersistentData().getBoolean(TAG_STAFF_HAS_CHARGE_DATA);
    }

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

    public static void reduceDurability(ItemStack stack) {
        int unbreakingLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, stack);
        if (unbreakingLevel > 0) {
            if (random.nextInt(100) + 1 > 100 / (unbreakingLevel + 1)) {
                return;
            }
        }
        stack.setDamageValue(stack.getDamageValue() + 1);
    }

    public static <T extends ParticleOptions> void spawnParticleCloud(T type, double x, double y, double z, Level level) {
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(type, x, y, z, 20, 0.5D, 0.5D, 0.5D, 0.1D);
        }
    }

    public static boolean invokeStaffCosts(Player player, ItemStack stack, int cost, Level level) {
        if (!level.isClientSide) {
            if (player.hasEffect(StaffEffects.ELEMENTAL_EFFICIENCY.get())) {
                int reducedCost = (cost / 100) * (100 - ((player.getEffect(StaffEffects.ELEMENTAL_EFFICIENCY.get()).getAmplifier() + 1) * 15));

                return applyCost(player, stack, reducedCost);
            } else {
                return applyCost(player, stack, cost);
            }
        }

        return true;
    }

    public static boolean applyCost(Player player, ItemStack stack, int cost) {
        if (getCharge(player) >= cost) {
            StaffUtils.reduceCharge(player, cost);
            StaffUtils.reduceDurability(stack);

            return false;
        }

        return true;
    }

    public static boolean posIsAir(Level level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() == Blocks.AIR;
    }

    public static boolean chargeIsBetween(LivingEntity entity, int min, int max) {
        if (getCharge(entity) == getMaxCharge(entity)) {
            return false;
        }
        return min <= getCharge(entity) && getCharge(entity) <= max;
    }

    public static ChatFormatting assignColor(Player player) {
        ChatFormatting color = switch (
                chargeIsBetween(player, 0, getMaxCharge(player) / 3) ?
                EChargeTextColors.RED :

                chargeIsBetween(player, getMaxCharge(player) / 3, getMaxCharge(player) / 3 * 2) ?
                EChargeTextColors.YELLOW :

                chargeIsBetween(player, getMaxCharge(player) / 3 * 2, getMaxCharge(player)) ?
                EChargeTextColors.GREEN :

                EChargeTextColors.AQUA) {
            case RED -> ChatFormatting.RED;
            case YELLOW -> ChatFormatting.YELLOW;
            case GREEN -> ChatFormatting.GREEN;
            default -> ChatFormatting.AQUA;
        };

        return color;
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

    public static <T extends ParticleOptions> List<LivingEntity> getEntitiesInBreathLine(Level level, Player player, double range, T particle) {
        BlockPos rayTracePos = rayTrace(level, player, ClipContext.Fluid.ANY, range).getBlockPos();
        BlockPos playerEyePos = new BlockPos(player.getX(), player.getEyeY(), player.getZ());
        BlockPos[] positions = new BlockPos[]{
                new BlockPos(playerEyePos.getX() + 0.5, playerEyePos.getY() + 0.5, playerEyePos.getZ() + 0.5),
                new BlockPos((playerEyePos.getX() + 0.5) * 0.9 + (rayTracePos.getX() + 0.5) * 0.1, (playerEyePos.getY() + 0.5) * 0.9 + (rayTracePos.getY() + 0.5) * 0.1, (playerEyePos.getZ() + 0.5) * 0.9 + (rayTracePos.getZ() + 0.5) * 0.1),
                new BlockPos((playerEyePos.getX() + 0.5) * 0.8 + (rayTracePos.getX() + 0.5) * 0.2, (playerEyePos.getY() + 0.5) * 0.8 + (rayTracePos.getY() + 0.5) * 0.2, (playerEyePos.getZ() + 0.5) * 0.8 + (rayTracePos.getZ() + 0.5) * 0.2),
                new BlockPos((playerEyePos.getX() + 0.5) * 0.7 + (rayTracePos.getX() + 0.5) * 0.3, (playerEyePos.getY() + 0.5) * 0.7 + (rayTracePos.getY() + 0.5) * 0.3, (playerEyePos.getZ() + 0.5) * 0.7 + (rayTracePos.getZ() + 0.5) * 0.3),
                new BlockPos((playerEyePos.getX() + 0.5) * 0.6 + (rayTracePos.getX() + 0.5) * 0.4, (playerEyePos.getY() + 0.5) * 0.6 + (rayTracePos.getY() + 0.5) * 0.4, (playerEyePos.getZ() + 0.5) * 0.6 + (rayTracePos.getZ() + 0.5) * 0.4),
                new BlockPos((playerEyePos.getX() + 0.5) * 0.5 + (rayTracePos.getX() + 0.5) * 0.5, (playerEyePos.getY() + 0.5) * 0.5 + (rayTracePos.getY() + 0.5) * 0.5, (playerEyePos.getZ() + 0.5) * 0.5 + (rayTracePos.getZ() + 0.5) * 0.5),
                new BlockPos((playerEyePos.getX() + 0.5) * 0.4 + (rayTracePos.getX() + 0.5) * 0.6, (playerEyePos.getY() + 0.5) * 0.4 + (rayTracePos.getY() + 0.5) * 0.6, (playerEyePos.getZ() + 0.5) * 0.4 + (rayTracePos.getZ() + 0.5) * 0.6),
                new BlockPos((playerEyePos.getX() + 0.5) * 0.3 + (rayTracePos.getX() + 0.5) * 0.7, (playerEyePos.getY() + 0.5) * 0.3 + (rayTracePos.getY() + 0.5) * 0.7, (playerEyePos.getZ() + 0.5) * 0.3 + (rayTracePos.getZ() + 0.5) * 0.7),
                new BlockPos((playerEyePos.getX() + 0.5) * 0.2 + (rayTracePos.getX() + 0.5) * 0.8, (playerEyePos.getY() + 0.5) * 0.2 + (rayTracePos.getY() + 0.5) * 0.8, (playerEyePos.getZ() + 0.5) * 0.2 + (rayTracePos.getZ() + 0.5) * 0.8),
                new BlockPos((playerEyePos.getX() + 0.5) * 0.1 + (rayTracePos.getX() + 0.5) * 0.9, (playerEyePos.getY() + 0.5) * 0.1 + (rayTracePos.getY() + 0.5) * 0.9, (playerEyePos.getZ() + 0.5) * 0.1 + (rayTracePos.getZ() + 0.5) * 0.9),
                new BlockPos(rayTracePos.getX() + 0.5, rayTracePos.getY() + 0.5, rayTracePos.getZ() + 0.5)
        };

        List<LivingEntity> entities = new ArrayList<>();

        for (BlockPos pos : positions) {
            spawnParticleCloud(particle, pos.getX(), pos.getY(), pos.getZ(), level);
            entities.addAll(level.getEntitiesOfClass(LivingEntity.class, createBoundingBox(pos, 1.5D)));
        }

        return entities;
    }

    public static void setHurtByStaff(LivingEntity entity, boolean isHurtByStaff) {
        entity.getPersistentData().putBoolean(TAG_HURT_BY_STAFF, isHurtByStaff);
    }

    public static boolean getHurtByStaff(LivingEntity entity) {
        return entity.getPersistentData().getBoolean(TAG_HURT_BY_STAFF);
    }

    public static void setFromStaff(DragonFireball dragonFireball, boolean isFromStaff) {
        dragonFireball.getPersistentData().putBoolean(TAG_FROM_STAFF, isFromStaff);
    }

    public static boolean getFromStaff(DragonFireball dragonFireball) {
        return dragonFireball.getPersistentData().getBoolean(TAG_FROM_STAFF);
    }

    public static void setEnchantmentLevel(Entity entity, Enchantment enchantment, int level) {
        entity.getPersistentData().putInt(TAG_ENCHANTMENT_LEVEL + enchantment.toString(), level);
    }

    public static int getEnchantmentLevel(Entity entity, Enchantment enchantment) {
        return entity.getPersistentData().getInt(TAG_ENCHANTMENT_LEVEL + enchantment.toString());
    }

    public static void setDirection(DragonFireball dragonFireball, long[] direction) {
        dragonFireball.getPersistentData().putLongArray(TAG_DIRECTION, direction);
    }

    public static long[] getDirection(DragonFireball dragonFireball) {
        return dragonFireball.getPersistentData().getLongArray(TAG_DIRECTION);
    }

    public static void invokeCriticalVisuals(Player player) {
        spawnParticleCloud(ParticleTypes.CRIT, player.getX(), player.getEyeY(), player.getZ(), player.getLevel());
        spawnParticleCloud(ParticleTypes.END_ROD, player.getX(), player.getEyeY(), player.getZ(), player.getLevel());
        player.getLevel().playSound(null, new BlockPos(player.getEyePosition()), SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 100.0F, 1.0F);
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

    public static void invokeWeatherVisuals(EWeather weather, BlockPos pos, Level level) {
        switch (weather) {
            case CLEAR:
                spawnParticleCloud(ParticleTypes.LAVA, pos.getX(), pos.getY() + 1.0D, pos.getZ(), level);
                level.playSound(null, pos, SoundEvents.LAVA_POP, SoundSource.PLAYERS, 100.0F, 1.0F);
                break;
            case RAINING:
                spawnParticleCloud(ParticleTypes.DRIPPING_WATER, pos.getX(), pos.getY() + 1.0D, pos.getZ(), level);
                level.playSound(null, pos, SoundEvents.WEATHER_RAIN, SoundSource.PLAYERS, 100.0F, 1.0F);
                break;
            case THUNDERING:
                spawnParticleCloud(ParticleTypes.DRIPPING_WATER, pos.getX(), pos.getY() + 1.0D, pos.getZ(), level);
                spawnParticleCloud(ParticleTypes.FIREWORK, pos.getX(), pos.getY() + 1.0D, pos.getZ(), level);
                level.playSound(null, pos, SoundEvents.WEATHER_RAIN, SoundSource.PLAYERS, 100.0F, 1.0F);
                level.playSound(null, pos, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 100.0F, 1.0F);
                break;
        }
    }

    public static BlockPos getHighestBlock(Level level, double x, double z) {
        BlockHitResult result = level.clip(new ClipContext(new Vec3(x, 319, z), new Vec3(x, -64, z), ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, null));

        return result.getBlockPos();
    }

    public static LightningBolt createLightningBolt(Level level) {
        return new LightningBolt(EntityType.LIGHTNING_BOLT, level);
    }

    public static void setWarpDimension(ItemStack stack, LivingEntity entity) {
        stack.getTag().putString(TAG_WARP_DIMENSION, entity.level.dimension().location().toString());
    }

    public static String getWarpDimension(ItemStack stack) {
        return stack.getTag().getString(TAG_WARP_DIMENSION);
    }

    public static void setWarpPosition(ItemStack stack, LivingEntity entity) {
        stack.getTag().putLongArray(TAG_WARP_POSITION, new long[]{(long) (entity.getX() * 1000000), (long) (entity.getY() * 1000000), (long) (entity.getZ() * 1000000)});
    }

    public static long[] getWarpPosition(ItemStack stack) {
        return stack.getTag().getLongArray(TAG_WARP_POSITION);
    }

    public static void setWarpOwner(ItemStack stack, LivingEntity entity) {
        stack.getTag().putString(TAG_WARP_OWNER, entity.getStringUUID());
    }

    public static String getWarpOwner(ItemStack stack) {
        return stack.getTag().getString(TAG_WARP_OWNER);
    }

    public static void setMagicalStrengtheningTime(Player player, int time) {
        player.getPersistentData().putInt(TAG_MAGICAL_STRENGTHENING_TIME, time);
    }

    public static int getMagicalStrengtheningTime(Player player) {
        return player.getPersistentData().getInt(TAG_MAGICAL_STRENGTHENING_TIME);
    }

    public static void reduceMagicalStrengtheningTime(Player player) {
        setMagicalStrengtheningTime(player, getMagicalStrengtheningTime(player) - 1);
    }

    public static boolean isMeleeWeapon(ItemStack stack) {
        return (stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem || stack.getItem() instanceof TridentItem);
    }

    public static void setSpectralWingsTime(Player player, int time) {
        player.getPersistentData().putInt(TAG_SPECTRAL_WINGS_TIME, time);
    }

    public static int getSpectralWingsTime(Player player) {
        return player.getPersistentData().getInt(TAG_SPECTRAL_WINGS_TIME);
    }

    public static void reduceSpectralWingsTime(Player player) {
        setSpectralWingsTime(player, getSpectralWingsTime(player) - 1);
    }

    public static void setIsSpectralWings(ItemStack stack, boolean isSpectralWings) {
        stack.getTag().putBoolean(TAG_IS_SPECTRAL_WINGS, isSpectralWings);
    }

    public static boolean getIsSpectralWings(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getBoolean(TAG_IS_SPECTRAL_WINGS);
        }
        else return false;
    }

    public static void setChestplateData(Player player) {
        player.getPersistentData().put(TAG_CHESTPLATE_DATA, player.getItemBySlot(EquipmentSlot.CHEST).serializeNBT());
    }

    public static Tag getChestplateData(Player player) {
        return player.getPersistentData().get(TAG_CHESTPLATE_DATA);
    }

    public static int calculateCharge(IStaffEnchantment staffEnchantment, Player player) {
        int cost = staffEnchantment.getChargeCost();
        int efficiencyLevel;

        if (player.hasEffect(StaffEffects.ELEMENTAL_EFFICIENCY.get()) && Arrays.stream(staffEnchantment.getElements()).toList().contains(EElement.getById(getElementalEfficiency(player)))) {
            efficiencyLevel = player.getEffect(StaffEffects.ELEMENTAL_EFFICIENCY.get()).getAmplifier() + 1;
            int reducedCost = (cost / 100) * (100 - (efficiencyLevel * 15));

            if (efficiencyLevel != 0) {
                cost = reducedCost;
            }
        }

        return cost;
    }

    public static void setElementalEfficiency(Player player, EElement element) {
        player.getPersistentData().putInt(TAG_ELEMENTAL_EFFICIENCY, element.getId());
    }

    public static void setElementalEfficiencyById(Player player, int id) {
        player.getPersistentData().putInt(TAG_ELEMENTAL_EFFICIENCY, id);
    }

    public static int getElementalEfficiency(Player player) {
        return player.getPersistentData().getInt(TAG_ELEMENTAL_EFFICIENCY);
    }

    public static Random getRandom() {
        return random;
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

    public static void setFriendly(LivingEntity entity, boolean isFriendly) {
        entity.getPersistentData().putBoolean(TAG_FRIENDLY, isFriendly);
    }

    public static boolean getFriendly(LivingEntity entity) {
        return entity.getPersistentData().getBoolean(TAG_FRIENDLY);
    }

    public static boolean isHoldingStaff(LivingEntity entity) {
        return entity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof Staff || entity.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof Staff;
    }

    public static boolean isInTemperatureRange(LivingEntity entity, float minTemp, float maxTemp) {
        float temperature = entity.level.getBiome(entity.getOnPos()).get().getBaseTemperature();

        return temperature >= minTemp && temperature <= maxTemp;
    }

    public static void passiveChargeModifierLogic(ItemStack stack, LivingEntity entity) {
        if (stack.getItem() instanceof Staff && Staff.hasModifier(stack)) {
            EStaffModifiers modifier = Staff.getModifier(stack);

            if (modifier == EStaffModifiers.COLD_AFFINITY && isInTemperatureRange(entity, -10.0F, 0.3F)) {
                addCharge(entity);
            }
            if (modifier == EStaffModifiers.HEAT_AFFINITY && isInTemperatureRange(entity, 0.95F, 10.0F)) {
                addCharge(entity);
            }
        }
    }

    public static void activeChargeModifierLogic(ItemStack stack, LivingEntity entity) {
        if (stack.getItem() instanceof Staff && Staff.hasModifier(stack)) {
            EStaffModifiers modifier = Staff.getModifier(stack);
            ItemStack offhand = entity.getOffhandItem();

            if (modifier == EStaffModifiers.GLUTTONY && offhand.isEdible() && getCharge(entity) < getMaxCharge(entity)) {
                int foodValue = offhand.getFoodProperties(entity).getNutrition();
                if (foodValue != 0) {
                    addCharge(entity, (int) Math.ceil(foodValue / 4));
                    offhand.shrink(1);
                }
            }
            if (modifier == EStaffModifiers.GREED && isGreedCompatible(offhand) && getCharge(entity) < getMaxCharge(entity)) {
                addCharge(entity, getGreedValue(offhand));
                offhand.shrink(1);
            }
        }
    }

    public static void envyLogic(ItemStack stack, LivingEntity entity, LivingEntity target) {
        if (stack.getItem() instanceof Staff && Staff.hasModifier(stack)) {
            EStaffModifiers modifier = Staff.getModifier(stack);

            if (modifier == EStaffModifiers.ENVY && getCharge(entity) < getMaxCharge(entity)) {
                addCharge(entity, (int) Math.ceil(target.getMaxHealth()));
            }
        }
    }

    public static boolean isGreedCompatible(ItemStack stack) {
        Item item = stack.getItem();
        
        return item == Items.COAL ||
                item == Items.CHARCOAL ||
                item == Items.RAW_COPPER ||
                item == Items.COPPER_INGOT ||
                item == Items.RAW_IRON ||
                item == Items.IRON_INGOT ||
                item == Items.REDSTONE ||
                item == Items.LAPIS_LAZULI ||
                item == Items.RAW_GOLD ||
                item == Items.GOLD_INGOT ||
                item == Items.EMERALD ||
                item == Items.AMETHYST_SHARD ||
                item == Items.QUARTZ ||
                item == Items.DIAMOND ||
                item == Items.NETHERITE_SCRAP ||
                item == Items.NETHERITE_INGOT;
    }

    public static int getGreedValue(ItemStack stack) {
        Item item = stack.getItem();

        if (item == Items.COAL || item == Items.CHARCOAL) {
            return StaffConfig.greedValueCoal.get();
        }
        if (item == Items.RAW_COPPER || item == Items.COPPER_INGOT) {
            return StaffConfig.greedValueCopper.get();
        }
        if (item == Items.RAW_IRON || item == Items.IRON_INGOT ) {
            return StaffConfig.greedValueIron.get();
        }
        if (item == Items.REDSTONE) {
            return StaffConfig.greedValueRedstone.get();
        }
        if (item == Items.LAPIS_LAZULI) {
            return StaffConfig.greedValueLapis.get();
        }
        if (item == Items.RAW_GOLD || item == Items.GOLD_INGOT) {
            return StaffConfig.greedValueGold.get();
        }
        if (item == Items.EMERALD) {
            return StaffConfig.greedValueEmerald.get();
        }
        if (item == Items.AMETHYST_SHARD) {
            return StaffConfig.greedValueAmethyst.get();
        }
        if (item == Items.QUARTZ) {
            return StaffConfig.greedValueNetherQuartz.get();
        }
        if (item == Items.DIAMOND) {
            return StaffConfig.greedValueDiamond.get();
        }
        if (item == Items.NETHERITE_SCRAP) {
            return StaffConfig.greedValueScrap.get();
        }
        if (item == Items.NETHERITE_INGOT) {
            return StaffConfig.greedValueNetherite.get();
        }

        return 0;
    }
}
