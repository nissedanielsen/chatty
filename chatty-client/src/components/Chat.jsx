import { useState, useEffect } from "react";
import useWebSocket from "../services/useWebSocket"; 
import SendMessageForm from "./SendMessageForm";
import { fetchMessagesByChatId } from "../services/messageService";

const Chat = () => {
  const [chatId, setChatId] = useState("");
  const [userId, setUserId] = useState("");
  const [connected, setConnected] = useState(false);
  const [socketUrl, setSocketUrl] = useState(null);

  const { messages, socket } = useWebSocket(socketUrl);

  useEffect(() => {
    if (chatId && connected) {
      fetchMessages();
    }
  }, [chatId, connected]);

  // Call the service to fetch messages and log them
  const fetchMessages = async () => {
    try {
      const messages = await fetchMessagesByChatId(chatId); // Fetch messages from backend
      console.log("Fetched messages:", messages); // Log the fetched messages
    } catch (error) {
      console.error("Error fetching messages:", error);
    }
  };

  const handleConnect = () => {
    if (chatId.trim() && userId.trim()) {
      const newUrl = `ws://localhost:8090/ws/joinchat/${chatId}`;
      console.log("Connecting to:", newUrl);
      setSocketUrl(newUrl);
      setConnected(true);
    } else {
      console.error("Chat ID and User ID cannot be empty!");
    }
  };

  return (
    <div>
      {!connected ? (
        <div>
          <h2>Enter Chat Details</h2>
          <input
            type="text"
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
            placeholder="Enter your username..."
          />
          <input
            type="text"
            value={chatId}
            onChange={(e) => setChatId(e.target.value)}
            placeholder="Enter chat ID..."
          />
          <button onClick={handleConnect}>Join Chat</button>
        </div>
      ) : (
        <div>
          <h2>Chat Room: {chatId} - Username: {userId}</h2>
          <ul>
            {messages.map((msg, index) => (
              <li key={index}>
                <strong>{msg.senderId === userId ? "Me" : msg.senderId}:</strong> {msg.content}
              </li>
            ))}
          </ul>
          <SendMessageForm socket={socket} userId={userId} chatId={chatId} />
        </div>
      )}
    </div>
  );
};

export default Chat;
