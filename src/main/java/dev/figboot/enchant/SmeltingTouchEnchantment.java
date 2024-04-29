package dev.figboot.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

public class SmeltingTouchEnchantment extends Enchantment {
    public SmeltingTouchEnchantment(Enchantment.Properties props) {
        super(props);
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Enchantments.SILK_TOUCH;
    }
}
