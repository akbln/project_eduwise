import axios from "axios";
import "./Login.css";
import { useNavigate } from "react-router-dom";
import React, {useEffect, useState} from "react";
import CompanyWrapper from "../../components/CompanyWrapper/CompanyWrapper.jsx";
import jwtParser from "../../components/JWTParser.jsx";
import {ToastContainer,toast} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const Login = () => {
  const navigate = useNavigate();

  useEffect(() => {
    if(jwtParser(localStorage.getItem("token"))) {
      localStorage.removeItem("token");
      return;
    }
    if(localStorage.getItem("token")) {
      navigate("/");
    }
  }, [navigate]);

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    try {
      console.log(email);
      console.log(password);
      const loginRequest = JSON.stringify({ email, password });
      console.log(loginRequest);
      const response = await axios.post(
        "http://127.0.0.1/login",
        loginRequest,
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      console.log(response.data.token);
      localStorage.setItem("token", response.data.token);
      navigate("/");
    } catch (error) {
      console.log(error.response.status);
      console.log(error.response.data);
      toast.error(error.response.data.errors[0] || error.message);
      navigate("/login");
    }
  };

  return (
    <div className={"login-page"}>
      <ToastContainer
          position="top-right"
          autoClose={3000}
          hideProgressBar={true}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
      />
      <div className={"login-box"}>
        <CompanyWrapper/>
        <div className={"login-wrapper"}>
          <h1>Welcome Back!</h1>
          <div className={"login-input"}>
            <div className={"email-input"}>
              <h3>Email:</h3>
              <input type="email" onChange={e => setEmail(e.target.value)}/>
            </div>
            <div className={"password-input"}>
              <h3>Password:</h3>
              <input type="password" onChange={e => setPassword(e.target.value)}/>
            </div>
          </div>
          <div className={"login-footer"}>
            <button onClick={handleLogin}>Continue</button>
            <button onClick={e => navigate("/register")}>Create Account</button>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Login;
