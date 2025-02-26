import { useState } from "react";
import { TextField, Button, Box, Typography } from "@mui/material";

const SendMessageForm = ({ socket, userId, chatId }) => {
  const [message, setMessage] = useState("");

  const handleSendMessage = () => {
    if (socket && message.trim()) {
      const msgObject = {
        chatId: chatId,
        senderId: userId, 
        content: message
      };

      socket.send(JSON.stringify(msgObject));
      console.log("sending message:" + JSON.stringify(msgObject))
      setMessage(""); 
    } else {
      console.error("Cannot send an empty message!");
    }
  };

  return (
    <Box
      component="form"
      display="flex"
      justifyContent="center"
      alignItems="center"
      padding={2}
      borderTop="1px solid #ddd"
    >
      <TextField
        label="Type a message..."
        variant="outlined"
        fullWidth
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        sx={{
          marginRight: 2,
          backgroundColor: "#fff",
        }}
      />
      <Button
        variant="contained"
        color="primary"
        onClick={handleSendMessage}
        disabled={!message.trim()}
        sx={{
          minWidth: 100,
          fontWeight: 'bold',
        }}
      >
        Send
      </Button>
    </Box>
  );
};

export default SendMessageForm;
