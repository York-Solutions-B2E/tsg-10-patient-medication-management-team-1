import Router from "./routes/Router";
import Navbar from "./components/common/Navbar";
import "./App.css";

import { useAppContext } from "./context/AppContext";

function App() {
  const { doctorUser, handleLogout } = useAppContext();
  return (
    <>
      <Navbar isLoggedIn={true} logout={handleLogout} />
      <Router isLoggedIn={true} />
    </>
  );
}

export default App;
