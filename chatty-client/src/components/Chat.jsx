import { useState, useEffect } from "react";
import useWebSocket from "../services/useWebSocket";
import SendMessageForm from "./SendMessageForm";
import ChatRoom from "./ChatRoom";
import ChatJoin from "./ChatJoin";
import { fetchMessagesByChatId } from "../services/messageService";
import { logout } from "../services/authService";
import { TextField, Button, Paper, Box, Typography } from "@mui/material";

const Chat = () => {
  // Get userId from localStorage on component mount
  const [chatId, setChatId] = useState("");
  const [userId, setUserId] = useState(localStorage.getItem("userId") || "");
  const [connected, setConnected] = useState(false);
  const [socketUrl, setSocketUrl] = useState(null);
  const [messages, setMessages] = useState([]);
  const [historicalMessagesLoaded, setHistoricalMessagesLoaded] = useState(false);

  const token = localStorage.getItem("token");
  const { messages: wsMessages, socket } = useWebSocket(socketUrl, token);

  // Check if token exists on component mount and redirect if not
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      window.location.href = "/login";
    }
  }, []);

  // Fetch messages when connected and chatId is set
  useEffect(() => {
    if (chatId && connected && !historicalMessagesLoaded) {
      fetchMessages();
    }
  }, [chatId, connected, historicalMessagesLoaded]);

  const fetchMessages = async () => {
    try {
      const fetchedMessages = await fetchMessagesByChatId(chatId);
      setMessages(fetchedMessages);
      setHistoricalMessagesLoaded(true);
    } catch (error) {
      console.error("Error fetching messages:", error);
    }
  };

  // Append WebSocket messages to the message state
  useEffect(() => {
    if (wsMessages.length > 0) {
      setMessages((prevMessages) => [...prevMessages, ...wsMessages]);
    }
  }, [wsMessages]);

  // Handle WebSocket connection
  const handleConnect = () => {
    if (!chatId.trim() || !userId.trim()) {
      console.error("Chat ID and User ID cannot be empty!");
      return;
    }
    setSocketUrl(`ws://localhost:8090/ws/joinchat/${chatId}`);
    setConnected(true);
  };

  // Handle logout
  const handleLogout = () => {
    logout();
    window.location.href = "/login";
  };

  // Show loading state if userId isn't available yet
  if (!userId) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
        <Paper elevation={3} sx={{ padding: 4, width: 350, textAlign: "center" }}>
          <Typography variant="h5" gutterBottom>
            Loading...
          </Typography>
        </Paper>
      </Box>
    );
  }

  return (
    <Box p={3}>
      <Button variant="contained" color="secondary" onClick={handleLogout} sx={{ mb: 2 }}>
        Logout
      </Button>

      {!connected ? (
        <ChatJoin userId={userId} setUserId={setUserId} chatId={chatId} setChatId={setChatId} onJoinChat={handleConnect} />
      ) : (
        <Box>
          <ChatRoom chatId={chatId} userId={userId} messages={messages} />
          <SendMessageForm socket={socket} userId={userId} chatId={chatId} />
        </Box>
      )}
    </Box>
  );
};

export default Chat;