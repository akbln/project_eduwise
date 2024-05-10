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
    const [email,setEmail] = useState(null);
    const [password,setPassword] = useState(null);
    const [age,setAge] = useState(null);
    const [gender, setGender] = useState(null);
    const [name, setName] = useState(null);
    const navigate = useNavigate();

    const makeRegisterRequest = async () => {
        if(role === "teacher"){
            console.log({
                "email":email,
                "password":password,
                "age":age,
                "gender":gender
            });
            try{
                const response = await axios.post("http://localhost/teachers/register",{
                    "email":email,
                    "password":password,
                    "name":name,
                    "age":parseInt(age),
                    "gender":gender
                },{headers:{"Content-Type":"application/json"}})
                if(response.status === 200){
                    toast.success("Successfully registered");
                    setTimeout(()=>{navigate("/login")},2000);
                }
            }catch(e){
                toast.error(e.response?.data?.errors[0] || e.message);
            }
        }
        if(role === "student"){
            try{
                const response = await axios.post("localhost/students/register",JSON.stringify({
                    "email":email,
                    "password":password,
                    "age":age,
                    "gender":gender,
                    "level":"Masters"
                }))
                if(response.status === 200){
                    toast.success("Successfully registered");
                    setTimeout(()=>{navigate("/home")},2000);
                }
            }catch(e){
                toast.error(e.data.errors);
            }
        }
    }




    return(
        <div className={"register-page"}>
            <ToastContainer position="top-right" autoClose={3000} hideProgressBar={false} newestOnTop={false} closeOnClick rtl={false} pauseOnFocusLoss draggable pauseOnHover />
            <div className={"register-box"}>
                <CompanyWrapper/>
                <div className={"steps-wrapper"}>
                    {step === 0 && <RoleStep setRole={setRole} setStep={setStep} />}
                    {step === 1 &&
                        <InfoStep setEmail={setEmail} setPassword={setPassword} setAge={setAge} setGender={setGender}
                                  makeRegisterRequest={makeRegisterRequest} setName={setName}/>
                    }
                </div>
            </div>
        </div>
    )
}
export default Register;