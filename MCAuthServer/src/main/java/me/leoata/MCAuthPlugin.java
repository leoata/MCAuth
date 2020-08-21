package me.leoata;

import lombok.Getter;
import me.leoata.brevis.Brevis;
import me.leoata.impl.APIClient;
import me.leoata.listener.MapListener;
import me.leoata.listener.ProtectionListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class MCAuthPlugin extends JavaPlugin {


    private static MCAuthPlugin instance;
    private Brevis brevis;
    private File configFile;
    private YamlConfiguration config;
    public void onEnable() {
        MCAuthPlugin.instance = this;
        this.brevis = new Brevis(this);
        Bukkit.getPluginManager().registerEvents(new MapListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProtectionListener(), this);

        this.setupConfig();
    }

    public void setupConfig(){
        this.config = new YamlConfiguration();

        this.configFile = new File(this.getDataFolder(), "config.yml");
        try {
            if (!this.getDataFolder().exists())
                this.getDataFolder().mkdirs();
            if (!this.configFile.exists())
                this.configFile.createNewFile();
        }catch (IOException ex){}
        this.loadConfig(this.config, this.configFile);

        this.config.options().copyDefaults(true).copyHeader(true);
        this.config.options().header("Cujo Operations LLC - Unauthorized distribution of this program could " +
                "lead to legal prosecution. Paste your server secret key below");
        this.config.addDefault("server.secretKey", "PASTE_KEY_HERE");
        this.config.addDefault("server.name", "PASTE_NAME_HERE");
        this.saveConfig(this.config, this.configFile);

    }

    public void loadConfig(YamlConfiguration yamlConfiguration, File file) {
        try {
            yamlConfiguration.load(file);
        }
        catch (Throwable t) {
            this.getLogger().severe("Failed to load " + file.getName() + " " + t.getClass().getSimpleName() + ": " + t.getMessage());
        }
    }

    public void saveConfig(YamlConfiguration yamlConfiguration, File file) {
        try {
            yamlConfiguration.save(file);
        }
        catch (Throwable t) {
            this.getLogger().severe("Failed to save " + file.getName() + " " + t.getClass().getSimpleName() + ": " + t.getMessage());
        }
    }

    public static MCAuthPlugin getInstance() {
        return instance;
    }

    public Brevis getBrevis() {
        return brevis;
    }

    public File getConfigFile() {
        return configFile;
    }

    @Override
    public YamlConfiguration getConfig() {
        return config;
    }

}
