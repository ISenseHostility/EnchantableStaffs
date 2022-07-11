package isensehostility.enchantable_staffs.enums;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.List;

public enum EElement {
    NATURE(new TranslatableComponent("element.enchantable_staffs.name.nature"), ChatFormatting.DARK_GREEN, 0),
    FIRE(new TranslatableComponent("element.enchantable_staffs.name.fire"), ChatFormatting.RED, 1),
    ENDER(new TranslatableComponent("element.enchantable_staffs.name.ender"), ChatFormatting.DARK_PURPLE, 2),
    WATER(new TranslatableComponent("element.enchantable_staffs.name.water"), ChatFormatting.BLUE, 3),
    ICE(new TranslatableComponent("element.enchantable_staffs.name.ice"), ChatFormatting.AQUA, 4),
    LIGHTNING(new TranslatableComponent("element.enchantable_staffs.name.lightning"), ChatFormatting.GRAY, 5),
    PURE(new TranslatableComponent("element.enchantable_staffs.name.pure"), ChatFormatting.WHITE, 6),
    UNDEAD(new TranslatableComponent("element.enchantable_staffs.name.undead"), ChatFormatting.DARK_RED, 7),
    SUMMON(new TranslatableComponent("element.enchantable_staffs.name.summon"), ChatFormatting.LIGHT_PURPLE, 8),
    NONE(new TranslatableComponent("element.enchantable_staffs.name.none"), ChatFormatting.DARK_GRAY, 9);

    private final TranslatableComponent name;
    private final ChatFormatting color;
    private final int id;

    EElement(TranslatableComponent name, ChatFormatting color, int id) {
        this.name = name;
        this.color = color;
        this.id = id;
    }

    public TranslatableComponent getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ChatFormatting getColor() {
        return color;
    }

    public static EElement getById(int id) {
        for(EElement element : values()) {
            if(element.id == id)
                return element;
        }
        throw new IllegalArgumentException("No element with id '" + id + "' exists.");
    }

    public List<EElement> getEfficiencyAllowedDefault() {
        return List.of(NATURE, FIRE, ENDER, WATER, ICE, LIGHTNING, PURE, UNDEAD, SUMMON);
    }
}
