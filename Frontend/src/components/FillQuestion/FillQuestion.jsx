import "./FillQuestion.css"
import {useState} from "react";



const FillQuestion = ({setQuestion,setAnswer1,setAnswer2,setAnswer3,setAnswer4,setAnswerKey}) => {

    const [answerKeyLocal,setAnswerKeyLocal] = useState("Select");

    const handleAnswerKey = (e) => {
        setAnswerKeyLocal(e.target.value);
        setAnswerKey(e.target.value);
    }
    return (
        <div className={"question-wrapper"}>
            <div className={"question"}>
                Question
                <input  type={"text"} onChange={e => setQuestion(e.target.value)}/>
            </div>
            <div className={"answer-a answer"}>
                A)
                <input type={"text"} onChange={e => setAnswer1(e.target.value)}/>
            </div>
            <div className={"answer-b answer"}>
                B)
                <input type={"text"} onChange={e => setAnswer2(e.target.value)}/>
            </div>
            <div className={"answer-c answer"}>
                C)
                <input type={"text"} onChange={e => setAnswer3(e.target.value)}/>
            </div>
            <div className={"answer-d answer"}>
                D)
                <input type={"text"} onChange={e => setAnswer4(e.target.value)}/>
            </div>
            <div className="answer-key">
                <label htmlFor="answerKey">Answer Key</label>
                <select id="answerKey" value={answerKeyLocal} onChange={handleAnswerKey}>
                    <option value="A">A</option>
                    <option value="B">B</option>
                    <option value="C">C</option>
                    <option value="D">D</option>
                </select>
            </div>
        </div>
    )
}
export default FillQuestion;