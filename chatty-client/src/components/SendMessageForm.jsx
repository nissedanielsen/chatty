import { useState } from "react";

const SendMessageForm = ({ socket, userId, chatId }) => {
  const [message, setMessage] = useState("");

  const handleSendMessage = () => {
    if (socket && message.trim()) {
      const msgObject = {
        chatId: chatId,
        senderId: userId, // Include the senderId from the user input
        content: message,
        timestamp: new Date().toISOString(),
      };
      
      socket.send(JSON.stringify(msgObject));
      setMessage(""); // Clear input after sending
    } else {
      console.error("Cannot send an empty message!");
    }
  };

  return (
    <div>
      <input
        type="text"
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        placeholder="Type a message..."
      />
      <button onClick={handleSendMessage}>Send</button>
    </div>
  );
};

export default SendMessageForm;
