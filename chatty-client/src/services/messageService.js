const API_BASE_URL = "http://localhost:8090/api/messages";

export const fetchMessagesByChatId = async (chatId) => {
  try {
    console.log("calling: " + `${API_BASE_URL}/${chatId}`)
    const response = await fetch(`${API_BASE_URL}/${chatId}`);
    if (!response.ok) {
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
