package de.cas_ual_ty.into;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(INeedThemOres.MOD_ID)
public class INeedThemOres
{
    public static final String MOD_ID = "ineedthemores";
    
    public static INTOItemGroup itemGroup;
    
    public INeedThemOres()
    {
        this.createMaterials();
        
        INeedThemOres.itemGroup = new INTOItemGroup(INeedThemOres.MOD_ID);
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        
        for(INTOMaterial material : INTOMaterial.MATERIALS)
        {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(material::updateFromConfig);
            ModLoadingContext.get().registerConfig(Type.COMMON, material.configSpec, INeedThemOres.MOD_ID + "-" + material.name.replace("_", "-") + ".toml");
        }
        
        MinecraftForge.EVENT_BUS.<TickEvent.ClientTickEvent>addListener(event ->
        {
            if(event.phase == Phase.START)
            {
                INeedThemOres.itemGroup.tick();
            }
        });
        
    }
    
    private void createMaterials()
    {
        new INTOMaterial(INeedThemOres.MOD_ID, "Copper", 1, 7, 25, 48, 96);
        new INTOMaterial(INeedThemOres.MOD_ID, "Tin", 1, 7, 20, 16, 48);
        new INTOMaterial(INeedThemOres.MOD_ID, "Aluminum", 1, 5, 4, 0, 64);
        new INTOMaterial(INeedThemOres.MOD_ID, "Platinum", 3, 3, 8, 0, 24);
        new INTOMaterial(INeedThemOres.MOD_ID, "Silver", 2, 7, 8, 8, 32);
        new INTOMaterial(INeedThemOres.MOD_ID, "Lead", 2, 7, 8, 16, 32);
        new INTOMaterial(INeedThemOres.MOD_ID, "Nickel", 2, 5, 2, 8, 24);
        new INTOMaterial(INeedThemOres.MOD_ID, "Iridium", 3, 3, 6, 0, 16);
        new INTOMaterial(INeedThemOres.MOD_ID, "Uranium", 2, 3, 8, 0, 64);
        new INTOMaterial(INeedThemOres.MOD_ID, "Zinc", 1, 7, 9, 16, 48);
    }
    
    private void setup(final FMLCommonSetupEvent event)
    {
        for(INTOMaterial material : INTOMaterial.MATERIALS)
        {
            for(Biome biome : ForgeRegistries.BIOMES)
            {
                material.registerGeneration(biome);
            }
        }
    }
}
