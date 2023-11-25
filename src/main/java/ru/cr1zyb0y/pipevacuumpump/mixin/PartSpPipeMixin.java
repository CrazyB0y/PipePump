package ru.cr1zyb0y.pipevacuumpump.mixin;

import alexiil.mc.lib.multipart.api.MultipartHolder;
import alexiil.mc.mod.pipes.pipe.PartSpPipe;
import alexiil.mc.mod.pipes.pipe.PipeSpBehaviour;
import alexiil.mc.mod.pipes.pipe.PipeSpBehaviourWood;
import alexiil.mc.mod.pipes.pipe.PipeSpDef;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.cr1zyb0y.pipevacuumpump.common.PartSpPipeEngineConnector;

@Mixin(PartSpPipe.class)
public abstract class PartSpPipeMixin {

    @Shadow(remap = false) public abstract BlockPos getPipePos();

    @Shadow(remap = false) public abstract World getPipeWorld();

    @Shadow(remap = false) @Final public PipeSpBehaviour behaviour;

    //engine connector class
    @Unique
    PartSpPipeEngineConnector EngineConnector;

    //constructor
    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    public void init(PipeSpDef definition, MultipartHolder holder, CallbackInfo ci) {

        if (PartSpPipeEngineConnector.contains((PartSpPipe) (Object) this)) {
            //get and remove
            EngineConnector = PartSpPipeEngineConnector.remove((PartSpPipe) (Object) this);
        } else {
            EngineConnector = new PartSpPipeEngineConnector();
            PartSpPipeEngineConnector.add((PartSpPipe) (Object) this, EngineConnector);
        }
    }

    //logic for connection to engine
    @Inject(method = "updateConnections", at = @At("TAIL"), remap = false)
    public void updateConnections(CallbackInfo ci) {
        if (behaviour instanceof PipeSpBehaviourWood)
            EngineConnector.findEngine(getPipePos(), getPipeWorld());
    }
}