package isensehostility.enchantable_staffs.effect;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.util.NBTUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ChargeEscalation extends MobEffect {

    public ChargeEscalation() {
        super(MobEffectCategory.BENEFICIAL, 4058623);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.hasEffect(StaffEffects.CHARGE_BREAKDOWN.get())) {
            NBTUtils.setMaxCharge(entity, StaffConfig.chargeMaxStarting.get() * (amplifier + 2));
        }
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
