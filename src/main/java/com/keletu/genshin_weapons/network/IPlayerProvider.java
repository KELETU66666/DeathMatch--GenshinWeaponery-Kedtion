package com.keletu.genshin_weapons.network;

import com.keletu.genshin_weapons.GenshinWeaponry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class IPlayerProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound>
{

	private final PlayerVariables playerVariables;

	public IPlayerProvider(PlayerVariables playerVariables)
	{
		this.playerVariables = playerVariables;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == GenshinWeaponry.PLAYER;
	}

	@Override
	@SuppressWarnings("unchecked") // Yay...
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if (capability == GenshinWeaponry.PLAYER)
		{
			return (T) this.playerVariables;
		}

		return null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound output = new NBTTagCompound();

		if (this.playerVariables != null)
		{
			this.playerVariables.saveNBTData(output);
		}

		return output;
	}

	@Override
	public void deserializeNBT(NBTTagCompound input)
	{
		if (this.playerVariables != null)
		{
			this.playerVariables.loadNBTData(input);
		}
	}

}