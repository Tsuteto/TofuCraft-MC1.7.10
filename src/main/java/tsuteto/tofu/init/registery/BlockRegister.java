package tsuteto.tofu.init.registery;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.item.ItemTcBlock;

public class BlockRegister<T extends Block>
{
    private T block;
    private Class<? extends ItemBlock> itemBlock = ItemBlock.class;
    private Object[] itemCtorArgs;
    private String uniqueName;
    private String resourceName;

    public BlockRegister(String name, T block)
    {
        this.block = block;
        this.uniqueName = name;
        this.resourceName = name;
    }

    public BlockRegister<T> withResource(String name)
    {
        this.resourceName = name;
        return this;
    }

    public BlockRegister<T> wrappedByItemTcBlock()
    {
        this.itemBlock = ItemTcBlock.class;
        return this;
    }

    public BlockRegister<T> wrappedBy(Class<? extends ItemBlock> itemBlock)
    {
        this.itemBlock = itemBlock;
        return this;
    }

    public BlockRegister<T> havingArgs(Object... itemCtorArgs)
    {
        this.itemCtorArgs = itemCtorArgs;
        return this;
    }

    public BlockRegister<T> setHarvestLevel(String tool, int level)
    {
        block.setHarvestLevel(tool, level);
        return this;
    }

    public BlockRegister<T> withTileEntity(Class<? extends TileEntity> tileEntity)
    {
        return this.withTileEntity(tileEntity, uniqueName);
    }

    public BlockRegister<T> withTileEntity(Class<? extends TileEntity> tileEntity, String name)
    {
        GameRegistry.registerTileEntity(tileEntity, TofuCraftCore.resourceDomain + name);
        return this;
    }

    public T registerBlock()
    {
        block.setBlockName(TofuCraftCore.resourceDomain + resourceName);
        block.setBlockTextureName(TofuCraftCore.resourceDomain + resourceName);
        if (itemCtorArgs != null)
        {
            GameRegistry.registerBlock(block, itemBlock, uniqueName, itemCtorArgs);
        }
        else
        {
            GameRegistry.registerBlock(block, itemBlock, uniqueName);
        }
        return block;
    }
}
