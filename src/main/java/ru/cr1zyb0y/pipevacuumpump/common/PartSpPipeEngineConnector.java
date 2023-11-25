package ru.cr1zyb0y.pipevacuumpump.common;

import alexiil.mc.mod.pipes.pipe.PartSpPipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import ru.cr1zyb0y.pipevacuumpump.blocks.MachinePipePumpBlock;

import java.util.*;

public class PartSpPipeEngineConnector
{
    private BlockPos _pipeEnginePos;
    private int _lastTick;

    public static Map<PartSpPipe, PartSpPipeEngineConnector> tmpMap = new HashMap<>();

    public static void add(PartSpPipe pipe, PartSpPipeEngineConnector connector) {
        tmpMap.put(pipe, connector);
    }

    public static PartSpPipeEngineConnector get(PartSpPipe pipe) {
        return tmpMap.get(pipe);
    }

    public static PartSpPipeEngineConnector remove(PartSpPipe pipe) {
        return tmpMap.remove(pipe);
    }

    public static boolean contains(PartSpPipe pipe) {
        return tmpMap.containsKey(pipe);
    }

    //connect to engine
    public void findEngine(BlockPos pipePos, World world)
    {
        if(pipePos != null && world != null && _pipeEnginePos == null)
        {
            //get all directions
            List<Direction> dirs = new ArrayList<>();
            Collections.addAll(dirs, Direction.values());

            for (Direction dir : dirs)
            {
                //for start get block by direction
                BlockPos enginePos = pipePos.offset(dir);
                BlockState engineState = world.getBlockState(enginePos);
                Block engineBlockN = engineState.getBlock();
                if(engineBlockN instanceof MachinePipePumpBlock)
                {
                    //get facing direction
                    Direction facingDirection = ((MachinePipePumpBlock) engineBlockN).getFacing(engineState);

                    //check is block faced to pipe
                    BlockPos facedBlockPos = enginePos.offset(facingDirection);
                    if(facedBlockPos.equals(pipePos))
                    {
                        //connect to engine
                        _pipeEnginePos = enginePos;
                    }
                }
            }
        }
    }

    //check is we allow extraction
    public boolean isCanExtract(World world)
    {
        if(_pipeEnginePos != null && world != null)
        {
            BlockState state = world.getBlockState(_pipeEnginePos);
            Block block = state.getBlock();

            if (block instanceof MachinePipePumpBlock)
            {
                MachinePipePumpBlock engineBlock = (MachinePipePumpBlock) block;

                if(engineBlock.isActive(state))
                {
                    _lastTick++; //wait ticks before go

                    if(_lastTick >= engineBlock.getEngineTickSpeed())
                    {
                        _lastTick = 0;
                        return true;
                    }
                }
            }
            else
            {
                //set pos to null - disconnect
                _pipeEnginePos = null;
            }
        }

        return false;
    }

    //save connector data to nbt tag
    public void saveToNbt(NbtCompound tag)
    {
        if(_pipeEnginePos != null)
        {
            tag.putIntArray("engineConnector",
                    new int[] { _pipeEnginePos.getX(), _pipeEnginePos.getY(), _pipeEnginePos.getZ() });
        }
        else
        {
            tag.remove("engineConnector");
        }
    }

    //load connector data from nbt tag
    public void loadFromNbt(NbtCompound tag)
    {
        int[] blockPosArr = tag.getIntArray("engineConnector");
        if(blockPosArr != null && blockPosArr.length == 3)
        {
            _pipeEnginePos = new BlockPos(blockPosArr[0], blockPosArr[1], blockPosArr[2]);
        }
    }
}