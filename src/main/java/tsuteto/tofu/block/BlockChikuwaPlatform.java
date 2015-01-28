package tsuteto.tofu.block;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tsuteto.tofu.entity.EntityFallingChikuwaPlatform;
import tsuteto.tofu.material.TcMaterial;
import tsuteto.tofu.tileentity.TileEntityChikuwaPlatform;

import java.util.EnumMap;
import java.util.EnumSet;

public class BlockChikuwaPlatform extends BlockContainer
{
    public enum Dir
    {
        X(ForgeDirection.WEST, ForgeDirection.EAST),
        Z(ForgeDirection.NORTH, ForgeDirection.SOUTH);

        public final EnumSet<ForgeDirection> connectedTo;
        public final ForgeDirection left;
        public final ForgeDirection right;

        Dir(ForgeDirection left, ForgeDirection right)
        {
            this.left = left;
            this.right = right;
            this.connectedTo = EnumSet.of(left, right);
        }
    }

    public enum ConnectionStat
    {
        SINGLE("S"), LEFT("L"), RIGHT("R"), MID("M");

        private static ConnectionStat[] opposites = new ConnectionStat[]{SINGLE, RIGHT, LEFT, MID};

        String texSign;

        ConnectionStat(String texSign)
        {
            this.texSign = texSign;
        }

        public ConnectionStat opposite()
        {
            return opposites[this.ordinal()];
        }
    }

    private final String typeName;
    private EnumMap<ConnectionStat, IIcon> iconSideH = Maps.newEnumMap(ConnectionStat.class);
    private EnumMap<ConnectionStat, IIcon> iconSideV = Maps.newEnumMap(ConnectionStat.class);
    private IIcon iconTip;
    private IIcon iconInner;

    protected BlockChikuwaPlatform(String typeName)
    {
        super(TcMaterial.tofu);
        this.typeName = typeName;
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        Dir dir = getDirection(meta);
        ConnectionStat stat = getConnectionStat(meta);

        if (dir == Dir.X && (side == 4 || side == 5))
        {
            return iconTip;
        }
        else if (dir == Dir.Z && (side == 2 || side == 3))
        {
            return iconTip;
        }
        else if (side == 0 || side == 1)
        {
            return dir == Dir.X ? iconSideH.get(stat) : iconSideV.get(stat);
        }
        else if (side == 2 || side == 5)
        {
            return iconSideH.get(stat.opposite());
        }
        else
        {
            return iconSideH.get(stat);
        }
    }

    public static Dir getDirection(int meta)
    {
        return Dir.values()[meta & 1];
    }

    public static ConnectionStat getConnectionStat(int meta)
    {
        return ConnectionStat.values()[(meta & 6) >> 1];
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        double d = 0.125D;
        return AxisAlignedBB.getBoundingBox((double) par2, (double) par3, (double) par4, (double) (par2 + 1), (par3 + 1) - d, (double) (par4 + 1));
    }

    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        int dir = MathHelper.floor_double((par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int blockDir = (dir + 1) % 2;
        int stat = calcChikuwaConnection(par1World, par2, par3, par4, blockDir);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, stat << 1 | blockDir, 2);
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        int meta = world.getBlockMetadata(x, y, z);
        this.updateChikuwaConnection(world, x, y, z, meta);
    }

    public void updateChikuwaConnection(World world, int x, int y, int z, int meta)
    {
        int stat = this.calcChikuwaConnection(world, x, y, z, meta);
        world.setBlockMetadataWithNotify(x, y, z, (stat << 1) | (meta & 9), 2);
    }

    public int calcChikuwaConnection(World world, int x, int y, int z, int meta)
    {
        Dir blockDir = getDirection(meta);

        boolean canConnectToLeft = this.canChikuwaConnectTo(world, x, y, z, meta, blockDir.left);
        boolean canConnectToRight = this.canChikuwaConnectTo(world, x, y, z, meta, blockDir.right);

        int stat = 0;
        if (canConnectToLeft) stat += 1;
        if (canConnectToRight) stat += 2;

        return stat;
    }

    public boolean canChikuwaConnectTo(IBlockAccess world, int x, int y, int z, int meta, ForgeDirection dir)
    {
        int anotherX = x + dir.offsetX;
        int anotherY = y + dir.offsetY;
        int anotherZ = z + dir.offsetZ;

        Block anotherBlock = world.getBlock(anotherX, anotherY, anotherZ);
        int anotherMeta = world.getBlockMetadata(anotherX, anotherY, anotherZ);

        Dir blockDir = getDirection(meta);
        return anotherBlock == this
                && getDirection(meta) == getDirection(anotherMeta)
                && blockDir.connectedTo.contains(dir);
    }

    public boolean dropBlock(World world, int x, int y, int z, Block block, int chunkUID)
    {
        if (canGoThrough(world, x, y - 1, z) && y >= 0)
        {
            byte b0 = 32;

            if (world.checkChunksExist(x - b0, y - b0, z - b0, x + b0, y + b0, z + b0))
            {
                if (!world.isRemote)
                {
                    int meta = world.getBlockMetadata(x, y, z);
                    world.setBlock(x, y, z, Blocks.air, 0, 2);

                    EntityFallingChikuwaPlatform fallingBlock = new EntityFallingChikuwaPlatform(
                            world, x + 0.5D, y, z + 0.5D, block, meta, chunkUID);
                    fallingBlock.setHome(new ChunkCoordinates(x, y, z));
                    world.spawnEntityInWorld(fallingBlock);
                }
            }
            else
            {
                world.setBlockToAir(x, y, z);

                while (canGoThrough(world, x, y - 1, z) && y > 0)
                {
                    --y;
                }

                if (y > 0)
                {
                    world.setBlock(x, y, z, block);
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean canGoThrough(World p_149831_0_, int p_149831_1_, int p_149831_2_, int p_149831_3_)
    {
        Block block = p_149831_0_.getBlock(p_149831_1_, p_149831_2_, p_149831_3_);

        if (block.isAir(p_149831_0_, p_149831_1_, p_149831_2_, p_149831_3_))
        {
            return true;
        }
        else if (block == Blocks.fire)
        {
            return true;
        }
        else
        {
            Material material = block.getMaterial();
            return material == Material.water || material == Material.lava;
        }
    }

    @Override
    public int getRenderType()
    {
        return RenderChikuwaPlatform.renderId;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

        @Override
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        super.registerBlockIcons(p_149651_1_);

        for (ConnectionStat stat : ConnectionStat.values())
        {
            iconSideH.put(stat, p_149651_1_.registerIcon("tofucraft:chikuwaPlatform_sideH" + stat.texSign + "_" + typeName));
            iconSideV.put(stat, p_149651_1_.registerIcon("tofucraft:chikuwaPlatform_sideV" + stat.texSign + "_" + typeName));
        }
        iconTip = p_149651_1_.registerIcon("tofucraft:chikuwaPlatform_tip");
        iconInner = p_149651_1_.registerIcon("tofucraft:chikuwaPlatform_inner");
    }

    public IIcon getInnerIcon()
    {
        return iconInner;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityChikuwaPlatform();
    }
}
