package de.cas_ual_ty.into;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = INeedThemOres.MOD_ID, bus = Bus.MOD)
@ObjectHolder(INeedThemOres.MOD_ID)
public class INTORegistries
{
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();
        
        for(INTOMaterial material : INTOMaterial.MATERIALS)
        {
            material.registerBlocks(registry);
        }
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();
        
        for(INTOMaterial material : INTOMaterial.MATERIALS)
        {
            material.registerItems(registry);
        }
    }
}
