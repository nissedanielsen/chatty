import { Box, Typography, Paper } from "@mui/material";

const ChatRoomHeader = ({ chatId, userId }) => {
  return (
    <Paper elevation={3} sx={{ padding: 2, marginBottom: 2 }}>
      <Typography variant="h5" sx={{ fontWeight: "bold" }}>
        Chat Room: {chatId}
      </Typography>
      <Typography variant="body1" sx={{ color: "gray" }}>
        Username: {userId}
      </Typography>
    </Paper>
  );
};

export default ChatRoomHeader;
