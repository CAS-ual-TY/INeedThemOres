package de.cas_ual_ty.into.datagen;

import de.cas_ual_ty.into.INTOMaterial;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class INTOBlockTags extends BlockTagsProvider
{
    public INTOBlockTags(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, existingFileHelper);
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
            this.getOrCreateBuilder(material.block_tag).add(material.getBlock());
            this.getOrCreateBuilder(material.ore_tag).add(material.getOre());
        }
    }
}
