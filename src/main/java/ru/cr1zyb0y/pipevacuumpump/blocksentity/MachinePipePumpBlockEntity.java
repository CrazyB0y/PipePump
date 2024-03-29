package ru.cr1zyb0y.pipevacuumpump.blocksentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import net.minecraft.world.World;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

import ru.cr1zyb0y.pipevacuumpump.RegistryManager;
import ru.cr1zyb0y.pipevacuumpump.blocks.MachinePipePumpBlock;

public class MachinePipePumpBlockEntity extends PowerAcceptorBlockEntity
{
    private int _energyCost = 1;

    public MachinePipePumpBlockEntity(BlockPos pos, BlockState state)
    {
        super(RegistryManager.PIPE_PUMP_BLOCK_ENTITY, pos, state);
    }

    //Consuming energy
    @Override
    public void tick(World world, BlockPos pos, BlockState state, MachineBaseBlockEntity blockEntity)
    {
        super.tick(world, pos, state, blockEntity);

        if (world == null || world.isClient)
        {
            return;
        }

        //BlockState state = world.getBlockState(getPos());
        Block block = state.getBlock();

        if (block instanceof MachinePipePumpBlock)
        {
            MachinePipePumpBlock vacuumPump = (MachinePipePumpBlock)block;
            Boolean isActive = vacuumPump.isActive(state);

            //Redstone signal turn off engine
            if(world.isReceivingRedstonePower(getPos()))
            {
                if(isActive)
                {
                    vacuumPump.setActive(false, world, pos);
                }

                return;
            }

            //Consume energy
            _energyCost = vacuumPump.getEnergyCost();
            long energyCost = getEuPerTick(_energyCost);
            if (getEnergy() > energyCost)
            {
                useEnergy(getEuPerTick(energyCost));

                if (!isActive)
                {
                    vacuumPump.setActive(true, world, pos);
                }
            }
            else if (isActive)
            {
                vacuumPump.setActive(false, world, pos);
            }
        }
    }

    //this is capacity
    @Override
    public long getBaseMaxPower() { return getBaseMaxInput() * 8; } //256 512 1024

    //return max input
    @Override
    public long getBaseMaxInput() { return _energyCost * 16 * 2; } // 32 64 128

    //we are won't generating energy
    @Override
    public long getBaseMaxOutput() { return 0; }

    //we are won't generating energy
    @Override
    public boolean canProvideEnergy(final Direction direction) { return false; }
}
