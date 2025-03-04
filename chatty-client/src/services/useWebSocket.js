import { useState, useEffect } from "react";

const useWebSocket = (url, token) => {
  const [messages, setMessages] = useState([]);
  const [socket, setSocket] = useState(null);

  useEffect(() => {

    if (!url || !token) return;
    const wsUrl = `${url}?token=${token}`;

    const ws = new WebSocket(wsUrl); // Create the WebSocket connection
    console.log("Attempting to connect to WebSocket:", url);


    // Handle incoming messages
    ws.onmessage = (event) => {
      try {
        const message = JSON.parse(event.data);
        console.log("Received message in websocket:", message);  // Log the received message
        setMessages([message]); 
      } catch (error) {
        console.error("Error parsing WebSocket message:", error, event.data);
      }
    };

    // Handle WebSocket connection errors
    ws.onerror = (error) => {
      console.error("WebSocket error:", error);
    };

    // Handle WebSocket connection close
    ws.onclose = () => {
      console.log("WebSocket connection closed. Attempting to reconnect...");
      setTimeout(() => {
        setSocket(null); // Recreate WebSocket connection
      }, 3000);
    };

    // Set the WebSocket object in the state
    setSocket(ws);

    // Cleanup WebSocket connection when the component is unmounted
    return () => {
      if (ws) {
        ws.close();
      }
    };
  }, [url]);

  return { messages, socket };
};

export default useWebSocket;
