import styles from "./StudentViewComp.module.css"
import {useEffect, useState} from "react";
import axios from "axios";
import Header from "../../components/Header/Header.jsx";
import Sidebar from "../../components/Sidebar/Sidebar.jsx";
import Timer from "../../components/Timer.jsx";

const StudentViewComp = () => {

    const [num,setNum] = useState(0);
    const [questionSet,setQuestionSet] = useState([]);
    const [question,setQuestion] = useState("Loading...")
    const [answer1,setAnswer1] = useState("Loading...")
    const [answer2,setAnswer2] = useState("Loading...")
    const [answer3,setAnswer3] = useState("Loading...")
    const [answer4,setAnswer4] = useState("Loading...")
    const [selectedAnswer,setSelectedAnswer] = useState("");
    const [timePerQuestion,setTimePerQuestion] = useState(30);
    const [index,setIndex] = useState(1);

    useEffect(() => {
        loadQuestion();
    },[])

    const submitAndLoadNext = () =>{
        if(questionSet[index] !== null){
            setQuestion(questionSet[index].question);
            setAnswer1(questionSet[index].answer1);
            setAnswer2(questionSet[index].answer2);
            setAnswer3(questionSet[index].answer3);
            setAnswer4(questionSet[index].answer4);
            setIndex(index + 1);
        }
    }
    const loadQuestion = async (num) => {
        try{
            const res = await axios.get("http://localhost/students/competitions",{headers:{
                    Authorization:"Bearer "+localStorage.getItem("token")
                }})
            if(res.status === 200) {
                setQuestionSet(res.data.questions);
                setTimePerQuestion(res.data.timePerQuestionSeconds);
                setQuestion(res.data.questions[0].question);
                setAnswer1(res.data.questions[0].answer1);
                setAnswer2(res.data.questions[0].answer2);
                setAnswer3(res.data.questions[0].answer3);
                setAnswer4(res.data.questions[0].answer4);
            }
        }catch (err){
            console.log(err);
        }
    }
    return (
        <div className={styles.page}>
            <Header/>
            <Sidebar/>
            <div className={styles.questionsContainer}>
                <div className={styles.questionWrapper}>
                    <div className={styles.question}>
                        {question}
                    </div>
                    <div className={styles.answer}>{answer1}</div>
                    <div className={styles.answer}>{answer2}</div>
                    <div className={styles.answer}>{answer3}</div>
                    <div className={styles.answer}>{answer4}</div>

                </div>
                <div className={styles.next}>
                <button onClick={submitAndLoadNext}>Next</button>
                </div>
            </div>
            <div className={styles.timer}>
                <Timer initialTime={timePerQuestion}/>
            </div>
        </div>
    )
}


export default StudentViewComp