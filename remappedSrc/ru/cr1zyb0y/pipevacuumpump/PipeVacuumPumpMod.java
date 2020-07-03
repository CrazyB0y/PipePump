package ru.cr1zyb0y.pipevacuumpump;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PipeVacuumPumpMod implements ModInitializer
{
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "pipe_vacuum_pump";
    public static final String MOD_NAME = "PipePump";

    @Override
    public void onInitialize()
    {
        RegistryManager.Init();
        log(Level.INFO, "Mod successful loaded!");
    }

    public static void log(Level level, String message)
    {
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }
}