package ru.cr1zyb0y.pipevacuumpump.mixin;

import alexiil.mc.mod.pipes.pipe.PipeSpFlow;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import ru.cr1zyb0y.pipevacuumpump.common.TilePipeEngineConnector;

import alexiil.mc.mod.pipes.blocks.*;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.Direction;

import java.util.function.Function;

@Mixin(TilePipeWood.class)
public abstract class TilePipeWoodMixin extends TilePipeSided
{
    //engine connector class
    TilePipeEngineConnector EngineConnector = new TilePipeEngineConnector();

    //ctor
    public TilePipeWoodMixin(BlockEntityType<?> type, BlockPos pos, BlockState state, BlockPipe pipeBlock, Function<TilePipe, PipeSpFlow> flowConstructor)
    { super(type, pos, state, pipeBlock, flowConstructor); }

    //logic for connection to engine
    @Override
    protected void onNeighbourChange()
    {
        super.onNeighbourChange();
        EngineConnector.findEngine(pos, world);
    }

    //save to tag
    @Override
    public NbtCompound writeNbt(NbtCompound tag)
    {
        tag = super.writeNbt(tag);
        EngineConnector.saveToTag(tag);
        return tag;
    }

    //load from tag
    @Override
    public void readNbt(NbtCompound tag)
    {
        super.readNbt(tag);
        EngineConnector.loadFromTag(tag);
    }

    //main logic of extraction
    @Override
    public void tick()
    {
        //call base logic
        super.tick();

        //don't run on client
        if (world == null || world.isClient)
        {
            return;
        }

        //check that inventory connected
        Direction dir = currentDirection();
        if (dir == null)
        {
            return;
        }

        //now need check all things
        if(EngineConnector.isCanExtract(world))
        {
            tryExtract(dir, 1);
            return;
        }
    }

    //extract method, overridden in derived class
    protected abstract void tryExtract(Direction dir, int pulses);
}
