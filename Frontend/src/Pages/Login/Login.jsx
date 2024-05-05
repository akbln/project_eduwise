import axios from "axios";
import "./Login.css";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import CompanyWrapper from "../../components/CompanyWrapper/CompanyWrapper.jsx";
import Header from "../../components/Header/Header.jsx";

const Login = () => {
  const navigate = useNavigate();
  if (localStorage.getItem("token")) {
    navigate("/");
  }
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
      window.localStorage.setItem("token", response.data.token);
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
      </div>
    </div>
  );
};
export default Login;
