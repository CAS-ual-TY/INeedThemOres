package de.cas_ual_ty.into.datagen;

import de.cas_ual_ty.into.INTOOre;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;

public class INTOItemModels extends ItemModelProvider
{
    public INTOItemModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, existingFileHelper);
    }
    
    @Override
    public String getName()
    {
        return "I Need Them Ores Item Models";
    }
    
    @Override
    protected void registerModels()
    {
        for(INTOOre material : INTOOre.MATERIALS)
        {
            this.getBuilder(material.getIngotRL().getPath())
                .parent(new UncheckedModelFile("item/generated"))
                .texture("layer0", this.modLoc("item/" + material.getIngotRL().getPath()));
            
            this.getBuilder(material.getNuggetRL().getPath())
                .parent(new UncheckedModelFile("item/generated"))
                .texture("layer0", this.modLoc("item/" + material.getNuggetRL().getPath()));
            
            this.getBuilder(material.getBlockRL().getPath())
                .parent(this.getExistingFile(this.modLoc("block/" + material.getBlockRL().getPath())));
            
            this.getBuilder(material.getOreRL().getPath())
                .parent(this.getExistingFile(this.modLoc("block/" + material.getOreRL().getPath())));
        }
    }
}
