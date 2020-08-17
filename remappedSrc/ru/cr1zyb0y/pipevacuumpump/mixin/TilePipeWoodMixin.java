package ru.cr1zyb0y.pipevacuumpump.mixin;

import ru.cr1zyb0y.pipevacuumpump.common.TilePipeEngineConnector;

import alexiil.mc.mod.pipes.blocks.*;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.Direction;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Function;

@Mixin(TilePipeWood.class)
public abstract class TilePipeWoodMixin extends TilePipeSided
{
    //engine connector class
    TilePipeEngineConnector EngineConnector = new TilePipeEngineConnector();

    //ctor
    public TilePipeWoodMixin(BlockEntityType<?> type, BlockPipe pipeBlock, Function<TilePipe, PipeFlow> flowConstructor)
    { super(type, pipeBlock, flowConstructor); }

    //logic for connection to engine
    @Override
    protected void onNeighbourChange()
    {
        super.onNeighbourChange();
        EngineConnector.findEngine(pos, world);
    }

    //save to tag
    @Override
    public CompoundTag toTag(CompoundTag tag)
    {
        tag = super.toTag(tag);
        EngineConnector.saveToTag(tag);
        return tag;
    }

    //load from tag
    @Override
    public void fromTag(CompoundTag tag)
    {
        super.fromTag(tag);
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
            tryExtract(dir);
            return;
        }
    }

    //extract method, overridden in derived class
    protected abstract void tryExtract(Direction dir);
}
