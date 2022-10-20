package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.enchantment.category.StaffCategory;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.item.Staff;
import isensehostility.enchantable_staffs.util.ModUtils;
import isensehostility.enchantable_staffs.util.StaffUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class LightningCircle extends Enchantment implements IStaffEnchantment {
    public LightningCircle() {
        super(Rarity.UNCOMMON, StaffCategory.getInstance(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.LIGHTNING};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.lightningCircleExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.lightningCircleChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        if (StaffUtils.invokeStaffCosts(player, stack, getChargeCost(), level)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        LightningBolt[] bolts = new LightningBolt[8];

        for (int i = 0; i < 8; i++) {
            bolts[i] = ModUtils.createLightningBolt(level);
        }

        double x = player.getX();
        double z = player.getZ();

        bolts[0].setPos(x + 10, ModUtils.getHighestBlock(level, x + 10, z).above().getY(), z);
        bolts[1].setPos(x, ModUtils.getHighestBlock(level, x, z + 10).above().getY(), z + 10);
        bolts[2].setPos(x - 10, ModUtils.getHighestBlock(level, x - 10, z).above().getY(), z);
        bolts[3].setPos(x, ModUtils.getHighestBlock(level, x, z - 10).above().getY(), z - 10);
        bolts[4].setPos(x + 5, ModUtils.getHighestBlock(level, x + 5, z + 5).above().getY(), z + 5);
        bolts[5].setPos(x - 5, ModUtils.getHighestBlock(level, x - 5, z - 5).above().getY(), z - 5);
        bolts[6].setPos(x + 5, ModUtils.getHighestBlock(level, x + 5, z - 5).above().getY(), z - 5);
        bolts[7].setPos(x - 5, ModUtils.getHighestBlock(level, x - 5, z + 5).above().getY(), z + 5);

        if (!level.isClientSide) {
            for (LightningBolt bolt : bolts) {
                level.addFreshEntity(bolt);
                ModUtils.spawnParticleCloud(ParticleTypes.FIREWORK, level, bolt.getX() + 0.5D, bolt.getY() + 1.0D, bolt.getZ() + 0.5D);
            }
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinCost(int level) {
        return 10;
    }

    @Override
    public int getMaxCost(int level) {
        return 50;
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
