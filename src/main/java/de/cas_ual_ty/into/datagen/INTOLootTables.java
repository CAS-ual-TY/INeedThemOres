package de.cas_ual_ty.into.datagen;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.cas_ual_ty.into.INTOMaterial;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.DynamicLootEntry;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.SetContents;
import net.minecraft.util.ResourceLocation;

public class INTOLootTables extends LootTableProvider
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    
    // Filled by subclasses
    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
    
    private final DataGenerator generator;
    
    public INTOLootTables(DataGenerator dataGeneratorIn)
    {
        super(dataGeneratorIn);
        this.generator = dataGeneratorIn;
    }
    
    @Override
    public String getName()
    {
        return "I Need Them Ores Loot Tables";
    }
    
    public void addTables()
    {
        for(INTOMaterial material : INTOMaterial.MATERIALS)
        {
            this.lootTables.put(material.getBlock(), this.createStandardTable(material.blockRL.getPath(), material.getBlock()));
            this.lootTables.put(material.getOre(), this.createStandardTable(material.oreRL.getPath(), material.getOre()));
        }
    }
    
    // Subclasses can call this if they want a standard loot table. Modify this for your own needs
    protected LootTable.Builder createStandardTable(String name, Block block)
    {
        LootPool.Builder builder = LootPool.builder()
            .name(name)
            .rolls(ConstantRange.of(1))
            .addEntry(ItemLootEntry.builder(block)
                .acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY))
                .acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY)
                    .addOperation("inv", "BlockEntityTag.inv", CopyNbt.Action.REPLACE)
                    .addOperation("energy", "BlockEntityTag.energy", CopyNbt.Action.REPLACE))
                .acceptFunction(SetContents.builderIn()
                    .addLootEntry(DynamicLootEntry.func_216162_a(new ResourceLocation("minecraft", "contents")))));
        return LootTable.builder().addLootPool(builder);
    }
    
    @Override
    // Entry point
    public void act(DirectoryCache cache)
    {
        this.addTables();
        
        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for(Map.Entry<Block, LootTable.Builder> entry : this.lootTables.entrySet())
        {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
        }
        this.writeTables(cache, tables);
    }
    
    // Actually write out the tables in the output folder
    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables)
    {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) ->
        {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try
            {
                IDataProvider.save(INTOLootTables.GSON, cache, LootTableManager.toJson(lootTable), path);
            }
            catch (IOException e)
            {
                INTOLootTables.LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }
}
