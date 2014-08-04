package tsuteto.tofu.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.tileentity.ContainerTfSaturator;
import tsuteto.tofu.tileentity.TileEntityTfSaturator;

public class GuiTfSaturator extends GuiTfMachineBase
{
    private final TileEntityTfSaturator machineInventory;

    private GuiLamp statusIcon = new GuiLamp(39, 30, TfMachineGuiParts.saturatorCharging, TfMachineGuiParts.saturatorSaturating);
    private GuiRedstoneLamp redstoneLamp = new GuiRedstoneLamp(44, 53);

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
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        this.mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawBasePanel(this.xSize, this.ySize);

        statusIcon.setSwitch(machineInventory.saturatingLampTick > 0).draw(this);
        redstoneLamp.setSwitch(machineInventory.isRedstonePowered()).draw(this);
    }
}
