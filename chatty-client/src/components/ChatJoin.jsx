import React, { useEffect, useState } from "react";
import { TextField, Button, Box, Typography, FormControl, InputLabel, Select, MenuItem } from "@mui/material";
import { getChatsByUser } from "../services/chatService";

const ChatJoin = ({ userId, chatId, setChatId, onJoinChat }) => {
  const [userChats, setUserChats] = useState([]);
  const [loading, setLoading] = useState(false);
  const [manualChatId, setManualChatId] = useState("");
  const [selectedChatId, setSelectedChatId] = useState("");

  useEffect(() => {
    const fetchChats = async () => {
      setLoading(true);
      try {
        const chats = await getChatsByUser(userId);
        setUserChats(chats);
      } catch (error) {
        console.error("Error fetching chats:", error);
      } finally {
        setLoading(false);
      }
    };

    if (userId) {
      fetchChats();
    }
  }, [userId]);

  const handleChatSelection = (e) => {
    const selectedChat = e.target.value;
    setSelectedChatId(selectedChat);

    if (selectedChat === "") {
      setManualChatId(""); 
    } else {
      setManualChatId(null);
    }
  };

  const handleJoinChat = () => {
    const finalChatId = selectedChatId || manualChatId;

    if (!finalChatId.trim()) {
      console.error("Chat ID cannot be empty!");
      return;
    }

    setChatId(finalChatId);
  };

  useEffect(() => {
    if (chatId) {
      onJoinChat(chatId);
    }
  }, [chatId, onJoinChat]);

  return (
    <Box sx={{ display: "flex", flexDirection: "column", gap: 2, maxWidth: 400, margin: "auto" }}>
      <Typography variant="h6">Username: {userId}</Typography>

      {loading ? (
        <Typography>Loading chats...</Typography>
      ) : (
        <>
          <FormControl fullWidth>
            <InputLabel>Select Chat</InputLabel>
            <Select
              value={selectedChatId}
              onChange={handleChatSelection}
              label="Select Chat"
            >
              {/* Empty option to enable manual input */}
              <MenuItem value="">(New Chat - Enter Below)</MenuItem>
              {userChats.map((chat) => (
                <MenuItem key={chat} value={chat}>
                  {chat}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <Typography variant="body2" sx={{ textAlign: 'center', my: 1 }}>
            Or
          </Typography>

          <TextField 
            label="Enter new chat ID"
            value={manualChatId || ""} 
            onChange={(e) => setManualChatId(e.target.value)}
            variant="outlined"
            fullWidth 
            disabled={manualChatId === null}
          />

          <Button 
            variant="contained" 
            color="primary" 
            onClick={handleJoinChat}
            disabled={!selectedChatId && !manualChatId}
          >
            Join Chat
          </Button>
        </>
      )}
    </Box>
  );
};

export default ChatJoin;