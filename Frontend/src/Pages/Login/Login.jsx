import axios from "axios";
import "./Login.css";
import { useNavigate } from "react-router-dom";
import {useEffect, useState} from "react";
import CompanyWrapper from "../../components/CompanyWrapper/CompanyWrapper.jsx";
import expiredJwt from "../../components/JWTParser.jsx";

const Login = () => {
  const navigate = useNavigate();

  useEffect(() => {
    if(expiredJwt(localStorage.getItem("token"))) {
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
      navigate("/login");
    }
  };

  return (
    <div className={"login-page"}>
      <div className={"login-box"}>
        <CompanyWrapper/>
        <div className={"login-wrapper"}>
          <input type="email" onChange={e => setEmail(e.target.value)}/>
          <input type="password" onChange={e => setPassword(e.target.value)}/>
          <button onClick={handleLogin}>Login</button>
        </div>
      </div>
    </div>
  );
};
export default Login;
