import {useState} from "react";
import "./RegisterInfoStep.css"
import register from "../../Register.jsx";
import {ToastContainer,toast} from "react-toastify";

const RegisterInfoStep = ({setStep,setEmail,setPassword,setGender,setAge,makeRegisterRequest,setName}) => {

    const [nameLocal,setNameLocal] = useState("");
    const [emailLocal,setEmailLocal] = useState("");
    const [passwordLocal,setPasswordLocal] = useState("");
    const [confirmPasswordLocal,setConfirmPasswordLocal] = useState("");
    const [ageLocal,setAgeLocal] = useState("");
    const [genderLocal, setGenderLocal] = useState("");


    const handleSubmit = (e) => {
        e.preventDefault();

        if (!/^\S+@\S+\.\S+$/.test(emailLocal)) {
            toast.error("Wrong Email Format");
            return;
        }
        if (!/^(?=.*\d)(?=.*[a-zA-Z]).{8,}$/.test(passwordLocal)) {
            toast.error("Password must have at least 8 characters and at least one must be upper case");
            return;
        }
        if (passwordLocal !== confirmPasswordLocal) {
            toast.error("Passwords do not match");
            return;
        }
        if(!/^[a-zA-Z]+( [a-zA-Z]+)+$/.test(nameLocal)){
            toast.error("Invalid name");
            return;
        }
        if (!/^\d+$/.test(ageLocal) || parseInt(ageLocal) < 18 || parseInt(ageLocal) > 100) {
            toast.error("Invalid age");
            return;
        }
        if (genderLocal !== "M" && genderLocal !== "F") {
            toast.error("Invalid Gender");
            return;
        }
        setEmail(emailLocal);
        setPassword(passwordLocal);
        setName(nameLocal)
        setAge(ageLocal);
        setGender(genderLocal);
        makeRegisterRequest();
    }




    return(
        <form onSubmit={handleSubmit} className={"info-wrapper"}>
            <h1 className={"welcome"}>Welcome!</h1>
            <div className={'email'}>
                <input onChange={e => setEmailLocal(e.target.value)} type={"email"} placeholder={"Email"}/>
            </div>
            <div className={"password"}>
                <input onChange={e => setPasswordLocal(e.target.value)} type={"password"} placeholder={"Password"}/>
            </div>
            <div className={"confirm-password"}>
                <input onChange={e => setConfirmPasswordLocal(e.target.value)} type={"password"} placeholder={"Confirm Password"}/>
            </div>
            <div className={'name-age'}>
                <input onChange={e => setNameLocal(e.target.value)} type={"text"} placeholder={"Name"}/>
                <input onChange={e => setAgeLocal(e.target.value)} type={"age"} placeholder={"Age"}/>
            </div>
            <div className={"gender"}>
            <select value={genderLocal} onChange={e => setGenderLocal(e.target.value)}>
                    <option value="">Select Gender</option>
                    <option value="M">Female</option>
                    <option value="F">Male</option>
                </select>
            </div>
            <button type={"submit"}>Confirm</button>
        </form>
    )
}
export default RegisterInfoStep;