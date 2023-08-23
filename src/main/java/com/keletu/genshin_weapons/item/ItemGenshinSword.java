package com.keletu.genshin_weapons.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.keletu.genshin_weapons.GenshinWeaponry;
import com.keletu.genshin_weapons.proxy.CommonProxy;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemGenshinSword extends ItemSword {

    private float attackDamage;

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

    public static NBTTagCompound getNBT(ItemStack stack) {
        if(!stack.hasTagCompound()) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setInteger("upgrade", 1);
            stack.setTagCompound(nbtTagCompound);
        }
        return stack.getTagCompound();
    }

    public void setInt(ItemStack stack, String tag, int i) {
        getNBT(stack).setInteger(tag, i);
    }

    public int getTier(ItemStack stack) {
        return getNBT(stack).getInteger("upgrade");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack mainStack = player.getHeldItemMainhand();
        ItemStack offStack = player.getHeldItemOffhand();
            if(mainStack.getItem() == offStack.getItem() && getTier(offStack) < 2 && getTier(mainStack) < 5) {
                offStack.setCount(offStack.getCount() - 1);
                setInt(mainStack, "upgrade", getTier(mainStack) + 1);
                return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
            }

        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
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
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, list, flagIn);
        if(stack.getItem() == CommonProxy.HarbingerOfDawn) {
            list.add(TextFormatting.BLUE + I18n.format("keletu.genshinweaponry.harbinger_of_dawn.name") + " R" + getTier(stack));
            list.add(I18n.format("keletu.genshinweaponry.harbinger_of_dawn.tooltip1"));
            list.add(I18n.format("keletu.genshinweaponry.harbinger_of_dawn.tooltip2"));
        }
        if(stack.getItem() == CommonProxy.TravelerHandySword) {
            list.add(TextFormatting.BLUE + I18n.format("keletu.genshinweaponry.traveler_handy_sword.name") + " R" + getTier(stack));
            list.add(I18n.format("keletu.genshinweaponry.traveler_handy_sword.tooltip1"));
        }
        if(stack.getItem() == CommonProxy.FilletBlade) {
            list.add(TextFormatting.BLUE + I18n.format("keletu.genshinweaponry.fillet_blade.name") + " R" + getTier(stack));
            list.add(I18n.format("keletu.genshinweaponry.fillet_blade.tooltip1"));
            list.add(I18n.format("keletu.genshinweaponry.fillet_blade.tooltip2"));
        }
        if(stack.getItem() == CommonProxy.ColdSteel) {
            list.add(TextFormatting.BLUE + I18n.format("keletu.genshinweaponry.cold_steel.name") + " R" + getTier(stack));
            list.add(I18n.format("keletu.genshinweaponry.cold_steel.tooltip1"));
        }
        if(stack.getItem() == CommonProxy.SkyriderSword) {
            list.add(TextFormatting.DARK_RED + "WIP");
        }
        if(stack.getItem() == CommonProxy.DarkIronSword) {
            list.add(TextFormatting.BLUE + I18n.format("keletu.genshinweaponry.dark_iron_sword.name") + " R" + 1);
            list.add(I18n.format("keletu.genshinweaponry.dark_iron_sword.tooltip1"));
        }
        if(stack.getItem() == CommonProxy.TheFlute) {
            list.add(TextFormatting.DARK_PURPLE + I18n.format("keletu.genshinweaponry.the_flute.name") + " R" + getTier(stack));
            list.add(I18n.format("keletu.genshinweaponry.the_flute.tooltip1"));
            list.add(I18n.format("keletu.genshinweaponry.the_flute.tooltip2"));
            list.add(I18n.format("keletu.genshinweaponry.the_flute.tooltip3"));
            list.add(I18n.format("keletu.genshinweaponry.the_flute.tooltip4"));
        }
        if(stack.getItem() == CommonProxy.PrototypeRancour) {
            list.add(TextFormatting.DARK_PURPLE + I18n.format("keletu.genshinweaponry.prototype_rancour.name") + " R" + getTier(stack));
            list.add(new TextComponentString("keletu.genshinweaponry.prototype_rancour.tooltip1").getText());
            list.add(I18n.format("keletu.genshinweaponry.prototype_rancour.tooltip2"));
        }
        if(stack.getItem() == CommonProxy.BlackcliffLongsword) {
            list.add(TextFormatting.DARK_PURPLE + I18n.format("keletu.genshinweaponry.blackcliff_longsword.name") + " R" + getTier(stack));
            list.add(I18n.format("keletu.genshinweaponry.blackcliff_longsword.tooltip1"));
            list.add(I18n.format("keletu.genshinweaponry.blackcliff_longsword.tooltip2"));
        }
        if(stack.getItem() == CommonProxy.FavoniusSword) {
            list.add(TextFormatting.DARK_PURPLE + I18n.format("keletu.genshinweaponry.favonius_sword.name") + " R" + getTier(stack));
            list.add(I18n.format("keletu.genshinweaponry.favonius_sword.tooltip1"));
            list.add(I18n.format("keletu.genshinweaponry.favonius_sword.tooltip2"));
        }
        if(stack.getItem() == CommonProxy.SwordOfDescension) {
            list.add(TextFormatting.DARK_PURPLE + I18n.format("keletu.genshinweaponry.sword_of_descension.name") + " R" + 1);
            list.add(I18n.format("keletu.genshinweaponry.sword_of_descension.tooltip1"));
            list.add(I18n.format("keletu.genshinweaponry.sword_of_descension.tooltip2"));
        }
        if(stack.getItem() == CommonProxy.TheAlleyFlesh) {
            list.add(TextFormatting.DARK_PURPLE + I18n.format("keletu.genshinweaponry.the_alley_flash.name") + " R" + getTier(stack));
            list.add(I18n.format("keletu.genshinweaponry.the_alley_flash.tooltip1"));
            list.add(I18n.format("keletu.genshinweaponry.the_alley_flash.tooltip2"));
        }
        if(stack.getItem() == CommonProxy.TheBlackSword) {
            list.add(TextFormatting.DARK_PURPLE + I18n.format("keletu.genshinweaponry.the_black_sword.name") + " R" + getTier(stack));
            list.add(I18n.format("keletu.genshinweaponry.the_black_sword.tooltip1"));
            list.add(I18n.format("keletu.genshinweaponry.the_black_sword.tooltip2"));
            list.add(I18n.format("keletu.genshinweaponry.the_black_sword.tooltip3"));
        }
        if(stack.getItem() == CommonProxy.LionsRoar) {
            list.add(TextFormatting.DARK_PURPLE + I18n.format("keletu.genshinweaponry.lions_roar.name") + " R" + getTier(stack));
            list.add(I18n.format("keletu.genshinweaponry.lions_roar.tooltip1"));
        }
        if(stack.getItem() == CommonProxy.RoyalSword) {
            list.add(TextFormatting.DARK_PURPLE + I18n.format("keletu.genshinweaponry.royal_sword.name") + " R" + getTier(stack));
            list.add(I18n.format("keletu.genshinweaponry.royal_sword.tooltip1"));
            list.add(I18n.format("keletu.genshinweaponry.royal_sword.tooltip2"));
        }
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
