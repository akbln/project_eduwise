import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Finished.css';  // Import the CSS file

const Finished = () => {
    const navigate = useNavigate();

    const handleGoBack = () => {
        navigate('/');  // Adjust this path to where you want the user to go
    };

    return (
        <div className="finished-container">
            <div className="finished-content">
                <h1>Competition Finished</h1>
                <p>Thank you for participating in the competition!</p>
                <button className="finished-button" onClick={handleGoBack}>
                    Go Back to Home
                </button>
            </div>
        </div>
    );
};

export default Finished;
