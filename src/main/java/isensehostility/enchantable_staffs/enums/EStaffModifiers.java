package isensehostility.enchantable_staffs.enums;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import isensehostility.enchantable_staffs.StaffUtils;
import isensehostility.enchantable_staffs.item.Staff;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum EStaffModifiers {
    NONE(0, Component.translatable("modifier.staff.none"), Items.BARRIER),
    COLD_AFFINITY(1, Component.translatable("modifier.staff.cold_affinity"), Items.BLUE_ICE),
    HEAT_AFFINITY(2, Component.translatable("modifier.staff.heat_affinity"), Items.SHROOMLIGHT);

    private final int id;
    private final MutableComponent name;
    private final Item material;

    EStaffModifiers(int id, MutableComponent name, Item material) {
        this.id = id;
        this.name = name;
        this.material = material;
    }

    public static EStaffModifiers getFromStack(ItemStack stack) {
        if (stack.getTag() != null) {
            int stackId = stack.getTag().getInt(Staff.TAG_STAFF_MODIFIER);
            if (stackId != 0) {
                return BY_ID[stackId];
            } else {
                return null;
            }
        }
        return null;
    }

    public static void setToStack(ItemStack stack, EStaffModifiers modifier) {
        stack.getOrCreateTag().putInt(Staff.TAG_STAFF_MODIFIER, modifier.getId());
    }

    protected static final EStaffModifiers[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(EStaffModifiers::getId)).toArray(num -> new EStaffModifiers[num]);

    public static EStaffModifiers getByMaterial(Item material) {
        for (EStaffModifiers modifier : EStaffModifiers.values()) {
            if (modifier.getMaterial() == material) {
                return modifier;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public MutableComponent getName() {
        return name;
    }

    public Item getMaterial() {
        return material;
    }
}
