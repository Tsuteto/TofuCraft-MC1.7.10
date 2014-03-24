package tsuteto.tofu.world.gen;

import java.util.concurrent.Callable;



import net.minecraft.world.ChunkCoordIntPair;

class CallableChunkPosHash implements Callable
{
    final int field_85165_a;

    final int field_85163_b;

    final TcMapGenStructure theMapStructureGenerator;

    CallableChunkPosHash(TcMapGenStructure par1MapGenStructure, int par2, int par3)
    {
        this.theMapStructureGenerator = par1MapGenStructure;
        this.field_85165_a = par2;
        this.field_85163_b = par3;
    }

    public String callChunkPositionHash()
    {
        return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(this.field_85165_a, this.field_85163_b));
    }

    @Override
    public Object call()
    {
        return this.callChunkPositionHash();
    }
}
