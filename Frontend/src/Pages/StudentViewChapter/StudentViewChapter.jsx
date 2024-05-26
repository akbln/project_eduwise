import styles from "./StudentViewChapter.module.css";
import {useParams} from "react-router-dom";
import Header from "../../components/Header/Header.jsx";
import Sidebar from "../../components/Sidebar/Sidebar.jsx";
import VideoPlayer from "../../components/VideoPlayer/VideoPlayer.jsx";
import QuestionLoader from "../../components/QuestionLoader/QuestionLoader.jsx";
import {ToastContainer,toast} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import LoginValidator from "../../components/LoginValidator.jsx";

const StudentViewChapter = () => {
    LoginValidator("student");
    const {classId} = useParams();
    const {chapterId} = useParams();
    const videoUrl = `http://localhost/students/classes/${classId}/chapters/${chapterId}/video?token=${localStorage.getItem("token")}`;
    console.log(videoUrl)
    return(
        <div className={styles.page}>
            <Sidebar role={"student"}/>
            <div className={styles.contentWrapper}>
                <div className={styles.content}>
                    <ToastContainer position="top-right" autoClose={3000} hideProgressBar={false} newestOnTop={false}
                                    closeOnClick rtl={false} pauseOnFocusLoss draggable pauseOnHover/>
                    <Header/>
                    <div className={styles.objectives}>
                    </div>
                    <div className={styles.videoPlayerWrapper}>
                        <VideoPlayer videoUrl={videoUrl}/>
                    </div>
                    <div className={styles.questionLoaderWrapper}>
                        <QuestionLoader loadType={"chapter"} chapterId={chapterId}/>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default StudentViewChapter;