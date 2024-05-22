// eslint-disable-next-line no-unused-vars
import React from "react";
import { Route, Routes } from "react-router-dom";
import Home from "./Pages/Home/Home.jsx";
import "./App.css";
import Login from "./Pages/Login/Login.jsx";
import Register from "./Pages/Register/Register.jsx";
import UploadPage from "./Pages/Teacher/Upload/Upload.jsx";
import StudentViewChapter from "./Pages/StudentViewChapter/StudentViewChapter.jsx";
import Logout from "./Pages/Login/Logout.jsx";
import SHome from "./Pages/Home/Student/sHome.jsx";
import StudentViewClass from "./Pages/StudentViewClass/StudentViewClass.jsx";
import StartCompetition from "./Pages/StartCompetition/StartCompetition.jsx";
import StudentViewComp from "./Pages/ViewComp/StudentViewComp.jsx";
import Finished from "./components/Finished/Finished.jsx";

function App() {
  return (
    <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/logout" element={<Logout />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/upload" element={<UploadPage />} />
        <Route path="/students/chapters/:id" element={<StudentViewChapter/>} />
        <Route path="/students/classes/:id" element={<StudentViewClass/>} />
        <Route path="/students/home" element={<SHome/>} />
        <Route path="/teachers/competitions/create" element={<StartCompetition/>} />
        <Route path="/students/competition" element={<StudentViewComp/>} />
        <Route path="/students/competition/finished" element={<Finished/>} />
    </Routes>
  );
}

export default App;
