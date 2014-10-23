package tsuteto.tofu.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.api.tileentity.TileEntityTfMachineBase;
import tsuteto.tofu.entity.EntityWhiteSmokeFX;
import tsuteto.tofu.tileentity.TileEntityTfAntenna;
import tsuteto.tofu.util.TileScanner;

import java.util.Random;

abstract public class BlockTfMachineBase extends BlockContainer4Directions
{
    protected final Random machineRand = new Random();
    protected final boolean isActive;
    protected IIcon iconFront;
    private boolean hasGuiScreen;
    private int guiId;

    protected BlockTfMachineBase(boolean isActive)
    {
        super(Material.iron);
        this.isActive = isActive;
    }

    public BlockTfMachineBase setGuiScreen(int guiId)
    {
        this.guiId = guiId;
        this.hasGuiScreen = true;
        return this;
    }

    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return Item.getItemFromBlock(this.getBlockIdle());
    }

    @SideOnly(Side.CLIENT)

    @Override
    public IIcon getIcon(int par1, int par2)
    {
        if (par1 == 1) // top
        {
            return this.blockIcon;
        }
        else if (par1 == 0) // bottom
        {
            return this.blockIcon;
        }
        else if (par1 == 3 && par2 == 0) // for inventory
        {
            return this.iconFront;
        }
        else
        {
            return par1 != (par2 & 7) ? this.blockIcon // side & back
                    : this.iconFront; // front
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("tofucraft:tfMachine_side");
        this.iconFront = par1IconRegister.registerIcon(this.isActive ? getFrontIconActive() : getFrontIconIdle());
    }

    abstract protected String getFrontIconActive();

    abstract protected String getFrontIconIdle();

    abstract protected Block getBlockActive();

    abstract protected Block getBlockIdle();

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (hasGuiScreen)
        {
            TileEntity tile = par1World.getTileEntity(par2, par3, par4);

            if (tile != null)
            {
                par5EntityPlayer.openGui(TofuCraftCore.instance, this.guiId, par1World, par2, par3, par4);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    @SideOnly(Side.CLIENT)

    @Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (this.isActive)
        {
            //int metadata = par1World.getBlockMetadata(par2, par3, par4);
            //int direction = metadata & 3;
            float var7 = par2 + 0.5F;
            float var8 = par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
            float var9 = par4 + 0.5F;
            float var10 = 0.52F;
            float var11 = par5Random.nextFloat() * 0.6F - 0.3F;
            EntityWhiteSmokeFX fx;

            fx = new EntityWhiteSmokeFX(par1World, (var7 - var10), var8, (var9 + var11), 0.0D, 0.0D, 0.0D);
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
            fx = new EntityWhiteSmokeFX(par1World, (var7 + var10), var8, (var9 + var11), 0.0D, 0.0D, 0.0D);
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
            fx = new EntityWhiteSmokeFX(par1World, (var7 + var11), var8, (var9 - var10), 0.0D, 0.0D, 0.0D);
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
            fx = new EntityWhiteSmokeFX(par1World, (var7 + var11), var8, (var9 + var10), 0.0D, 0.0D, 0.0D);
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);

        TileEntity tileEntity = par1World.getTileEntity(par2, par3, par4);

        if (tileEntity instanceof TileEntityTfMachineBase)
        {
            TileEntityTfMachineBase machineTE = (TileEntityTfMachineBase) tileEntity;
            if (par6ItemStack.hasDisplayName())
            {
                machineTE.setCustomName(par6ItemStack.getDisplayName());
            }
            machineTE.onTileEntityCreated(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
        }

        this.connectToTfAntenna(tileEntity);
    }

    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6, boolean keepMachineInventory)
    {
        TileEntity te = par1World.getTileEntity(par2, par3, par4);

        if (!keepMachineInventory && te instanceof TileEntityTfMachineBase)
        {
            TileEntityTfMachineBase var7 = (TileEntityTfMachineBase)te;

            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
            {
                ItemStack var9 = var7.getStackInSlot(var8);

                if (var9 != null)
                {
                    float var10 = this.machineRand.nextFloat() * 0.8F + 0.1F;
                    float var11 = this.machineRand.nextFloat() * 0.8F + 0.1F;
                    float var12 = this.machineRand.nextFloat() * 0.8F + 0.1F;

                    while (var9.stackSize > 0)
                    {
                        int var13 = this.machineRand.nextInt(21) + 10;

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
                        var14.motionX = ((float)this.machineRand.nextGaussian() * var15);
                        var14.motionY = ((float)this.machineRand.nextGaussian() * var15 + 0.2F);
                        var14.motionZ = ((float)this.machineRand.nextGaussian() * var15);
                        par1World.spawnEntityInWorld(var14);
                    }
                }
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    public void connectToTfAntenna(final TileEntity tileEntity)
    {
        TileScanner scanner = new TileScanner(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
        scanner.scan(BlockTfAntenna.MAX_RADIUS, TileScanner.Method.full, new TileScanner.Impl()
        {
            @Override
            public void apply(World world, int x, int y, int z)
            {
                TileEntity t = world.getTileEntity(x, y, z);
                if (t instanceof TileEntityTfAntenna)
                {
                    ((TileEntityTfAntenna)t).registerMachine(tileEntity);
                }
            }
        });
    }

    protected boolean isRedstonePowered(World world, int x, int y, int z)
    {
        return (world.getBlockMetadata(x, y, z) & 8) == 8;
    }

    @Override
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
    {
        return Container.calcRedstoneFromInventory((IInventory) par1World.getTileEntity(par2, par3, par4));
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        return Item.getItemFromBlock(getBlockIdle());
    }

}
