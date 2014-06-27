package tsuteto.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class TcBlock extends Block
{


    public TcBlock(Material par2Material)
    {
        super(par2Material);
    }

    @Override
    public TcBlock setBlockName(String name)
    {
        super.setBlockName(name);
        this.setBlockTextureName(name);
        return this;
    }

    public TcBlock setHardness(float f)
    {
        super.setHardness(f);
        return this;
    }

    public TcBlock setResistance(float f)
    {
        super.setResistance(f);
        return this;
    }

    public TcBlock setStepSound(SoundType type)
    {
        super.setStepSound(type);
        return this;
    }

    public TcBlock setLightLevel(float f)
    {
        super.setLightLevel(f);
        return this;
    }

    public TcBlock setCreativeTab(CreativeTabs tab)
    {
        super.setCreativeTab(tab);
        return this;
    }

}
