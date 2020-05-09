package ru.cr1zyb0y.pipevacuumpump.blocks;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.common.blocks.BlockMachineBase;

import ru.cr1zyb0y.pipevacuumpump.blocksentity.MachinePipePumpBlockEntity;

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
}
