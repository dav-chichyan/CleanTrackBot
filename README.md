# CleanTrackBot

**CleanTrackBot** is a Telegram bot that allows users to check the status, price, and readiness of their cleaning orders (such as dry cleaning or laundry) by sending an order ID. If an order is still in cleaning, the bot automatically notifies users when their order is ready for pickup or delivery.

The bot uses Telegram webhooks for real-time updates and PostgreSQL for robust order data storage.

---

## Features

- **Order Status:** Send an order ID to receive up-to-date status and price information.
- **Automated Notifications:** Get notified automatically when your order is complete.
- **Real-Time Updates:** Uses Telegram webhooks for fast, reliable message delivery.
- **User-Friendly:** Simple commands and clear responses in Telegram chat.

---

## Technologies Used

- **Java & Spring Boot** — Core application logic and webhook handling.
- **Telegram Bot API** — For receiving user messages and sending notifications.
- **PostgreSQL** — For storing order and status information.
- **SQL** — For fast and reliable database queries.
- **Maven** — Project build and dependency management.
- **Docker** *(optional)* — For containerized deployment.

---

## Usage

1. **Start the bot** in Telegram.
2. **Send your order ID** as a message.
3. **Receive information** about your order’s current status and price.
4. **Get notified** when your order is ready, if it is still being cleaned.

---

## Example


---
🧾 Order Summary
---

🆔 Order ID: 47588

🧼 Cleaning Status: Cleaning

📦 Order Type: 🚚 Delivery Order

💰 Total Cost: 18000 ֏

✅ Order has been paid


🔔 We’ll notify you as soon as your order is ready.

🙏 Thank you for choosing MAQOOR!

---
🔔Notification
---
✅Your Order has been completed


🆔 Order ID: 47588

🧼 Cleaning Status: Completed

💰 Total Cost: 18000 ֏

✅ Order has been paid


🙏 Thank you for choosing MAQOOR!


## Setup

1. **Clone the repository:**
    ```
    git clone https://github.com/Chicho-byte/CleanTrackBot.git
    ```
2. **Configure your environment:**
    - Copy `application.properties.example` to `application.properties` in `/src/main/resources/` and update with your credentials.
    - Set up your PostgreSQL database and update connection details as needed.
    - Add your Telegram bot token.

3. **Build and run the project:**
    ```
    # Example with Maven
    mvn clean install
    java -jar target/your-jar-name.jar
    ```

---

## Configuration

All sensitive information (API tokens, DB credentials, etc.) should be stored in `application.properties`, which is **excluded from version control** for security.  
Provide your own config based on the example file.

---

## Contributing

Contributions, issues, and feature requests are welcome!  
Feel free to open an issue or submit a pull request.

---



## Contact
Questions? Open an issue or contact via Telegram!



