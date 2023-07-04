# Candy-Factory
Java project that simulates the simplified operation of a candy factory. The project includes various GUI applications (java fx) that allow interaction with users, order management, product management, and communication with customers and raw material distributors.

Customer Application:

Upon launching the application, users log in to the system by entering their username and password. There is also an option to register new users.
Registration and login to the system are performed through the factory's RESTful endpoints.
After registration approval by the operator, users can view all available products and create orders.
Products are obtained from a REST service.
When creating an order, users select desired products, and the order is sent to the factory's message queue (MQ) in XML format.
The order must be valid to be processed at the factory.
Factory User Management Application:

Allows the display of all client accounts in a tabular form, with options for approving, rejecting, deleting, and blocking accounts.
User accounts are stored in the "users.json" file on the server.
Factory Operations Application:

Enables CRUD operations on products, which are stored in a Redis database.
Provides the ability to automatically add test data when the application is launched.
Users can write promotional text that is multicast to all clients.
Order Viewing Application:

Operators log in by entering their name.
Login is performed using Secure Socket.
After logging in, operators can retrieve and process orders, which are retrieved based on the client's submission time from the MQ.
After reviewing, operators can approve or reject an order.
Clients automatically receive an email with information about the order status.
The order status information is stored in a text format on the factory server.
Raw Material Ordering Application:

Distributors are connected to the factory via RMI.
Distributors have their own applications where they enter company information and generate products.
Factory workers can view a list of all clients and the products of a selected client, and they can select desired products and enter the quantity for ordering.
Communication with distributors is performed via RMI.
![image](https://github.com/Nemanja1105/Candy-Factory/assets/93669392/389a1de9-b0f2-4123-93d6-ad49dc8fe6e2)
![image](https://github.com/Nemanja1105/Candy-Factory/assets/93669392/c3840623-4c1c-49f2-bf65-9365b49740ed)
![image](https://github.com/Nemanja1105/Candy-Factory/assets/93669392/b1151ac7-9fd7-42f3-9cd4-1cf32b85fdeb)
![image](https://github.com/Nemanja1105/Candy-Factory/assets/93669392/3dd9b885-d7a4-45ea-b57b-94b500bbd599)
![image](https://github.com/Nemanja1105/Candy-Factory/assets/93669392/2e6f0e47-30aa-4a69-bb5c-e46f1905abbb)
![image](https://github.com/Nemanja1105/Candy-Factory/assets/93669392/b1cc4a34-4d38-4d29-b0b3-6ab3dcaae6b6)
![image](https://github.com/Nemanja1105/Candy-Factory/assets/93669392/192598da-5d7c-4eed-aea9-8dd6ac2df7d8)







