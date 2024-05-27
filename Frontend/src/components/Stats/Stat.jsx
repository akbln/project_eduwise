import styles from "./Stat.module.css";
import { useEffect, useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import axios from "axios";
import { useParams } from "react-router-dom";

const Stat = ({ type }) => {
    const { chapterId, classId } = useParams();
    const [correct, setCorrect] = useState(0);
    const [incorrect, setIncorrect] = useState(0);
    const [correctHeight, setCorrectHeight] = useState(0);
    const [incorrectHeight, setIncorrectHeight] = useState(0);
    const [loaded, setLoaded] = useState(false);
    const [url, setUrl] = useState("");
    const [noData, setNoData] = useState(true);

    useEffect(() => {
        let newUrl = "";
        if (type === "chapter" && chapterId) {
            newUrl = `http://localhost/students/chapter/${chapterId}/results`;
        } else if (type === "comp" && classId) {
            newUrl = `http://localhost/teachers/classes/${classId}/competition`;
        }
        setUrl(newUrl);
    }, [type, chapterId, classId]);

    useEffect(() => {
        const total = correct + incorrect;
        const maxPercentage = 90;

        if (total > 0) {
            const correctPercentage = (correct / total) * maxPercentage;
            const incorrectPercentage = (incorrect / total) * maxPercentage;

            setCorrectHeight(correctPercentage);
            setIncorrectHeight(incorrectPercentage);
        } else {
            setCorrectHeight(0);
            setIncorrectHeight(0);
        }
    }, [correct, incorrect]);

    useEffect(() => {
        const loadData = async () => {
            if (url) {
                try {
                    const res = await axios.get(url, {
                        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
                    });
                    if (res.status === 200) {
                        setCorrect(res.data.correct);
                        setIncorrect(res.data.incorrect);
                        setLoaded(true);
                        toast.success("Loaded");
                        setNoData(false);
                    }
                } catch (err) {
                    setNoData(true);
                    toast.error("Error Loading Statistics");
                    console.log(err);
                }
            }
        };

        loadData();
    }, [url]);

    return (
        <div className={styles.wrapper}>
            <ToastContainer position="top-right" autoClose={1000} hideProgressBar={true} newestOnTop={false}
                            closeOnClick rtl={false} pauseOnFocusLoss draggable pauseOnHover />

            {!noData && (
                <div className={styles.dataWrapper}>
                    <div className={styles.numbers}>
                        {loaded && <div className={styles.correct} style={{ height: `${correctHeight}%` }}></div>}
                        {loaded && <div className={styles.incorrect} style={{ height: `${incorrectHeight}%` }}></div>}
                    </div>
                    <div className={styles.line}>
                        <p>Correct: {correct}</p>
                        <p>Not Correct: {incorrect}</p>
                    </div>
                </div>
            )}
            {noData && (
                <div className={styles.noData}>
                    <p>No Available Data Right Now</p>
                </div>
            )}
        </div>
    );
};

export default Stat;
