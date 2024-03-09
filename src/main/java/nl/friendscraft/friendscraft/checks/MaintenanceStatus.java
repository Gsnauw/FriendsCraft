package nl.friendscraft.friendscraft.checks;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import nl.friendscraft.friendscraft.configs.MaintenanceConfig;
import nl.friendscraft.friendscraft.utils.ChatUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MaintenanceStatus {
    private HttpServer server;

    public MaintenanceStatus(JavaPlugin plugin) {
        try {
            // Maak een HTTP-server die luistert op de poort
            server = HttpServer.create(new InetSocketAddress(MaintenanceConfig.httpserverport), 0);
            // Wijs een context toe aan het MaintenanceHandler object
            server.createContext("/maintenance", new MaintenanceHandler());
            server.setExecutor(null); // Default executor gebruiken
        } catch (IOException e) {
            ChatUtil.sendConsolePrefixError("Fout bij het starten van de HTTP-server: " + e.getMessage());
        }
    }

    public void startHttpServer() {
        if (server != null) {
            // Start de HTTP-server als deze niet al actief is
            server.start();
            ChatUtil.sendConsolePrefixInfo("De HTTP-server is gestart op poort " + MaintenanceConfig.httpserverport + ".");
        }
    }

    public void stopHttpServer() {
        if (server != null) {
            // Stop de HTTP-server als deze actief is
            server.stop(0);
            ChatUtil.sendConsolePrefixInfo("De HTTP-server is afgesloten.");
        }
    }

    private class MaintenanceHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Hier kun je logica toevoegen om te reageren op inkomende HTTP-verzoeken
            // Je kunt de status van onderhoud ophalen uit de MaintenanceConfig en een reactie terugsturen naar de client
            // Bijvoorbeeld:
            String response = "Maintenance status: " + (MaintenanceConfig.whitelistStatus ? "Active" : "Inactive");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        }
    }
}
