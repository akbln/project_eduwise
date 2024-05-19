import styles from "./StartCompetition.module.css"
import Sidebar from "../../components/Sidebar/Sidebar.jsx";
import Header from "../../components/Header/Header.jsx";
import {useEffect, useState} from "react";
import axios from "axios";


const StartCompetition = () => {
    const [loaded, setLoaded] = useState(false);
    const [schoolClasses, setSchoolClasses] = useState([]);
    const [questions, setQuestions] = useState([]);
    const [selectedClass,setSelectedClass] = useState("");
    const [selectedQuestions,setSelectedQuestions] = useState([]);

    const createCompRequest = async () => {

        const reqJson = {
            "questions":selectedQuestions,
            "classId":selectedClass
        }

        try {
            const response1 = await axios.put('http://localhost/teachers/competitions',JSON.stringify(reqJson), {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                    "Content-Type" : "application/json"
                }
            });
        }catch (e){
            console.log(e);
        }
    }

    const toggleSelectedQuestions = (q) => {
        if(!selectedQuestions.includes(q.id)){
            setSelectedQuestions([...selectedQuestions,q.id])
        }else{
            setSelectedQuestions(selectedQuestions.filter(item => item !== q.id));
        }
    }


    useEffect(() => {
        const fetchData = async () => {
            const token = localStorage.getItem('token');  // Retrieve the token from localStorage
            try {
                const response1 = await axios.get('http://localhost/teachers/classes', {
                    headers: {
                        Authorization: `Bearer ${token}`  // Include the token in the request headers
                    }
                });
                const response2 = await axios.get('http://localhost/teachers/questions', {
                    headers: {
                        Authorization: `Bearer ${token}`  // Include the token in the request headers
                    }
                });
                if(response1.status === 200 && response2.status === 200){
                    setSchoolClasses(response1.data.allClasses);
                    setQuestions(response2.data.allQuestions);
                    setLoaded(true);
                }
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);

    return(
        <div className={styles.page}>
            <div className={"header"}>
                <Header/>
            </div>
            <div className={"menu-wrapper"}>
                <Sidebar/>
            </div>
            <div className={styles.questions}>
                {loaded && questions?.map((q) => (
                    <div className={`${styles.questions} ${selectedQuestions.includes(q.id) ? styles.selectedQuestion : ''} transform-ease-m no-select`} onClick={() => toggleSelectedQuestions(q)}
                         key={q.id}>
                        <p>{q.question}</p>
                    </div>
                ))}
            </div>
            <div className={styles.schoolClasses}>
                {loaded && schoolClasses?.map((cls) => (
                    <div
                        className={`${styles.schoolClass} ${selectedClass === cls.classId ? styles.selectedClass : ''} transform-ease-m no-select`}
                        onClick={() => setSelectedClass(cls.classId)}
                        key={cls.classId}>
                        <p>{cls.name}</p>
                    </div>

                ))}
                <button onClick={createCompRequest}>Upload</button>
            </div>

        </div>
    )


}
export default StartCompetition