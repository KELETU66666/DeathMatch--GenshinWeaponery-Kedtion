package com.keletu.genshin_weapons.network;

import net.minecraft.world.World;

public class ParticleNote extends net.minecraft.client.particle.ParticleNote
{

	public ParticleNote(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed)
	{
		super(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);

		this.particleBlue = 0;
		this.particleRed = 255;
		this.particleGreen = 215;
	}

}