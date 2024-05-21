// src/Timer.js
import React, { useState, useEffect } from 'react';
import styles from './Timer.module.css'; // Import the CSS module

const Timer = ({ initialTime }) => {
    const [time, setTime] = useState(() => {
        const savedTime = localStorage.getItem('timer');
        return savedTime !== null ? JSON.parse(savedTime) : initialTime;
    });

    useEffect(() => {
        if (time > 0) {
            const timerId = setInterval(() => {
                setTime(prevTime => {
                    const newTime = prevTime - 1;
                    localStorage.setItem('timer', JSON.stringify(newTime));
                    return newTime;
                });
            }, 1000);

            return () => clearInterval(timerId); // Cleanup interval on component unmount
        } else {
            localStorage.removeItem('timer');
        }
    }, [time]);

    const calculateWidth = () => {
        return (time / initialTime) * 100 + '%';
    };

    const calculateColor = () => {
        const ratio = time / initialTime;
        const red = Math.min(255, 255 * (1 - ratio));
        const green = Math.min(255, 255 * ratio);
        return `rgb(${Math.floor(red)}, ${Math.floor(green)}, 0)`;
    };

    return (
        <div className={styles.timerContainer}>
            <div className={styles.timerBar} style={{ width: calculateWidth(), backgroundColor: calculateColor() }}></div>
        </div>
    );
};

export default Timer;
