import { authHeader } from "./authService";

const API_BASE_URL = "http://localhost:8090/api/messages";

export const fetchMessagesByChatId = async (chatId) => {
  try {
    console.log("calling: " + `${API_BASE_URL}/${chatId}`);
    
    const response = await fetch(`${API_BASE_URL}/${chatId}`, {
      headers: {
        ...authHeader()
      }
    });
    
    if (!response.ok) {
      if (response.status === 401) {
        // Unauthorized - token might be invalid or expired
        sessionStorage.removeItem("token");
        window.location.href = "/login";
        throw new Error("Session expired. Please login again.");
      }
      throw new Error("Failed to fetch messages");
    }
    
    const data = await response.json();
    console.log("Fetched messages:", data);
    return data;
  } catch (error) {
    console.error("Error fetching messages:", error);
    return [];
  }
};

export const sendMessage = async (messageData) => {
  try {
    const response = await fetch(`${API_BASE_URL}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        ...authHeader()
      },
      body: JSON.stringify(messageData)
    });
    
    if (!response.ok) {
      if (response.status === 401) {
        sessionStorage.removeItem("token");
        window.location.href = "/login";
        throw new Error("Session expired. Please login again.");
      }
      throw new Error("Failed to send message");
    }
    
    return await response.json();
  } catch (error) {
    console.error("Error sending message:", error);
    throw error;
  }
};