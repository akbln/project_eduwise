
import "./VideoPlayer.css";

const VideoPlayer = ({videoUrl}) => {

    const handleError = (e)=>{
        console.error("Error loading video:", e);
        console.error("Error details:", e.target.error); // Get more detailed error
    }

    return (
        <div className="video-player">
            <video controls>
                <source src={videoUrl} onError={handleError} type="video/mp4" />
                Your browser does not support the video tag.
            </video>
        </div>
    );
};

export default VideoPlayer;