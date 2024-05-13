import {useState} from "react";


import "./QuestionLoader.css"

const QuestionLoader = () => {
    const [question,setQuestion] = useState("Could not load question");
    const [answer1,setAnswer1] = useState("Could not load");
    const [answer2,setAnswer2] = useState("Could not load");
    const [answer3,setAnswer3] = useState("Could not load");
    const [answer4,setAnswer4] = useState("Could not load");

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
                        <p>{answer1}</p>
                    </div>
                    <div className={"answer-d-l answer-l"}>
                        <div className={"circle"}></div>
                        <p>{answer2}</p>
                    </div>
                </div>
            </div>
            <div className={"next-question"}>
                Next
            </div>
        </div>
    )
}
export default QuestionLoader;