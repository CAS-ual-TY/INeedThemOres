package de.cas_ual_ty.into;

import java.util.LinkedList;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.IForgeRegistry;

public class INTOOre
{
    public static final LinkedList<INTOOre> MATERIALS = new LinkedList<>();
    
    public final Config config;
    public final ForgeConfigSpec configSpec;
    
    public String modId;
    public String name;
    public String lang;
    
    public FillerBlockType fillerBlockType;
    public int harvestLevel;
    public int veignSize;
    public int triesAmount;
    public int bottom;
    public int top;
    public boolean enabled;
    
    protected Block block;
    protected Block ore;
    protected Item ingot;
    protected Item nugget;
    
    public INTOOre(String modId, String name, FillerBlockType fillerBlockType, int harvestLevel, int veignSize, int triesAmount, int bottom, int top)
    {
        this.modId = modId;
        this.name = name.replace(" ", "_").toLowerCase();
        this.lang = name;
        this.fillerBlockType = fillerBlockType;
        
        this.harvestLevel = harvestLevel;
        this.veignSize = veignSize;
        this.triesAmount = triesAmount;
        this.bottom = bottom;
        this.top = top;
        this.enabled = true;
        
        Pair<Config, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure((builder) -> new Config(builder, this));
        this.config = pair.getLeft();
        this.configSpec = pair.getRight();
        
        INTOOre.MATERIALS.add(this);
    }
    
    public INTOOre(String modId, String name, int harvestLevel, int veignSize, int triesAmount, int bottom, int top)
    {
        this(modId, name, FillerBlockType.NATURAL_STONE, harvestLevel, veignSize, triesAmount, bottom, top);
    }
    
    public void updateFromConfig(ModConfig.ModConfigEvent event)
    {
        if(event.getConfig().getSpec() == this.configSpec)
        {
            this.harvestLevel = this.config.harvestLevel.get();
            this.veignSize = this.config.veignSize.get();
            this.triesAmount = this.config.triesAmount.get();
            this.bottom = this.config.bottom.get();
            this.top = this.config.top.get();
            this.enabled = this.config.enabled.get();
        }
    }
    
    public void registerBlocks(IForgeRegistry<Block> registry)
    {
        registry.register(this.block = new Block(Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(this.harvestLevel).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)).setRegistryName(this.getBlockRL()));
        registry.register(this.ore = new Block(Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(this.harvestLevel).hardnessAndResistance(3.0F, 3.0F)).setRegistryName(this.getOreRL()));
    }
    
    public void registerItems(IForgeRegistry<Item> registry)
    {
        registry.register(this.ingot = new Item(new Item.Properties().group(INeedThemOres.itemGroup)).setRegistryName(this.getIngotRL()));
        registry.register(this.nugget = new Item(new Item.Properties().group(INeedThemOres.itemGroup)).setRegistryName(this.getNuggetRL()));
        registry.register(new BlockItem(this.getBlock(), new Item.Properties().group(INeedThemOres.itemGroup)).setRegistryName(this.getBlockRL()));
        registry.register(new BlockItem(this.getOre(), new Item.Properties().group(INeedThemOres.itemGroup)).setRegistryName(this.getOreRL()));
    }
    
    public void registerGeneration(Biome biome)
    {
        if(this.enabled)
        {
            // Y = random.nextInt(maximum - topOffset) + bottomOffset;
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(this.fillerBlockType, this.getOre().getDefaultState(), this.veignSize)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(this.triesAmount, this.bottom, 0, this.top - this.bottom))));
        }
    }
    
    public ResourceLocation getBlockRL()
    {
        return new ResourceLocation(this.modId, this.name + "_block");
    }
    
    public ResourceLocation getOreRL()
    {
        return new ResourceLocation(this.modId, this.name + "_ore");
    }
    
    public ResourceLocation getIngotRL()
    {
        return new ResourceLocation(this.modId, this.name + "_ingot");
    }
    
    public ResourceLocation getNuggetRL()
    {
        return new ResourceLocation(this.modId, this.name + "_nugget");
    }
    
    public Block getBlock()
    {
        return this.block;
    }
    
    public Block getOre()
    {
        return this.ore;
    }
    
    public Item getIngot()
    {
        return this.ingot;
    }
    
    public Item getNugget()
    {
        return this.nugget;
    }
    
    protected static class Config
    {
        private ForgeConfigSpec.IntValue harvestLevel;
        private ForgeConfigSpec.IntValue veignSize;
        private ForgeConfigSpec.IntValue triesAmount;
        private ForgeConfigSpec.IntValue bottom;
        private ForgeConfigSpec.IntValue top;
        private ForgeConfigSpec.BooleanValue enabled;
        
        public Config(ForgeConfigSpec.Builder builder, INTOOre material)
        {
            builder.push(material.name);
            this.harvestLevel = Config.createSimpleInt(builder, material.modId, "harvest_level", "0 = Wood, 1 = Stone, 2 = Iron, 3 = Diamond, 4+ = Modded Items (See each individual Mod)", material.harvestLevel, 0, 100);
            this.veignSize = Config.createSimpleInt(builder, material.modId, "veign_size", "The Size of eich Veign", material.veignSize, 0, 100);
            this.triesAmount = Config.createSimpleInt(builder, material.modId, "tries_amount", "Amount of Tries per Chunk to generate a Veign", material.triesAmount, 0, 100);
            this.bottom = Config.createSimpleInt(builder, material.modId, "bottom", "Bottom Edge for Overworld Generation", material.bottom, 0, 255);
            this.top = Config.createSimpleInt(builder, material.modId, "top", "Top Edge for Overworld Generation", material.top, 0, 255);
            this.enabled = builder.comment("Enable Generation in the Overworld").translation(material.modId + ".config.enabled").define("enabled", material.enabled);
            builder.pop();
        }
        
        private static ForgeConfigSpec.IntValue createSimpleInt(ForgeConfigSpec.Builder builder, String modId, String name, String comment, int def, int min, int max)
        {
            return builder
                .comment(comment)
                .translation(modId + ".config." + name)
                .defineInRange(name, def, min, max);
        }
    }
}
