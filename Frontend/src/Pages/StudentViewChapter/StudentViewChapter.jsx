import "./StudentViewChapter.css";
import {useParams} from "react-router-dom";
import Header from "../../components/Header/Header.jsx";
import Sidebar from "../../components/Sidebar/Sidebar.jsx";
import VideoPlayer from "../../components/VideoPlayer/VideoPlayer.jsx";
import QuestionLoader from "../../components/QuestionLoader/QuestionLoader.jsx";

const StudentViewChapter = () => {
    const {id} = useParams();
    const videoUrl = `http://localhost/students/chapters/${id}/video?token=${localStorage.getItem("token")}`;
    console.log(videoUrl)
    return(
        <div className="svc-page">
            <div className={"header-wrapper-svc"}>
                <Header/>
            </div>
            <div className={"sidebar-wrapper-svc"}>
                <Sidebar/>
            </div>
            <div className={"svc-objectives"}></div>
            <div className="video-player-wrapper">
                <VideoPlayer videoUrl={videoUrl}/>
            </div>
            <div className={"question-loader-wrapper"}>
                <QuestionLoader/>
            </div>
        </div>
    )
}
export default StudentViewChapter;