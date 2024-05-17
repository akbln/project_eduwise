import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import ClassDiv from "../../components/ClassDiv/ClassDiv.jsx";
import ChapterDiv from "../../components/ChapterDiv/ChapterDiv.jsx";
import "./StudentViewClass.css";
import Sidebar from "../../components/Sidebar/Sidebar.jsx";
import Header from "../../components/Header/Header.jsx";

const StudentViewClass = () => {
    const {id} = useParams();
    const [chapters,setChapters] = useState([]);
    const [loaded,setLoaded] = useState(false);
    const navigate = useNavigate();
    useEffect(()=>{
        if(!localStorage.getItem("token")){
            navigate("/login");
        }
        fetchChapters();
    },[])



    const fetchChapters = async ()=> {
        try{
            const res = await axios.get(`http://localhost/students/classes/${id}/chapters`,{headers:{
                    Authorization:"Bearer "+localStorage.getItem("token"),
                }})
            if(res.status === 200){
                setLoaded(true);
                console.log(res.data);
                setChapters(res.data.allChapters)
            }
        }catch (err){
            console.log(err);
        }
    }


    return (
        <div className={"student-view-class"}>
            <div className={`header-wrapper`}>
                <Header/>
            </div>
            <div className={`menu-wrapper`}>
                <Sidebar/>
            </div>
            <div className={"svc-objectives"}></div>
            <div className={"chapters-wrapper"}>
                {loaded && chapters.map((item) => (
                    <div key={item.name} className="chapter-wrapper" onClick={() => {
                        navigate(`/students/chapters/${item.id}`)
                    }}>
                        {<ChapterDiv chapter={item}/>}
                    </div>
                ))}
            </div>


        </div>
    )
}
export default StudentViewClass