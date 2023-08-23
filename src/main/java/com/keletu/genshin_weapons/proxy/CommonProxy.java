
package com.keletu.genshin_weapons.proxy;


import com.keletu.genshin_weapons.item.ItemGenshinSword;
import com.keletu.genshin_weapons.network.IPlayerStorage;
import com.keletu.genshin_weapons.network.PlayerVariables;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommonProxy {

    public static ItemGenshinSword TravelerHandySword = new ItemGenshinSword("travelers_handy_sword", 6);
    public static ItemGenshinSword BlackcliffLongsword = new ItemGenshinSword("blackcliff_longsword", 7);
    public static ItemGenshinSword HarbingerOfDawn = new ItemGenshinSword("harbinger_of_dawn", 5);
    public static ItemGenshinSword FilletBlade = new ItemGenshinSword("fillet_blade", 5);
    public static ItemGenshinSword ColdSteel = new ItemGenshinSword("cold_steel", 5);
    public static ItemGenshinSword SkyriderSword = new ItemGenshinSword("skyrider_sword", 4);
    public static ItemGenshinSword DarkIronSword = new ItemGenshinSword("dark_iron_sword", 5);
    public static ItemGenshinSword PrototypeRancour = new ItemGenshinSword("prototype_rancour", 7);
    public static ItemGenshinSword FavoniusSword = new ItemGenshinSword("favonius_sword", 5);
    public static ItemGenshinSword SwordOfDescension = new ItemGenshinSword("sword_of_descension", 5);
    public static ItemGenshinSword TheAlleyFlesh = new ItemGenshinSword("the_alley_flash", 8);
    public static ItemGenshinSword TheBlackSword = new ItemGenshinSword("the_black_sword", 6);
    public static ItemGenshinSword LionsRoar = new ItemGenshinSword("lions_roar", 6);
    public static ItemGenshinSword RoyalSword = new ItemGenshinSword("royal_sword", 6);
    public static ItemGenshinSword TheFlute = new ItemGenshinSword("the_flute", 6);


    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(PlayerVariables.class, new IPlayerStorage(), () -> null);
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }


    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(TravelerHandySword);
        event.getRegistry().register(BlackcliffLongsword);
        event.getRegistry().register(HarbingerOfDawn);
        event.getRegistry().register(FilletBlade);
        event.getRegistry().register(ColdSteel);
        event.getRegistry().register(SkyriderSword);
        event.getRegistry().register(DarkIronSword);
        event.getRegistry().register(PrototypeRancour);
        event.getRegistry().register(FavoniusSword);
        event.getRegistry().register(SwordOfDescension);
        event.getRegistry().register(TheAlleyFlesh);
        event.getRegistry().register(TheBlackSword);
        event.getRegistry().register(LionsRoar);
        event.getRegistry().register(RoyalSword);
        event.getRegistry().register(TheFlute);
    }

    @SideOnly(Side.CLIENT)
    public void modelRegistryEvent(ModelRegistryEvent event) {

    }
}
