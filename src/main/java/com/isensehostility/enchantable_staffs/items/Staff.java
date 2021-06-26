package com.isensehostility.enchantable_staffs.items;

import com.isensehostility.enchantable_staffs.EnchantableStaffs;
import com.isensehostility.enchantable_staffs.init.EnchantmentRegistry;
import com.isensehostility.enchantable_staffs.init.ItemRegistry;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import java.util.*;

public abstract class Staff extends Item {

    World warpWorld;
    BlockPos warpPos;

    public Staff(Properties builder) {
        super(builder);
        this.warpWorld = null;
        this.warpPos = null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        Map enchant = EnchantmentHelper.getEnchantments(stack);

        if (enchant.get(EnchantmentRegistry.EXPLOSION) != null && Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("tooltip.enchantable_staffs.explosion"));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        Map enchant = EnchantmentHelper.getEnchantments(stack);

        // if enchantment = lightning bolt
        if (enchant.get(EnchantmentRegistry.LIGHTNING_BOLT) != null) {
            RayTraceResult result = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY, 100);
            BlockPos pos = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY,100).getPos();

            if (result.getType() == RayTraceResult.Type.BLOCK) {
                LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);
                lightning.setPosition(pos.getX(), pos.getY(), pos.getZ());
                worldIn.addEntity(lightning);

