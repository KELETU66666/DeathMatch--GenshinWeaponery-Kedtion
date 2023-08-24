package com.keletu.genshin_weapons;

import com.keletu.genshin_weapons.network.IPlayerProvider;
import com.keletu.genshin_weapons.network.ParticleNote;
import com.keletu.genshin_weapons.network.PlayerVariables;
import com.keletu.genshin_weapons.proxy.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = GenshinWeaponry.MOD_ID)
public class Events {

    @SubscribeEvent
    public static void PlayerConstructingEvent(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof EntityPlayer)
        {
            IPlayerProvider provider = new IPlayerProvider(new PlayerVariables());
            event.addCapability(new ResourceLocation("genshin_weapons", "player_variables"),  provider);
        }
    }

    @SubscribeEvent
    public static void onPlayerUpdate(LivingEvent.LivingUpdateEvent event)
    {
        if ((event.getEntityLiving() instanceof EntityPlayer))
        {
            PlayerVariables playerVariables = GenshinWeaponry.getInstance().get((EntityPlayer) event.getEntityLiving());

            if (playerVariables != null)
            {
                playerVariables.onUpdate();
            }

            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack stack = player.getHeldItemMainhand();

            if(stack.getItem() == CommonProxy.TheAlleyFlesh && playerVariables != null)
            {
                if(player.hurtTime > 0)
                    playerVariables.ability_cd = 100;
            }
         //
         //   if (playerVariables != null && stack.getItem() == CommonProxy.SkyriderSword && player.experienceLevel != getPlayerLevel(player) && playerVariables.skyrider_ab_dur == 0) {
         //       double s = /*1.024*/2 + 0.006 * CommonProxy.SkyriderSword.getTier(stack);
         //       if(playerVariables.skyrider_ab_dur > 0)
         //           player.setVelocity(s * player.motionX, s * player.motionY, s * player.motionZ);
         //       playerVariables.skyrider_ab_dur = 300;
         //   }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event)
    {
        PlayerVariables original = GenshinWeaponry.getInstance().get(event.getOriginal());

        PlayerVariables newPlayer = GenshinWeaponry.getInstance().get(event.getEntityPlayer());

        NBTTagCompound data = new NBTTagCompound();

        if (original instanceof PlayerVariables)
        {
            original.saveNBTData(data);

            if (newPlayer instanceof PlayerVariables)
            {
                newPlayer.loadNBTData(data);
            }
        }
    }

    @SubscribeEvent
    public static void TravelerHandySwordTickEvent(PlayerPickupXpEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if(player.getHeldItemMainhand().getItem() == CommonProxy.TravelerHandySword)
        {
            player.heal(1 + (float) (CommonProxy.TravelerHandySword.getTier(player.getHeldItemMainhand()) - 1) / 4);
        }
    }
    @SubscribeEvent
    public static void SwordTickEvent(LivingHurtEvent event)
    {
        if(event.getEntity() == null || event.getSource().getTrueSource() == null || !(event.getSource().getTrueSource() instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
        ItemStack stack = player.getHeldItemMainhand();
        PlayerVariables playerVariables = GenshinWeaponry.getInstance().get(player);

        if(stack.getItem() == CommonProxy.BlackcliffLongsword)
        {
            int i = 0;

            if(playerVariables.blackcliff_st1 > 0)
                i += 1;
            if(playerVariables.blackcliff_st2 > 0)
                i += 1;
            if(playerVariables.blackcliff_st3 > 0)
                i += 1;

            event.setAmount(event.getAmount() + (float) (event.getAmount() * i * 0.12 * ((CommonProxy.BlackcliffLongsword.getTier(stack) + 3) / 4)));
        }

        if(stack.getItem() == CommonProxy.HarbingerOfDawn && player.getHealth() / player.getMaxHealth() > 0.9 && player.getEntityWorld().rand.nextInt(100) < 14 * ((CommonProxy.HarbingerOfDawn.getTier(stack) +3) / 4))
        {
            event.setAmount(event.getAmount() * (float) ((1 + 0.18 * (1 + (CommonProxy.HarbingerOfDawn.getTier(stack) - 1) / 4))));
        }

        if(stack.getItem() == CommonProxy.FilletBlade && player.getEntityWorld().rand.nextInt(100) < 50 && playerVariables.ability_cd == 0)
        {
            event.setAmount(event.getAmount() * (float) ((2.4 + 0.4  * (CommonProxy.FilletBlade.getTier(stack) - 1))));
            GenshinWeaponry.getInstance().get(player).ability_cd = 3 * (480 - (CommonProxy.FilletBlade.getTier(stack) - 1) * 40);
            playerVariables.ability_cd = 480;
        }

        if(stack.getItem() == CommonProxy.ColdSteel && ((player.getEntityWorld().getBiome(player.getPosition()).isSnowyBiome() || event.getEntity().isWet())))
        {
            event.setAmount(event.getAmount() + (float) (event.getAmount() * (0.12 * ((CommonProxy.HarbingerOfDawn.getTier(stack) +3 ) / 4))));
        }

        if(stack.getItem() == CommonProxy.LionsRoar && (player.getEntityWorld().getBiome(player.getPosition()).getTemperature(player.getPosition()) > 1.0 || event.getEntity().isBurning() || event.getEntity().isInLava()))
        {
            event.setAmount(event.getAmount() * (float) (event.getAmount() * (0.16 + CommonProxy.HarbingerOfDawn.getTier(stack) * 0.04)));
        }

        if(stack.getItem() == CommonProxy.PrototypeRancour)
        {
            if(playerVariables.prototype_rancour_stack < 4 && playerVariables.prototype_rancour_cd == 0)
            {
                playerVariables.prototype_rancour_cd = 34;
                playerVariables.prototype_rancour_stack += 1;
            }

            event.setAmount(event.getAmount() + event.getAmount() * (float) (playerVariables.prototype_rancour_stack * (0.04 * ((CommonProxy.PrototypeRancour.getTier(stack) +3 ) / 4))));
            playerVariables.prototype_rancour_dur = 120;
        }

        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack1 = player.inventory.getStackInSlot(i);
            if (stack.getItem() == CommonProxy.DarkIronSword && stack1.isItemEqual(new ItemStack(Items.REDSTONE)) && playerVariables.dark_iron_sword_ab_dur == 0) {
                playerVariables.dark_iron_sword_ab_dur = 240;
                stack1.setCount(stack1.getCount() - 1);
            }
        }

        if(stack.getItem() == CommonProxy.DarkIronSword && playerVariables.dark_iron_sword_ab_dur > 0)
            event.setAmount((float) (event.getAmount() * 1.2));

        if(stack.getItem() == CommonProxy.FavoniusSword && player.getEntityWorld().rand.nextInt(110 - 10 * CommonProxy.FavoniusSword.getTier(stack)) < 60 && playerVariables.ability_cd == 0 && !player.onGround && !player.isInWater())
        {
            if(!player.getEntityWorld().isRemote)
            {
                player.getEntityWorld().spawnEntity(new EntityXPOrb(player.getEntityWorld(), event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, 3));
                player.getEntityWorld().spawnEntity(new EntityXPOrb(player.getEntityWorld(), event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, 3));
                player.getEntityWorld().spawnEntity(new EntityXPOrb(player.getEntityWorld(), event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, 3));
            }

            playerVariables.ability_cd = 120;
        }

        if(stack.getItem() == CommonProxy.SwordOfDescension && player.getEntityWorld().rand.nextInt(100) < 49 && playerVariables.ability_cd == 0)
        {
            Vec3d vec3d = new Vec3d(player.posX, player.posY, player.posZ);
            List<Entity> entities = player.getEntityWorld().getEntitiesWithinAABB(Entity.class, (new AxisAlignedBB(vec3d, vec3d)).grow(4.5D), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.getDistanceSqToCenter(new BlockPos(player.posX, player.posY, player.posZ)))).collect(Collectors.toList());
            for (Entity entityiterator : entities)
            {
                if(entityiterator != player)
                    entityiterator.attackEntityFrom(DamageSource.GENERIC, event.getAmount() * 2);
            }
            playerVariables.ability_cd = 400;
        }

        if(stack.getItem() == CommonProxy.SwordOfDescension && player.getEntityWorld().rand.nextInt(100) < 49 && playerVariables.ability_cd == 0)
        {
            Vec3d vec3d = new Vec3d(player.posX, player.posY, player.posZ);
            List<Entity> entities = player.getEntityWorld().getEntitiesWithinAABB(Entity.class, (new AxisAlignedBB(vec3d, vec3d)).grow(4.5D), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.getDistanceSqToCenter(new BlockPos(player.posX, player.posY, player.posZ)))).collect(Collectors.toList());
            for (Entity entityiterator : entities)
            {
                if(entityiterator != player)
                    entityiterator.attackEntityFrom(DamageSource.GENERIC, event.getAmount() * 2);
            }
            playerVariables.ability_cd = 400;
        }

        if(stack.getItem() == CommonProxy.TheAlleyFlesh && playerVariables.ability_cd == 0)
        {
            event.setAmount((event.getAmount() + (float) (event.getAmount() * 0.12 * (CommonProxy.TheAlleyFlesh.getTier(stack)+ 3) / 4)));
        }

        if(stack.getItem() == CommonProxy.TheBlackSword)
        {
            event.setAmount((float) (event.getAmount() + event.getAmount() * 0.2 * (CommonProxy.TheBlackSword.getTier(stack)+ 3) / 4));
            if(player.getEntityWorld().rand.nextInt(110 - 10 * CommonProxy.TheBlackSword.getTier(stack)) < 60 && playerVariables.ability_cd == 0) {
                player.heal((float) (event.getAmount() * 0.6));
                playerVariables.ability_cd = 240;
            }
        }

        if(stack.getItem() == CommonProxy.RoyalSword)
        {
            if((player.onGround || player.isInWater()) && playerVariables.royal_stacks < 5)
                playerVariables.royal_stacks += 1;
            if(!player.onGround && !player.isInWater() && playerVariables.royal_stacks > 0)
            {
                event.setAmount(event.getAmount() + (float) (event.getAmount() * 0.08 * (CommonProxy.TheBlackSword.getTier(stack)+ 3) / 4 * playerVariables.royal_stacks));
                playerVariables.royal_stacks = 0;
            }
        }

        if(stack.getItem() == CommonProxy.TheFlute)
        {
            if(playerVariables.the_flute_ab_stack < 5  && playerVariables.the_flute_ab_dur == 0)
            {
                playerVariables.the_flute_ab_stack += 1;
                playerVariables.the_flute_ab_dur = 30;
            }

            if(playerVariables.the_flute_ab_stack == 5) {
                Vec3d vec3d = new Vec3d(player.posX, player.posY, player.posZ);
                List<Entity> entities = player.getEntityWorld().getEntitiesWithinAABB(Entity.class, (new AxisAlignedBB(vec3d, vec3d)).grow(4.5D), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.getDistanceSqToCenter(new BlockPos(player.posX, player.posY, player.posZ)))).collect(Collectors.toList());
                for (Entity entityiterator : entities) {
                    if (entityiterator != player) {
                        entityiterator.attackEntityFrom(DamageSource.MAGIC, event.getAmount() * (CommonProxy.TheFlute.getTier(stack) + 3) / 4);

                        if(player.world.isRemote)
                            for (int l = 0; l < 15; l++) {
                                double d = event.getEntity().posX + (player.world.rand.nextFloat() - 0.5D) * 6.0D;
                                double d1 = event.getEntity().posY + (player.world.rand.nextFloat() - 0.5D) * 6.0D;
                                double d2 = event.getEntity().posZ + (player.world.rand.nextFloat() - 0.5D) * 6.0D;
                                double d3 = (player.world.rand.nextFloat() - 0.5D) * 0.5D;
                                double d4 = (player.world.rand.nextFloat() - 0.5D) * 0.5D;
                                double d5 = (player.world.rand.nextFloat() - 0.5D) * 0.5D;
                                ParticleNote entityPurpleFX = new ParticleNote(player.world, d, d1, d2, d3, d4, d5);
                                (FMLClientHandler.instance().getClient()).effectRenderer.addEffect(entityPurpleFX);
                        }
                    }
                }
                playerVariables.the_flute_ab_stack = 0;
            }
        }
    }

  //  private static int getPlayerLevel(EntityPlayer player)
  //  {
  //      return player.experienceLevel;
  //  }

    @SubscribeEvent
    public static void BlackcliffSwordTickEvent(LivingDeathEvent event)
    {
        if(event.getEntity() == null || event.getSource().getTrueSource() == null || !(event.getSource().getTrueSource() instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
        ItemStack stack = player.getHeldItemMainhand();
        PlayerVariables playerVariables = GenshinWeaponry.getInstance().get(player);

        if(stack.getItem() == CommonProxy.BlackcliffLongsword && playerVariables.blackcliff_st1 == 0)
        {
            playerVariables.blackcliff_st1 = 601;
        }
        else if(stack.getItem() == CommonProxy.BlackcliffLongsword && playerVariables.blackcliff_st2 == 0)
        {
            playerVariables.blackcliff_st2 = 601;
        }
        else if(stack.getItem() == CommonProxy.BlackcliffLongsword && playerVariables.blackcliff_st3 == 0)
        {
            playerVariables.blackcliff_st3 = 601;
        }

        if(stack.getItem() == CommonProxy.BlackcliffLongsword && playerVariables.blackcliff_stacks < 0)
        {
            playerVariables.blackcliff_st3 = 0;
        }
    }
}
