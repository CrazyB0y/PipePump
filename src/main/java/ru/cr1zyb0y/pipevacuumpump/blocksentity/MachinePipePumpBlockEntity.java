package ru.cr1zyb0y.pipevacuumpump.blocksentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

import ru.cr1zyb0y.pipevacuumpump.RegistryManager;
import ru.cr1zyb0y.pipevacuumpump.blocks.MachinePipePumpBlock;

public class MachinePipePumpBlockEntity extends PowerAcceptorBlockEntity
{
    public MachinePipePumpBlockEntity()
    {
        super(RegistryManager.PIPE_PUMP_BLOCK_ENTITY);
    }

    //Consuming energy
    @Override
    public void tick()
    {
        super.tick();

        if (world == null || world.isClient)
        {
            return;
        }

        BlockState state = world.getBlockState(pos);
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
            double energyCost = getEuPerTick(vacuumPump.getEnergyCost());
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
    public double getBaseMaxPower() { return 33; }

    //return max input
    @Override
    public double getBaseMaxInput() { return 32; }

    //we are won't generating energy
    @Override
    public double getBaseMaxOutput() { return 0; }

    //we are won't generating energy
    @Override
    public boolean canProvideEnergy(final Direction direction) { return false; }
}
