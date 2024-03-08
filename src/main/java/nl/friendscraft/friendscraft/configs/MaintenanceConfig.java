package nl.friendscraft.friendscraft.configs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class MaintenanceConfig {
    private static Plugin instance;
    private static String filename;
    private static File CONFIG_FILE;
    private static final String HEADER = "Friends-Craft Maintenance Config\n" + "********************************\n";

    public static YamlConfiguration config;

    @SuppressWarnings("static-access")
    public MaintenanceConfig(String filename, String resource, Plugin instance) {
        this.load(filename, resource, instance);
    }

    @SuppressWarnings("static-access")
    public MaintenanceConfig(String filename, Plugin instance) {
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

        readConfig(MaintenanceConfig.class, null);
    }

    public void reload() {
        try {
            this.config.load(CONFIG_FILE);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        readConfig(MaintenanceConfig.class, null);
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


    public static String maintenanceKick = "De server ondergaat momenteel onderhoud. Probeer het later opnieuw.";
    public static String maintenanceOnlineKick = "De server is in maintenance mode geplaatst. Join later terug.";
    public static String addStaatAl = "&cDeze speler staat al op de whitelist momenteel.";
    public static String addToegevoegd = "&9Speler met naam &b%player% &9werd &atoegevoegd &9aan de maintenance whitelist.";
    public static String removeStaatNiet = "&cDeze speler staat niet op de whitelist momenteel.";
    public static String removeVerwijderd = "&9Speler met naam &b%player% &9werd &cverwijderd &9van de maintenance whitelist.";
    public static String statusAan = "&9De maintenance mode is momenteel &aingeschakeld&9.";
    public static String statusUit = "&9De maintenance mode is momenteel &cuitgeschakeld&9.";
    public static String enabled = "&9De maintenance mode is &aingeschakeld&9.";
    public static String enabledAl = "&cDe maintenance mode is al ingeschakeld.";
    public static String disabled = "&9De maintenance mode is &cuitgeschakeld&9.";
    public static String disabledAl = "&cDe maintenance mode is al uitgeschakeld.";
    public static String list = "&9Deze spelers staan op de maintenance whitelist: &b%players%&9.";
    public static String listNiemand = "&cEr staat niemand op de whitelist momenteel.";
    public static String bestaatNiet = "&cDeze speler bestaat niet of is nog nooit online geweest.";

    private static void maintenanceMessages() {
        maintenanceKick = getString("berichten.kick_bericht", maintenanceKick);
        maintenanceOnlineKick = getString("berichten.kick_bericht_online", maintenanceOnlineKick);
        addStaatAl = getString("berichten.al_toegevoegd", addStaatAl);
        addToegevoegd = getString("berichten.toegevoegd", addToegevoegd);
        removeStaatNiet = getString("berichten.niet_verwijderd", removeStaatNiet);
        removeVerwijderd = getString("berichten.verwijderd", removeVerwijderd);
        statusAan = getString("berichten.status_enabled", statusAan);
        statusUit = getString("berichten.status_disabled", statusUit);
        enabled = getString("berichten.ingeschakeld", enabled);
        enabledAl = getString("berichten.al_ingeschakeld", enabledAl);
        disabled = getString("berichten.uitgeschakeld", disabled);
        disabledAl = getString("berichten.al_uitgeschakeld", disabledAl);
        list = getString("berichten.lijst", list);
        listNiemand = getString("berichten.niemand_op_lijst", listNiemand);
        bestaatNiet = getString("berichten.bestaat_niet", bestaatNiet);
    }

    public static Boolean whitelistStatus = false;
    public static List<String> whitelist = new ArrayList<>();
    private static void maintenanceConfig() {
        whitelist = getList("users", whitelist);
        whitelistStatus = getBoolean("whitelist", whitelistStatus);
    }
}
