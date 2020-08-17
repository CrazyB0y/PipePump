package ru.cr1zyb0y.pipevacuumpump;

import net.fabricmc.api.ClientModInitializer;

public class PipeVacuumPumpModClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        RegistryManager.ClientInit();
    }
}
