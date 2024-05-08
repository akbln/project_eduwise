import {useState} from "react";
import RoleStep from "./steps/step0/RoleStep.jsx";
import "./Register.css";
import CompanyWrapper from "../../components/CompanyWrapper/CompanyWrapper.jsx";
import InfoStep from "./steps/step1/InfoStep.jsx";

const Register = () => {
    const [step, setStep] = useState(0);
    const [role, setRole] = useState(null);
    const [email,setEmail] = useState(null);
    const [password,setPassword] = useState(null);
    const [age,setAge] = useState(null);
    const [gender, setGender] = useState(null);
    return(
        <div className={"register-page"}>
            <div className={"register-box"}>
                <CompanyWrapper/>
                <div className={"steps-wrapper"}>
                    {step === 0 && <RoleStep setRole={setRole} setStep={setStep} />}
                    {step === 1 &&
                        <InfoStep setEmail={setEmail} setPassword={setPassword} setAge={setAge} setGender={setGender}/>
                    }
                </div>
            </div>
        </div>
    )
}
export default Register;