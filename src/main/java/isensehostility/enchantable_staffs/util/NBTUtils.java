package isensehostility.enchantable_staffs.util;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import isensehostility.enchantable_staffs.enums.EElement;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class NBTUtils {

    public static void setCharge(LivingEntity entity, int charge) {
        entity.getPersistentData().putInt(Tags.STAFF_CHARGE, charge);
    }

    public static void addCharge(LivingEntity entity) {
        if (getCharge(entity) < getMaxCharge(entity)) {
            setCharge(entity, entity.getPersistentData().getInt(Tags.STAFF_CHARGE) + 1);
        }
    }

    public static void addCharge(LivingEntity entity, int amount) {
        if (getCharge(entity) < getMaxCharge(entity)) {
            setCharge(entity, entity.getPersistentData().getInt(Tags.STAFF_CHARGE) + amount);
        }
    }

    public static void reduceCharge(LivingEntity entity, int cost) {
        setCharge(entity, getCharge(entity) - cost);
    }

    public static int getCharge(LivingEntity entity) {
        return entity.getPersistentData().getInt(Tags.STAFF_CHARGE);
    }

    public static void setMaxCharge(LivingEntity entity, int charge) {
        entity.getPersistentData().putInt(Tags.STAFF_MAX_CHARGE, charge);
    }

    public static int getMaxCharge(LivingEntity entity) {
        return entity.getPersistentData().getInt(Tags.STAFF_MAX_CHARGE);
    }

    public static void generateChargeData(LivingEntity entity) {
        entity.getPersistentData().putBoolean(Tags.STAFF_HAS_CHARGE_DATA, true);
    }

    public static boolean hasChargeData(LivingEntity entity) {
        return entity.getPersistentData().getBoolean(Tags.STAFF_HAS_CHARGE_DATA);
    }

    public static void setHurtByStaff(LivingEntity entity, boolean isHurtByStaff) {
        entity.getPersistentData().putBoolean(Tags.HURT_BY_STAFF, isHurtByStaff);
    }

    public static boolean getHurtByStaff(LivingEntity entity) {
        return entity.getPersistentData().getBoolean(Tags.HURT_BY_STAFF);
    }

    public static void setFromStaff(DragonFireball dragonFireball, boolean isFromStaff) {
        dragonFireball.getPersistentData().putBoolean(Tags.FROM_STAFF, isFromStaff);
    }

    public static boolean getFromStaff(DragonFireball dragonFireball) {
        return dragonFireball.getPersistentData().getBoolean(Tags.FROM_STAFF);
    }

    public static void setEnchantmentLevel(Entity entity, Enchantment enchantment, int level) {
        entity.getPersistentData().putInt(Tags.ENCHANTMENT_LEVEL + enchantment.toString(), level);
    }

    public static int getEnchantmentLevel(Entity entity, Enchantment enchantment) {
        return entity.getPersistentData().getInt(Tags.ENCHANTMENT_LEVEL + enchantment.toString());
    }

    public static void setWarpDimension(ItemStack stack, LivingEntity entity) {
        stack.getTag().putString(Tags.WARP_DIMENSION, entity.level.dimension().location().toString());
    }

    public static String getWarpDimension(ItemStack stack) {
        return stack.getTag().getString(Tags.WARP_DIMENSION);
    }

    public static void setWarpPosition(ItemStack stack, LivingEntity entity) {
        stack.getTag().putLongArray(Tags.WARP_POSITION, new long[]{(long) (entity.getX() * 1000000), (long) (entity.getY() * 1000000), (long) (entity.getZ() * 1000000)});
    }

    public static long[] getWarpPosition(ItemStack stack) {
        return stack.getTag().getLongArray(Tags.WARP_POSITION);
    }

    public static void setWarpOwner(ItemStack stack, LivingEntity entity) {
        stack.getTag().putString(Tags.WARP_OWNER, entity.getStringUUID());
    }

    public static String getWarpOwner(ItemStack stack) {
        return stack.getTag().getString(Tags.WARP_OWNER);
    }

    public static void setMagicalStrengtheningTime(Player player, int time) {
        player.getPersistentData().putInt(Tags.MAGICAL_STRENGTHENING_TIME, time);
    }

    public static int getMagicalStrengtheningTime(Player player) {
        return player.getPersistentData().getInt(Tags.MAGICAL_STRENGTHENING_TIME);
    }

    public static void setSpectralWingsTime(Player player, int time) {
        player.getPersistentData().putInt(Tags.SPECTRAL_WINGS_TIME, time);
    }

    public static int getSpectralWingsTime(Player player) {
        return player.getPersistentData().getInt(Tags.SPECTRAL_WINGS_TIME);
    }

    public static void reduceSpectralWingsTime(Player player) {
        setSpectralWingsTime(player, getSpectralWingsTime(player) - 1);
    }

    public static void setIsSpectralWings(ItemStack stack, boolean isSpectralWings) {
        stack.getTag().putBoolean(Tags.IS_SPECTRAL_WINGS, isSpectralWings);
    }

    public static boolean getIsSpectralWings(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getBoolean(Tags.IS_SPECTRAL_WINGS);
        }
        else return false;
    }

    public static void setChestplateData(Player player) {
        player.getPersistentData().put(Tags.CHESTPLATE_DATA, player.getItemBySlot(EquipmentSlot.CHEST).serializeNBT());
    }

    public static Tag getChestplateData(Player player) {
        return player.getPersistentData().get(Tags.CHESTPLATE_DATA);
    }

    public static void setElementalEfficiency(Player player, EElement element) {
        player.getPersistentData().putInt(Tags.ELEMENTAL_EFFICIENCY, element.getId());
    }

    public static void setElementalEfficiencyById(Player player, int id) {
        player.getPersistentData().putInt(Tags.ELEMENTAL_EFFICIENCY, id);
    }

    public static int getElementalEfficiency(Player player) {
        return player.getPersistentData().getInt(Tags.ELEMENTAL_EFFICIENCY);
    }

    public static void setFriendly(LivingEntity entity, boolean isFriendly) {
        entity.getPersistentData().putBoolean(Tags.FRIENDLY, isFriendly);
    }

    public static boolean getFriendly(LivingEntity entity) {
        return entity.getPersistentData().getBoolean(Tags.FRIENDLY);
    }

    public static void reduceMagicalStrengtheningTime(Player player) {
        NBTUtils.setMagicalStrengtheningTime(player, NBTUtils.getMagicalStrengtheningTime(player) - 1);
    }

    private static class Tags {
        private static String createTag(String name) {
            return EnchantableStaffs.MODID + ":" + name;
        }

        public static final String STAFF_CHARGE = createTag("staff_charge");
        public static final String STAFF_MAX_CHARGE = createTag("staff_max_charge");
        public static final String STAFF_HAS_CHARGE_DATA = createTag("staff_has_charge_data");
        public static final String HURT_BY_STAFF = createTag("hurt_by_staff");
        public static final String FROM_STAFF = createTag("from_staff");
        public static final String ENCHANTMENT_LEVEL = createTag("enchantment_level_");
        public static final String DIRECTION = createTag("direction");
        public static final String WARP_DIMENSION = createTag("warp_dimension");
        public static final String WARP_POSITION = createTag("warp_position");
        public static final String WARP_OWNER = createTag("warp_owner");
        public static final String MAGICAL_STRENGTHENING_TIME = createTag("magical_strengthening_time");
        public static final String SPECTRAL_WINGS_TIME = createTag("spectral_wings_time");
        public static final String IS_SPECTRAL_WINGS = createTag("is_spectral_wings");
        public static final String CHESTPLATE_DATA = createTag("chestplate_data");
        public static final String FRIENDLY = createTag("friendly");
        public static final String ELEMENTAL_EFFICIENCY = createTag("elemental_efficiency");
    }
}
