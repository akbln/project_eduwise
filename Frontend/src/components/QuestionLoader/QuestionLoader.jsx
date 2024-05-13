import {useEffect, useState} from "react";


import "./QuestionLoader.css"
import axios from "axios";
import {toast} from "react-toastify";

const QuestionLoader = ({chapterId}) => {
    const [question,setQuestion] = useState("Could not load question");
    const [answer1,setAnswer1] = useState("Could not load");
    const [answer2,setAnswer2] = useState("Could not load");
    const [answer3,setAnswer3] = useState("Could not load");
    const [answer4,setAnswer4] = useState("Could not load");
    const [qNumber, setQNumber] = useState(1);

    useEffect( ()=>{
        const fetchEffect = async ()=>{
            await fetchQuestion(0);
        }
        fetchEffect();
    },[])
    const loadNextQuestion = async () => {
        setQNumber(qNumber+1);
        await fetchQuestion(qNumber);
    }
    const fetchQuestion = async (index)=> {
        try{
            const response = await axios.get(`http://localhost/students/chapters/${chapterId}/questions/${index}`,{headers:{
                Authorization:"Bearer "+localStorage.getItem("token")
                }})
            if(response.status === 200){
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
        <div className={"question-loader"}>
            <div className={"question-l"}>
                {question}
            </div>
            <div className={"answers-wrapper-l"}>
                <div className={"answers-wrapper-l-top"}>
                    <div className={"answer-a-l answer-l"}>
                        <div className={"circle"}></div>
                        <p>{answer1}</p>
                    </div>
                    <div className={"answer-b-l answer-l"}>
                        <div className={"circle"}></div>
                        <p>{answer2}</p>
                    </div>
                </div>
                <div className={"answers-wrapper-l-bottom"}>
                    <div className={"answer-c-l answer-l"}>
                        <div className={"circle"}></div>
                        <p>{answer3}</p>
                    </div>
                    <div className={"answer-d-l answer-l"}>
                        <div className={"circle"}></div>
                        <p>{answer4}</p>
                    </div>
                </div>
            </div>
            <div className={"next-question no-select"} onClick={loadNextQuestion}>
                Next
            </div>
        </div>
    )
}
export default QuestionLoader;