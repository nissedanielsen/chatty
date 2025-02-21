const Message = ({ senderId, content, timestamp, userId }) => {
    return (
      <li>
        <strong>{senderId === userId ? "Me" : senderId}:</strong> {content}
        <br />
        <small style={{ fontSize: "0.8em" }}>
          {new Date(timestamp).toLocaleString()}
        </small>
      </li>
    );
  };
  
  export default Message;