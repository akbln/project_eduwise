import styles from "./Home.module.css";
import Timer from "../../components/Timer.jsx";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import jwtParser from "../../components/JWTParser.jsx";
import LoginValidator from "../../components/LoginValidator.jsx";

const Home = () => {

    const [selectedAnswer,setSelectedAnswer] = useState("");
    const navigate = useNavigate();
    useEffect(()=>{
        let details = jwtParser(localStorage.getItem("token"));
        if(details[1]===true){
            localStorage.removeItem("token");
        }
        if(details[0]==="teacher"){
            navigate("/teachers/home");
        }
        if(details[0]==="student"){
            navigate("/students/home");
        }

    },[])
    return(
        <div className={styles.page}>
            <div className={styles.homeHeader}>
                <div className={styles.name}>WiseEd</div>
                <div className={styles.studentEducator}>
                    <div>Student</div>
                    <div>Educator</div>
                </div>
                <div className={styles.loginRegister}>
                    <div onClick={()=>navigate("/login")} className={styles.login}>Login</div>
                    <div onClick={()=>navigate("/register")} className={styles.register}>Register</div>
                </div>
            </div>

            <div className={styles.questionsContainer}>
                <div className={styles.questionWrapper}>
                    <div className={styles.question}>
                        {"Who discovered electricity ?"}
                    </div>
                    <div
                        className={`${styles.answerWrapper}`}>
                        <div onClick={() => setSelectedAnswer("A")}
                             className={`${selectedAnswer === "A" ? styles.selected : ""} transform-ease-s`}>{"Nicolas Tesla"}</div>
                    </div>
                    <div
                        className={`${styles.answerWrapper}`}>
                        <div onClick={() => setSelectedAnswer("B")}
                             className={`${selectedAnswer === "B" ? styles.selected : ""} transform-ease-s`}>{"Thomas Edison"}</div>
                    </div>
                    <div
                        className={`${styles.answerWrapper}`}>
                        <div onClick={() => setSelectedAnswer("C")}
                             className={`${selectedAnswer === "C" ? styles.selected : ""} transform-ease-s`}>{"Benjamin Franklin"}</div>
                    </div>
                    <div
                        className={`${styles.answerWrapper}`}>
                        <div onClick={() => setSelectedAnswer("D")}
                             className={`${selectedAnswer === "D" ? styles.selected : ""} transform-ease-s`}>{"Albert Einstein"}</div>
                    </div>
                </div>
            </div>
            <div className={styles.timer}>
                {<Timer initialTime={30}/>}
            </div>
            <div className={styles.homePhoto}></div>
        </div>
    )

}
export default Home;