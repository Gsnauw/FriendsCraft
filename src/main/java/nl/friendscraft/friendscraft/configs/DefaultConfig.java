package nl.friendscraft.friendscraft.configs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.logging.Level;

public class DefaultConfig {
    private static Plugin instance;
    private static String filename;
    private static File CONFIG_FILE;
    private static final String HEADER = "Friends-Craft Default Config\n" + "****************************\n";

    public static YamlConfiguration config;






    @SuppressWarnings("static-access")
    public DefaultConfig(String filename, String resource, Plugin instance) {
        this.load(filename, resource, instance);
    }

    @SuppressWarnings("static-access")
    public DefaultConfig(String filename, Plugin instance) {
        this.load(filename, null, instance);
    }

    private static void load(String fileName, String resource, Plugin iNstance) {
        filename = fileName;
        instance = iNstance;
        File file = new File(instance.getDataFolder() + "/" + filename);
        init(file);
    }

    private static void init(File configFile) {
        CONFIG_FILE = configFile;
        config = new YamlConfiguration();

        try {
            config.load(CONFIG_FILE);
        } catch (IOException ex) {
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load " + CONFIG_FILE + " , please correct your syntax errors", ex);

        }

        config.options().header(HEADER);
        config.options().copyDefaults(true);

        readConfig(nl.friendscraft.friendscraft.configs.DefaultConfig.class, null);
    }

    public void reload() {
        try {
            config.load(CONFIG_FILE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        readConfig(DefaultConfig.class, null);
    }


    static void readConfig(Class<?> clazz, Object instance) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isPrivate(method.getModifiers())) {
                if (method.getParameterTypes().length == 0 && method.getReturnType() == Void.TYPE) {
                    try {
                        method.setAccessible(true);
                        method.invoke(instance);
                    } catch (InvocationTargetException ex) {
                        Bukkit.getLogger().log(Level.SEVERE, "Error invoking " + ex.getCause(), ex);
                    } catch (Exception ex) {
                        Bukkit.getLogger().log(Level.SEVERE, "Error invoking " + method, ex);
                    }
                }
            }
        }

        try {
            config.save(CONFIG_FILE);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save " + CONFIG_FILE, ex);
        }
    }

    private static void set(String path, Object val) {
        config.set(path, val);
    }

    public static boolean getBool(String path) {
        return config.getBoolean(path, config.getBoolean(path));
    }

    private static boolean getBoolean(String path, boolean def) {
        config.addDefault(path, def);
        return config.getBoolean(path, config.getBoolean(path));
    }

    private static int getInt(String path, int def) {
        config.addDefault(path, def);
        return config.getInt(path, config.getInt(path));
    }

    @SuppressWarnings("rawtypes")
    private static <T> List getList(String path, T def) {
        config.addDefault(path, def);
        return (List<T>) config.getList(path, config.getList(path));
    }

    private static String getString(String path, String def) {
        config.addDefault(path, def);
        return config.getString(path, config.getString(path));
    }

    @SuppressWarnings("unused")
    private static double getDouble(String path, double def) {
        config.addDefault(path, def);
        return config.getDouble(path, config.getDouble(path));
    }


    public static Boolean debug = false;
    private static void DefaultConfigs() {
        debug = getBoolean("Debug", debug);
    }
}