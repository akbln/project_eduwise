import {useState} from "react";
import "./RegisterInfoStep.css"
import register from "../../Register.jsx";
const RegisterInfoStep = ({setStep,setEmail,setPassword,setGender,setAge}) => {


    const [emailLocal,setEmailLocal] = useState(null);
    const [passwordLocal,setPasswordLocal] = useState(null);
    const [confirmPasswordLocal,setConfirmPasswordLocal] = useState(null);
    const [ageLocal,setAgeLocal] = useState(null);
    const [genderLocal, setGenderLocal] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();

    }




    return(
        <form onSubmit={handleSubmit} className={"info-wrapper"}>
            <div className={'email'}>
                <input onChange={e => setEmailLocal(e.target.value)} type={"email"}/>
            </div>
            <div className={"password"}>
                <input onChange={e => setPasswordLocal(e.target.value)} type={"password"}/>
            </div>
            <div className={"confirm-password"}>
                <input onChange={e => setConfirmPasswordLocal(e.target.value)} type={"password"}/>
            </div>
            <div className={"age"}>
                <input onChange={e => setAgeLocal(e.target.value)} type={"age"}/>
            </div>
            <div className={"gender"}>
                <select value={genderLocal} onChange={e => setGenderLocal(e.target.value)}>
                    <option value="">Select</option>
                    <option value="M">Female</option>
                    <option value="F">Male</option>
                </select>
            </div>
        </form>
    )
}
export default RegisterInfoStep;