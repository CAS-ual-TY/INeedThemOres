package de.cas_ual_ty.into.datagen;

import java.util.function.Consumer;

import de.cas_ual_ty.into.INTOMaterial;
import de.cas_ual_ty.into.INeedThemOres;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class INTORecipes extends RecipeProvider
{
    public INTORecipes(DataGenerator generatorIn)
    {
        super(generatorIn);
    }
    
    @Override
    public String getName()
    {
        return "I Need Them Ores Recipes";
    }
    
    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        for(INTOMaterial material : INTOMaterial.MATERIALS)
        {
            ShapedRecipeBuilder.shapedRecipe(material.getBlock())
                .patternLine("XXX")
                .patternLine("XXX")
                .patternLine("XXX")
                .key('X', material.ingot_tag)
                .setGroup(INeedThemOres.MOD_ID + "_" + material.name)
                .addCriterion("has_" + material.ingotRL.getPath(), this.hasItem(material.ingot_tag))
                .build(consumer);
            
            ShapedRecipeBuilder.shapedRecipe(material.getIngot())
                .patternLine("XXX")
                .patternLine("XXX")
                .patternLine("XXX")
                .key('X', material.nugget_tag)
                .setGroup(INeedThemOres.MOD_ID + "_" + material.name)
                .addCriterion("has_" + material.nuggetRL.getPath(), this.hasItem(material.nugget_tag))
                .build(consumer, new ResourceLocation(material.modId, material.ingotRL.getPath() + "_from_nuggets"));
            
            ShapelessRecipeBuilder.shapelessRecipe(material.getIngot(), 9)
                .addIngredient(material.block_item_tag)
                .setGroup(INeedThemOres.MOD_ID + "_" + material.name)
                .addCriterion("has_" + material.blockRL.getPath(), this.hasItem(material.block_item_tag))
                .build(consumer, new ResourceLocation(material.modId, material.ingotRL.getPath() + "_from_" + material.blockRL.getPath()));
            
            ShapelessRecipeBuilder.shapelessRecipe(material.getNugget(), 9)
                .addIngredient(material.ingot_tag)
                .setGroup(INeedThemOres.MOD_ID + "_" + material.name)
                .addCriterion("has_" + material.ingotRL.getPath(), this.hasItem(material.ingot_tag))
                .build(consumer);
            
            CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(material.ore_item_tag), material.getIngot(), 1.0F, 200).addCriterion("has_" + material.oreRL.getPath(), this.hasItem(material.getOre())).build(consumer);
            CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(material.ore_item_tag), material.getIngot(), 1.0F, 100).addCriterion("has_" + material.oreRL.getPath(), this.hasItem(material.getOre())).build(consumer, new ResourceLocation(material.modId, material.ingotRL.getPath() + "_from_blasting"));
        }
    }
}
