// eslint-disable-next-line no-unused-vars
import React from "react";
import { Route, Routes } from "react-router-dom";
import Home from "./Pages/Home.jsx";
import "./Bootstrap.css"
import "./App.css";
import Login from "./Pages/Login/Login.jsx";
import Register from "./Pages/Register/Register.jsx";

function App() {
  return (
    <Routes>
      <Route path="/videos/:id" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
    </Routes>
  );
}

export default App;
