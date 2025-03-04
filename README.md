
# Chatty

Chatty is a real-time chat application with AI bot capabilities. Clients establish a handshake via WebSocket for real-time communication, and messages are processed through Kafka events. Users can engage in conversations and interact with an LLM-powered bot using the `@bot` command.

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
- JWT authetication

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
To run the entire system locally using Docker Compose, follow these steps:

1. **Clone the repository:**
    ```bash
    git clone https://github.com/nissedanielsen/chatty.git
    cd chatty
    ```

2. **Build and start the services using Docker Compose:**
    ```bash
    docker-compose up --build
    ```
---
# Services

### 1. **chatty-kafka** (Kafka Broker)
   - **Ports**: `9092` (Access via `localhost:9092`)

### 2. **zookeeper** (Zookeeper for Kafka)
   - **Ports**: `2181` (Access via `localhost:2181`)

### 3. **postgres** (PostgreSQL Database)
   - **Ports**: `5432` (Access via `localhost:5432`)
   - **Environment Variables**:
     - `POSTGRES_DB`: `chatdb`
     - `POSTGRES_USER`: `chatuser`
     - `POSTGRES_PASSWORD`: `chatpass`

### 4. **chatty-ms** (Spring Boot Backend)
   - **Ports**: `8090` (Access via `localhost:8090`)
   - **Authentication**: The backend uses a JWT-based authentication flow. A JWT can be obtained from the `auth/login` endpoint upon successful authentication. Postgres db is preloaded with 10 users (`user1`, `user2`, ..., `user10`), each with the password `password`, password is salted and hashed. JWTs are valid for 1 day.

### 5. **chatty-client** (React Frontend)
   - **Ports**: `80` (Access via `localhost:80`)

### 6. **huggingface** (AI Bot Interaction - Flask API)
   - **Ports**: `5000` (Access via `localhost:5000`)