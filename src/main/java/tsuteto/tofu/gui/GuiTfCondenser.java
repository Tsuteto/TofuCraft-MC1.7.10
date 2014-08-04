package tsuteto.tofu.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.tileentity.ContainerTfCondenser;
import tsuteto.tofu.tileentity.TileEntityTfCondenser;
import tsuteto.tofu.fluids.TcFluids;
import tsuteto.tofu.item.TcItems;

import java.util.Iterator;

@SideOnly(Side.CLIENT)
public class GuiTfCondenser extends GuiTfMachineBase
{
    private final TileEntityTfCondenser machineInventory;

    private GuiPartGaugeV nigariGauge = new GuiPartGaugeV(33, 18, TfMachineGuiParts.gaugeV2Frame, TfMachineGuiParts.gaugeV2)
            .setIndicatorColor(0xc0d0ff)
            .setItemDisplay(TfMachineGuiParts.gaugeVItemDisplay)
            .setItemStack(new ItemStack(TcItems.nigari))
            .setFluidStack(new FluidStack(TcFluids.NIGARI, 0))
            .setInfoTip(51, 22, HoverTextPosition.LOWER_CENTER);

    private GuiPartGaugeV additiveGauge = new GuiPartGaugeV(72, 18, TfMachineGuiParts.gaugeV2Frame, TfMachineGuiParts.gaugeV2)
            .setIndicatorColor(0xb0f0a7)
            .setItemDisplay(TfMachineGuiParts.gaugeVItemDisplay)
            .setInfoTip(51, 22, HoverTextPosition.LOWER_CENTER);

    private GuiPartGaugeV tfGauge = new GuiPartGaugeV(95, 18, TfMachineGuiParts.gaugeV2Frame, TfMachineGuiParts.gaugeV2)
            .setInfoTip(51, 22, HoverTextPosition.LOWER_CENTER);

    private GuiPartGaugeH progress = new GuiPartGaugeH(115, 35, TfMachineGuiParts.progressArrowBg, TfMachineGuiParts.progressArrow);

    private GuiTcButtonFixed btnAdditiveDrop;
    private GuiRedstoneLamp redstoneLamp = new GuiRedstoneLamp(121, 58);

    public GuiTfCondenser(InventoryPlayer par1InventoryPlayer, TileEntityTfCondenser tfstorage)
    {
        super(new ContainerTfCondenser(par1InventoryPlayer, tfstorage));
        this.ySize = 185;
        this.machineInventory = tfstorage;
    }

    public void initGui()
    {
        super.initGui();

        btnAdditiveDrop = new GuiTcButtonFixed(0, this.guiLeft + 56, this.guiTop + 67, TfMachineGuiParts.btnSmallEnabled, StatCollector.translateToLocal("tofucraft.drop"))
                .setTextureDisabled(TfMachineGuiParts.btnSmallDisabled)
                .setTextureHover(TfMachineGuiParts.btnSmallHover);

        buttonList.add(btnAdditiveDrop);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int px, int py)
    {
        String s = this.machineInventory.hasCustomInventoryName() ? this.machineInventory.getInventoryName() : StatCollector.translateToLocal(this.machineInventory.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 0x5c5e54);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 3, 0x5c5e54);

        Iterator iterator = this.buttonList.iterator();

        while (iterator.hasNext())
        {
            GuiTcButtonBase guibutton = (GuiTcButtonBase) iterator.next();

            if (guibutton.func_146115_a())
            {
                guibutton.onMouseOver(px, py, this);
                break;
            }
        }

        /*
         * Info Tips
         */
        nigariGauge.showInfoTip(this, px, py, new IHoverDrawingHandler()
        {
            @Override
            public void draw(int ox, int oy, int fw, int fh)
            {
                String s;
                s = String.format("%dmB", machineInventory.nigariTank.getFluidAmount());
                fontRendererObj.drawString(s, ox + 46 - fontRendererObj.getStringWidth(s), oy + 2, COLOR_TIP_TEXT);

                s = String.format("/%dmB", machineInventory.nigariTank.getCapacity());
                fontRendererObj.drawString(s, ox + 50 - fontRendererObj.getStringWidth(s), oy + 12, 0xb4b5aa);
            }
        });

        additiveGauge.showInfoTip(this, px, py, new IHoverDrawingHandler()
        {
            @Override
            public void draw(int ox, int oy, int fw, int fh)
            {
                String s;
                s = String.format("%dmB", machineInventory.additiveTank.getFluidAmount());
                fontRendererObj.drawString(s, ox + 46 - fontRendererObj.getStringWidth(s), oy + 2, COLOR_TIP_TEXT);

                s = String.format("/%dmB", machineInventory.additiveTank.getCapacity());
                fontRendererObj.drawString(s, ox + 50 - fontRendererObj.getStringWidth(s), oy + 12, 0xb4b5aa);
            }
        });

        tfGauge.showInfoTip(this, px, py, new IHoverDrawingHandler()
        {
            @Override
            public void draw(int ox, int oy, int fw, int fh)
            {
                String s;
                s = String.format("%.0f", machineInventory.tfPooled);
                fontRendererObj.drawString(s, ox + 41 - fontRendererObj.getStringWidth(s), oy + 2, COLOR_TIP_TEXT);

                s = String.format("/%.0f", machineInventory.tfNeeded);
                fontRendererObj.drawString(s, ox + 41 - fontRendererObj.getStringWidth(s), oy + 12, 0xb4b5aa);

                printTfSign(ox + 41, oy + 2, COLOR_TIP_TEXT);
                printTfSign(ox + 41, oy + 12, 0xb4b5aa);
            }
        });

        redstoneLamp.showInfoTip(this, px, py);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawStandardBasePanel();
        this.drawTfMachineSlot();

        this.drawGuiPart(17, 42, TfMachineGuiParts.smallArrowDown);
        this.drawGuiPart(57, 42, TfMachineGuiParts.smallArrowDown);

        // Nigari guage
        nigariGauge
                .setPercentage((double) machineInventory.nigariTank.getFluidAmount() / (double) machineInventory.nigariTank.getCapacity())
                .draw(this);

        // Additive guage
        additiveGauge
                .setPercentage((double) machineInventory.additiveTank.getFluidAmount() / (double) machineInventory.additiveTank.getCapacity())
                .setItemStack(machineInventory.additiveFluidItem)
                .setFluidStack(machineInventory.additiveTank.getFluid())
                .draw(this);

        tfGauge
                .setPercentage(machineInventory.tfPooled / machineInventory.tfNeeded)
                .draw(this);
        this.drawGuiPart(95, 62, TfMachineGuiParts.tfMark);

        progress.setPercentage((double) machineInventory.processTimeOutput / (double) machineInventory.wholeTimeOutput)
                .draw(this);

        redstoneLamp.setSwitch(machineInventory.isRedstonePowered()).draw(this);

        // Item Stacks
        nigariGauge.drawItem(this);
        additiveGauge.drawItem(this);
    }

    protected void actionPerformed(GuiButton btn)
    {
        EntityClientPlayerMP player = this.mc.thePlayer;

        if (btn.id == 0)
        {
            this.machineInventory.additiveTank.setFluid(null);
            this.machineInventory.postGuiControl(player.openContainer.windowId, 0);
        }
    }

}
