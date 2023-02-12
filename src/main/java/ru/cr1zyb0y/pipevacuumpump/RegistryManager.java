package ru.cr1zyb0y.pipevacuumpump;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ru.cr1zyb0y.pipevacuumpump.blocks.MachinePipePumpBlock;
import ru.cr1zyb0y.pipevacuumpump.blocksentity.MachinePipePumpBlockEntity;

public class RegistryManager
{
    //Blocks
    public static MachinePipePumpBlock PIPE_PUMP_BLOCK_TIER1;
    public static MachinePipePumpBlock PIPE_PUMP_BLOCK_TIER2;
    public static MachinePipePumpBlock PIPE_PUMP_BLOCK_TIER3;

    //Entity
    public static BlockEntityType<MachinePipePumpBlockEntity> PIPE_PUMP_BLOCK_ENTITY;

    //ItemGroup
    public static ItemGroup PIPE_PUMP_GROUP;

    //Init all things
    public static void Init()
    {
        //Create blocks
        PIPE_PUMP_BLOCK_TIER1 = new MachinePipePumpBlock(1, 30);
        PIPE_PUMP_BLOCK_TIER2 = new MachinePipePumpBlock(2, 20);
        PIPE_PUMP_BLOCK_TIER3 = new MachinePipePumpBlock(4, 10);

        //Create item group
        PIPE_PUMP_GROUP = FabricItemGroup.builder(
            new Identifier(PipeVacuumPumpMod.MOD_ID, "main"))
            .icon(() -> new ItemStack(PIPE_PUMP_BLOCK_TIER1))
            .build();

        //Reg blocks
        registerBlockWithItem("vacuum_pump_block_tier1", PIPE_PUMP_BLOCK_TIER1);
        registerBlockWithItem("vacuum_pump_block_tier2", PIPE_PUMP_BLOCK_TIER2);
        registerBlockWithItem("vacuum_pump_block_tier3", PIPE_PUMP_BLOCK_TIER3);

        //Reg entity
        PIPE_PUMP_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, getName("vacuum_pump"),
                FabricBlockEntityTypeBuilder.create(MachinePipePumpBlockEntity::new,
                        PIPE_PUMP_BLOCK_TIER1, PIPE_PUMP_BLOCK_TIER2, PIPE_PUMP_BLOCK_TIER3).build(null));
    }

    //Init client side effects
    @SuppressWarnings("MethodCallSideOnly")
    public static void ClientInit()
    {
        BlockRenderLayerMap.INSTANCE.putBlock(PIPE_PUMP_BLOCK_TIER1, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PIPE_PUMP_BLOCK_TIER2, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PIPE_PUMP_BLOCK_TIER3, RenderLayer.getCutout());
    }

    //Dumb way to register block with item
    private static void registerBlockWithItem(String name, Block block)
    {
        Identifier identity = getIdentifier(name);
        Registry.register(Registries.BLOCK, identity, block);
        Item BLOCK_ITEM = new BlockItem(block, new Item.Settings());
        Registry.register(Registries.ITEM, identity, BLOCK_ITEM);
        ItemGroupEvents.modifyEntriesEvent(PIPE_PUMP_GROUP).register((entries) -> entries.add(BLOCK_ITEM));
    }

    //Dumb way to get identifier
    private static Identifier getIdentifier(String name)
    {
        return new Identifier( PipeVacuumPumpMod.MOD_ID, name);
    }

    //Dumb way to get name
    private static String getName(String name)
    {
        return String.format("%s:%s", PipeVacuumPumpMod.MOD_ID, name);
    }
}
