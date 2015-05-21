package tsuteto.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import tsuteto.tofu.Settings;
import tsuteto.tofu.block.render.RenderSaltPan;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.util.ModLog;
import tsuteto.tofu.util.TileScanner;

import java.util.Random;

public class BlockSaltPan extends TcBlock
{
    public enum Stat
    {
        EMPTY, WATER, SALT, BITTERN, NA
    }

    public BlockSaltPan()
    {
        super(Material.wood);
        this.setTickRandomly(true);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F * 6, 1.0F);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return Blocks.planks.getIcon(p_149691_1_, 0);
    }

    public int getRenderType()
    {
        return RenderSaltPan.renderId;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderBlockPass()
    {
        return Settings.canSaltPanUseAlpha ? 1 : 0;
    }

    public Stat getStat(IBlockAccess world, int x, int y, int z)
    {
        if (world.getBlock(x, y, z) == this)
        {
            int meta = world.getBlockMetadata(x, y, z);
            return this.getStat(meta);
        }
        else
        {
            return Stat.NA;
        }
    }

    public Stat getStat(int meta)
    {
        if (meta > 0 && meta < 4)
        {
            return Stat.WATER;
        }
        else if (meta == 4)
        {
            return Stat.SALT;
        }
        else if (meta == 8)
        {
            return Stat.BITTERN;
        }
        else
        {
            return Stat.EMPTY; // meta must be 0
        }
    }

    public boolean canConnectTo(IBlockAccess p_150091_1_, int p_150091_2_, int p_150091_3_, int p_150091_4_)
    {
        Block block = p_150091_1_.getBlock(p_150091_2_, p_150091_3_, p_150091_4_);
        return block == this;
    }

    public boolean onBlockActivated(World world, int tileX, int tileY, int tileZ, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            ItemStack itemHeld = player.inventory.getCurrentItem();
            Stat i1 = this.getStat(world, tileX, tileY, tileZ);

            if (i1 == Stat.EMPTY && itemHeld != null && itemHeld.getItem() == Items.water_bucket)
            {
                if (!player.capabilities.isCreativeMode)
                {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
                }

                TileScanner tileScanner = new TileScanner(world, tileX, tileY, tileZ);
                tileScanner.scan(1, TileScanner.Method.fullSimply, new TileScanner.Impl()
                {
                    @Override
                    public void apply(World world, int x, int y, int z)
                    {
                        if (BlockSaltPan.this.getStat(world, x, y, z) == Stat.EMPTY)
                        {
                            world.setBlockMetadataWithNotify(x, y, z, 1, 3);
                        }
                    }
                });

                world.setBlockMetadataWithNotify(tileX, tileY, tileZ, 1, 3);

                return true;
            }
            else if (i1 == Stat.BITTERN && itemHeld != null && itemHeld.getItem() == Items.glass_bottle)
            {
                ItemStack nigari = new ItemStack(TcItems.nigari);
                if (itemHeld.stackSize == 1)
                {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, nigari);
                    if (player instanceof EntityPlayerMP)
                    {
                        ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                    }
                }
                else
                {
                    if (!player.inventory.addItemStackToInventory(nigari))
                    {
                        world.spawnEntityInWorld(new EntityItem(world, (double)tileX + 0.5D, (double)tileY + 1.5D, (double)tileZ + 0.5D, nigari));
                    }
                    else if (player instanceof EntityPlayerMP)
                    {
                        ((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
                    }

                    itemHeld.stackSize--;
                }
                world.setBlockMetadataWithNotify(tileX, tileY, tileZ, 0, 3);

                return true;
            }
            else if (i1 == Stat.BITTERN && itemHeld == null)
            {
                world.setBlockMetadataWithNotify(tileX, tileY, tileZ, 0, 3);
            }
            else if (i1 == Stat.SALT)
            {
                ItemStack salt = new ItemStack(TcItems.salt, 1);

                float f = 0.7F;
                double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.2D + 0.6D;
                double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                EntityItem entityitem = new EntityItem(world, (double)tileX + d0, (double)tileY + d1, (double)tileZ + d2, salt);
                entityitem.delayBeforeCanPickup = 10;
                world.spawnEntityInWorld(entityitem);

                world.setBlockMetadataWithNotify(tileX, tileY, tileZ, 8, 3);
                return true;
            }
            return false;
        }
    }

    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
    {
        super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);

        int l = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);

        if (this.getStat(l) == Stat.WATER)
        {
            float f = this.calcAdaptation(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);

            if (f > 0.0F && p_149674_5_.nextInt((int) (25.0F / f) + 1) == 0)
            {
                ++l;
                p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, l, 2);
            }
        }
    }

    private float calcAdaptation(World world, int x, int y, int z)
    {
        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
        boolean isUnderTheSun = world.canBlockSeeTheSky(x, y, z);
        boolean isRaining = world.isRaining();
        boolean isDaytime = world.getWorldTime() % 24000 < 12000;
        float humidity = biome.rainfall;
        float temperature = biome.getFloatTemperature(x, y, z);
        float rate;

        if (!isUnderTheSun || isRaining)
        {
            rate = 0.0F;
        }
        else
        {
            rate = isDaytime ? 2.0F : 1.0F;
            rate *= humidity < 0.2D ? 4.0D : humidity < 0.7D ? 2.0D : humidity < 0.9 ? 1.0D : 0.5D;
            rate *= temperature < 0.0D ? 1.0D : temperature < 0.6D ? 1.5D : temperature < 1.0D ? 2.0D : 4.0D;
        }
        ModLog.debug("rate: %.2f", rate);
        return rate;
    }

    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
    {
        return World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_);
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
    }
}
