import {useState} from "react";
import RoleStep from "./steps/step0/RoleStep.jsx";
import "./Register.css";
import CompanyWrapper from "../../components/CompanyWrapper/CompanyWrapper.jsx";
import InfoStep from "./steps/step1/InfoStep.jsx";
import {ToastContainer,toast} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import axios from "axios";
import {json, useNavigate} from "react-router-dom";
import header from "../../components/Header/Header.jsx";

const Register = () => {
    const [step, setStep] = useState(0);
    const [role, setRole] = useState(null);







    return(
        <div className={"register-page"}>
            <ToastContainer position="top-right" autoClose={3000} hideProgressBar={false} newestOnTop={false} closeOnClick rtl={false} pauseOnFocusLoss draggable pauseOnHover />
            <div className={"register-box"}>
                <CompanyWrapper/>
                <div className={"steps-wrapper"}>
                    {step === 0 && <RoleStep setRole={setRole} setStep={setStep} />}
                    {step === 1 &&
                        <InfoStep role={role}/>
                    }
                </div>
            </div>
        </div>
    )
}
export default Register;