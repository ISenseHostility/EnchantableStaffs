package isensehostility.enchantable_staffs.enums;

import isensehostility.enchantable_staffs.item.Staff;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Comparator;

public enum EStaffModifiers {
    DIAMOND(0, "diamond"),
    EMERALD(1, "emerald"),
    LAPIS(2, "lapis");

    private final int id;
    private final String name;

    EStaffModifiers(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static EStaffModifiers getFromStack(ItemStack stack) {
        if (stack.getTag() != null) {
            int stackId = stack.getTag().getInt(Staff.TAG_STAFF_MODIFIER);
            return BY_ID[stackId];
        }
        return null;
    }

    protected static final EStaffModifiers[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(EStaffModifiers::getId)).toArray(num -> new EStaffModifiers[num]);

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
