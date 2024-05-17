import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";

const StudentViewClass = () => {
    const {id} = useParams();
    const [chapters,setChapters] = useState([]);
    const [loaded,setLoaded] = useState(false);
    const navigate = useNavigate();
    useEffect(()=>{
        if(!localStorage.getItem("token")){
            navigate("/login");
        }
        fetchChapters();
    },[])



    const fetchChapters = async ()=> {
        try{
            const res = await axios.get(`http://localhost/students/classes/${id}`,{headers:{
                    Authorization:"Bearer "+localStorage.getItem("token"),
                }})
            if(res.status === 200){
                setLoaded(true);
                console.log(res.data);
                setChapters(res.data.allChapters)
            }
        }catch (err){
            console.log(err);
        }
    }


    return (
        <div className={"student-view-class"}>



        </div>
    )
}
export default StudentViewClass