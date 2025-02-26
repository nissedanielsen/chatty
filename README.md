
# Chatty

Chatty is a real-time chat application with AI bot capabilities. Users can engage in conversations and interact with an LLM-powered bot using the `@bot` command.

# Tech Stack

## Frontend
- React
- Material UI

## Backend
- Spring Boot (Java)
- Kafka & Zookeeper for message streaming
- PostgreSQL for data persistence
- WebSockets for real-time communication

## AI Integration
- HuggingFace LLM
- Python Flask API for LLM interaction

# Features
- Real-time messaging through WebSockets
- Message persistence in PostgreSQL database
- AI bot integration via the `@bot` command

# System Architecture
1. Users connect to the system via WebSockets.
2. Messages are published to Kafka topics.
3. Messages are persisted in PostgreSQL.
4. Kafka listeners process messages and broadcast them to relevant WebSocket clients.
5. Messages containing `@bot` are sent to the HuggingFace LLM via a Flask API.
6. AI responses are sent back through the system and delivered to users through websocket.

# Setup and Installation

## Prerequisites
- Docker and Docker Compose
- Git

## Running the Application Using Docker Compose
To run the entire system locally using Docker Compose, follow these steps (ensure that the backend is built and the JAR file is generated in the target directory).

1. **Clone the repository:**
    ```bash
    git clone https://github.com/nissedanielsen/EventMonitor.git
    cd EventMonitor
    ```

2. **Build and start the services using Docker Compose:**
    ```bash
    docker-compose up --build
    ```
