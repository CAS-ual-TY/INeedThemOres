package de.cas_ual_ty.into.datagen;

import java.util.function.Consumer;

import de.cas_ual_ty.into.INTOOre;
import de.cas_ual_ty.into.INeedThemOres;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.util.ResourceLocation;

public class INTORecipes extends RecipeProvider
{
    protected final String modid;
    
    public INTORecipes(DataGenerator generatorIn, String modid)
    {
        super(generatorIn);
        this.modid = modid;
    }
    
    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        for(INTOOre material : INTOOre.MATERIALS)
        {
            ShapedRecipeBuilder.shapedRecipe(material.getBlock())
                .patternLine("XXX")
                .patternLine("XXX")
                .patternLine("XXX")
                .key('X', material.getIngot())
                .setGroup(INeedThemOres.MOD_ID + "_" + material.name)
                .addCriterion(material.getIngotRL().getPath(), InventoryChangeTrigger.Instance.forItems(material.getIngot()))
                .build(consumer, new ResourceLocation(this.modid, material.name + "_ingot_block"));
            
            ShapedRecipeBuilder.shapedRecipe(material.getIngot())
                .patternLine("XXX")
                .patternLine("XXX")
                .patternLine("XXX")
                .key('X', material.getNugget())
                .setGroup(INeedThemOres.MOD_ID + "_" + material.name)
                .addCriterion(material.getNuggetRL().getPath(), InventoryChangeTrigger.Instance.forItems(material.getNugget()))
                .build(consumer, new ResourceLocation(this.modid, material.name + "_nugget_ingot"));
            
            ShapelessRecipeBuilder.shapelessRecipe(material.getIngot(), 9)
                .addIngredient(material.getBlock())
                .setGroup(INeedThemOres.MOD_ID + "_" + material.name)
                .addCriterion(material.getBlockRL().getPath(), InventoryChangeTrigger.Instance.forItems(material.getBlock()))
                .build(consumer, new ResourceLocation(this.modid, material.name + "_block_ingot"));
            
            ShapelessRecipeBuilder.shapelessRecipe(material.getNugget(), 9)
                .addIngredient(material.getIngot())
                .setGroup(INeedThemOres.MOD_ID + "_" + material.name)
                .addCriterion(material.getIngotRL().getPath(), InventoryChangeTrigger.Instance.forItems(material.getIngot()))
                .build(consumer, new ResourceLocation(this.modid, material.name + "_ingot_nugget"));
        }
    }
}
