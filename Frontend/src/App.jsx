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
import THome from "./Pages/Home/Teacher/tHome.jsx";
import ProfilePage from "./Pages/ProfilePicture/ProfilePicture.jsx";
import Stat from "./components/Stats/Stat.jsx";
import ViewComp from "./Pages/ViewCompResults/ViewComp.jsx";
import TeacherAdminAdd from "./Pages/Admin/TeacherAdminAdd.jsx";
import AdminHome from "./Pages/Admin/AdminHome.jsx";

function App() {
  return (
    <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/logout" element={<Logout />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/upload" element={<UploadPage />} />
        <Route path="/students/classes/:classId/chapters/:chapterId" element={<StudentViewChapter/>} />
        <Route path="/students/classes/:classId" element={<StudentViewClass/>} />
        <Route path="/students/home" element={<SHome/>} />
        <Route path="/teachers/home" element={<THome/>} />
        <Route path="/teachers/competitions/create" element={<StartCompetition/>} />
        <Route path="/students/competition" element={<StudentViewComp/>} />
        <Route path="/students/competition/finished" element={<Finished/>} />
        <Route path="/profile" element={<ProfilePage/>} />
        <Route path="/stats" element={<Stat/>}/>
        <Route path="/teachers/classes/:classId" element={<ViewComp/>}/>
        <Route path="/admins/classes/:classIdP/modify" element={<TeacherAdminAdd/>}/>
        <Route path="/admins/home" element={<AdminHome/>}/>

    </Routes>
  );
}

export default App;
