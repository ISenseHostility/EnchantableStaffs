package isensehostility.enchantable_staffs.effect;

import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import static isensehostility.enchantable_staffs.StaffUtils.*;

public class ChargeInsertion extends MobEffect {

    public ChargeInsertion() {
        super(MobEffectCategory.BENEFICIAL, 4058623);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity p_19462_, @Nullable Entity p_19463_, LivingEntity entity, int amplifier, double p_19466_) {
        if (!entity.hasEffect(StaffEffects.CHARGE_SICKNESS.get())) {
            setCharge(entity, Mth.clamp(getCharge(entity) + (250 * (amplifier + 1)), 0, getMaxCharge(entity)));
            entity.addEffect(new MobEffectInstance(StaffEffects.CHARGE_SICKNESS.get(), 600, amplifier));
        }
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }
}