                stack.damageItem(5, playerIn, playerEntity -> {
                });
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
            }
        }

        // if enchantment = lightning circle
        else if (enchant.get(EnchantmentRegistry.LIGHTNING_CIRCLE) != null) {
            int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.LIGHTNING_CIRCLE, stack);

            // gets coordinates
            double posY = playerIn.getPosY();
            double posX = playerIn.getPosX();
            double posZ = playerIn.getPosZ();

            double posX1 = playerIn.getPosX() + 10;
            double posX2 = playerIn.getPosX() - 10;
            double posX3 = playerIn.getPosX() + 5;
            double posX4 = playerIn.getPosX() - 5;

            double posZ1 = playerIn.getPosZ() + 10;
            double posZ2 = playerIn.getPosZ() - 10;
            double posZ3 = playerIn.getPosZ() + 5;
            double posZ4 = playerIn.getPosZ() - 5;

            // creates the lightning bolts
            LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);
            lightning.setPosition(posX1, posY, posZ);

            LightningBoltEntity lightning2 = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);
            lightning2.setPosition(posX, posY, posZ1);

            LightningBoltEntity lightning3 = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);
            lightning3.setPosition(posX2, posY, posZ);

            LightningBoltEntity lightning4 = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);
            lightning4.setPosition(posX, posY, posZ2);

            LightningBoltEntity lightning5 = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);
            lightning5.setPosition(posX3, posY, posZ3);

            LightningBoltEntity lightning6 = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);
            lightning6.setPosition(posX4, posY, posZ4);

            LightningBoltEntity lightning7 = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);
            lightning7.setPosition(posX3, posY, posZ4);

            LightningBoltEntity lightning8 = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);
            lightning8.setPosition(posX4, posY, posZ3);

            // summons 8 lightning bolts
            worldIn.addEntity(lightning);
            worldIn.addEntity(lightning2);
            worldIn.addEntity(lightning3);
            worldIn.addEntity(lightning4);
            worldIn.addEntity(lightning5);
            worldIn.addEntity(lightning6);
            worldIn.addEntity(lightning7);
            worldIn.addEntity(lightning8);

            stack.damageItem(8, playerIn, playerEntity -> {
            });

            return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
        }

        // if enchantment = fireball
        else if (enchant.get(EnchantmentRegistry.FIREBALL) != null) {
            int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.FIREBALL, stack);

            // direction player is looking towards
            double yaw = Math.toRadians(playerIn.rotationYaw);
            double pitch = Math.toRadians(playerIn.rotationPitch);

            // calculates direction for the fireball
            double xDirection = -Math.sin(yaw) * Math.cos(pitch);
            double yDirection = -Math.sin(pitch);
            double zDirection = Math.cos(yaw) * Math.cos(pitch);

            // calculates starting coords for fireball
            double x = playerIn.getPosX() + xDirection * 2;
            double y = playerIn.getPosY() + 1.5 + yDirection;
            double z = playerIn.getPosZ() + zDirection * 2;
            FireballEntity fireballentity = new FireballEntity(worldIn, playerIn, xDirection, yDirection, zDirection);
            fireballentity.setPosition(x, y, z);


            // checks level
            if (level == 1) {
                // spawns fireball with specified power
                fireballentity.explosionPower = 1;
                worldIn.addEntity(fireballentity);
                // subtracts 3 durability
                stack.damageItem(5, playerIn, playerEntity -> {
                });
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
            }

            else if (level == 2) {
                fireballentity.explosionPower = 2;
                worldIn.addEntity(fireballentity);
                // subtracts 1 durability
                stack.damageItem(7, playerIn, playerEntity -> {
                });
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
            }
        }

        // if enchant = explosion
        if (enchant.get(EnchantmentRegistry.EXPLOSION) != null) {
            int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.EXPLOSION, stack);
            RayTraceResult result = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY,100);
            BlockPos pos = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY,100).getPos();

            // checks if the ray hits a block
            if (result.getType() == RayTraceResult.Type.BLOCK) {

                // checks enchantment level
                if (level == 1) {
                    // creates an explosion at the ray traced coords
                    worldIn.createExplosion(playerIn, pos.getX(), pos.getY(), pos.getZ(), 1.5F, Explosion.Mode.BREAK);
                    stack.damageItem(4, playerIn, playerEntity -> {
                    });
                    return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
                }

                else if (level == 2) {
                    worldIn.createExplosion(playerIn, pos.getX(), pos.getY(), pos.getZ(), 2.5F, Explosion.Mode.BREAK);
                    stack.damageItem(6, playerIn, playerEntity -> {
                    });
                    return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
                }

                else if (level == 3) {
                    worldIn.createExplosion(playerIn, pos.getX(), pos.getY(), pos.getZ(), 3.5F, Explosion.Mode.BREAK);
                    stack.damageItem(7, playerIn, playerEntity -> {
                    });
                    return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
                }

                else if (level == 4) {
                    worldIn.createExplosion(playerIn, pos.getX(), pos.getY(), pos.getZ(), 4.5F, Explosion.Mode.BREAK);
                    stack.damageItem(9, playerIn, playerEntity -> {
                    });
                    return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
                }

                else if (level == 5) {
                    worldIn.createExplosion(playerIn, pos.getX(), pos.getY(), pos.getZ(), 5.5F, Explosion.Mode.BREAK);
                    stack.damageItem(12, playerIn, playerEntity -> {
                    });
                    return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
                }
            }
        }

        // if enchantment = create water
        else if (enchant.get(EnchantmentRegistry.CREATE_WATER) != null) {
            RayTraceResult result = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY,100);
            BlockPos rayPos = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY,100).getPos();
            int posY = rayPos.getY() + 1;
            BlockPos pos = new BlockPos(rayPos.getX(), posY, rayPos.getZ());

            if (result.getType() == RayTraceResult.Type.BLOCK) {
                worldIn.setBlockState(pos, Blocks.WATER.getDefaultState());
                stack.damageItem(3, playerIn, playerEntity -> {
                });
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
            }
        }

        // if enchantment = necromancy
        else if (enchant.get(EnchantmentRegistry.NECROMANCY) != null) {
            RayTraceResult result = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY,100);
            BlockPos pos = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY,100).getPos();
            BlockPos pos1 = playerIn.getPosition();

            if (result.getType() == RayTraceResult.Type.BLOCK) {
                // reddit 50/50
                if ((Math.random() * 100) < 50) {
                    // creates a zombie, sets position at raytraced coords, summons the zombie
                    ZombieEntity zombie = new ZombieEntity(worldIn);
                    zombie.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
                    worldIn.addEntity(zombie);
                }
                else {
                    SkeletonEntity skeleton = new SkeletonEntity(EntityType.SKELETON, worldIn);
                    skeleton.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
                    worldIn.addEntity(skeleton);
                }

            stack.damageItem(6, playerIn, playerEntity -> {
            });
            return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
            }
        }

        // if enchant = warp
        else if (enchant.get(EnchantmentRegistry.WARP) != null) {

            if (playerIn.getEntityWorld().isRemote) {
                if (Screen.hasShiftDown()) {
                    this.warpWorld = playerIn.getEntityWorld();
                    this.warpPos = new BlockPos(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ());

                    stack.damageItem(16, playerIn, playerEntity -> {
                    });
                    return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
                }

                else if (this.warpPos != null) {
                    if (playerIn.getEntityWorld() == this.warpWorld) {
                        playerIn.setWorld(this.warpWorld);
                        playerIn.setPosition(this.warpPos.getX(), this.warpPos.getY(), this.warpPos.getZ());

                        stack.damageItem(7, playerIn, playerEntity -> {
                        });
                        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
                    }
                }
            }

            else {
                return new ActionResult<ItemStack>(ActionResultType.FAIL, stack);
            }
        }

        //if enchantment = teleport
        if (enchant.get(EnchantmentRegistry.TELEPORT) != null) {
            RayTraceResult result = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY,100);
            BlockPos pos = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY,100).getPos();

            if (result.getType() == RayTraceResult.Type.BLOCK) {
                // sets player pos 1 block above raytrace so you dont spawn with your legs in the ground
                playerIn.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());

                stack.damageItem(7, playerIn, playerEntity -> {
                });
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
            }
        }

        //if enchantment = weather alteration
        if (enchant.get(EnchantmentRegistry.WEATHER_ALTERATION) != null) {
            if (!worldIn.isRemote) {
                ServerWorld serverWorld = (ServerWorld) worldIn;

                if (Math.random() * 100 < 40) {
                    // clear weather
                    serverWorld.func_241113_a_(0, 0, false, false);

                    stack.damageItem(4, playerIn, playerEntity -> {
                    });
                    return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
                }
                else if (Math.random() * 100 < 20) {
                    // rain weather
                    serverWorld.func_241113_a_(0, 0, true, false);

                    stack.damageItem(4, playerIn, playerEntity -> {
                    });
                    return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
                }
                else {
                    // rain & thunder weather
                    serverWorld.func_241113_a_(0, 0, true, true);

                    stack.damageItem(4, playerIn, playerEntity -> {
                    });
                    return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
                }
            }
        }

        // mega scuffed
        else if (enchant.get(EnchantmentRegistry.RING_OF_FIRE) != null) {

            // to spam in a few lines
            BlockState fire = Blocks.FIRE.getDefaultState();

            // gets coordinates
            double posY = playerIn.getPosY();
            double posX = playerIn.getPosX();
            double posZ = playerIn.getPosZ();

            double posX1 = posX + 4;
            double posX2 = posX - 4;
            double posX3 = posX + 1;
            double posX4 = posX - 1;
            double posX5 = posX + 3;
            double posX6 = posX - 3;
            double posX7 = posX + 2;
            double posX8 = posX - 2;

            double posZ1 = posZ + 4;
            double posZ2 = posZ - 4;
            double posZ3 = posZ + 1;
            double posZ4 = posZ - 1;
            double posZ5 = posZ + 3;
            double posZ6 = posZ - 3;
            double posZ7 = posZ + 2;
            double posZ8 = posZ - 2;

            BlockPos pos1 = new BlockPos(posX1, posY, posZ3);
            BlockPos pos2 = new BlockPos(posX1, posY, posZ4);
            BlockPos pos3 = new BlockPos(posX2, posY, posZ3);
            BlockPos pos4 = new BlockPos(posX2, posY, posZ4);
            BlockPos pos5 = new BlockPos(posX1, posY, posZ);
            BlockPos pos6 = new BlockPos(posX2, posY, posZ);
            BlockPos pos7 = new BlockPos(posX, posY, posZ2);
            BlockPos pos8 = new BlockPos(posX, posY, posZ1);
            BlockPos pos9 = new BlockPos(posX3, posY, posZ1);
            BlockPos pos10 = new BlockPos(posX4, posY, posZ1);
            BlockPos pos11 = new BlockPos(posX3, posY, posZ2);
            BlockPos pos12 = new BlockPos(posX4, posY, posZ2);
            BlockPos pos13 = new BlockPos(posX5, posY, posZ7);
            BlockPos pos14 = new BlockPos(posX5, posY, posZ5);
            BlockPos pos15 = new BlockPos(posX7, posY, posZ5);
            BlockPos pos16 = new BlockPos(posX5, posY, posZ8);
            BlockPos pos17 = new BlockPos(posX5, posY, posZ6);
            BlockPos pos18 = new BlockPos(posX7, posY, posZ6);
            BlockPos pos19 = new BlockPos(posX8, posY, posZ6);
            BlockPos pos20 = new BlockPos(posX6, posY, posZ6);
            BlockPos pos21 = new BlockPos(posX6, posY, posZ8);
            BlockPos pos22 = new BlockPos(posX8, posY, posZ5);
            BlockPos pos23 = new BlockPos(posX6, posY, posZ5);
            BlockPos pos24 = new BlockPos(posX6, posY, posZ7);

            for (int i = 0; i < 24; i++) {
                if (i == 0) {
                    stack.damageItem(5, playerIn, playerEntity -> {
                    });
                    if (!(worldIn.getBlockState(new BlockPos(pos1.getX(), posY - 1, pos1.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos1.getX(), posY, pos1.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos1, fire);
                        }
                    }
                }

                else if (i == 1) {
                    if (!(worldIn.getBlockState(new BlockPos(pos2.getX(), posY - 1, pos2.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos2.getX(), posY, pos2.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos2, fire);
                        }
                    }
                }

                else if (i == 2) {
                    if (!(worldIn.getBlockState(new BlockPos(pos3.getX(), posY - 1, pos3.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos3.getX(), posY, pos3.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos3, fire);
                        }
                    }
                }

                else if (i == 3) {
                    if (!(worldIn.getBlockState(new BlockPos(pos4.getX(), posY - 1, pos4.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos4.getX(), posY, pos4.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos4, fire);
                        }
                    }
                }

                else if (i == 4) {
                    if (!(worldIn.getBlockState(new BlockPos(pos5.getX(), posY - 1, pos5.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos5.getX(), posY, pos5.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos5, fire);
                        }
                    }
                }

                else if (i == 5) {
                    if (!(worldIn.getBlockState(new BlockPos(pos6.getX(), posY - 1, pos6.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos6.getX(), posY, pos6.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos6, fire);
                        }
                    }
                }

                else if (i == 6) {
                    if (!(worldIn.getBlockState(new BlockPos(pos7.getX(), posY - 1, pos7.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos7.getX(), posY, pos7.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos7, fire);
                        }
                    }
                }

                else if (i == 7) {
                    if (!(worldIn.getBlockState(new BlockPos(pos8.getX(), posY - 1, pos8.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos8.getX(), posY, pos8.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos8, fire);
                        }
                    }
                }

                else if (i == 8) {
                    if (!(worldIn.getBlockState(new BlockPos(pos9.getX(), posY - 1, pos9.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos9.getX(), posY, pos9.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos9, fire);
                        }
                    }
                }

                else if (i == 9) {
                    if (!(worldIn.getBlockState(new BlockPos(pos10.getX(), posY - 1, pos10.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos10.getX(), posY, pos10.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos10, fire);
                        }
                    }
                }

                else if (i == 10) {
                    if (!(worldIn.getBlockState(new BlockPos(pos11.getX(), posY - 1, pos11.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos11.getX(), posY, pos11.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos11, fire);
                        }
                    }
                }

                else if (i == 11) {
                    if (!(worldIn.getBlockState(new BlockPos(pos12.getX(), posY - 1, pos12.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos12.getX(), posY, pos12.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos12, fire);
                        }
                    }
                }

                else if (i == 12) {
                    if (!(worldIn.getBlockState(new BlockPos(pos13.getX(), posY - 1, pos13.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos13.getX(), posY, pos13.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos13, fire);
                        }
                    }
                }

                else if (i == 13) {
                    if (!(worldIn.getBlockState(new BlockPos(pos14.getX(), posY - 1, pos14.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos14.getX(), posY, pos14.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos14, fire);
                        }
                    }
                }

                else if (i == 14) {
                    if (!(worldIn.getBlockState(new BlockPos(pos15.getX(), posY - 1, pos15.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos15.getX(), posY, pos15.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos15, fire);
                        }
                    }
                }

                else if (i == 15) {
                    if (!(worldIn.getBlockState(new BlockPos(pos16.getX(), posY - 1, pos16.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos16.getX(), posY, pos16.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos16, fire);
                        }
                    }
                }

                else if (i == 16) {
                    if (!(worldIn.getBlockState(new BlockPos(pos17.getX(), posY - 1, pos17.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos17.getX(), posY, pos17.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos17, fire);
                        }
                    }
                }

                else if (i == 17) {
                    if (!(worldIn.getBlockState(new BlockPos(pos18.getX(), posY - 1, pos18.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos18.getX(), posY, pos18.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos18, fire);
                        }
                    }
                }

                else if (i == 18) {
                    if (!(worldIn.getBlockState(new BlockPos(pos19.getX(), posY - 1, pos19.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos19.getX(), posY, pos19.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos19, fire);
                        }
                    }
                }

                else if (i == 19) {
                    if (!(worldIn.getBlockState(new BlockPos(pos20.getX(), posY - 1, pos20.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos20.getX(), posY, pos20.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos20, fire);
                        }
                    }
                }

                else if (i == 20) {
                    if (!(worldIn.getBlockState(new BlockPos(pos21.getX(), posY - 1, pos21.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos21.getX(), posY, pos21.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos21, fire);
                        }
                    }
                }

                else if (i == 21) {
                    if (!(worldIn.getBlockState(new BlockPos(pos22.getX(), posY - 1, pos22.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos22.getX(), posY, pos22.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos22, fire);
                        }
                    }
                }

                else if (i == 22) {
                    if (!(worldIn.getBlockState(new BlockPos(pos23.getX(), posY - 1, pos23.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos23.getX(), posY, pos23.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos23, fire);
                        }
                    }
                }

                else if (i == 23) {
                    if (!(worldIn.getBlockState(new BlockPos(pos24.getX(), posY - 1, pos24.getZ())).getBlock() instanceof AirBlock)) {
                        if (worldIn.getBlockState(new BlockPos(pos24.getX(), posY, pos24.getZ())).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos24, fire);
                            return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
                        }
                    }
                }
            }
        }

        // no enchant
        else {
            return new ActionResult<ItemStack>(ActionResultType.FAIL, stack);
        }
        return new ActionResult<ItemStack>(ActionResultType.FAIL, stack);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    // raytrace method with custom reach
    protected static BlockRayTraceResult rayTrace(World worldIn, PlayerEntity player, RayTraceContext.FluidMode fluidMode, double range) {
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        Vector3d vec3d = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vector3d vec3d1 = vec3d.add((double)f6 * range, (double)f5 * range, (double)f7 * range);
        return worldIn.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.type == EnchantableStaffs.STAFF;
    }

}
