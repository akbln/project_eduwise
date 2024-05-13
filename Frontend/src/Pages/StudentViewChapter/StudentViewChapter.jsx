import "./StudentViewChapter.css";
import {useParams} from "react-router-dom";
import Header from "../../components/Header/Header.jsx";
import Sidebar from "../../components/Sidebar/Sidebar.jsx";
import VideoPlayer from "../../components/VideoPlayer/VideoPlayer.jsx";
import QuestionLoader from "../../components/QuestionLoader/QuestionLoader.jsx";
import {ToastContainer,toast} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const StudentViewChapter = () => {
    const {id} = useParams();
    const videoUrl = `http://localhost/students/chapters/${id}/video?token=${localStorage.getItem("token")}`;
    console.log(videoUrl)
    return(
        <div className="svc-page">
            <ToastContainer position="top-right" autoClose={3000} hideProgressBar={false} newestOnTop={false} closeOnClick rtl={false} pauseOnFocusLoss draggable pauseOnHover />
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
                <QuestionLoader chapterId={id} />
            </div>
        </div>
    )
}
export default StudentViewChapter;