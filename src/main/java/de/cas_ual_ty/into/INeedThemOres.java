package de.cas_ual_ty.into;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(INeedThemOres.MOD_ID)
public class INeedThemOres
{
    public static final String MOD_ID = "ineedthemores";
    
    public static INTOItemGroup itemGroup;
    
    public INeedThemOres()
    {
        this.createMaterials();
        
        INeedThemOres.itemGroup = new INTOItemGroup(INeedThemOres.MOD_ID);
        
        MinecraftForge.EVENT_BUS.addListener(this::biomeLoading);
        
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
        
        this.overworld = ImmutableList.of(
            Biome.Category.BEACH,
            Biome.Category.DESERT,
            Biome.Category.EXTREME_HILLS,
            Biome.Category.FOREST,
            Biome.Category.ICY,
            Biome.Category.JUNGLE,
            Biome.Category.MESA,
            Biome.Category.MUSHROOM,
            Biome.Category.OCEAN,
            Biome.Category.PLAINS,
            Biome.Category.RIVER,
            Biome.Category.SAVANNA,
            Biome.Category.SWAMP,
            Biome.Category.TAIGA);
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
        new INTOMaterial(INeedThemOres.MOD_ID, "Bismuth", 1, 9, 4, 16, 64);
    }
    
    private final List<Biome.Category> overworld;
    
    private void biomeLoading(final BiomeLoadingEvent event)
    {
        if(this.overworld.contains(event.getCategory()))
        {
            for(INTOMaterial material : INTOMaterial.MATERIALS)
            {
                material.registerGeneration(event.getGeneration());
            }
        }
    }
}
