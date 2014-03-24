package tsuteto.tofu.world.gen;

import java.util.concurrent.Callable;



class CallableStructureType implements Callable
{
    final TcMapGenStructure theMapStructureGenerator;

    CallableStructureType(TcMapGenStructure par1MapGenStructure)
    {
        this.theMapStructureGenerator = par1MapGenStructure;
    }

    public String callStructureType()
    {
        return this.theMapStructureGenerator.getClass().getCanonicalName();
    }

    @Override
    public Object call()
    {
        return this.callStructureType();
    }
}
