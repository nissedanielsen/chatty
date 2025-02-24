import { TextField, Button, Box } from "@mui/material";

const ChatJoin = ({ userId, setUserId, chatId, setChatId, onJoinChat }) => {
  return (
    <Box sx={{ display: "flex", flexDirection: "column", gap: 2, maxWidth: 400, margin: "auto" }}>
      <TextField
        label="Enter your username"
        value={userId}
        onChange={(e) => setUserId(e.target.value)}
        variant="outlined"
        fullWidth
      />
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
