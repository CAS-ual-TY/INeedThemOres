package de.cas_ual_ty.into.datagen;

import de.cas_ual_ty.into.INeedThemOres;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;;

@EventBusSubscriber(bus = Bus.MOD)
public class INTODataGenerator
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new INTOBlockModels(generator, INeedThemOres.MOD_ID, event.getExistingFileHelper()));
        generator.addProvider(new INTOBlockStates(generator, INeedThemOres.MOD_ID, event.getExistingFileHelper()));
        generator.addProvider(new INTOItemModels(generator, INeedThemOres.MOD_ID, event.getExistingFileHelper()));
        generator.addProvider(new INTOBlockTags(generator));
        generator.addProvider(new INTOItemTags(generator));
        generator.addProvider(new INTORecipes(generator));
        generator.addProvider(new INTOLanguage(generator, INeedThemOres.MOD_ID));
        generator.addProvider(new INTOLootTables(generator));
    }
}
