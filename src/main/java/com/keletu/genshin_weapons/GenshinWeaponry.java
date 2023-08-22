package com.keletu.genshin_weapons;

import com.keletu.genshin_weapons.network.PlayerVariables;
import com.keletu.genshin_weapons.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(
        modid = GenshinWeaponry.MOD_ID,
        name = GenshinWeaponry.MOD_NAME,
        version = GenshinWeaponry.VERSION
)
public class GenshinWeaponry {

    public static final String MOD_ID = "genshin_weapons";
    public static final String MOD_NAME = "DeathMatch: Genshin Weaponry Kedition";
    public static final String VERSION = "0.1";

    @CapabilityInject(PlayerVariables.class)
    public static Capability<PlayerVariables> PLAYER = null;

    private static final GenshinWeaponry instance = new GenshinWeaponry();

    @SidedProxy(clientSide = "com.keletu.genshin_weapons.proxy.ClientProxy", serverSide = "com.keletu.genshin_weapons.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {

        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
            proxy.registerItems(event);
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void modelRegistryEvent(ModelRegistryEvent event) {
            proxy.modelRegistryEvent(event);
        }
    }

    public PlayerVariables get(EntityPlayer player)
    {
        return player.getCapability(PLAYER, null);
    }

    public static GenshinWeaponry getInstance()
    {
        return instance;
    }
}
