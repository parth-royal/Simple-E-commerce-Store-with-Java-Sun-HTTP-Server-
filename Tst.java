import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.net.URLDecoder;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.nio.file.Files;

public class Tst {
    // In-memory database to store user data
    private static Map<String, Map<String, Object>> users = new HashMap<>();
    private static Map<String, Boolean> sessions = new HashMap<>();

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
                    OutputStream os = exchange.getResponseBody();
                    Files.copy(file.toPath(), os);
                    os.close();
                } else {
                    String response = "404 Not Found";
                    exchange.sendResponseHeaders(404, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
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
                    OutputStream os = exchange.getResponseBody();
                    Files.copy(file.toPath(), os);
                    os.close();
                } else {
                    String response = "404 Not Found";
                    exchange.sendResponseHeaders(404, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            }
        });

        // Handler for cart page
        server.createContext("/cart", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // Check if user is logged in
                String sessionId = exchange.getRequestHeaders().getFirst("Cookie");
                boolean loggedIn = sessionId != null && sessions.containsKey(sessionId) && sessions.get(sessionId);

                if (loggedIn) {
                    // Perform redirection to cart.html
                    File file = new File("cart.html");
                    if (file.exists()) {
                        exchange.sendResponseHeaders(200, file.length());
                        OutputStream os = exchange.getResponseBody();
                        Files.copy(file.toPath(), os);
                        os.close();
                    } else {
                        String response = "404 Not Found";
                        exchange.sendResponseHeaders(404, response.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    }
                } else {
                    // Redirect to login if not logged in
                    exchange.getResponseHeaders().set("Location", "/login");
                    exchange.sendResponseHeaders(302, -1);
                }
            }
        });

        // Handler for adding product to cart
        server.createContext("/add-to-cart", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // Check if user is logged in
                String sessionId = exchange.getRequestHeaders().getFirst("Cookie");
                boolean loggedIn = sessionId != null && sessions.containsKey(sessionId) && sessions.get(sessionId);

                if (loggedIn) {
                    // Get product ID from request
                    InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                    BufferedReader br = new BufferedReader(isr);
                    String formData = br.readLine();
                    String productId = URLDecoder.decode(formData.split("=")[1], "UTF-8");

                    // Add product to cart (logic to be implemented)
                    // For now, let's just print the product ID
                    System.out.println("Product added to cart: " + productId);

                    // Redirect to cart page
                    exchange.getResponseHeaders().set("Location", "/cart");
                    exchange.sendResponseHeaders(302, -1);
                } else {
                    // Redirect to login if not logged in
                    exchange.getResponseHeaders().set("Location", "/login");
                    exchange.sendResponseHeaders(302, -1);
                }
            }
        });

        // Handler for register page
        server.createContext("/register", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // Perform redirection to register.html
                File file = new File("register.html");
                if (file.exists()) {
                    exchange.sendResponseHeaders(200, file.length());
                    OutputStream os = exchange.getResponseBody();
                    Files.copy(file.toPath(), os);
                    os.close();
                } else {
                    String response = "404 Not Found";
                    exchange.sendResponseHeaders(404, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            }
        });

        // Handler for login form submission
        server.createContext("/login-submit", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine(); // Read form data
                String[] params = formData.split("&");
                String username = URLDecoder.decode(params[0].split("=")[1], "UTF-8");
                String password = URLDecoder.decode(params[1].split("=")[1], "UTF-8");

                // Simulate authentication (check if user exists)
                if (users.containsKey(username) && users.get(username).get("password").equals(password)) {
                    String sessionId = UUID.randomUUID().toString(); // Generate session ID
                    sessions.put(sessionId, true); // Store session ID
                    // Set session cookie
                    exchange.getResponseHeaders().set("Set-Cookie", "sessionId=" + sessionId + "; HttpOnly; Path=/");
                    // Redirect to landing page
                    exchange.getResponseHeaders().set("Location", "/");
                    exchange.sendResponseHeaders(302, -1);
                } else {
                    // Redirect to login with error message
                    exchange.getResponseHeaders().set("Location", "/login?error=1");
                    exchange.sendResponseHeaders(302, -1);
                }
                exchange.close();
            }
        });

        // Handler for registration form submission
        server.createContext("/register-submit", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine(); // Read form data
                String[] params = formData.split("&");
                String username = URLDecoder.decode(params[0].split("=")[1], "UTF-8");
                String password = URLDecoder.decode(params[1].split("=")[1], "UTF-8");
                String email = URLDecoder.decode(params[2].split("=")[1], "UTF-8");

                // Create user
                Map<String, Object> userData = new HashMap<>();
                userData.put("password", password);
                userData.put("email", email);
                users.put(username, userData);

                // Redirect to login page
                exchange.getResponseHeaders().set("Location", "/login");
                exchange.sendResponseHeaders(302, -1);
                exchange.close();
            }
        });

        // Handler for logout
        server.createContext("/logout", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String sessionId = exchange.getRequestHeaders().getFirst("Cookie");
                if (sessionId != null && sessions.containsKey(sessionId)) {
                    sessions.remove(sessionId); // Remove session
                }
                // Redirect to landing page
                exchange.getResponseHeaders().set("Location", "/");
                exchange.sendResponseHeaders(302, -1);
                exchange.close();
            }
        });

        server.setExecutor(null);
        server.start();
        System.out.println("Server is running on port " + port);
    }
}
