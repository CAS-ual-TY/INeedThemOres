package de.cas_ual_ty.into.datagen;

import de.cas_ual_ty.into.INTOOre;
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
    protected void registerStatesAndModels()
    {
        for(INTOOre material : INTOOre.MATERIALS)
        {
            this.getVariantBuilder(material.getBlock())
                .forAllStates(state -> ConfiguredModel.builder()
                    .modelFile(this.models().getExistingFile(this.modLoc("block/" + material.getBlockRL().getPath())))
                    .build());
            
            this.getVariantBuilder(material.getOre())
                .forAllStates(state -> ConfiguredModel.builder()
                    .modelFile(this.models().getExistingFile(this.modLoc("block/" + material.getOreRL().getPath())))
                    .build());
        }
    }
}
