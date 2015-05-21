package tsuteto.tofu.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import tsuteto.tofu.Settings;
import tsuteto.tofu.achievement.TcAchievementMgr;
import tsuteto.tofu.achievement.TcAchievementMgr.Key;
import tsuteto.tofu.data.DataType;
import tsuteto.tofu.data.EntityInfo;
import tsuteto.tofu.data.PortalTripInfo;
import tsuteto.tofu.dimension.DimensionTeleportation;
import tsuteto.tofu.entity.EntityTofuPortalFX;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.init.TcEntity;
import tsuteto.tofu.network.PacketDispatcher;
import tsuteto.tofu.network.packet.PacketDimTrip;

import java.util.Random;

public class BlockTofuPortal extends BlockBreakable
{
    public static final int[][] field_150001_a = new int[][] {new int[0], {3, 1}, {2, 0}};

    private final DimensionTeleportation teleportHandler = new DimensionTeleportation();

    public BlockTofuPortal()
    {
        super("tofucraft:tofuPortal", Material.portal, false);
        this.setTickRandomly(true);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.updateTick(par1World, par2, par3, par4, par5Random);

        if (par1World.provider.isSurfaceWorld() && par1World.getGameRules().getGameRuleBooleanValue("doMobSpawning") && par5Random.nextInt(2000) < par1World.difficultySetting.getDifficultyId())
        {
            int var6;

            for (var6 = par3; !par1World.doesBlockHaveSolidTopSurface(par1World, par2, var6, par4) && var6 > 0; --var6)
            {
                ;
            }

            if (var6 > 0 && !par1World.getBlock(par2, var6 + 1, par4).isNormalCube())
            {
                Entity var7 = ItemMonsterPlacer.spawnCreature(par1World, TcEntity.entityIdTofuSlime, par2 + 0.5D, var6 + 1.1D, par4 + 0.5D);

                if (var7 != null)
                {
                    var7.timeUntilPortal = var7.getPortalCooldown();
                }
            }
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = func_149999_b(par1IBlockAccess.getBlockMetadata(par2, par3, par4));

        if (l == 0)
        {
            if (par1IBlockAccess.getBlock(par2 - 1, par3, par4) != this && par1IBlockAccess.getBlock(par2 + 1, par3, par4) != this)
            {
                l = 2;
            }
            else
            {
                l = 1;
            }

            if (par1IBlockAccess instanceof World && !((World)par1IBlockAccess).isRemote)
            {
                ((World)par1IBlockAccess).setBlockMetadataWithNotify(par2, par3, par4, l, 2);
            }
        }

        float f = 0.125F;
        float f1 = 0.125F;

        if (l == 1)
        {
            f = 0.5F;
        }

        if (l == 2)
        {
            f1 = 0.5F;
        }

        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Checks to see if this location is valid to create a portal and will return True if it does. Args: world, x, y, z
     */
    public boolean tryToCreatePortal(World par1World, int par2, int par3, int par4)
    {
        BlockTofuPortal.Size size = new BlockTofuPortal.Size(par1World, par2, par3, par4, 1);
        BlockTofuPortal.Size size1 = new BlockTofuPortal.Size(par1World, par2, par3, par4, 2);

        if (size.func_150860_b() && size.field_150864_e == 0)
        {
            size.func_150859_c();
            return true;
        }
        else if (size1.func_150860_b() && size1.field_150864_e == 0)
        {
            size1.func_150859_c();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know register neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
    {
        int l = func_149999_b(par1World.getBlockMetadata(par2, par3, par4));
        BlockTofuPortal.Size size = new BlockTofuPortal.Size(par1World, par2, par3, par4, 1);
        BlockTofuPortal.Size size1 = new BlockTofuPortal.Size(par1World, par2, par3, par4, 2);

        if (l == 1 && (!size.func_150860_b() || size.field_150864_e < size.field_150868_h * size.field_150862_g))
        {
            par1World.setBlock(par2, par3, par4, Blocks.air);
        }
        else if (l == 2 && (!size1.func_150860_b() || size1.field_150864_e < size1.field_150868_h * size1.field_150862_g))
        {
            par1World.setBlock(par2, par3, par4, Blocks.air);
        }
        else if (l == 0 && !size.func_150860_b() && !size1.func_150860_b())
        {
            par1World.setBlock(par2, par3, par4, Blocks.air);
        }
    }

    public static int func_149999_b(int p_149999_0_)
    {
        return p_149999_0_ & 3;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @Override
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        int i1 = 0;

        if (par1IBlockAccess.getBlock(par2, par3, par4) == this)
        {
            i1 = func_149999_b(par1IBlockAccess.getBlockMetadata(par2, par3, par4));

            if (i1 == 0)
            {
                return false;
            }

            if (i1 == 2 && par5 != 5 && par5 != 4)
            {
                return false;
            }

            if (i1 == 1 && par5 != 3 && par5 != 2)
            {
                return false;
            }
        }

        boolean flag = par1IBlockAccess.getBlock(par2 - 1, par3, par4) == this && par1IBlockAccess.getBlock(par2 - 2, par3, par4) != this;
        boolean flag1 = par1IBlockAccess.getBlock(par2 + 1, par3, par4) == this && par1IBlockAccess.getBlock(par2 + 2, par3, par4) != this;
        boolean flag2 = par1IBlockAccess.getBlock(par2, par3, par4 - 1) == this && par1IBlockAccess.getBlock(par2, par3, par4 - 2) != this;
        boolean flag3 = par1IBlockAccess.getBlock(par2, par3, par4 + 1) == this && par1IBlockAccess.getBlock(par2, par3, par4 + 2) != this;
        boolean flag4 = flag || flag1 || i1 == 1;
        boolean flag5 = flag2 || flag3 || i1 == 2;
        return flag4 && par5 == 4 ? true : (flag4 && par5 == 5 ? true : (flag5 && par5 == 2 ? true : flag5 && par5 == 3));
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par5Entity.ridingEntity == null && par5Entity.riddenByEntity == null)
        {
            byte var3;

            if (par5Entity.worldObj.provider.dimensionId == Settings.tofuDimNo)
            {
                var3 = 0;
            }
            else
            {
                var3 = (byte)Settings.tofuDimNo;
            }

            if (!par1World.isRemote && par5Entity.worldObj instanceof WorldServer)
            {
                if (par5Entity instanceof EntityPlayerMP)
                {
                    EntityPlayerMP playermp = (EntityPlayerMP)par5Entity;
                    EntityInfo pinfo = EntityInfo.instance();
                    if (!pinfo.doesDataExist(playermp.getEntityId(), DataType.TicksPortalCooldown))
                    {
                        this.travelToDimension(var3, playermp);

                        // Make a sound on client side
                        PacketDispatcher.packet(new PacketDimTrip()).sendToPlayer(playermp);

                        TcAchievementMgr.achieve(playermp, Key.tofuWorld);
                    }
                    pinfo.set(playermp.getEntityId(), DataType.TicksPortalCooldown, this.getNewTripInfo(playermp.dimension));
                }
                else
                {
                    EntityInfo pinfo = EntityInfo.instance();
                    if (!pinfo.doesDataExist(par5Entity.getEntityId(), DataType.TicksPortalCooldown))
                    {
                        Entity traveledEntity = this.travelToDimension(var3, par5Entity);
                        if (traveledEntity != null)
                        {
                            pinfo.set(traveledEntity.getEntityId(), DataType.TicksPortalCooldown, this.getNewTripInfo(traveledEntity.dimension));
                        }
                    }
                    else
                    {
                        PortalTripInfo info = pinfo.get(par5Entity.getEntityId(), DataType.TicksPortalCooldown);
                        info.ticksCooldown = 0;
                    }
                }
            }
        }
    }

    private PortalTripInfo getNewTripInfo(int tripTo)
    {
        PortalTripInfo info = new PortalTripInfo();
        info.ticksCooldown = 0;
        info.dimensionIdTripTo = tripTo;
        return info;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns register pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par5Random.nextInt(100) == 0)
        {
            par1World.playSound(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, "portal.portal", 0.5F, par5Random.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int var6 = 0; var6 < 4; ++var6)
        {
            double var7 = (par2 + par5Random.nextFloat());
            double var9 = (par3 + par5Random.nextFloat());
            double var11 = (par4 + par5Random.nextFloat());
            double var13 = 0.0D;
            double var15 = 0.0D;
            double var17 = 0.0D;
            int var19 = par5Random.nextInt(2) * 2 - 1;
            var13 = (par5Random.nextFloat() - 0.5D) * 0.5D;
            var15 = (par5Random.nextFloat() - 0.5D) * 0.5D;
            var17 = (par5Random.nextFloat() - 0.5D) * 0.5D;

            if (par1World.getBlock(par2 - 1, par3, par4) != this && par1World.getBlock(par2 + 1, par3, par4) != this)
            {
                var7 = par2 + 0.5D + 0.25D * var19;
                var13 = (par5Random.nextFloat() * 2.0F * var19);
            }
            else
            {
                var11 = par4 + 0.5D + 0.25D * var19;
                var17 = (par5Random.nextFloat() * 2.0F * var19);
            }

            EntityTofuPortalFX fx = new EntityTofuPortalFX(par1World, var7, var9, var11, var13, var15, var17);
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    @Override
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        return Item.getItemById(0);
    }

    /**
     * Teleports the entity to another dimension. Params: Dimension number to teleport to
     */
    public void travelToDimension(int par1, EntityPlayerMP player)
    {
        teleportHandler.transferPlayerToDimension(player, par1);
    }

    /**
     * Teleports the entity to another dimension. Params: Dimension number to teleport to
     */
    public Entity travelToDimension(int par1, Entity entity)
    {
        return teleportHandler.transferEntityToDimension(entity, par1);
    }

    public static class Size
    {
        private static Block frameBlock = TcBlocks.tofuGrilled;
        private static Block portalBlock = TcBlocks.tofuPortal;

        private final World field_150867_a;
        private final int field_150865_b;
        private final int field_150866_c;
        private final int field_150863_d;
        private int field_150864_e = 0;
        private ChunkCoordinates field_150861_f;
        private int field_150862_g;
        private int field_150868_h;
        private static final String __OBFID = "CL_00000285";

        public Size(World p_i45415_1_, int p_i45415_2_, int p_i45415_3_, int p_i45415_4_, int p_i45415_5_)
        {
            this.field_150867_a = p_i45415_1_;
            this.field_150865_b = p_i45415_5_;
            this.field_150863_d = BlockTofuPortal.field_150001_a[p_i45415_5_][0];
            this.field_150866_c = BlockTofuPortal.field_150001_a[p_i45415_5_][1];

            for (int i1 = p_i45415_3_; p_i45415_3_ > i1 - 21 && p_i45415_3_ > 0 && this.func_150857_a(p_i45415_1_.getBlock(p_i45415_2_, p_i45415_3_ - 1, p_i45415_4_)); --p_i45415_3_)
            {
                ;
            }

            int j1 = this.func_150853_a(p_i45415_2_, p_i45415_3_, p_i45415_4_, this.field_150863_d) - 1;

            if (j1 >= 0)
            {
                this.field_150861_f = new ChunkCoordinates(p_i45415_2_ + j1 * Direction.offsetX[this.field_150863_d], p_i45415_3_, p_i45415_4_ + j1 * Direction.offsetZ[this.field_150863_d]);
                this.field_150868_h = this.func_150853_a(this.field_150861_f.posX, this.field_150861_f.posY, this.field_150861_f.posZ, this.field_150866_c);

                if (this.field_150868_h < 2 || this.field_150868_h > 21)
                {
                    this.field_150861_f = null;
                    this.field_150868_h = 0;
                }
            }

            if (this.field_150861_f != null)
            {
                this.field_150862_g = this.func_150858_a();
            }
        }

        protected int func_150853_a(int p_150853_1_, int p_150853_2_, int p_150853_3_, int p_150853_4_)
        {
            int j1 = Direction.offsetX[p_150853_4_];
            int k1 = Direction.offsetZ[p_150853_4_];
            int i1;
            Block block;

            for (i1 = 0; i1 < 22; ++i1)
            {
                block = this.field_150867_a.getBlock(p_150853_1_ + j1 * i1, p_150853_2_, p_150853_3_ + k1 * i1);

                if (!this.func_150857_a(block))
                {
                    break;
                }

                Block block1 = this.field_150867_a.getBlock(p_150853_1_ + j1 * i1, p_150853_2_ - 1, p_150853_3_ + k1 * i1);

                if (block1 != frameBlock)
                {
                    break;
                }
            }

            block = this.field_150867_a.getBlock(p_150853_1_ + j1 * i1, p_150853_2_, p_150853_3_ + k1 * i1);
            return block == frameBlock ? i1 : 0;
        }

        protected int func_150858_a()
        {
            int i;
            int j;
            int k;
            int l;
            label56:

            for (this.field_150862_g = 0; this.field_150862_g < 21; ++this.field_150862_g)
            {
                i = this.field_150861_f.posY + this.field_150862_g;

                for (j = 0; j < this.field_150868_h; ++j)
                {
                    k = this.field_150861_f.posX + j * Direction.offsetX[BlockTofuPortal.field_150001_a[this.field_150865_b][1]];
                    l = this.field_150861_f.posZ + j * Direction.offsetZ[BlockTofuPortal.field_150001_a[this.field_150865_b][1]];
                    Block block = this.field_150867_a.getBlock(k, i, l);

                    if (!this.func_150857_a(block))
                    {
                        break label56;
                    }

                    if (block == portalBlock)
                    {
                        ++this.field_150864_e;
                    }

                    if (j == 0)
                    {
                        block = this.field_150867_a.getBlock(k + Direction.offsetX[BlockTofuPortal.field_150001_a[this.field_150865_b][0]], i, l + Direction.offsetZ[BlockTofuPortal.field_150001_a[this.field_150865_b][0]]);

                        if (block != frameBlock)
                        {
                            break label56;
                        }
                    }
                    else if (j == this.field_150868_h - 1)
                    {
                        block = this.field_150867_a.getBlock(k + Direction.offsetX[BlockTofuPortal.field_150001_a[this.field_150865_b][1]], i, l + Direction.offsetZ[BlockTofuPortal.field_150001_a[this.field_150865_b][1]]);

                        if (block != frameBlock)
                        {
                            break label56;
                        }
                    }
                }
            }

            for (i = 0; i < this.field_150868_h; ++i)
            {
                j = this.field_150861_f.posX + i * Direction.offsetX[BlockTofuPortal.field_150001_a[this.field_150865_b][1]];
                k = this.field_150861_f.posY + this.field_150862_g;
                l = this.field_150861_f.posZ + i * Direction.offsetZ[BlockTofuPortal.field_150001_a[this.field_150865_b][1]];

                if (this.field_150867_a.getBlock(j, k, l) != frameBlock)
                {
                    this.field_150862_g = 0;
                    break;
                }
            }

            if (this.field_150862_g <= 21 && this.field_150862_g >= 3)
            {
                return this.field_150862_g;
            }
            else
            {
                this.field_150861_f = null;
                this.field_150868_h = 0;
                this.field_150862_g = 0;
                return 0;
            }
        }

        protected boolean func_150857_a(Block p_150857_1_)
        {
            return p_150857_1_.getMaterial() == Material.air || p_150857_1_ == Blocks.fire || p_150857_1_ == portalBlock;
        }

        public boolean func_150860_b()
        {
            return this.field_150861_f != null && this.field_150868_h >= 2 && this.field_150868_h <= 21 && this.field_150862_g >= 3 && this.field_150862_g <= 21;
        }

        public void func_150859_c()
        {
            for (int i = 0; i < this.field_150868_h; ++i)
            {
                int j = this.field_150861_f.posX + Direction.offsetX[this.field_150866_c] * i;
                int k = this.field_150861_f.posZ + Direction.offsetZ[this.field_150866_c] * i;

                for (int l = 0; l < this.field_150862_g; ++l)
                {
                    int i1 = this.field_150861_f.posY + l;
                    this.field_150867_a.setBlock(j, i1, k, portalBlock, this.field_150865_b, 2);
                }
            }
        }
    }

}
