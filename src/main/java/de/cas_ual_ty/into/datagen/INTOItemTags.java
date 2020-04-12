package de.cas_ual_ty.into.datagen;

import de.cas_ual_ty.into.INTOMaterial;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;

public class INTOItemTags extends ItemTagsProvider
{
    public INTOItemTags(DataGenerator generatorIn)
    {
        super(generatorIn);
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
            this.getBuilder(material.block_item_tag).add(material.getBlock().asItem());
            this.getBuilder(material.ore_item_tag).add(material.getOre().asItem());
            this.getBuilder(material.ingot_tag).add(material.getIngot());
            this.getBuilder(material.nugget_tag).add(material.getNugget());
        }
    }
}
