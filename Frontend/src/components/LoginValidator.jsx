import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import jwtParser from "./JWTParser.jsx";

const LoginValidator = (expectedRole) => {
    const navigate = useNavigate();
    const [role, setRole] = useState("");

    useEffect(() => {
        const token = localStorage.getItem("token");

        if (!token) {
            navigate("/login");
            return;
        }

        try {
            const tokenData = jwtParser(token);

            if (!tokenData || !tokenData[0]) {
                localStorage.removeItem("token");
                navigate("/login");
                return;
            }

            const [userRole, isTokenExpired] = tokenData;

            if (isTokenExpired) {
                localStorage.removeItem("token");
                navigate("/login");
                return;
            }

            if (expectedRole !== null && userRole !== expectedRole) {
                navigate("/login");
                return;
            }

            setRole(userRole);
        } catch (error) {
            localStorage.removeItem("token");
            navigate("/login");
        }
    }, [navigate, expectedRole]);

    return role;
};

export default LoginValidator;
