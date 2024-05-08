import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

const Logout = () => {
    const navigate = useNavigate();
    useEffect(() => {
        localStorage.removeItem('token');
        navigate('/');
    }, [navigate]);
    return(
        <div>Hi</div>
    )
}
export default Logout;