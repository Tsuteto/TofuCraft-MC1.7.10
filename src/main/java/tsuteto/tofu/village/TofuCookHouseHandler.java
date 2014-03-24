package tsuteto.tofu.village;

import cpw.mods.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import java.util.List;
import java.util.Random;

public class TofuCookHouseHandler implements IVillageCreationHandler
{

    @Override
    public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int i)
    {
        return new StructureVillagePieces.PieceWeight(ComponentVillageHouseTofu.class, 20, MathHelper.getRandomIntegerInRange(random, 0 + i, 1 + i));
    }

    @Override
    public Class<?> getComponentClass()
    {
        return ComponentVillageHouseTofu.class;
    }

    @Override
    public Object buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, -4, 0, 7, 6, 7, par6);
        return StructureComponent.findIntersecting(par1List, structureboundingbox) == null ? new ComponentVillageHouseTofu(par0ComponentVillageStartPiece, par7, par2Random, structureboundingbox, par6) : null;
    }

}
