package com.keletu.genshin_weapons.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void modelRegistryEvent(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(TravelerHandySword, 0, new ModelResourceLocation(TravelerHandySword.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BlackcliffLongsword, 0, new ModelResourceLocation(BlackcliffLongsword.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(HarbingerOfDawn, 0, new ModelResourceLocation(HarbingerOfDawn.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(FilletBlade, 0, new ModelResourceLocation(FilletBlade.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ColdSteel, 0, new ModelResourceLocation(ColdSteel.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(SkyriderSword, 0, new ModelResourceLocation(SkyriderSword.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(DarkIronSword, 0, new ModelResourceLocation(DarkIronSword.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(PrototypeRancour, 0, new ModelResourceLocation(PrototypeRancour.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(FavoniusSword, 0, new ModelResourceLocation(FavoniusSword.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(SwordOfDescension, 0, new ModelResourceLocation(SwordOfDescension.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(TheAlleyFlesh, 0, new ModelResourceLocation(TheAlleyFlesh.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(TheBlackSword, 0, new ModelResourceLocation(TheBlackSword.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(LionsRoar, 0, new ModelResourceLocation(LionsRoar.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(RoyalSword, 0, new ModelResourceLocation(RoyalSword.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(TheFlute, 0, new ModelResourceLocation(TheFlute.getRegistryName(), "inventory"));

    }
}
