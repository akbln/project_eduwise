import { json } from "react-router-dom";
import VideoPlayer from "../components/VideoPlayer/VideoPlayer.jsx";
import "./videoHome.css";
import axios from "axios";

const Home = () => {
  makeRequest();
  return (
    <div className="home-body">
      <div className="home-wrapper">
        <VideoPlayer />
      </div>
    </div>
  );
};

const makeRequest = async () => {
  try {
    const response = await axios.post(
      "http://127.0.0.1/login",
      JSON.stringify({
        email: "teacher@admin.com",
        password: "Password123",
        role: "teacher",
      }),
      {
        headers: { "Content-Type": "application/json" },
      }
    );
    if (response.status !== 200) {
      console.log("Error in harake function", response.status);
    }
    console.log(response.data.token);
  } catch (error) {
    console.log(error);
  }
};

export default Home;
