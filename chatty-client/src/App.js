// src/App.js
import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import './App.css';
import Chat from './components/Chat.jsx';
import Login from './components/Login.jsx'

function App() {
  // Function to check if user is authenticated
  const isAuthenticated = () => {
    return sessionStorage.getItem("token") !== null;
  };

  return (
    <BrowserRouter>
      <Routes>
        <Route 
          path="/" 
          element={isAuthenticated() ? <Navigate to="/chat" /> : <Navigate to="/login" />} 
        />
        <Route 
          path="/login" 
          element={isAuthenticated() ? <Navigate to="/chat" /> : <Login />} 
        />
        <Route 
          path="/chat" 
          element={isAuthenticated() ? <Chat /> : <Navigate to="/login" />} 
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;