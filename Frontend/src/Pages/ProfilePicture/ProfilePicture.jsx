import React, { useState, useEffect } from 'react';
import axios from 'axios';
import styles from './ProfilePage.module.css';
import Sidebar from "../../components/Sidebar/Sidebar.jsx";
import {useNavigate} from "react-router-dom";
import LoginValidator from "../../components/LoginValidator.jsx";
import jwtParser from "../../components/JWTParser.jsx";
import Header from "../../components/Header/Header.jsx";

function ProfilePage() {
    const [role,setRole] = useState("");
    const [profilePicture, setProfilePicture] = useState(null);
    const [profileName, setProfileName] = useState("Your Full Name Here");
    const [errorMessage, setErrorMessage] = useState(null);
    const navigate = useNavigate();
    useEffect(() => {
        let details = jwtParser(localStorage.getItem("token"));
        if(details[0]==="teacher"){
            setRole("teacher");
        }
        if(details[0]==="student"){
            setRole("student");
        }
        console.log(details[0]);


        const fetchProfile = async () => {
            try {
                const response = await axios.get("http://localhost/profile", {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                });

                const profileData = response.data;
                setProfileName(profileData.name);

                if (profileData.profilePicture) {
                    setProfilePicture(`data:image/jpeg;base64,${profileData.profilePicture}`);
                }
            } catch (error) {
                console.error('Error fetching profile data:', error);
            }
        };

        fetchProfile();
    }, []);

    const handleImageChange = async (event) => {
        const file = event.target.files[0];
        const supportedTypes = ['image/jpeg', 'image/png'];

        if (file) {
            if (!supportedTypes.includes(file.type)) {
                setErrorMessage('Unsupported file type. Please upload a JPEG or PNG image.');
                return;
            }

            const formData = new FormData();
            formData.append('profilePicture', file);

            try {
                await axios.put("http://localhost/profile", formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                });

                const reader = new FileReader();
                reader.onload = function(e) {
                    setProfilePicture(e.target.result);
                };
                reader.readAsDataURL(file);
                setErrorMessage(null);
            } catch (error) {
                console.error('Error updating profile picture:', error);
                setErrorMessage('Failed to upload the profile picture. Please try again.');
            }
        }
    };

    return (
        <div className={styles.page}>
            <Sidebar role={role}/>
            <div className={styles.content}>
                <div className={styles.profileArea}>
                    <div className={styles.profilePictureContainer}
                         onClick={() => document.getElementById('fileInput').click()}>
                        <img src={profilePicture || 'default-profile.png'} alt="Profile"
                             className={styles.profilePicture}/>
                        <input type="file" id="fileInput" style={{display: 'none'}} onChange={handleImageChange}/>
                    </div>
                    <p>{profileName}</p>
                    {errorMessage && <p className={styles.errorMessage}>{errorMessage}</p>}
                    <button onClick={() => navigate("/logout")} className={styles.logoutButton}>Logout</button>
                </div>
            </div>
        </div>
    );
}

export default ProfilePage;
