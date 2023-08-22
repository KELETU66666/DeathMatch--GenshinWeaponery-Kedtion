package com.keletu.genshin_weapons.item;

public class AnvilRecipe
{
	final ItemGenshinSword sword;
	final int cost;

	public AnvilRecipe(ItemGenshinSword sword, int cost)
	{
		this.cost = cost;
		this.sword = sword;
	}

	public ItemGenshinSword getOutput()
	{
		sword.setTier(sword.getTier() + 1);
		return sword;
	}

	public ItemGenshinSword getSecond()
	{
		if(sword.getTier() == 1)
			return sword;

		return null;
	}

	public ItemGenshinSword getFirst()
	{
		return sword;
	}

	public int getCost()
	{
		return cost;
	}
}