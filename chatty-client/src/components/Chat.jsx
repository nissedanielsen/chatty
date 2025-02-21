import { useState, useEffect } from "react";
import useWebSocket from "../services/useWebSocket"; 
import SendMessageForm from "./SendMessageForm";
import { fetchMessagesByChatId } from "../services/messageService";

const Chat = () => {
  const [chatId, setChatId] = useState("");
  const [userId, setUserId] = useState("");
  const [connected, setConnected] = useState(false);
  const [socketUrl, setSocketUrl] = useState(null);
  const [allMessages, setAllMessages] = useState([]); // State to store both REST and WebSocket messages

  const { messages: wsMessages, socket } = useWebSocket(socketUrl);

  // Fetch messages from REST API
  useEffect(() => {
    if (chatId && connected) {
      fetchMessages();
    }
  }, [chatId, connected]);

  const fetchMessages = async () => {
    try {
      const messages = await fetchMessagesByChatId(chatId); // Fetch messages from backend
      console.log("Fetched messages:", messages); // Log the fetched messages
      setAllMessages(messages); // Set the fetched messages into state
    } catch (error) {
      console.error("Error fetching messages:", error);
    }
  };

  // Use WebSocket messages and add them to the state when they come in
  useEffect(() => {
    if (wsMessages.length > 0) {
      setAllMessages((prevMessages) => [...prevMessages, ...wsMessages]);
    }
  }, [wsMessages]); // Trigger this effect whenever WebSocket messages arrive

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
            {allMessages.map((msg, index) => (
              <li key={index}>
                <strong>{msg.senderId === userId ? "Me" : msg.senderId}:</strong> {msg.content}
                <br /> 
                <small style={{ fontSize: "0.8em" }}>
                  {new Date(msg.timestamp).toLocaleString()}
                </small>
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
