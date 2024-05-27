import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import ClassDiv from "../../components/ClassDiv/ClassDiv.jsx";
import styles from "./AdminHome.module.css"
import Header from "../../components/Header/Header.jsx";
import Sidebar from "../../components/Sidebar/Sidebar.jsx";
import LoginValidator from "../../components/LoginValidator.jsx";

const AHome = () => {
    LoginValidator("teacherAdmin");

    const [loaded,setLoaded] = useState(false);
    const [fetchedClasses, setFetchedClasses] = useState([]);
    const navigate = useNavigate();

    useEffect(()=>{
        fetchClasses();
    },[])

    const fetchClasses = async ()=> {
        try{
            const res = await axios.get("http://localhost/admins/classes",{headers:{
                    Authorization:"Bearer "+localStorage.getItem("token"),
                }})
            if(res.status === 200){
                setLoaded(true);
                console.log(res.data);
                setFetchedClasses(res.data.allClasses)
            }
        }catch (err){
            console.log(err);
        }
    }


    return (
        <div className={styles.page}>
            <Sidebar role={"teacherAdmin"} />
            <div className={styles.contentWrapper}>
                <Header/>
                <div className={styles.classes}>
                    {loaded && fetchedClasses.map((item) => (
                        <div key={item.classId} className={styles.class} onClick={() => {
                            navigate(`/admins/classes/${item.classId}/modify`)
                        }}>
                            {<ClassDiv schoolClassInfo={item}/>}
                        </div>
                    ))}
                </div>
            </div>


        </div>
    )
}
export default AHome;