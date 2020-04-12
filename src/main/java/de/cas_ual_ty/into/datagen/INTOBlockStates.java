package de.cas_ual_ty.into.datagen;

import de.cas_ual_ty.into.INTOMaterial;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class INTOBlockStates extends BlockStateProvider
{
    public INTOBlockStates(DataGenerator gen, String modid, ExistingFileHelper exFileHelper)
    {
        super(gen, modid, exFileHelper);
    }
    
    @Override
    public String getName()
    {
        return "I Need Them Ores Block States";
    }
    
    @Override
    protected void registerStatesAndModels()
    {
        for(INTOMaterial material : INTOMaterial.MATERIALS)
        {
            this.getVariantBuilder(material.getBlock())
                .forAllStates(state -> ConfiguredModel.builder()
                    .modelFile(this.models().getExistingFile(this.modLoc("block/" + material.blockRL.getPath())))
                    .build());
            
            this.getVariantBuilder(material.getOre())
                .forAllStates(state -> ConfiguredModel.builder()
                    .modelFile(this.models().getExistingFile(this.modLoc("block/" + material.oreRL.getPath())))
                    .build());
        }
    }
}
