package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.enchantment.category.StaffCategory;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.item.Staff;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static isensehostility.enchantable_staffs.StaffUtils.*;

public class DraconicFireball extends Enchantment implements IStaffEnchantment {
    public DraconicFireball() {
        super(Rarity.UNCOMMON, StaffCategory.getInstance(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.FIRE, EElement.ENDER};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.draconicFireballExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.draconicFireballChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        if (invokeStaffCosts(player, stack, getChargeCost(), level)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }
     
        int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(StaffEnchantments.DRACONIC_FIREBALL.get(), stack);
        Vec3 direction = getDirection(player);
        Vec3 pos = getPosFromDirection(direction, player);

        DragonFireball dragonFireball = new DragonFireball(level, player, direction.x(), direction.y(), direction.z());
        dragonFireball.setPos(pos);
        setFromStaff(dragonFireball, true);
        setEnchantmentLevel(dragonFireball, StaffEnchantments.DRACONIC_FIREBALL.get(), enchantmentLevel);
        setDirection(dragonFireball, new long[]{(long) (pos.x() * 1000000000), (long) (pos.y() * 1000000000), (long) (pos.z() * 1000000000L)});
        level.addFreshEntity(dragonFireball);

        level.playSound(null, new BlockPos(player.getEyePosition()), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 100.0F, 1.0F);
        spawnParticleCloud(ParticleTypes.PORTAL, pos.x() + 0.5D, pos.y() + 1.0D, pos.z() + 0.5D, level);

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinCost(int level) {
        return 10 + 10 * (level - 1);
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 50;
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment)
                && enchantment == Enchantments.MENDING
                && enchantment == Enchantments.UNBREAKING;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof Staff && doesExist();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return this.canEnchant(stack);
    }
}
