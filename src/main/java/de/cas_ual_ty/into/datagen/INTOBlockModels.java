package de.cas_ual_ty.into.datagen;

import de.cas_ual_ty.into.INTOOre;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;

public class INTOBlockModels extends BlockModelProvider
{
    public INTOBlockModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, existingFileHelper);
    }
    
    @Override
    public String getName()
    {
        return "I Need Them Ores Block Models";
    }
    
    @Override
    protected void registerModels()
    {
        for(INTOOre material : INTOOre.MATERIALS)
        {
            this.getBuilder(material.getBlockRL().getPath())
                .parent(new UncheckedModelFile("block/cube_all"))
                .texture("all", this.modLoc("block/" + material.getBlockRL().getPath()));
            
            this.getBuilder(material.getOreRL().getPath())
                .parent(new UncheckedModelFile("block/cube_all"))
                .texture("all", this.modLoc("block/" + material.getOreRL().getPath()));
        }
    }
}
