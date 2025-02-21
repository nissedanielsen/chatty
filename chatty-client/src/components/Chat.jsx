import { useState } from "react";
import useWebSocket from "../services/useWebSocket"; 
import SendMessageForm from "./SendMessageForm";

const Chat = () => {
  const [chatId, setChatId] = useState("");
  const [userId, setUserId] = useState("");
  const [connected, setConnected] = useState(false);
  const [socketUrl, setSocketUrl] = useState(null);

  const { messages, socket } = useWebSocket(socketUrl);

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
