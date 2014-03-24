package tsuteto.tofu.dimension;

import net.minecraft.util.ChunkCoordinates;

public class TofuPortalPosition extends ChunkCoordinates
{
    public long field_85087_d;

    final TofuTeleporter field_85088_e;

    public TofuPortalPosition(TofuTeleporter par1Teleporter, int par2, int par3, int par4, long par5)
    {
        super(par2, par3, par4);
        this.field_85088_e = par1Teleporter;
        this.field_85087_d = par5;
    }
}
