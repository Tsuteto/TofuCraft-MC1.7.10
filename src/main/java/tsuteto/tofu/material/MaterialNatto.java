package tsuteto.tofu.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;

public class MaterialNatto extends Material
{
    public MaterialNatto(MapColor par1MapColor)
    {
        super(par1MapColor);
    }
    
    public boolean blocksMovement()
    {
        return false;
    }
}
