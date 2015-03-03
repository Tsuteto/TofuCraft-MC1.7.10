package tsuteto.tofu.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.gui.guiparts.*;
import tsuteto.tofu.tileentity.ContainerTfSaturator;
import tsuteto.tofu.tileentity.TileEntityTfSaturator;

public class GuiTfSaturator extends GuiTfMachineBase
{
    private final TileEntityTfSaturator machineInventory;

    private GuiPartGaugeBase chargeIcon = new GuiPartGaugeV(45, 36, TfMachineGuiParts.saturatorChargingBg, TfMachineGuiParts.saturatorCharging);
    private GuiRedstoneLamp redstoneLamp = new GuiRedstoneLamp(34, 53);
    private GuiPartTfCharge tfCharged = new GuiPartTfCharge(54, 52, TileEntityTfSaturator.COST_TF_PER_TICK / TileEntityTfSaturator.TF_CAPACITY);

    public GuiTfSaturator(InventoryPlayer par1InventoryPlayer, TileEntityTfSaturator tfSaturator)
    {
        super(new ContainerTfSaturator(par1InventoryPlayer, tfSaturator));
        this.xSize = 100;
        this.ySize = 80;
        this.machineInventory = tfSaturator;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int px, int py)
    {
        String s = this.machineInventory.hasCustomInventoryName() ? this.machineInventory.getInventoryName() : StatCollector.translateToLocal(this.machineInventory.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 0x5c5e54);

        redstoneLamp.showInfoTip(this, px, py);
        tfCharged.showInfoTip(this, px, py);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawBasePanel(this.xSize, this.ySize);

        if (machineInventory.paramSuffocating.get())
        {
            this.drawGuiPart(37, 28, TfMachineGuiParts.saturatorSuffocating);
        }
        else if(machineInventory.saturatingTime > 0)
        {
            this.drawGuiPart(37, 28, TfMachineGuiParts.saturatorSaturating);
        }
        else
        {
            chargeIcon.setPercentage(machineInventory.getProgressScaled()).draw(this);
        }

        redstoneLamp.setSwitch(machineInventory.isRedstonePowered()).draw(this);
        tfCharged.setPercentage(machineInventory.getTfCharged()).draw(this);
    }
}
