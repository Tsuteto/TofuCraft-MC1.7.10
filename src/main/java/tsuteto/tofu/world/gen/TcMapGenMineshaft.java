package tsuteto.tofu.world.gen;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.StructureMineshaftStart;
import net.minecraft.world.gen.structure.StructureStart;

public class TcMapGenMineshaft extends TcMapGenStructure
{
    private double field_82673_e = 0.01D;

    public TcMapGenMineshaft() {}

    public TcMapGenMineshaft(Map par1Map)
    {
        Iterator iterator = par1Map.entrySet().iterator();

        while (iterator.hasNext())
        {
            Entry entry = (Entry)iterator.next();

            if ((entry.getKey()).equals("chance"))
            {
                this.field_82673_e = MathHelper.parseDoubleWithDefault((String)entry.getValue(), this.field_82673_e);
            }
        }
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int par1, int par2)
    {
        return this.rand.nextDouble() < this.field_82673_e && this.rand.nextInt(80) < Math.max(Math.abs(par1), Math.abs(par2));
    }

    @Override
    protected StructureStart getStructureStart(int par1, int par2)
    {
        return new StructureMineshaftStart(this.worldObj, this.rand, par1, par2);
    }
}
