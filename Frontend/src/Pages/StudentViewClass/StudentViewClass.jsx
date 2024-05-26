import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import ChapterDiv from "../../components/ChapterDiv/ChapterDiv.jsx";
import styles from "./StudentViewClass.module.css";
import Sidebar from "../../components/Sidebar/Sidebar.jsx";
import Header from "../../components/Header/Header.jsx";
import LoginValidator from "../../components/LoginValidator.jsx";

const StudentViewClass = () => {
    LoginValidator("student");
    const {chapterId} = useParams();
    const {classId} = useParams();
    const [chapters,setChapters] = useState([]);
    const [loaded,setLoaded] = useState(false);
    const navigate = useNavigate();
    const deAuth = (err,navigate) => {
        if(err.response.data.errors.includes("Bad JWT")){
            localStorage.removeItem("token");
            navigate("/login");
        }
    }
    useEffect(() => {
        const token = localStorage.getItem("token");
        if (!token) {
            navigate("/login");
            return;
        }
        fetchChapters();
    },[navigate])



    const fetchChapters = async ()=> {
        try{
            const res = await axios.get(`http://localhost/students/classes/${classId}/chapters`,{headers:{
                    Authorization:"Bearer "+localStorage.getItem("token"),
                }})
            if(res.status === 200){
                setLoaded(true);
                console.log(res.data);
                setChapters(res.data.allChapters)
            }
        }catch (err){
            deAuth(err,navigate)
            console.log(err);
        }
    }


    return (
        <div className={styles.page}>

            <Sidebar role={"student"}/>
            <div className={styles.content}>
                <Header/>
                <div className={styles.objectives}></div>
                <div className={styles.chaptersWrapper}>
                    <div className={styles.chapterWrapperText}>
                        <p>Chapters</p>
                    </div>
                    <div className={styles.chapters}>
                        {loaded && chapters.map((item) => (
                            <div key={item.name} className={styles.chapterWrapper} onClick={() => {
                                navigate(`/students/classes/${classId}/chapters/${item.id}`)
                            }}>
                                {<ChapterDiv chapter={item}/>}
                            </div>
                        ))}
                    </div>
                </div>
            </div>


        </div>
    )
}
export default StudentViewClass