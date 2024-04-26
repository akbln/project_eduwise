// eslint-disable-next-line no-unused-vars
import React from "react";
import Register from "./components/registerationForm/Register";
import { Route, Routes } from "react-router-dom";
import "./index.css";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Register />} />
    </Routes>
  );
}

export default App;
