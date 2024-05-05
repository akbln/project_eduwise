import VideoPlayer from "../../components/VideoPlayer/VideoPlayer.jsx";
import {useState} from "react";
import SelectFile from "../../components/SelectFile/SelectFile.jsx";
import "./Upload.css";
import axios from "axios";
import Header from "../../components/Header/Header.jsx";
import Sidebar from "../../components/Sidebar/Sidebar.jsx";
const UploadPage = () => {
    const [selectedFile, setSelectedFile] = useState(null);
    const [videoUrl, setVideoUrl] = useState("");
    const [course, setCourse] = useState("");
    const [chapter, setChapter] = useState("");

    const finalizeUpload = async () => {

        if(selectedFile === null){
            alert("Please select a video file");
            return;
        }
        if(!course.match("^[a-zA-Z0-9]+$")){
            alert("Invalid course name");
            return;
        }
        if(!chapter.match("^[0-9]+$") || chapter < 1){
            alert("Invalid chapter name");
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
            console.log(res.status);
        }catch (error){
            console.log(error);
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
        <div className="upload-page">
            <Header/>
            <Sidebar/>
            <div className="upload-area">
                {selectedFile === null && <SelectFile onFileSelect={handleFileSelect}/>}
                {selectedFile !== null && <VideoPlayer videoUrl={videoUrl}/>}
            </div>
            <div className="upload-options">
                <div className="upload-type">

                </div>
                <div className="course-name">
                    <h4>Course Name: </h4>
                    <input className="course-input" type="text" onChange={e => setCourse(e.target.value)}/>
                </div>
                <div className="chapter-number">
                    <h4>Chapter Number: </h4>
                    <input className="chapter-input" type="text" onChange={e => setChapter(e.target.value)}/>
                </div>
                <div className="upload-button">
                    <button onClick={finalizeUpload}>Upload</button>
                </div>
            </div>
        </div>
    )
}
export default UploadPage;