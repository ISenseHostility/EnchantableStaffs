package isensehostility.enchantable_staffs.enums;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;

public enum EElement {
    NATURE(new TranslatableComponent("element.enchantable_staffs.name.nature"), ChatFormatting.DARK_GREEN),
    FIRE(new TranslatableComponent("element.enchantable_staffs.name.fire"), ChatFormatting.RED),
    ENDER(new TranslatableComponent("element.enchantable_staffs.name.ender"), ChatFormatting.DARK_PURPLE),
    WATER(new TranslatableComponent("element.enchantable_staffs.name.water"), ChatFormatting.BLUE),
    ICE(new TranslatableComponent("element.enchantable_staffs.name.ice"), ChatFormatting.AQUA),
    LIGHTNING(new TranslatableComponent("element.enchantable_staffs.name.lightning"), ChatFormatting.GRAY),
    PURE(new TranslatableComponent("element.enchantable_staffs.name.pure"), ChatFormatting.WHITE),
    UNDEAD(new TranslatableComponent("element.enchantable_staffs.name.undead"), ChatFormatting.DARK_RED),
    SUMMON(new TranslatableComponent("element.enchantable_staffs.name.summon"), ChatFormatting.LIGHT_PURPLE),
    NONE(new TranslatableComponent("element.enchantable_staffs.name.none"), ChatFormatting.DARK_GRAY);

    private TranslatableComponent name;
    private ChatFormatting color;

    EElement(TranslatableComponent name, ChatFormatting color) {
        this.name = name;
        this.color = color;
    }

    public TranslatableComponent getName() {
        return name;
    }

    public ChatFormatting getColor() {
        return color;
    }
}
