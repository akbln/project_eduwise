import {useEffect, useState} from "react";
import axios from "axios";
import {toast} from "react-toastify";
import styles from "./QuestionLoader.module.css"


const QuestionLoader = ({chapterId}) => {
    const [question,setQuestion] = useState("Could not load question");
    const [answer1,setAnswer1] = useState("Could not load");
    const [answer2,setAnswer2] = useState("Could not load");
    const [answer3,setAnswer3] = useState("Could not load");
    const [answer4,setAnswer4] = useState("Could not load");
    const [selectedAnswer,setSelectedAnswer] = useState("");
    const [qNumber, setQNumber] = useState(1);

    useEffect( ()=>{
        const fetchEffect = async ()=>{
            await fetchQuestion(0);
        }
        fetchEffect();
    },[])
    const submitAnswer = async () => {
        try{
            const response = await axios.post()
        }
    }
    const loadNextQuestion = async () => {
        setQNumber(qNumber+1);
        await fetchQuestion(qNumber);
    }
    const fetchQuestion = async (index)=> {
        try{
            const response = await axios.get(`http://localhost/students/chapters/${chapterId}/questions`,{headers:{
                Authorization:"Bearer "+localStorage.getItem("token")
                }})
            if(response.status === 200){
                setSelectedAnswer("");
                setQuestion(response.data.question);
                setAnswer1(response.data.answer1);
                setAnswer2(response.data.answer2);
                setAnswer3(response.data.answer3);
                setAnswer4(response.data.answer4);
            }
        }
        catch (ex){
            toast.error(ex.response?.data?.errors[0] || ex.message);
        }
    }


    return (
        <div className={styles.questionLoader}>
                <div className={styles.questionWrapper}>
                    <div className={styles.question}>
                        {question}
                    </div>
                    <div
                        className={`${styles.answerWrapper}`}>
                        <div onClick={() => setSelectedAnswer("A")}
                             className={`${selectedAnswer === "A" ? styles.selected : ""} transform-ease-s`}>{answer1}</div>
                    </div>
                    <div
                        className={`${styles.answerWrapper}`}>
                        <div onClick={() => setSelectedAnswer("B")}
                             className={`${selectedAnswer === "B" ? styles.selected : ""} transform-ease-s`}>{answer2}</div>
                    </div>
                    <div
                        className={`${styles.answerWrapper}`}>
                        <div onClick={() => setSelectedAnswer("C")}
                             className={`${selectedAnswer === "C" ? styles.selected : ""} transform-ease-s`}>{answer3}</div>
                    </div>
                    <div
                        className={`${styles.answerWrapper}`}>
                        <div onClick={() => setSelectedAnswer("D")}
                             className={`${selectedAnswer === "D" ? styles.selected : ""} transform-ease-s`}>{answer4}</div>
                    </div>
                </div>
            <div className={styles.nextQuestionWrapper}>
                <div className={`${styles.nextQuestion} no-select`} onClick={loadNextQuestion}>
                    Next
                </div>
            </div>
        </div>
    )
}
export default QuestionLoader;