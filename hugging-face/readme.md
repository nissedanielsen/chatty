# Chatty

Chatty is a real-time chat application with AI bot capabilities. Users can engage in conversations and interact with an LLM-powered bot by using the @bot command.

# Tech Stack

## Frontend
React
Material UI

## Backend
Spring Boot (Java)
Kafka & Zookeeper for message streaming
PostgreSQL for data persistence
WebSockets for real-time communication

## AI Integration
HuggingFace LLM
Python Flask API for LLM interaction

## Features
Real-time messaging through WebSockets
Message persistence in PostgreSQL database
AI bot integration with @bot command

## System Architecture
Users connect to the system via WebSockets
Messages are published to Kafka topics
Messages are persisted in PostgreSQL
Kafka listeners process messages and broadcast them to relevant WebSocket clients
Messages containing @bot are sent to the HuggingFace LLM via a Flask API
AI responses are sent back through the system and delivered to users


# Setup and Installation
## Prerequisites
Docker and Docker Compose
Git

## Running the Application Using Docker Compose
To run the entire system locally using Docker Compose, follow these steps. (Make sure you have built backend and jar is generated in target)

1. **Clone the repository:**
    ```bash
    git clone https://github.com/nissedanielsen/EventMonitor.git
    cd EventMonitor
    ```

2. **Build and start the services using Docker Compose:**
    ```bash
    docker-compose up --build
    ```