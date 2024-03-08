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
import java.util.Arrays;
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

        readConfig(DefaultConfig.class, null);
    }

    public void reload() {
        try {
            this.config.load(CONFIG_FILE);

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

    public static void save(String path, Object val) {
        config.set(path, val);
        try {
            config.save(CONFIG_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public static String motd1 = "&6Friends&6-&9Craft&e";
    public static String motd2 = "&f&lDe server voor jou!";
    public static String maintenanceMotd1 = "&6De server is momenteel in onderhoud.";
    public static String maintenanceMotd2 = "&eKom later terug om te spelen op &bFriends&6-&9Craft&e!";
    private static void DefaultConfigs() {
        debug = getBoolean("Debug", debug);
        motd1 = getString("motd_lijn_1", motd1);
        motd2 = getString("motd_lijn_2", motd2);
        maintenanceMotd1 = getString("maintenance_motd_lijn_1", maintenanceMotd1);
        maintenanceMotd2 = getString("maintenance_motd_lijn_2", maintenanceMotd2);
    }
}