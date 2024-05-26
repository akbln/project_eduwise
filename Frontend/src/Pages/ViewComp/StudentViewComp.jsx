import styles from "./StudentViewComp.module.css"
import {useEffect, useState} from "react";
import axios from "axios";
import Header from "../../components/Header/Header.jsx";
import Sidebar from "../../components/Sidebar/Sidebar.jsx";
import Timer from "../../components/Timer.jsx";
import {useNavigate} from "react-router-dom";
import LoginValidator from "../../components/LoginValidator.jsx";

const StudentViewComp = () => {
    LoginValidator("student");

    const [loaded,setLoaded] = useState(false);

    const [compId,setCompId] = useState("");
    const [questionSet,setQuestionSet] = useState([]);
    const [qId,setQId] = useState("");
    const [question,setQuestion] = useState("Loading...")
    const [answer1,setAnswer1] = useState("Loading...")
    const [answer2,setAnswer2] = useState("Loading...")
    const [answer3,setAnswer3] = useState("Loading...")
    const [answer4,setAnswer4] = useState("Loading...")
    const [selectedAnswer,setSelectedAnswer] = useState("");
    const [timePerQuestion,setTimePerQuestion] = useState(30);
    const [index,setIndex] = useState(1);
    const [size,setSize] = useState(0);

    const navigate = useNavigate();

    useEffect(() => {
        loadQuestion();
    },[])

    const submitAndLoadNext = async () =>{
        await submit();
        if(index < size){
            setQuestion(questionSet[index].question);
            setQId(questionSet[index].id)
            setAnswer1(questionSet[index].answer1);
            setAnswer2(questionSet[index].answer2);
            setAnswer3(questionSet[index].answer3);
            setAnswer4(questionSet[index].answer4);
            setIndex(index + 1);
        }else{
            navigate("/students/competition/finished");
        }
    }
    const submit = async () =>{
        const json = {
            "compId":compId,
            "questionId":qId,
            "submittedAnswer":selectedAnswer
        }
        console.log(compId)
        console.log(json);
        const response = await axios.post("http://localhost/students/competition",JSON.stringify(json),{headers:{
                Authorization:"Bearer "+localStorage.getItem("token"),
                "Content-Type":"application/json"
            }})
    }
    const loadQuestion = async (num) => {
        try{
            const res = await axios.get("http://localhost/students/competition",{headers:{
                    Authorization:"Bearer "+localStorage.getItem("token")
                }})
            if(res.status === 200) {
                setSize(res.data.questions.length);
                setCompId(res.data.compId);
                console.log(res.data.compId);
                setQuestionSet(res.data.questions);
                setTimePerQuestion(res.data.timePerQuestions);
                console.log(res.data.timePerQuestions);
                setLoaded(true);
                setQId(res.data.questions[0].id);
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
                    <div onClick={() => setSelectedAnswer("A")} className={`${styles.answer} ${selectedAnswer === "A" ? styles.selected : ""} transform-ease-s`}>{answer1}</div>
                    <div onClick={() => setSelectedAnswer("B")} className={`${styles.answer} ${selectedAnswer === "B" ? styles.selected : ""} transform-ease-s`}>{answer2}</div>
                    <div onClick={() => setSelectedAnswer("C")} className={`${styles.answer} ${selectedAnswer === "C" ? styles.selected : ""} transform-ease-s`}>{answer3}</div>
                    <div onClick={() => setSelectedAnswer("D")} className={`${styles.answer} ${selectedAnswer === "D" ? styles.selected : ""} transform-ease-s`}>{answer4}</div>

                </div>
                <div className={styles.next}>
                <button onClick={submitAndLoadNext}>Next</button>
                </div>
            </div>
            <div className={styles.timer}>
                {loaded && <Timer initialTime={timePerQuestion}/>}
            </div>
        </div>
    )
}


export default StudentViewComp