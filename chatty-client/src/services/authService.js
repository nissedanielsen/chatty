const AUTH_API_URL = "http://localhost:8090/auth";

export const login = async (username, password) => {
  try {
    console.log("calling login service");
    const response = await fetch(`${AUTH_API_URL}/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || "Login failed");
    }

    const data = await response.json();
    console.log("jwt: " + data.token)

    // Store token from auth-call and form from client
    localStorage.setItem("token", data.token);
    localStorage.setItem("userId", username); 
    
    return data;
  } catch (error) {
    console.error("Authentication error:", error);
    throw error;
  }
};

// Logout function
export const logout = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("userId");
  window.location.href = "/login";
};

// Get auth header with token for API requests
export const authHeader = () => {
  const token = localStorage.getItem("token");
  
  if (token) {
    return { Authorization: `Bearer ${token}` };
  } else {
    return {};
  }
};