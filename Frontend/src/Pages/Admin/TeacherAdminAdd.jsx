import React, { useEffect, useState } from "react";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import styles from "./TeacherAdminAdd.module.css";
import { useParams } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar.jsx";
import Header from "../../components/Header/Header.jsx";

const TeacherAdminAdd = () => {
    const { classIdP } = useParams();
    const [classId, setClassId] = useState("");
    const [emailList, setEmailList] = useState("");
    const [teacherEmail, setTeacherEmail] = useState("");

    useEffect(() => {
        setClassId(classIdP);
    }, [classIdP]);

    const isValidEmail = (email) => {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    };

    const handleStudentSubmit = async (e) => {
        e.preventDefault();
        const emails = emailList.split(",").map((email) => email.trim());
        const validEmails = emails.filter(isValidEmail);

        if (validEmails.length === 0) {
            toast.error("Please enter at least one valid student email address.");
            return;
        }

        const data = {
            "classId":classId,
            "emails": validEmails,
        };

        try {
            const response = await axios.put("http://localhost/admins/classes/students", data, {
                headers: { Authorization: "Bearer " + localStorage.getItem("token"),
                    "Content-Type": "application/json" },
            });
            if (response.status === 200) {
                setEmailList("");
                toast.success("Student emails successfully sent!");
            }
        } catch (error) {
            const errorMessage = error.response?.data?.errors?.[0] || error.response?.message || "An error occurred.";
            toast.error(errorMessage);
        }
    };

    const handleTeacherSubmit = async (e) => {
        e.preventDefault();

        if (!isValidEmail(teacherEmail)) {
            toast.error("Please enter a valid teacher email address.");
            return;
        }

        const data = {
            "classId":classId,
            email: teacherEmail,
        };

        try {
            const response = await axios.put("http://localhost/classes/teacher", data, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token"),
                    "Content-Type": "application/json",
                },
            });
            if (response.status === 200) {
                setTeacherEmail("");
                toast.success("Teacher email successfully sent!");
            }
        } catch (error) {
            const errorMessage = error.response?.data?.errors?.[0] || error.message || "An error occurred.";
            toast.error(errorMessage);
            console.log(data)
        }
    };

    return (
        <div className={styles.page}>
            <Sidebar role={"teacherAdmin"} />
            <div className={styles.content}>
                <Header />
                <div className={styles.formContainer}>
                    <ToastContainer
                        position="top-right"
                        autoClose={3000}
                        hideProgressBar={true}
                        newestOnTop={false}
                        closeOnClick
                        rtl={false}
                        pauseOnFocusLoss
                        draggable
                        pauseOnHover
                    />
                    <div className={styles.section}>
                        <h2 className={styles.sectionHeading}>Add Students</h2>
                        <form onSubmit={handleStudentSubmit}>
                            <div className={styles.formGroup}>
                                <label htmlFor="emailList">Student Emails (comma-separated):</label>
                                <textarea
                                    id="emailList"
                                    value={emailList}
                                    onChange={(e) => setEmailList(e.target.value)}
                                    required
                                />
                            </div>
                            <button type="submit" className={styles.submitBtn}>
                                Send Student Emails
                            </button>
                        </form>
                    </div>

                    <div className={styles.section}>
                        <h2 className={styles.sectionHeading}>Add Teacher</h2>
                        <form onSubmit={handleTeacherSubmit}>
                            <div className={styles.formGroup}>
                                <label htmlFor="teacherEmail">Teacher Email:</label>
                                <input
                                    type="email"
                                    id="teacherEmail"
                                    value={teacherEmail}
                                    onChange={(e) => setTeacherEmail(e.target.value)}
                                    required
                                />
                            </div>
                            <button type="submit" className={styles.submitBtn}>
                                Send Teacher Email
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default TeacherAdminAdd;
