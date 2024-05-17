import {useEffect} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import ClassDiv from "../../../components/ClassDiv/ClassDiv.jsx";


const SHome = () => {

    const fetchedClasses = [];
    const navigate = useNavigate();

    useEffect(()=>{

        if(!localStorage.getItem("token")){
            navigate("/login");
        }


        const fetchC = async() =>{
            await fetchClasses();
        }
        fetchC();
    },[])



    const fetchClasses = async ()=> {
        try{
            const res = await axios.get("http://students/classes",{headers:{
                    Authorization:"Bearer "+localStorage.getItem("token"),
                    'Accept': 'application/json'
                }})
            if(res.status === 200){
                console.log("OK");
                fetchedClasses.push(res.data.schoolClasses);
            }
        }catch (err){
            console.log(err.message);
        }
    }


    return (
        <div>
            <div className={"sHome-header"}></div>
            {fetchedClasses.map((item, index) => (
                <div key={index} className="sHome-classes">
                    {item} {<ClassDiv schoolClassInfo={item}/>}
                </div>
            ))}
        </div>
    )
}
export default SHome;