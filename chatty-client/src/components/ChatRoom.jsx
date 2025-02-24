import Message from "./Message";
import { Box } from "@mui/material";
import ChatRoomHeader from "./ChatRoomHeader";

const ChatRoom = ({ chatId, userId, messages }) => {
  return (
    <Box sx={{ padding: 2, minHeight: '100vh', backgroundColor: '#f4f4f4' }}>
      {/* Chat Room Header Component */}
      <ChatRoomHeader chatId={chatId} userId={userId} />

      {/* Messages List */}
      <Box
        component="ul"
        sx={{
          listStyleType: "none",
          paddingLeft: 0,
          marginBottom: 3,
          maxHeight: '60vh',
          overflowY: 'auto',
        }}
      >
        {messages.map((msg, index) => (
          <Message
            key={index}
            senderId={msg.senderId}
            content={msg.content}
            timestamp={msg.timestamp}
            userId={userId}
          />
        ))}
      </Box>
    </Box>
  );
};

export default ChatRoom;
