import {useState} from "react";
import RoleStep from "./steps/step0/RoleStep.jsx";
import "./Register.css";
import CompanyWrapper from "../../components/CompanyWrapper/CompanyWrapper.jsx";
import NameStep from "./steps/step1/NameStep.jsx";

const Register = () => {
    const [step, setStep] = useState(0);
    const [role, setRole] = useState("");
    const [gender, setGender] = useState("");
    return(
        <div className="register-page">
            <div className="col-2 col-m-0 col-s-0"></div>
            <div className="register-box col-8 col-m-12 col-s-12">
                <div className={"register-text"}><h1>Registration</h1></div>
                <CompanyWrapper />
                <div className="register-form-wrapper col-8 col-s-12">
                    {step === 0 && <RoleStep setStep={setStep} setRole={setRole} />}
                    {step === 1 && <NameStep setGender={setGender} setStep={setStep} />}
                    {/*{step === 2 && <RoleStep setRole={setRole} setStep={setStep} />}*/}
                </div>
            </div>
            <div className="col-2 col-m-0 col-s-0"></div>
        </div>
    )
}
export default Register;