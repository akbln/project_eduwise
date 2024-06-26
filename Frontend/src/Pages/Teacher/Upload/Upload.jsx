import VideoPlayer from "../../../components/VideoPlayer/VideoPlayer.jsx";
import {useState} from "react";
import SelectFile from "../../../components/SelectFile/SelectFile.jsx";
import styles from "./Upload.module.css";
import axios from "axios";
import Header from "../../../components/Header/Header.jsx";
import Sidebar from "../../../components/Sidebar/Sidebar.jsx";
import FillQuestion from "../../../components/FillQuestion/FillQuestion.jsx";
import {ToastContainer,toast} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import loginValidator from "../../../components/LoginValidator.jsx";


const UploadPage = () => {
    loginValidator("teacher");
    const [selectedFile, setSelectedFile] = useState(null);
    const [videoUrl, setVideoUrl] = useState("");
    const [course, setCourse] = useState("");
    const [chapter, setChapter] = useState(0);
    const [uploadType , setUploadType] = useState("Type: Video")

    const [question,setQuestion] = useState("");
    const [answer1,setAnswer1] = useState("");
    const [answer2,setAnswer2] = useState("");
    const [answer3,setAnswer3] = useState("");
    const [answer4,setAnswer4] = useState("");
    const [answerKey,setAnswerKey] = useState("A");

    const clearPage = () => {
        const inputs = document.querySelectorAll('input');
        setQuestion("");
        setAnswer1("");
        setAnswer2("");
        setAnswer3("");
        setAnswer4("");
        setAnswerKey("A");
        setChapter(0);
        setCourse("");
        for (let i = 0; i < inputs.length; i++) {
            if (inputs[i].type === 'text' || inputs[i].type === 'number') {
                inputs[i].value = '';
            }
        }
    }


    const makeQuestionRequest = async () => {

        const requestJson = {
            "question":question,
            "answer1":answer1,
            "answer2":answer2,
            "answer3":answer3,
            "answer4":answer4,
            "answerKey":answerKey,
            "chapterNumber":chapter,
            "courseName":course
        }
        if(isValid()){
            try{
                const response = await axios.post(
                    "http://localhost/teachers/questions/upload",
                    JSON.stringify(requestJson),
                    {headers:
                            {"Content-Type":"application/json","Accept":"application/json",
                                Authorization:"Bearer " + localStorage.getItem("token")}
                    }
                );
                if (response.status === 200){
                    toast.success("Uploaded question successfully");
                    clearPage();
                }
                else{
                    toast.error(`Response ${response}`)
                }
            }catch (error){
                toast.error(error.response?.data?.errors[0] || error.message);
            }
        }

    }

    const makeVideoRequest = async () => {

        if(selectedFile === null){
            alert("Please select a video file");
            return;
        }
        if (!course.match(/^[a-zA-Z0-9 ]+$/)) {
            toast.error("Invalid course name");
            return;
        }

        if (chapter <= 0) {
            toast.error("Invalid Chapter Number");
            return;
        }
        const formData = new FormData();
        formData.append('video', selectedFile);
        formData.append('courseName', course);
        formData.append('chapterNumber', chapter);
        try{
            const res = await axios.put("http://localhost/videos/upload", formData,{
                headers:{"Content-Type":"multipart/form-data","Accept":"application/json",
                    Authorization:"Bearer " + localStorage.getItem("token")}
            })
            if(res.status === 200){
                toast.success("Successfully Uploaded Video")
            }
        }catch (error){
            toast.error(error.response?.data?.errors[0] || error.message);
        }
    }

    const changeUploadType = () => {
        if(uploadType === "Type: Video"){
            setUploadType("Type: Question");
        }else{
            setUploadType("Type: Video");
        }
    }

    const isValid = () => {
        const isValid = (value) => /^[a-zA-Z0-9?. ]+$/.test(value);

        const isQuestionValid = isValid(question);
        const isAnswer1Valid = isValid(answer1);
        const isAnswer2Valid = isValid(answer2);
        const isAnswer3Valid = isValid(answer3);
        const isAnswer4Valid = isValid(answer4);

        if (!isQuestionValid) {
            toast.error("Invalid character detected in the question field.");
            return false;
        }

        if (!isAnswer1Valid) {
            toast.error("Invalid character detected in answer1 field.");
            return false;
        }

        if (!isAnswer2Valid) {
            toast.error("Invalid character detected in answer2 field.");
            return false;
        }

        if (!isAnswer3Valid) {
            toast.error("Invalid character detected in answer3 field.");
            return false;
        }

        if (!isAnswer4Valid) {
            toast.error("Invalid character detected in answer4 field.");
            return false;
        }
        switch (answerKey){
            case("A"):break;
            case("B"):break;
            case("C"):break;
            case("D"):break;
            default:
                return false;
        }
        return true;
    }

    const finalizeUpload = async () => {
        if(uploadType==="Type: Video"){
            await makeVideoRequest();
        }else{
            await makeQuestionRequest();
        }
    }

    const handleFileSelect = (e) => {
        const file = e.target.files[0];
        if (file) {
            if (file.type === "video/mp4") {
                setSelectedFile(file);
                const url = URL.createObjectURL(file);
                setVideoUrl(url);
                console.log(url);
            } else {
                alert("Please select an MP4 video file.");
                setSelectedFile(null);
                setVideoUrl("");
            }
        } else {
            setSelectedFile(null);
            setVideoUrl("");
        }
    };

    return(
        <div className={styles.page}>
            <Sidebar role={"teacher"}/>
            <ToastContainer position="top-right" autoClose={3000} hideProgressBar={false} newestOnTop={false} closeOnClick rtl={false} pauseOnFocusLoss draggable pauseOnHover />
            <div className={styles.content}>
                <Header/>

                <div className={styles.uploadArea}>
                    {uploadType === "Type: Video" && selectedFile === null && <SelectFile onFileSelect={handleFileSelect}/>}
                    {uploadType === "Type: Video" && selectedFile !== null && <VideoPlayer videoUrl={videoUrl}/>}
                    {uploadType === "Type: Question" && <FillQuestion setQuestion={setQuestion} setAnswer1={setAnswer1} setAnswer2={setAnswer2}
                                                                      setAnswer3={setAnswer3} setAnswer4={setAnswer4} setAnswerKey={setAnswerKey} />}
                </div>
                <div className={styles.uploadOptions}>
                    <div onClick={changeUploadType} className={`${styles.uploadType} transform-ease no-select`}>
                        <h2>{uploadType}</h2>
                    </div>
                    <div className="courseName">
                        <h4>Course Name: </h4>
                        <input className={styles.courseInput} type="text" onChange={e => setCourse(e.target.value)}/>
                    </div>
                    <div className="chapterNumber">
                        <h4>Chapter Number: </h4>
                        <input className={styles.chapterInput} type="number" onChange={e => setChapter(parseInt(e.target.value))}/>
                    </div>
                    <div className={`${styles.uploadButton} transform-ease`}>
                        <button onClick={finalizeUpload}>Upload</button>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default UploadPage;