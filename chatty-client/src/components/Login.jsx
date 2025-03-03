import { useState } from "react";
import { TextField, Button, Box, Typography, Container, Paper, Alert } from "@mui/material";
import { login } from "../services/authService";

const Login = () => {
  const [credentials, setCredentials] = useState({ username: "", password: "" });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCredentials((prev) => ({ ...prev, [name]: value }));
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);
  
    try {
      console.log("Attemting login")
      const data = await login(credentials.username, credentials.password);
      console.log("Login successful", data);
      
      // Redirect using window.location.href
      window.location.href = "/chat";
    } catch (error) {
      setError(error.message || "Invalid username or password");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="sm">
      <Box sx={{ marginTop: 8, display: "flex", flexDirection: "column", alignItems: "center" }}>
        <Paper elevation={3} sx={{ padding: 4, width: "100%", borderRadius: 2 }}>
          <Typography component="h1" variant="h5" align="center" gutterBottom>
            Sign In
          </Typography>

          {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

          <Box component="form" onSubmit={handleLogin} noValidate>
            <TextField
              margin="normal"
              required
              fullWidth
              id="username"
              label="Username"
              name="username"
              autoComplete="username"
              autoFocus
              value={credentials.username}
              onChange={handleChange}
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
              value={credentials.password}
              onChange={handleChange}
            />
            <Button type="submit" fullWidth variant="contained" color="primary" disabled={loading} sx={{ mt: 3, mb: 2, py: 1.5 }}>
              {loading ? "Signing in..." : "Sign In"}
            </Button>
          </Box>
        </Paper>
      </Box>
    </Container>
  );
};

export default Login;
