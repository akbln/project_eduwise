import { useEffect, useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import styles from "./QuestionLoader.module.css";
import { useParams } from "react-router-dom";

const QuestionLoader = ({ loadType }) => {
    const { classId, chapterId } = useParams();
    const url = `http://localhost/students/classes/${classId}/chapters/${chapterId}/questions`;

    const [questions, setQuestions] = useState([]);
    const [qId, setQId] = useState("");
    const [question, setQuestion] = useState("Could not load question");
    const [answer1, setAnswer1] = useState("Could not load");
    const [answer2, setAnswer2] = useState("Could not load");
    const [answer3, setAnswer3] = useState("Could not load");
    const [answer4, setAnswer4] = useState("Could not load");
    const [selectedAnswer, setSelectedAnswer] = useState("");
    const [size, setSize] = useState(0);
    const [index, setIndex] = useState(0);

    useEffect(() => {
        fetchQuestions().then(() => console.log("loaded"));
    }, [url]);

    const submitAnswer = async () => {
        const submitJson = {
            questionId: qId,
            submission: selectedAnswer,
        };
        console.log(submitJson)
        try {
            const response = await axios.post(url, JSON.stringify(submitJson), {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token"),
                    "Content-Type": "application/json",
                },
            });
            if (response.status === 200) {
                console.log("ok");
            }
        } catch (error) {
            toast.error("Error in submission");
        }
    };

    const loadNextQuestion = async () => {
        await submitAnswer();
        if (index < size - 1) {
            setIndex(prevIndex => prevIndex + 1);
            const nextQuestion = questions[index + 1];
            setSelectedAnswer("");
            setQId(nextQuestion.id);
            setQuestion(nextQuestion.question);
            setAnswer1(nextQuestion.answer1);
            setAnswer2(nextQuestion.answer2);
            setAnswer3(nextQuestion.answer3);
            setAnswer4(nextQuestion.answer4);
        } else {
            console.log("Finished");
        }
    };

    const fetchQuestions = async () => {
        try {
            const response = await axios.get(url, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token"),
                },
            });
            if (response.status === 200) {
                const questionsResponse = response.data.questions;
                console.log(response)
                setSize(questionsResponse.length);
                if (questionsResponse.length > 0) {
                    setQuestions(questionsResponse);
                    const firstQuestion = questionsResponse[0];
                    setQId(firstQuestion.id);
                    setQuestion(firstQuestion.question);
                    setAnswer1(firstQuestion.answer1);
                    setAnswer2(firstQuestion.answer2);
                    setAnswer3(firstQuestion.answer3);
                    setAnswer4(firstQuestion.answer4);
                }
            }
        } catch (ex) {
            toast.error(ex.response?.data?.errors[0] || ex.message);
        }
    };

    return (
        <div className={styles.questionLoader}>
            <div className={styles.questionWrapper}>
                <div className={styles.question}>{question}</div>
                <div className={styles.answerWrapper}>
                    <div
                        onClick={() => setSelectedAnswer("A")}
                        className={`${selectedAnswer === "A" ? styles.selected : ""} transform-ease-s`}
                    >
                        {answer1}
                    </div>
                </div>
                <div className={styles.answerWrapper}>
                    <div
                        onClick={() => setSelectedAnswer("B")}
                        className={`${selectedAnswer === "B" ? styles.selected : ""} transform-ease-s`}
                    >
                        {answer2}
                    </div>
                </div>
                <div className={styles.answerWrapper}>
                    <div
                        onClick={() => setSelectedAnswer("C")}
                        className={`${selectedAnswer === "C" ? styles.selected : ""} transform-ease-s`}
                    >
                        {answer3}
                    </div>
                </div>
                <div className={styles.answerWrapper}>
                    <div
                        onClick={() => setSelectedAnswer("D")}
                        className={`${selectedAnswer === "D" ? styles.selected : ""} transform-ease-s`}
                    >
                        {answer4}
                    </div>
                </div>
            </div>
            <div className={styles.nextQuestionWrapper}>
                <div className={`${styles.nextQuestion} no-select`} onClick={loadNextQuestion}>
                    Next
                </div>
            </div>
        </div>
    );
};

export default QuestionLoader;
