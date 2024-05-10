import "./FillQuestion.css"



const FillQuestion = () => {
    return (
        <div className={"question-wrapper"}>
            <div className={"question"}>
                Question
                <input type={"text"}/>
            </div>
            <div className={"answer-a answer"}>
                A)
                <input type={"text"}/>
            </div>
            <div className={"answer-b answer"}>
                B)
                <input type={"text"}/>
            </div>
            <div className={"answer-c answer"}>
                C)
                <input type={"text"}/>
            </div>
            <div className={"answer-d answer"}>
                D)
                <input type={"text"}/>
            </div>
            <div className={"answer-key"}>
                <label>Answer Key</label>
                <select>
                    <option>A</option>
                    <option>B</option>
                    <option>C</option>
                    <option>D</option>
                </select>
            </div>
        </div>
    )
}
export default FillQuestion;