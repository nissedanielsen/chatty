import { authHeader } from "./authService";

const API_BASE_URL = "http://localhost:8090/api/users";  // Replace with your correct API base URL for users

// Fetch all chats for a specific user
export const getChatsByUser = async (userId) => {
  try {

    
    console.log("calling: " + `${API_BASE_URL}/${userId}/chats`);
    
    const response = await fetch(`${API_BASE_URL}/${userId}/chats`, {
      headers: {
        ...authHeader() // Attach the authentication headers
      }
    });

    if (!response.ok) {
      if (response.status === 401) {
        // Unauthorized - token might be invalid or expired
        sessionStorage.removeItem("token");
        window.location.href = "/login";
        throw new Error("Session expired. Please login again.");
      }
      throw new Error("Failed to fetch chats");
    }

    const data = await response.json();
    console.log("Fetched chats:", data);
    return data; // Returning list of chat IDs
  } catch (error) {
    console.error("Error fetching chats:", error);
    return [];
  }
};

// Function to join or create a new chat
export const joinChat = async (userId, chatId) => {
  try {
    console.log("calling: " + `${API_BASE_URL}/${userId}/joinChat`);
    
    const response = await fetch(`${API_BASE_URL}/${userId}/joinChat`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        ...authHeader() // Attach the authentication headers
      },
      body: JSON.stringify({ chatId }) // Sending the chatId for the user to join
    });

    if (!response.ok) {
      if (response.status === 401) {
        sessionStorage.removeItem("token");
        window.location.href = "/login";
        throw new Error("Session expired. Please login again.");
      }
      throw new Error("Failed to join chat");
    }

    return await response.json(); // Returning the response data (e.g., chat info or success message)
  } catch (error) {
    console.error("Error joining chat:", error);
    throw error;
  }
};
