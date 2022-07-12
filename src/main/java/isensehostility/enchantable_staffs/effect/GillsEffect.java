package isensehostility.enchantable_staffs.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;

public class GillsEffect extends MobEffect {

    public GillsEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.getLevel().getBlockState(new BlockPos(entity.getX(), entity.getEyeY(), entity.getZ())).getBlock() == Blocks.WATER) {
            entity.setAirSupply(entity.getMaxAirSupply());
        } else {
            entity.hurt(DamageSource.DRY_OUT, 1.0F);
        }
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        int k = 50 >> p_19456_;
        if (k > 0) {
            return p_19455_ % k == 0;
        } else {
            return true;
        }
    }
}
