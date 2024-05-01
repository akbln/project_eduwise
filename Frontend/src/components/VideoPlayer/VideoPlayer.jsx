import { useParams } from 'react-router-dom';
import "./VideoPlayer.css";

const VideoPlayer = () => {
    const { id } = useParams();
    const videoUrl = `http://localhost/videos/${id}`;
    return (
        <div className="video-player">
            <h1>Video Stream Test</h1>
            <video controls>
                <source src={videoUrl} type="video/mp4" />
                Your browser does not support the video tag.
            </video>
        </div>
    );
};

export default VideoPlayer;