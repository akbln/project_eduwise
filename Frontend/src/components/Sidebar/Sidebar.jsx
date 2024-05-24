import styles from "./Sidebar.module.css";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {useEffect, useState} from "react";
import Burger from "../BurgerMenu/burger.jsx";

const Sidebar = ({role}) => {
    const [closed, setClosed] = useState(false);
    const [hidden, setHidden] = useState(false);
    const [classes, setClasses] = useState([]);
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
            let url = "";
            if(role === "student"){
                url = "http://localhost/students/classes";
            }
            else{
                url = "http://localhost/teachers/classes";
            }
            const res = await axios.get(url,{headers:{
                    Authorization:"Bearer "+localStorage.getItem("token"),
                }})
            if(res.status === 200){
                console.log(res.data);
                setClasses(res.data.allClasses)
            }
        }catch (err){
            console.log(err);
        }
    }
    useEffect(()=>{
        fetchClasses();
    },[])

    const navigate = useNavigate();
    return (
        <div className={styles.sidebarWrapper}>
            <div className={`${styles.miniSidebar} ${hidden ? "" : "displayNone"}`}>
                <div className={styles.burger} onClick={transitionMenuIntoHeaven}><Burger/></div>
            </div>
            <div
                className={`${styles.sidebar} ${closed ? `${styles.sidebarClosed}` : `${styles.sidebarOpen}`} ${hidden ? "displayNone" : ""}`}>
                <div className={styles.burger} onClick={transitionMenuIntoHeaven}><Burger/></div>
                <div className={styles.sidebarUtility}>
                    <div className={styles.join}>{role === "student" && <p>Join Competition</p>}{role === "teacher" && <p>Create Competition</p>}</div>
                </div>
                <div className={styles.classesWrapper}>
                    {
                        classes && classes.map((item) => (
                            <div key={item.classId} className={styles.sidebarClasses} onClick={() => {
                                navigate(`/students/classes/${item.classId}`)
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