import Message from "./Message";

const ChatRoom = ({ chatId, userId, messages }) => {
  return (
    <div>
      <h2>Chat Room: {chatId} - Username: {userId}</h2>
      <ul>
        {messages.map((msg, index) => (
          <Message
            key={index}
            senderId={msg.senderId}
            content={msg.content}
            timestamp={msg.timestamp}
            userId={userId}
          />
        ))}
      </ul>
    </div>
  );
};

export default ChatRoom;
