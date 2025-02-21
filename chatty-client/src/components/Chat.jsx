import { useState, useEffect } from "react";
import useWebSocket from "../services/useWebSocket"; 
import SendMessageForm from "./SendMessageForm";
import { fetchMessagesByChatId } from "../services/messageService";
import ChatRoom from "./ChatRoom";

const Chat = () => {
  const [chatId, setChatId] = useState("");
  const [userId, setUserId] = useState("");
  const [connected, setConnected] = useState(false);
  const [socketUrl, setSocketUrl] = useState(null);
  const [messages, setMessages] = useState([]);
  const [historicalMessagesLoaded, setHistoricalMessagesLoaded] = useState(false);

  const { messages: wsMessages, socket } = useWebSocket(socketUrl);

  // Trigger this effect on start to fetch messages from db
  useEffect(() => {
    if (chatId && connected && !historicalMessagesLoaded) {
      console.log("Calling db");
      console.log("historial" + historicalMessagesLoaded)
      fetchMessages();
    }
  }, [chatId, connected, historicalMessagesLoaded]);

  const fetchMessages = async () => {
    try {
      const fetchedMessages = await fetchMessagesByChatId(chatId);
      console.log("Fetched messages:", fetchedMessages);
      setMessages(fetchedMessages); 
      setHistoricalMessagesLoaded(true);
    } catch (error) {
      console.error("Error fetching messages:", error);
    }
  };

  // Trigger this effect whenever WebSocket messages arrive
  useEffect(() => {
    if (wsMessages.length > 0) {
      setMessages((prevMessages) => [...prevMessages, ...wsMessages]);
    }
  }, [wsMessages]); 



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
          <ChatRoom chatId={chatId} userId={userId} messages={messages} />
          <SendMessageForm socket={socket} userId={userId} chatId={chatId} />
        </div>
      )}
    </div>
  );
};

export default Chat;
