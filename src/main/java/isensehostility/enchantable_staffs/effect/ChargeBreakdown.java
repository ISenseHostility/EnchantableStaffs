package isensehostility.enchantable_staffs.effect;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.util.NBTUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ChargeBreakdown extends MobEffect {

    public ChargeBreakdown() {
        super(MobEffectCategory.HARMFUL, 4171955);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        NBTUtils.setMaxCharge(entity, (int) (StaffConfig.chargeMaxStarting.get() / (amplifier + 2) * 1.5D));
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
