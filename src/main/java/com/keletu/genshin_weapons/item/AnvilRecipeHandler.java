package com.keletu.genshin_weapons.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class AnvilRecipeHandler
{
	static List<AnvilRecipe> anvilRecipes = new ArrayList<>();

	public static List<AnvilRecipe> getAllRecipes()
	{
		return anvilRecipes;
	}

	public static void addAnvilRecipe(ItemGenshinSword input1, int cost)
	{
		anvilRecipes.add(new AnvilRecipe(input1, cost));
	}
}