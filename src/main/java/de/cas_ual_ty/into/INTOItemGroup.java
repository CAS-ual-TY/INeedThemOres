package de.cas_ual_ty.into;

import java.util.Random;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class INTOItemGroup extends ItemGroup
{
    public static final int SWITCH_TIME = 20;
    
    private int timer;
    private final Random random;
    private final NonNullList<ItemStack> itemStacks;
    
    public INTOItemGroup(String label)
    {
        super(label);
        this.timer = 0;
        this.random = new Random();
        this.itemStacks = NonNullList.create();
    }
    
    @Override
    public ItemStack createIcon()
    {
        if(this.itemStacks.isEmpty())
        {
            this.fill(this.itemStacks);
        }
        
        return this.itemStacks.get(this.random.nextInt(this.itemStacks.size())).copy();
    }
    
    public void tick()
    {
        if(this.timer++ >= INTOItemGroup.SWITCH_TIME)
        {
            this.timer = 0;
            this.getIcon().setCount(0);
        }
    }
    
    @Override
    public void fill(NonNullList<ItemStack> items)
    {
        super.fill(items);
        
        items.sort((i1, i2) ->
        {
            String p1 = i1.getItem().getRegistryName().getPath();
            String p2 = i2.getItem().getRegistryName().getPath();
            
            String s1[] = p1.split("_");
            String s2[] = p2.split("_");
            
            if(s1[s1.length - 1].equals(s2[s2.length - 1]))
            {
                return s1[0].compareTo(s2[0]);
            }
            else
            {
                return p1.compareTo(p2);
            }
        });
    }
}
