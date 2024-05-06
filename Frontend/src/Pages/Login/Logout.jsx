import {useNavigate} from "react-router-dom";

const Logout = () => {
    localStorage.removeItem('token');
    const navigate = useNavigate();
    navigate('/');
    return(<div></div>);
}
export default Logout;