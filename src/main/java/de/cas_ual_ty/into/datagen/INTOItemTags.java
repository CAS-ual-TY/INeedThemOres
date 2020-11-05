package de.cas_ual_ty.into.datagen;

import de.cas_ual_ty.into.INTOMaterial;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class INTOItemTags extends ItemTagsProvider
{
    public INTOItemTags(DataGenerator generator, BlockTagsProvider blockTagsProvider, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, blockTagsProvider, modid, existingFileHelper);
    }
    
    @Override
    public String getName()
    {
        return "I Need Them Ores Item Tags";
    }
    
    @Override
    protected void registerTags()
    {
        for(INTOMaterial material : INTOMaterial.MATERIALS)
        {
            //            this.copy(material.block_tag, material.block_item_tag); Does not work. Why?
            //            this.copy(material.ore_tag, material.ore_item_tag); Does not work. Why?
            this.getOrCreateBuilder(material.block_item_tag).add(material.getBlock().asItem());
            this.getOrCreateBuilder(material.ore_item_tag).add(material.getOre().asItem());
            this.getOrCreateBuilder(material.ingot_tag).add(material.getIngot());
            this.getOrCreateBuilder(material.nugget_tag).add(material.getNugget());
        }
    }
}
