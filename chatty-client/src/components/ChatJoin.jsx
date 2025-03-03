import { TextField, Button, Box, Typography } from "@mui/material";

const ChatJoin = ({ userId, setUserId, chatId, setChatId, onJoinChat }) => {
  return (
    <Box sx={{ display: "flex", flexDirection: "column", gap: 2, maxWidth: 400, margin: "auto" }}>

      <Typography variant="h6">Username: {userId}</Typography>

      <TextField
        label="Enter chat ID"
        value={chatId}
        onChange={(e) => setChatId(e.target.value)}
        variant="outlined"
        fullWidth
      />
      <Button variant="contained" color="primary" onClick={onJoinChat}>
        Join Chat
      </Button>
    </Box>
  );
};

export default ChatJoin;
