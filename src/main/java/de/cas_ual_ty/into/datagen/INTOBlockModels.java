package de.cas_ual_ty.into.datagen;

import de.cas_ual_ty.into.INTOMaterial;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

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
        for(INTOMaterial material : INTOMaterial.MATERIALS)
        {
            this.getBuilder(material.blockRL.getPath())
                .parent(new UncheckedModelFile("block/cube_all"))
                .texture("all", this.modLoc("block/" + material.blockRL.getPath()));
            
            this.getBuilder(material.oreRL.getPath())
                .parent(new UncheckedModelFile("block/cube_all"))
                .texture("all", this.modLoc("block/" + material.oreRL.getPath()));
        }
    }
}
