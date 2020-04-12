package de.cas_ual_ty.into.datagen;

import de.cas_ual_ty.into.INTOMaterial;
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
        for(INTOMaterial material : INTOMaterial.MATERIALS)
        {
            this.getBuilder(material.ingotRL.getPath())
                .parent(new UncheckedModelFile("item/generated"))
                .texture("layer0", this.modLoc("item/" + material.ingotRL.getPath()));
            
            this.getBuilder(material.nuggetRL.getPath())
                .parent(new UncheckedModelFile("item/generated"))
                .texture("layer0", this.modLoc("item/" + material.nuggetRL.getPath()));
            
            this.getBuilder(material.blockRL.getPath())
                .parent(this.getExistingFile(this.modLoc("block/" + material.blockRL.getPath())));
            
            this.getBuilder(material.oreRL.getPath())
                .parent(this.getExistingFile(this.modLoc("block/" + material.oreRL.getPath())));
        }
    }
}
