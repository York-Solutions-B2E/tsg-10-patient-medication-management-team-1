import Router from "./routes/Router";
import Navbar from "./components/common/Navbar";
import "./App.css";
import { useCookies } from "react-cookie";

import { useAppContext } from "./context/AppContext";

function App() {
  const { doctorUser, handleLogout, isLoading } = useAppContext();
  const [cookies] = useCookies(["XSRF-TOKEN"]);
  return (
    <>
      <Navbar
        isLoggedIn={doctorUser ? true : false}
        logout={handleLogout}
        isLoading={isLoading}
        userInfo={doctorUser}
      />
      <Router isLoggedIn={cookies["XSRF-TOKEN"] ? true : false} />
    </>
  );
}

export default App;
