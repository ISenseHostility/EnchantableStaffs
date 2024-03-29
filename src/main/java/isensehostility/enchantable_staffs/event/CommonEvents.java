package isensehostility.enchantable_staffs.event;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import isensehostility.enchantable_staffs.ai.goal.TargetUnfriendlyGoal;
import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.effect.StaffEffects;
import isensehostility.enchantable_staffs.enchantment.StaffEnchantments;
import isensehostility.enchantable_staffs.enums.EStaffModifiers;
import isensehostility.enchantable_staffs.item.Staff;
import isensehostility.enchantable_staffs.network.ChargeUpdatePacket;
import isensehostility.enchantable_staffs.network.ElementalEfficiencyUpdatePacket;
import isensehostility.enchantable_staffs.network.MaxChargeUpdatePacket;
import isensehostility.enchantable_staffs.network.StaffPacketHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

import static isensehostility.enchantable_staffs.StaffUtils.*;

@Mod.EventBusSubscriber(modid = EnchantableStaffs.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {

    @SubscribeEvent
    public static void onJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player player && !hasChargeData(player)) {
            generateChargeData(player);
            setMaxCharge(player, StaffConfig.chargeMaxStarting.get());
        }

        if ((entity instanceof Monster monster) && getFriendly(monster)) {
            monster.targetSelector.removeAllGoals();
            monster.targetSelector.addGoal(0, new TargetUnfriendlyGoal<>(monster, Monster.class, false));
        }
    }

    @SubscribeEvent
    public static void onLeave(EntityLeaveLevelEvent event) {
        if (event.getEntity() instanceof DragonFireball dragonFireball) {
            if (getFromStaff(dragonFireball)) {
                long[] direction = getDirection(dragonFireball);
                int enchantmentLevel = getEnchantmentLevel(dragonFireball, StaffEnchantments.DRACONIC_FIREBALL.get());

                Fireball fireball = new LargeFireball(event.getLevel(), (LivingEntity) dragonFireball.getOwner(), direction[0] / 100000000D, direction[1] / 100000000D, direction[2] / 100000000D, enchantmentLevel);
                fireball.setPos(dragonFireball.getX(), dragonFireball.getY(), dragonFireball.getZ());
                event.getLevel().addFreshEntity(fireball);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START && event.side.isServer()) {
            Player player = event.player;

            addCharge(player);

            ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);

            runModifierLogic(mainHand, player);
            runModifierLogic(offHand, player);

            if (isHoldingStaff(player)) {
                StaffPacketHandler.INSTANCE.send(
                        PacketDistributor.TRACKING_CHUNK
                                .with(() ->
                                        player.level.getChunkAt(player.blockPosition())
                                ),
                        new ChargeUpdatePacket(getCharge(player))
                );
            }

            if (player.hasEffect(StaffEffects.ELEMENTAL_EFFICIENCY.get())) {
                StaffPacketHandler.INSTANCE.send(
                        PacketDistributor.TRACKING_CHUNK
                                .with(() ->
                                        player.level.getChunkAt(player.blockPosition())
                                ),
                        new ElementalEfficiencyUpdatePacket(getElementalEfficiency(player))
                );
            }

            if (getMagicalStrengtheningTime(player) > 0) {
                reduceMagicalStrengtheningTime(player);
            } else {
                NonNullList<ItemStack> allItems = NonNullList.create();
                allItems.addAll(player.getInventory().offhand);
                allItems.addAll(player.getInventory().items);
                allItems.addAll(player.getInventory().armor);

                ItemStack strengthenedStack = null;

                for (ItemStack stack : allItems) {
                    if (EnchantmentHelper.getItemEnchantmentLevel(StaffEnchantments.MAGICAL_STRENGTHENING_WEAPON.get(), stack) != 0) {
                        strengthenedStack = stack;
                    }
                }

                if (strengthenedStack != null) {
                    Map<Enchantment, Integer> enchantments = new HashMap<>();

                    for (Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.getEnchantments(strengthenedStack).entrySet()) {
                        if (entry.getKey() != StaffEnchantments.MAGICAL_STRENGTHENING_WEAPON.get()) {
                            enchantments.put(entry.getKey(), entry.getValue());
                        }
                    }

                    EnchantmentHelper.setEnchantments(enchantments, strengthenedStack);
                }
            }

            if (getSpectralWingsTime(player) > 0) {
                reduceSpectralWingsTime(player);
            } else {
                NonNullList<ItemStack> allItems = NonNullList.create();
                allItems.addAll(player.getInventory().offhand);
                allItems.addAll(player.getInventory().items);
                allItems.addAll(player.getInventory().armor);

                ItemStack spectralWingsStack = null;

                for (ItemStack stack : allItems) {
                    if (getIsSpectralWings(stack)) {
                        spectralWingsStack = stack;
                    }
                }

                if (spectralWingsStack != null) {
                    spectralWingsStack.shrink(1);
                    player.setItemSlot(EquipmentSlot.CHEST, ItemStack.of((CompoundTag) getChestplateData(player)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        Entity source = event.getSource().getDirectEntity();
        if (source instanceof Player player && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof Staff) {
            setHurtByStaff(event.getEntity(), true);
        }
        if (source instanceof Player player && player.hasEffect(StaffEffects.CRITICAL.get())) {
            int enchantmentLevel = player.getEffect(StaffEffects.CRITICAL.get()).getAmplifier() + 1;
            int chance = player.getRandom().nextInt(6) + 1;

            if (chance <= enchantmentLevel) {
                invokeCriticalVisuals(player);
                event.setAmount(event.getAmount() * 1.5F + (enchantmentLevel * 1.2F));
            }
        }

        if (source instanceof Player player && EnchantmentHelper.getItemEnchantmentLevel(StaffEnchantments.MAGICAL_STRENGTHENING_WEAPON.get(), player.getItemInHand(InteractionHand.MAIN_HAND)) != 0) {
            int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(StaffEnchantments.MAGICAL_STRENGTHENING_WEAPON.get(), player.getItemInHand(InteractionHand.MAIN_HAND));

            event.setAmount(event.getAmount() + 1.0F + (enchantmentLevel * 1.5F));
        }
    }

    @SubscribeEvent
    public static void onKnockbackApplied(LivingKnockBackEvent event) {
        if (getHurtByStaff(event.getEntity())) {
            event.setStrength((event.getOriginalStrength() * StaffConfig.staffKnockback.get().floatValue()));
            setHurtByStaff(event.getEntity(), false);
        }
    }

    @SubscribeEvent
    public static void onPotionEffectExpired(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();
        int maxChargeDefault = StaffConfig.chargeMaxStarting.get();

        if (event.getEffectInstance().getEffect() == StaffEffects.CHARGE_ESCALATION.get()) {
            if (!entity.level.isClientSide) {
                entity.addEffect(new MobEffectInstance(StaffEffects.CHARGE_BREAKDOWN.get(), 1200, event.getEffectInstance().getAmplifier()));

                StaffPacketHandler.INSTANCE.send(
                        PacketDistributor.TRACKING_CHUNK
                                .with(() ->
                                        entity.level.getChunkAt(entity.blockPosition())
                                ),
                        new MaxChargeUpdatePacket(getMaxCharge(entity))
                );
            }

            if (getCharge(entity) > getMaxCharge(entity)) {
                setCharge(entity, getMaxCharge(entity));
            }
        }

        if (event.getEffectInstance().getEffect() == StaffEffects.CHARGE_BREAKDOWN.get()) {
            if (!entity.level.isClientSide) {
                setMaxCharge(entity, maxChargeDefault);
                if (getCharge(entity) > maxChargeDefault) {
                    setCharge(entity, maxChargeDefault);
                }

                StaffPacketHandler.INSTANCE.send(
                        PacketDistributor.TRACKING_CHUNK
                                .with(() ->
                                        entity.level.getChunkAt(entity.blockPosition())
                                ),
                        new MaxChargeUpdatePacket(maxChargeDefault)
                );
            }
        }
    }
}
