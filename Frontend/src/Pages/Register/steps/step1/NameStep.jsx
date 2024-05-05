import {useState} from "react";
import "./RegisterInfoStep.css"
const RegisterInfoStep = ({setStep,setGender}) => {

    const handleChange = (e) => {
        setGender(e.target.value);
        console.log(e.target.value);
    }
    return(
        <div className={"register-step-wrapper"}>
            <div className={"email-input-wrapper row col-12 inputs"}>

                <input type="email" placeholder="Email"/>
            </div>
            <div className={"name-input-wrapper row col-6 col-s-12 inputs"}>

                <input placeholder={"Full name"} type="text"/>
            </div>
            <div className={"age-input-wrapper col-6 col-s-12 inputs"}>

                <input placeholder={"Age"} type="number"/>
            </div>
            <div className={"password-input-wrapper row col-12 inputs"}>

                <input placeholder={"Password"} type="password"/>
            </div>
            <div className={"password-input-wrapper row col-12 inputs"}>

                <input placeholder={"Confirm Password"} type="password"/>
            </div>

            <div className={"gender-input-wrapper"}></div>
        </div>


    )
}
export default RegisterInfoStep;