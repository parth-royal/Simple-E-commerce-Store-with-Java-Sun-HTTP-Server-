## Simple E-commerce Store with Java (Sun HTTP Server)

This project implements a basic e-commerce store using Java and the Sun HTTP Server. It provides a simple web interface for browsing products, logging in, and viewing a shopping cart.

### Project Structure

The project consists of the following files:

* **Tst.java:** The main Java file, containing the server logic and handlers for different routes.
* **index.html:** The landing page of the store.
* **login.html:** The login page.
* **cart.html:** The shopping cart page.

### Functionality

The application provides the following functionalities:

* **Routing:**  It defines three routes:
    * `/`: The home page (index.html)
    * `/login`: The login page (login.html)
    * `/cart`: The cart page (cart.html)
* **Serving HTML Files:** Each route serves the corresponding HTML file.
* **User Data Storage:** It uses an in-memory `HashMap` (`users`) to store basic user data (currently not implemented).

### Key Components

* **Sun HTTP Server:** The embedded web server used to handle HTTP requests.
* **HttpHandler:**  Handles specific HTTP requests based on the defined routes.
* **HttpExchange:**  Represents an exchange between the server and the client.
* **`users` Map:**  Stores user data (currently not implemented).

### Running the Application

1. Ensure you have a Java Development Kit (JDK) installed on your system.
2. Compile the `Tst.java` file using `javac Tst.java`.
3. Run the compiled class using `java Tst`.
4. Open a web browser and navigate to `http://localhost:8000/` to access the home page.

### Further Development

This is a basic implementation with limited functionality. To extend the project, consider:

* **Database Integration:** Implement a database to store user data persistently and manage products.
* **Login/Registration:** Add functionality to handle user login and registration.
* **Cart Functionality:** Implement a shopping cart to allow users to add items, view their cart, and proceed to checkout.
* **Product Display:** Dynamically fetch product data from the database and display it on the home page.
* **Security:** Implement security measures for user data and prevent unauthorized access.
* **Additional Features:**  Add more features such as order processing, user accounts, and search functionality.

### Limitations

* **In-Memory Data:** User data is not persistent.
* **Limited Functionality:**  The project only provides basic functionality for routing and serving static HTML files.
* **Security:**  Security is not implemented; the application is vulnerable to various security threats.

This README provides a basic overview of the project. Further documentation for specific functionalities or additional features can be added as needed.
