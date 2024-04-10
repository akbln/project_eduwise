import { useState, useEffect } from "react";
import "./App.css";

function App() {
  const [count, setCount] = useState(0);
  const makeRequest = async () => {
    try {
      const response = await fetch("http://localhost/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: "Omar@gmail.com",
          password: "Omar1234",
          role: "student",
        }),
      });
      if (response.status !== 200) {
        console.log(response);
      }
      const data = await response.json();
      console.log(data);
    } catch (error) {
      console.error(error);
    }
  };
  useEffect(() => {
    makeRequest();
  }, []);
  return <div>Haruka test</div>;
}

export default App;
