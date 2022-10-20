package isensehostility.enchantable_staffs.ai.goal;

import isensehostility.enchantable_staffs.util.NBTUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;

public class TargetUnfriendlyGoal<T extends LivingEntity> extends NearestAttackableTargetGoal {

    public TargetUnfriendlyGoal(Mob attacker, Class<T> target, boolean hasToSee) {
        super(attacker, target, hasToSee);
    }

    @Override
    protected void findTarget() {
        this.target = this.mob.level.getNearestEntity(this.mob.level.getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()),
                (entity) -> {
                    return (!(entity instanceof LivingEntity livingEntity) || !NBTUtils.getFriendly(livingEntity)) && !(entity instanceof Creeper);
                }),
                this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    }
}
