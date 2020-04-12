package de.cas_ual_ty.into.datagen;

import de.cas_ual_ty.into.INTOMaterial;
import de.cas_ual_ty.into.INeedThemOres;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class INTOLanguage extends LanguageProvider
{
    public INTOLanguage(DataGenerator gen, String modid)
    {
        super(gen, modid, "en_us");
    }
    
    @Override
    public String getName()
    {
        return "I Need Them Ores Language Files";
    }
    
    @Override
    protected void addTranslations()
    {
        this.add("itemGroup." + INeedThemOres.MOD_ID, "I Need Them Ores");
        
        for(INTOMaterial material : INTOMaterial.MATERIALS)
        {
            this.add(material.getIngot(), material.lang + " Ingot");
            this.add(material.getNugget(), material.lang + " Nugget");
            this.add(material.getBlock(), "Block of " + material.lang);
            this.add(material.getOre(), material.lang + " Ore");
        }
    }
}
