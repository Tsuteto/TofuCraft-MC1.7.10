package tsuteto.tofu.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemDiamondTofuArmor extends ItemTofuArmor
{
    public static int[][] registanceList = new int[][]{
            {Potion.blindness.id, Potion.confusion.id}, // Helmet
            {Potion.poison.id, Potion.hunger.id, Potion.wither.id}, // Chestplate
            {Potion.weakness.id, Potion.digSlowdown.id}, // Leggings
            {Potion.moveSlowdown.id} // Boots
    };

    public ItemDiamondTofuArmor(ArmorMaterial material, int par3, int par4)
    {
        super(material, par3, par4);

    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        StringBuilder sb = new StringBuilder();
        for (int id : registanceList[this.armorType])
        {
            if (sb.length() > 0) sb.append(", ");
            sb.append(I18n.format(Potion.potionTypes[id].getName()));
        }
        par3List.add(StatCollector.translateToLocalFormatted("item.tofucraft:armorDiamond.desc", sb.toString()));

        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
    }
}
