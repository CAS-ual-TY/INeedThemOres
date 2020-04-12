package de.cas_ual_ty.into.datagen;

import de.cas_ual_ty.into.INTOMaterial;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;

public class INTOBlockTags extends BlockTagsProvider
{
    public INTOBlockTags(DataGenerator generatorIn)
    {
        super(generatorIn);
    }
    
    @Override
    public String getName()
    {
        return "I Need Them Ores Block Tags";
    }
    
    @Override
    protected void registerTags()
    {
        for(INTOMaterial material : INTOMaterial.MATERIALS)
        {
            this.getBuilder(material.block_tag).add(material.getBlock());
            this.getBuilder(material.ore_tag).add(material.getOre());
        }
    }
}
