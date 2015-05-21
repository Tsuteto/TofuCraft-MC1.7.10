package tsuteto.tofu.data;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tsuteto.tofu.block.BlockTofuBase;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.item.TofuMaterial;

public class TofuInfo
{
    public final TofuMaterial tofuMaterial;
    public final float hardness;
    public final boolean hasCustomResistance;
    public final float resistance;
    public final Material material;
    public final Block.SoundType stepSound;

    public String harvestTool = null;
    public int harvestLevel;

    public TofuInfo(TofuMaterial tofuMaterial, float hardness, Material material, Block.SoundType stepSound)
    {
        this(tofuMaterial, hardness, false, 0.0F, material, stepSound);
    }

    public TofuInfo(TofuMaterial tofuMaterial, float hardness, float resistance, Material material, Block.SoundType stepSound)
    {
        this(tofuMaterial, hardness, true, resistance, material, stepSound);
    }

    public TofuInfo(TofuMaterial tofuMaterial, float hardness, boolean hasCustomResistance, float resistance, Material material, Block.SoundType stepSound)
    {
        this.tofuMaterial = tofuMaterial;
        this.hardness = hardness;
        this.hasCustomResistance = hasCustomResistance;
        this.resistance = resistance;
        this.material = material;
        this.stepSound = stepSound;
    }

    public TofuInfo setHarvestLevel(String harvestTool, int harvestLevel)
    {
        this.harvestTool = harvestTool;
        this.harvestLevel = harvestLevel;
        return this;
    }

    public BlockTofuBase getBlock()
    {
        return TcBlocks.tofuBlockMap.get(this.tofuMaterial);
    }

    public void setBasicFeature(Block block)
    {
        block.setStepSound(this.stepSound);
        block.setHardness(this.hardness);
        if (this.hasCustomResistance)
        {
            block.setResistance(this.resistance);
        }
        if (this.harvestTool != null)
        {
            block.setHarvestLevel(harvestTool, harvestLevel);
        }
    }
}
