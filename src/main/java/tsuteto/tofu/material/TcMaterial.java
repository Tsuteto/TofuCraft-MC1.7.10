package tsuteto.tofu.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;

public class TcMaterial extends Material
{
    public TcMaterial(MapColor par1MapColor)
    {
        super(par1MapColor);
    }
    public static final Material tofu = new TcMaterial(MapColor.clothColor);
    public static final Material soymilk = new MaterialLiquid(MapColor.waterColor); // Applied noPushMobility by MaterialLiquid
    public static final Material natto = new MaterialNatto(MapColor.woodColor);

}
