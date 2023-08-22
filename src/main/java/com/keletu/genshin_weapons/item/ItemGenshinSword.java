package com.keletu.genshin_weapons.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.keletu.genshin_weapons.GenshinWeaponry;
import com.keletu.genshin_weapons.proxy.CommonProxy;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemGenshinSword extends ItemSword {

    private float attackDamage;
    private int tier = 1;

    public ItemGenshinSword(String name, int attackDamage) {
        super(ToolMaterial.DIAMOND);
        this.setMaxDamage(1561);
        this.attackDamage = attackDamage;
        setRegistryName(GenshinWeaponry.MOD_ID, name);
        setTranslationKey(name);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }

    public void setTier(int tier){
        this.tier = tier;
    }

    public int getTier(){
        return this.tier;
    }

    @Override
    public float getAttackDamage()
    {
        return attackDamage;
    }

    @Override
    public int getItemEnchantability()
    {
        return 2;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack pItemStack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        super.addInformation(pItemStack, worldIn, list, flagIn);
        list.add("keletu.genshinweaponry.genshin_sword.namwe");
        list.add("keletu.genshinweaponry.genshin_sword.tooltip");
    }

    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
}
