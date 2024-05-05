import "./StudentViewChapter.css";
import {useParams} from "react-router-dom";
import Header from "../../components/Header/Header.jsx";
import Sidebar from "../../components/Sidebar/Sidebar.jsx";
import VideoPlayer from "../../components/VideoPlayer/VideoPlayer.jsx";

const StudentViewChapter = () => {
    const {id} = useParams();
    const videoUrl = `http://localhost/students/chapters/${id}/video?token=${localStorage.getItem("token")}`;
    console.log(videoUrl)
    return(
        <div className="svc-page">
            <Header/>
            <Sidebar/>
            <div className="video-player-wrapper">
                <VideoPlayer videoUrl={videoUrl}/>
            </div>
        </div>
    )
}
export default StudentViewChapter;