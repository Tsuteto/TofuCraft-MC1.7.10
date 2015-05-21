package tsuteto.tofu.init.registery;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.item.TofuMaterial;

public class ItemRegister<T extends Item>
{
    private T item;
    private String uniqueName;
    private String resourceName;

    public ItemRegister(String name, T item)
    {
        this.item = item;
        this.uniqueName = name;
        this.resourceName = name;
    }

    public ItemRegister<T> withResource(String name)
    {
        this.resourceName = name;
        return this;
    }

    public ItemRegister<T> asTofu(TofuMaterial material)
    {
        TcItems.tofuItems.put(material, item);
        return this;
    }

    public T register()
    {
        item.setUnlocalizedName(TofuCraftCore.resourceDomain + resourceName);
        item.setTextureName(TofuCraftCore.resourceDomain + resourceName);
        GameRegistry.registerItem(item, uniqueName);
        return item;
    }

}
