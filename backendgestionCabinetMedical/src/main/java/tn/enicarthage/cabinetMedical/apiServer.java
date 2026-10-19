package tn.enicarthage.cabinetMedical;

import com.sun.net.httpserver.HttpServer;

import tn.enicarthage.cabinetMedical.router.Router;

import java.io.IOException;
import java.net.InetSocketAddress;

public class apiServer {

    private final HttpServer server;

    public apiServer(int port) throws IOException {
        Router router = new Router();
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.server.createContext("/", router::dispatcher);
        this.server.setExecutor(null);
    }

    public void demarrer() {
        server.start();
    }

    public void arreter() {
        server.stop(0);
    }
}