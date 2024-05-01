import {useState} from "react";
import "./NameStep.css"
const NameStep = ({setStep,setGender}) => {

    const handleChange = (e) => {
        setGender(e.target.value);
        console.log(e.target.value);
    }
    return(
        <div className="name-step-wrapper">
            <div className={"name-wrapper"}>
                <div className={"name-input"}>
                    <div>Name:</div>
                    <input type="text"/>
                </div>
            </div>
            <div className={"gender-wrapper"}>
            <div className={"gender-selector"}>
                    <div>Gender</div>
                    <select className="gender-select" onChange={handleChange}>
                        <option value="" disabled>Select your gender</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                    </select>
                </div>
            </div>
            <div className={"age-wrapper"}>
                <div>Age:</div>
                <input type="number" onChange={handleChange}></input>
            </div>
        </div>


    )
}
export default NameStep;