package de.cas_ual_ty.into;

import java.util.LinkedList;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.IForgeRegistry;

public class INTOMaterial
{
    public static final LinkedList<INTOMaterial> MATERIALS = new LinkedList<>();
    
    public final Config config;
    public final ForgeConfigSpec configSpec;
    
    public final String modId;
    public final String name;
    public final String lang;
    
    protected RuleTest fillerBlockType;
    protected int harvestLevel;
    protected int veignSize;
    protected int triesAmount;
    protected int bottom;
    protected int top;
    protected boolean enabled;
    
    protected Block block;
    protected Block ore;
    protected Item ingot;
    protected Item nugget;
    
    public final ResourceLocation blockRL;
    public final ResourceLocation oreRL;
    public final ResourceLocation ingotRL;
    public final ResourceLocation nuggetRL;
    
    public final INamedTag<Block> block_tag;
    public final INamedTag<Block> ore_tag;
    public final INamedTag<Item> block_item_tag;
    public final INamedTag<Item> ore_item_tag;
    public final INamedTag<Item> ingot_tag;
    public final INamedTag<Item> nugget_tag;
    
    public INTOMaterial(String modId, String name, RuleTest field241882a, int harvestLevel, int veignSize, int triesAmount, int bottom, int top)
    {
        this.modId = modId;
        this.name = name.replace(" ", "_").toLowerCase();
        this.lang = name;
        this.fillerBlockType = field241882a;
        
        this.harvestLevel = harvestLevel;
        this.veignSize = veignSize;
        this.triesAmount = triesAmount;
        this.bottom = bottom;
        this.top = top;
        this.enabled = true;
        
        this.blockRL = new ResourceLocation(this.modId, this.name + "_block");
        this.oreRL = new ResourceLocation(this.modId, this.name + "_ore");
        this.ingotRL = new ResourceLocation(this.modId, this.name + "_ingot");
        this.nuggetRL = new ResourceLocation(this.modId, this.name + "_nugget");
        
        this.block_tag = BlockTags.makeWrapperTag("forge:storage_blocks/" + this.name);
        this.ore_tag = BlockTags.makeWrapperTag("forge:ores/" + this.name);
        this.block_item_tag = ItemTags.makeWrapperTag("forge:storage_blocks/" + this.name);
        this.ore_item_tag = ItemTags.makeWrapperTag("forge:ores/" + this.name);
        this.ingot_tag = ItemTags.makeWrapperTag("forge:ingots/" + this.name);
        this.nugget_tag = ItemTags.makeWrapperTag("forge:nuggets/" + this.name);
        
        Pair<Config, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure((builder) -> new Config(builder, this));
        this.config = pair.getLeft();
        this.configSpec = pair.getRight();
        
        INTOMaterial.MATERIALS.add(this);
    }
    
    public INTOMaterial(String modId, String name, int harvestLevel, int veignSize, int triesAmount, int bottom, int top)
    {
        this(modId, name, FillerBlockType.field_241882_a, harvestLevel, veignSize, triesAmount, bottom, top);
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
        registry.register(this.block = new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(this.harvestLevel).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)).setRegistryName(this.blockRL));
        registry.register(this.ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(this.harvestLevel).hardnessAndResistance(3.0F, 3.0F)).setRegistryName(this.oreRL));
    }
    
    public void registerItems(IForgeRegistry<Item> registry)
    {
        registry.register(new BlockItem(this.getBlock(), new Item.Properties().group(INeedThemOres.itemGroup)).setRegistryName(this.blockRL));
        registry.register(new BlockItem(this.getOre(), new Item.Properties().group(INeedThemOres.itemGroup)).setRegistryName(this.oreRL));
        registry.register(this.ingot = new Item(new Item.Properties().group(INeedThemOres.itemGroup)).setRegistryName(this.ingotRL));
        registry.register(this.nugget = new Item(new Item.Properties().group(INeedThemOres.itemGroup)).setRegistryName(this.nuggetRL));
    }
    
    public void registerGeneration(BiomeGenerationSettingsBuilder builder)
    {
        if(this.enabled)
        {
            builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                Feature.ORE.withConfiguration(
                    new OreFeatureConfig(
                        OreFeatureConfig.FillerBlockType.field_241882_a,
                        this.ore.getDefaultState(),
                        this.veignSize))
                    .withPlacement(Placement.field_242907_l.configure(
                        new TopSolidRangeConfig(this.bottom, this.bottom, this.top)))
                    .func_242728_a()
                    .func_242731_b(this.triesAmount));
        }
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
        
        public Config(ForgeConfigSpec.Builder builder, INTOMaterial material)
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
