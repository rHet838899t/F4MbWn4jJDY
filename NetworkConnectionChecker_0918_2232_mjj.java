// 代码生成时间: 2025-09-18 22:32:08
package com.example.checkers;

import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import static play.mvc.Results.ok;

/**
 * Network connection checker controller
 */
public class NetworkConnectionChecker extends Controller {

    private final WSClient ws;

    /**
     * Dependency injection for WSClient
     * @param ws the WSClient instance
     */
    @Inject
    public NetworkConnectionChecker(WSClient ws) {
        this.ws = ws;
    }

    /**
     * Checks the network connection status to a given host and port
     * @param host the host to check
     * @param port the port to check
     * @return a JSON response indicating the connection status
     */
    public CompletionStage<Result> checkConnection(String host, int port) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Create a socket and attempt to connect
                new Socket().connect(new InetSocketAddress(host, port), 3000);
                return ok("Connection to " + host + " on port " + port + " is established.");
            } catch (SocketTimeoutException e) {
                return ok("Connection to " + host + " on port " + port + " timed out.");
            } catch (Exception e) {
                return ok("Connection to " + host + " on port " + port + " failed.");
            }
        });
    }
}
