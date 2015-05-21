package tsuteto.tofu.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.gui.TcGuiHandler;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.tileentity.TileEntitySaltFurnace;

import java.util.Random;

public class BlockSaltFurnace extends BlockContainer
{
    private final Random furnaceRand = new Random();

    private final boolean isActive;
    
    private IIcon iconTop;
    private IIcon iconBottom;
    private IIcon iconFront;

    private static boolean keepFurnaceInventory = false;

    public BlockSaltFurnace(boolean par2)
    {
        super(Material.rock);
        this.isActive = par2;
    }

    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return Item.getItemFromBlock(TcBlocks.saltFurnaceIdle);
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Item.getItemFromBlock(TcBlocks.saltFurnaceIdle);
    }

    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDefaultDirection(par1World, par2, par3, par4);
    }

    private void setDefaultDirection(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isRemote)
        {
            Block block = par1World.getBlock(par2, par3, par4 - 1);
            Block block1 = par1World.getBlock(par2, par3, par4 + 1);
            Block block2 = par1World.getBlock(par2 - 1, par3, par4);
            Block block3 = par1World.getBlock(par2 + 1, par3, par4);
            byte b0 = 1;

            if (block.func_149730_j() && !block1.func_149730_j())
            {
                b0 = 1;
            }

            if (block1.func_149730_j() && !block.func_149730_j())
            {
                b0 = 0;
            }

            if (block2.func_149730_j() && !block3.func_149730_j())
            {
                b0 = 3;
            }

            if (block3.func_149730_j() && !block2.func_149730_j())
            {
                b0 = 2;
            }

            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        par2 &= 3;

        if (par1 == 1) // top
        {
            return this.iconTop;
        }
        else if (par1 == 0) // bottom
        {
            return this.iconBottom;
        }
        else if (par1 == 3 && par2 == 0) // for inventory
        {
            return this.iconFront;
        }
        else
        {
            return par1 != par2 + 2 ? this.blockIcon // side & back
                                : this.iconFront; // front
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("tofucraft:saltFurnace_side");
        this.iconFront = par1IconRegister.registerIcon(this.isActive ? "tofucraft:saltFurnace_front_lit" : "tofucraft:saltFurnace_front");
        this.iconTop = par1IconRegister.registerIcon(this.isActive ? "tofucraft:saltFurnace_top_lit" : "tofucraft:saltFurnace_top");
        this.iconBottom = par1IconRegister.registerIcon("tofucraft:saltFurnace_bottom");
    }
    
    @SideOnly(Side.CLIENT)

    @Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (this.isActive)
        {
            int metadata = par1World.getBlockMetadata(par2, par3, par4);
            int direction = metadata & 3;
            float var7 = par2 + 0.5F;
            float var8 = par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
            float var9 = par4 + 0.5F;
            float var10 = 0.52F;
            float var11 = par5Random.nextFloat() * 0.6F - 0.3F;
            
            if (direction == 2)
            {
                par1World.spawnParticle("smoke", (var7 - var10), var8, (var9 + var11), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (var7 - var10), var8, (var9 + var11), 0.0D, 0.0D, 0.0D);
            }
            else if (direction == 3)
            {
                par1World.spawnParticle("smoke", (var7 + var10), var8, (var9 + var11), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (var7 + var10), var8, (var9 + var11), 0.0D, 0.0D, 0.0D);
            }
            else if (direction == 0)
            {
                par1World.spawnParticle("smoke", (var7 + var11), var8, (var9 - var10), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (var7 + var11), var8, (var9 - var10), 0.0D, 0.0D, 0.0D);
            }
            else if (direction == 1)
            {
                par1World.spawnParticle("smoke", (var7 + var11), var8, (var9 + var10), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (var7 + var11), var8, (var9 + var10), 0.0D, 0.0D, 0.0D);
            }
            
            int cauldronStatus = TileEntitySaltFurnace.getCauldronStatus(metadata);
            float topX = par2 + 0.5F;
            float topY = par3 + 1.05F;
            float topZ = par4 + 0.5F;
            float randX = par5Random.nextFloat() * 0.6F - 0.3F;
            float randZ = par5Random.nextFloat() * 0.6F - 0.3F;

            if (cauldronStatus == 1)
            {
                float steamX = par2 + 0.5F;
                float steamY = par3 + 1.8F;
                float steamZ = par4 + 0.5F;
                for (int i = 0; i < 3; i++)
                {
                    float steamRandX = par5Random.nextFloat() * 0.6F - 0.3F;
                    float steamRandZ = par5Random.nextFloat() * 0.6F - 0.3F;
                    par1World.spawnParticle("smoke", (steamX + steamRandX), steamY, (steamZ + steamRandZ), 0.0D, 0.0D, 0.0D);
                }
            }
            else if (cauldronStatus == 2)
            {
                float steamX = par2 + 0.5F;
                float steamY = par3 + 1.8F;
                float steamZ = par4 + 0.5F;
                float steamRandX = par5Random.nextFloat() * 0.6F - 0.3F;
                float steamRandZ = par5Random.nextFloat() * 0.6F - 0.3F;
                double gRand1 = par5Random.nextGaussian() * 0.01D;
                double gRand2 = par5Random.nextGaussian() * 0.01D;
                double gRand3 = par5Random.nextGaussian() * 0.01D;
                par1World.spawnParticle("explode", (steamX + steamRandX), steamY, (steamZ + steamRandZ), gRand1, gRand2, gRand3);
            }
            par1World.spawnParticle("flame", (topX + randX), topY, (topZ + randZ), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntity tile = par1World.getTileEntity(par2, par3, par4);

        if (tile != null)
        {
            par5EntityPlayer.openGui(TofuCraftCore.instance, TcGuiHandler.GUIID_SALT_FURNACE, par1World, par2, par3, par4);
        }

        return true;
    }

    public static void updateFurnaceBlockState(boolean par0, World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity var6 = par1World.getTileEntity(par2, par3, par4);
        keepFurnaceInventory = true;

        if (par0)
        {
            par1World.setBlock(par2, par3, par4, TcBlocks.saltFurnaceActive);
        }
        else
        {
            par1World.setBlock(par2, par3, par4, TcBlocks.saltFurnaceIdle);
            
            Block blockAbove = par1World.getBlock(par2, par3 + 1, par4);
            if (blockAbove == Blocks.fire)
            {
                par1World.setBlockToAir(par2, par3 + 1, par4);
            }
        }

        keepFurnaceInventory = false;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var5, 2);

        if (var6 != null)
        {
            var6.validate();
            par1World.setTileEntity(par2, par3, par4, var6);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World par1World, int i)
    {
        return new TileEntitySaltFurnace();
    }

    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        int var6 = MathHelper.floor_double((par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var6 == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 2);
        }

        if (var6 == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
        }

        if (var6 == 2)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 2);
        }

        if (var6 == 3)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
        }

        if (par6ItemStack.hasDisplayName())
        {
            ((TileEntitySaltFurnace)par1World.getTileEntity(par2, par3, par4)).setCustomName(par6ItemStack.getDisplayName());
        }
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
        if (!keepFurnaceInventory)
        {
            TileEntitySaltFurnace var7 = (TileEntitySaltFurnace)par1World.getTileEntity(par2, par3, par4);

            if (var7 != null)
            {
                for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
                {
                    ItemStack var9 = var7.getStackInSlot(var8);

                    if (var9 != null)
                    {
                        float var10 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float var11 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float var12 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;

                        while (var9.stackSize > 0)
                        {
                            int var13 = this.furnaceRand.nextInt(21) + 10;

                            if (var13 > var9.stackSize)
                            {
                                var13 = var9.stackSize;
                            }

                            var9.stackSize -= var13;
                            EntityItem var14 = new EntityItem(par1World, (par2 + var10), (par3 + var11), (par4 + var12), new ItemStack(var9.getItem(), var13, var9.getItemDamage()));

                            if (var9.hasTagCompound())
                            {
                                var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                            }

                            float var15 = 0.05F;
                            var14.motionX = ((float)this.furnaceRand.nextGaussian() * var15);
                            var14.motionY = ((float)this.furnaceRand.nextGaussian() * var15 + 0.2F);
                            var14.motionZ = ((float)this.furnaceRand.nextGaussian() * var15);
                            par1World.spawnEntityInWorld(var14);
                        }
                    }
                }
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    @Override
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
    {
        return Container.calcRedstoneFromInventory((IInventory)par1World.getTileEntity(par2, par3, par4));
    }
}
