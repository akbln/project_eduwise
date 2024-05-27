import styles from "./Sidebar.module.css";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {useEffect, useState} from "react";
import Burger from "../BurgerMenu/burger.jsx";

const Sidebar = ({role}) => {
    const [closed, setClosed] = useState(false);
    const [hidden, setHidden] = useState(false);
    const [classes, setClasses] = useState([]);
    const navigate = useNavigate();
    const [fetchUrl,setFetchUrl] = useState("/none");
    const [loaded,setLoaded]=useState(false);

    let compUrl = "";
    let classUrl = "";
    if(role === "teacher"){
        compUrl = "/teachers/competitions/create";
        classUrl="/teachers/classes/"
    }
    else if (role === "student"){
        classUrl="/students/classes/"
        compUrl = "/students/competition";
    }



    const transitionMenuIntoHeaven = () =>{
        if(closed){
            setClosed(false);
            setHidden(false);
        }
        else{
            setClosed(true);
            setHidden(true);
        }
    }
    const fetchClasses = async ()=> {
        try{
            if(role === "student"){
                setFetchUrl("http://localhost/students/classes");
            }
            else{
                setFetchUrl("http://localhost/teachers/classes");
            }
            const res = await axios.get(fetchUrl,{headers:{
                    Authorization:"Bearer "+localStorage.getItem("token"),
                }})
            if(fetchUrl!=="" && res.status === 200){
                console.log(res.data);
                setClasses(res.data.allClasses)
                setLoaded(true);
            }
        }catch (err){
            console.log(err);
        }
    }
    useEffect(()=>{
        fetchClasses();
    },[fetchUrl])


    return (
        <div className={styles.sidebarWrapper}>
            <div className={`${styles.miniSidebar} ${hidden ? "" : "displayNone"}`}>
                <div className={styles.burger} onClick={transitionMenuIntoHeaven}><Burger/></div>
            </div>
            <div
                className={`${styles.sidebar} ${closed ? `${styles.sidebarClosed}` : `${styles.sidebarOpen}`} ${hidden ? "displayNone" : ""}`}>
                <div className={styles.burger} onClick={transitionMenuIntoHeaven}><Burger/></div>
                <div className={styles.sidebarUtility}>
                    <div onClick={() => navigate(compUrl)} className={`${styles.join} pointer transform-ease-m`}>{role === "student" &&
                        <p>Join Competition</p>}{role === "teacher" && <p>Create Competition</p>}</div>

                    {role === "teacher" && <div onClick={() => navigate("/upload")} className={`${styles.join} pointer transform-ease-m`}><p>Create</p></div>}
                </div>
                <div className={styles.classesWrapper}>
                    {
                        loaded && classes && classes.map((item) => (
                            <div key={item.classId} className={`${styles.sidebarClasses} pointer transform-ease-m`} onClick={() => {
                                navigate(`${classUrl}${item.classId}`)
                            }}>
                                {item.name}
                            </div>
                        ))
                    }
                </div>
            </div>
        </div>
    )
}
export default Sidebar;