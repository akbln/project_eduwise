import styles from "./Header.module.css";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

const Header = () => {
    const [profilePicture, setProfilePicture] = useState(null);
    const [profileName, setProfileName] = useState("Your Name");
    const navigate = useNavigate();
    useEffect(() => {
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
    return (
        <div className={styles.header}>
            <div onClick={() => navigate("/")} className={`${styles.home} pointer`}></div>
            <div className={styles.profileArea}>
                <div onClick={() => navigate("/profile")} className={styles.picWrapper}><img src={profilePicture || 'default-profile.png'} alt="Profile"
                          className={styles.profilePicture}/></div>
                <div className={styles.name}><p>{profileName}</p></div>
            </div>
        </div>
    )
}
export default Header;