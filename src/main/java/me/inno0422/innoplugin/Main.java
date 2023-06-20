package me.inno0422.innoplugin;

import me.inno0422.innoplugin.commands.TestCommand;
import me.inno0422.innoplugin.listeners.*;
import me.inno0422.innoplugin.utils.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {
    public static DataManager data;
    public static Main plugin;

    @Override
    public void onEnable() {
        System.out.println("Yes");
        // Plugin startup logic
        plugin = this;
        data = new DataManager(this);
        listener();
        command();
        setup();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void listener() {
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new AttackListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new DoubleJumpListener(), this);
        getServer().getPluginManager().registerEvents(new EnchantListener(), this);
    }
    public void command() {
        getCommand("test").setExecutor(new TestCommand());
    }
    private void setup() {
        File dataFolder = new File(getDataFolder().getPath());
        if (!dataFolder.exists()) {
            if (dataFolder.mkdir()) {
                getLogger().info("Data folder created successfully.");
            } else {
                getLogger().warning("Failed to create data folder.");
            }
        }
    }
}
