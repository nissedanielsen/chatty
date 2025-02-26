import React from 'react';
import { ListItem, Typography, Box } from '@mui/material';

const Message = ({ senderId, content, timestamp, userId }) => {
  const isSenderMe = senderId === userId;
  
  return (
    <ListItem
      sx={{
        justifyContent: isSenderMe ? 'flex-end' : 'flex-start',
        marginBottom: 1,
      }}
    >
      <Box
        sx={{
          maxWidth: '75%',
          backgroundColor: isSenderMe ? '#1976d2' : '#e0e0e0',
          color: isSenderMe ? '#fff' : '#000',
          padding: 1,
          borderRadius: '10px',
        }}
      >
        {/* Sender name */}
        <Typography variant="body2" sx={{ fontWeight: 'bold' }}>
          {isSenderMe ? 'Me' : senderId}:
        </Typography>

        {/* Message content */}
        <Typography variant="body1" sx={{ marginBottom: 1 }}>
          {content}
        </Typography>

        {/* Timestamp */}
        <Typography variant="caption" sx={{ fontSize: '0.75em' }}>
          {new Date(timestamp).toLocaleString()}
        </Typography>
      </Box>
    </ListItem>
  );
};

export default Message;
