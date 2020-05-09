package ru.cr1zyb0y.pipevacuumpump.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;

import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.common.blocks.BlockMachineBase;

import ru.cr1zyb0y.pipevacuumpump.blocksentity.MachinePipePumpBlockEntity;

import java.util.List;

public class MachinePipePumpBlock extends BlockMachineBase
{
    private int _energyCost;
    private int _pumpSpeedTick;

    public MachinePipePumpBlock(int energyCost, int pumpSpeedTick)
    {
        _energyCost = energyCost;
        _pumpSpeedTick = pumpSpeedTick;
    }

    //Get pump item speed
    public int getEngineTickSpeed()
    {
        return _pumpSpeedTick;
    }

    //Now is no gui, later we need add GUI with "vacuuming progress" and upgrades
    @Override
    public IMachineGuiHandler getGui() { return null; }

    //Base cost per tick
    public int getEnergyCost() { return _energyCost; }

    //Create block entity
    @Override
    public BlockEntity createBlockEntity(BlockView worldIn) { return new MachinePipePumpBlockEntity(); }

    //make tooltip for block
    @Environment(EnvType.CLIENT)
    @Override
    public void buildTooltip(ItemStack stack, BlockView worldIn, List<Text> tooltip, TooltipContext flagIn)
    {
        if(Screen.hasShiftDown())
        {
            tooltip.add(new TranslatableText("pipe_vacuum_pump.tooltip.vacuum_pump_block").formatted(Formatting.GOLD));
        }
        else
        {
            tooltip.add(new TranslatableText("pipe_vacuum_pump.tooltip.hold_shift").formatted(Formatting.BLUE));
        }

        tooltip.add(new TranslatableText("pipe_vacuum_pump.tooltip.speed",
                Formatting.GOLD, "1", getEngineTickSpeed()).formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("pipe_vacuum_pump.tooltip.consumption",
                Formatting.GOLD, getEnergyCost()).formatted(Formatting.GRAY));
    }
}
