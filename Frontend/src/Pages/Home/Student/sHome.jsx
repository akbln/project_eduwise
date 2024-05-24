import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import ClassDiv from "../../../components/ClassDiv/ClassDiv.jsx";
import styles from "./sHome.module.css"
import Header from "../../../components/Header/Header.jsx";
import Sidebar from "../../../components/Sidebar/Sidebar.jsx";

const SHome = () => {
    const [loaded,setLoaded] = useState(false);
    const [fetchedClasses, setFetchedClasses] = useState([]);
    const navigate = useNavigate();

    useEffect(()=>{

        if(!localStorage.getItem("token")){
            navigate("/login");
        }
        const fetchC = async() =>{
            await fetchClasses();
        }
        fetchC();
    },[])



    const fetchClasses = async ()=> {
        try{
            const res = await axios.get("http://localhost/students/classes",{headers:{
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
            <Sidebar role={"student"} />
            <div className={styles.contentWrapper}>
                <Header/>
                <div className={styles.classes}>
                    {loaded && fetchedClasses.map((item) => (
                        <div key={item.classId} className={styles.class} onClick={() => {
                            navigate(`/students/classes/${item.classId}`)
                        }}>
                            {<ClassDiv schoolClassInfo={item}/>}
                        </div>
                    ))}
                </div>
            </div>


        </div>
    )
}
export default SHome;