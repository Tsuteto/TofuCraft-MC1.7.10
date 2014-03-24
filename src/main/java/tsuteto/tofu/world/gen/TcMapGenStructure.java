package tsuteto.tofu.world.gen;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;


import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public abstract class TcMapGenStructure extends TcMapGenBase
{
    /**
     * Used to store a list of all structures that have been recursively generated. Used so that during recursive
     * generation, the structure generator can avoid generating structures that intersect ones that have already been
     * placed.
     */
    protected Map structureMap = new HashMap();

    /**
     * Recursively called by generate() (generate) and optionally by itself.
     */
    @Override
    protected void func_151538_a(World par1World, int par2, int par3, int par4, int par5, Block[] par6ArrayOfByte)
    {
        if (!this.structureMap.containsKey(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(par2, par3))))
        {
            this.rand.nextInt();

            try
            {
                if (this.canSpawnStructureAtCoords(par2, par3))
                {
                    StructureStart structurestart = this.getStructureStart(par2, par3);
                    this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(par2, par3)), structurestart);
                }
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception preparing structure feature");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Feature being prepared");
                crashreportcategory.addCrashSectionCallable("Is feature chunk", new CallableIsFeatureChunk(this, par2, par3));
                crashreportcategory.addCrashSection("Chunk location", String.format("%d,%d", new Object[] {Integer.valueOf(par2), Integer.valueOf(par3)}));
                crashreportcategory.addCrashSectionCallable("Chunk pos hash", new CallableChunkPosHash(this, par2, par3));
                crashreportcategory.addCrashSectionCallable("Structure type", new CallableStructureType(this));
                throw new ReportedException(crashreport);
            }
        }
    }

    /**
     * Generates structures in specified chunk next to existing structures. Does *not* generate StructureStarts.
     */
    public boolean generateStructuresInChunk(World par1World, Random par2Random, int par3, int par4)
    {
        int k = (par3 << 4) + 8;
        int l = (par4 << 4) + 8;
        boolean flag = false;
        Iterator iterator = this.structureMap.values().iterator();

        while (iterator.hasNext())
        {
            StructureStart structurestart = (StructureStart)iterator.next();

            if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().intersectsWith(k, l, k + 15, l + 15))
            {
                structurestart.generateStructure(par1World, par2Random, new StructureBoundingBox(k, l, k + 15, l + 15));
                flag = true;
            }
        }

        return flag;
    }

    /**
     * Returns true if the structure generator has generated a structure located at the given position tuple.
     */
    public boolean hasStructureAt(int par1, int par2, int par3)
    {
        Iterator iterator = this.structureMap.values().iterator();

        while (iterator.hasNext())
        {
            StructureStart structurestart = (StructureStart)iterator.next();

            if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().intersectsWith(par1, par3, par1, par3))
            {
                Iterator iterator1 = structurestart.getComponents().iterator();

                while (iterator1.hasNext())
                {
                    StructureComponent structurecomponent = (StructureComponent)iterator1.next();

                    if (structurecomponent.getBoundingBox().isVecInside(par1, par2, par3))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public ChunkPosition getNearestInstance(World par1World, int par2, int par3, int par4)
    {
        this.worldObj = par1World;
        this.rand.setSeed(par1World.getSeed());
        long l = this.rand.nextLong();
        long i1 = this.rand.nextLong();
        long j1 = (par2 >> 4) * l;
        long k1 = (par4 >> 4) * i1;
        this.rand.setSeed(j1 ^ k1 ^ par1World.getSeed());
        this.func_151538_a(par1World, par2 >> 4, par4 >> 4, 0, 0, null);
        double d0 = Double.MAX_VALUE;
        ChunkPosition chunkposition = null;
        Iterator iterator = this.structureMap.values().iterator();
        ChunkPosition chunkposition1;
        int l1;
        int i2;
        double d1;
        int j2;

        while (iterator.hasNext())
        {
            StructureStart structurestart = (StructureStart)iterator.next();

            if (structurestart.isSizeableStructure())
            {
                StructureComponent structurecomponent = (StructureComponent)structurestart.getComponents().get(0);
                chunkposition1 = structurecomponent.func_151553_a(); // getCenter
                i2 = chunkposition1.chunkPosX - par2;
                l1 = chunkposition1.chunkPosY - par3;
                j2 = chunkposition1.chunkPosZ - par4;
                d1 = (i2 + i2 * l1 * l1 + j2 * j2);

                if (d1 < d0)
                {
                    d0 = d1;
                    chunkposition = chunkposition1;
                }
            }
        }

        if (chunkposition != null)
        {
            return chunkposition;
        }
        else
        {
            List list = this.getCoordList();

            if (list != null)
            {
                ChunkPosition chunkposition2 = null;
                Iterator iterator1 = list.iterator();

                while (iterator1.hasNext())
                {
                    chunkposition1 = (ChunkPosition)iterator1.next();
                    i2 = chunkposition1.chunkPosX - par2;
                    l1 = chunkposition1.chunkPosY - par3;
                    j2 = chunkposition1.chunkPosZ - par4;
                    d1 = (i2 + i2 * l1 * l1 + j2 * j2);

                    if (d1 < d0)
                    {
                        d0 = d1;
                        chunkposition2 = chunkposition1;
                    }
                }

                return chunkposition2;
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * Returns a list of other locations at register the structure generation has been run, or null if not relevant to this
     * structure generator.
     */
    protected List getCoordList()
    {
        return null;
    }

    protected abstract boolean canSpawnStructureAtCoords(int i, int j);

    protected abstract StructureStart getStructureStart(int i, int j);
}
