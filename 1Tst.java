
// sudo apt install openjdk-8-jre-headless 
// sudo apt install openjdk-8-jdk-headless  

// import com.sun.net.httpserver.HttpServer;
// import com.sun.net.httpserver.HttpHandler;
// import com.sun.net.httpserver.HttpExchange;
// import java.io.File;
// import java.io.IOException;
// import java.nio.file.Files;

// public class Tst {
//     public static void main(String[] args) throws IOException {
//         int port = 8000; // Port to run the server on
//         HttpServer server = HttpServer.create(new java.net.InetSocketAddress(port), 0);
//         server.createContext("/", new HttpHandler() {
//             @Override
//             public void handle(HttpExchange exchange) throws IOException {
//                 File file = new File("index.html");
//                 if (file.exists()) {
//                     exchange.sendResponseHeaders(200, file.length());
//                     Files.copy(file.toPath(), exchange.getResponseBody());
//                 } else {
//                     String response = "404 Not Found";
//                     exchange.sendResponseHeaders(404, response.length());
//                     exchange.getResponseBody().write(response.getBytes());
//                 }
//                 exchange.close();
//             }
//         });
//         server.setExecutor(null);
//         server.start();
//         System.out.println("Server is running on port " + port);
//     }
// }



import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Tst {
    // In-memory database to store user data
    private static Map<String, String> users = new HashMap<>();

    public static void main(String[] args) throws IOException {
        int port = 8000; // Port to run the server on
        HttpServer server = HttpServer.create(new java.net.InetSocketAddress(port), 0);
        
        // Handler for landing page
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                File file = new File("index.html");
                if (file.exists()) {
                    exchange.sendResponseHeaders(200, file.length());
                    Files.copy(file.toPath(), exchange.getResponseBody());
                } else {
                    String response = "404 Not Found";
                    exchange.sendResponseHeaders(404, response.length());
                    exchange.getResponseBody().write(response.getBytes());
                }
                exchange.close();
            }
        });

        // Handler for login page
        server.createContext("/login", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // Perform redirection to login.html
                File file = new File("login.html");
                if (file.exists()) {
                    exchange.sendResponseHeaders(200, file.length());
                    Files.copy(file.toPath(), exchange.getResponseBody());
                } else {
                    String response = "404 Not Found";
                    exchange.sendResponseHeaders(404, response.length());
                    exchange.getResponseBody().write(response.getBytes());
                }
                exchange.close();
            }
        });

        // Handler for cart page
        server.createContext("/cart", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // Perform redirection to cart.html
                File file = new File("cart.html");
                if (file.exists()) {
                    exchange.sendResponseHeaders(200, file.length());
                    Files.copy(file.toPath(), exchange.getResponseBody());
                } else {
                    String response = "404 Not Found";
                    exchange.sendResponseHeaders(404, response.length());
                    exchange.getResponseBody().write(response.getBytes());
                }
                exchange.close();
            }
        });

        server.setExecutor(null);
        server.start();
        System.out.println("Server is running on port " + port);
    }
}
