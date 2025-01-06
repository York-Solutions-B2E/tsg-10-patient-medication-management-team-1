import Router from "./routes/Router";
import Navbar from "./components/common/Navbar";
import "./App.css";

import { useAppContext } from "./context/AppContext";

function App() {
  const { doctorUser, handleLogout, isLoading } = useAppContext();
  return (
    <>
      <Navbar
        isLoggedIn={doctorUser ? true : false}
        logout={handleLogout}
        isLoading={isLoading}
        userInfo={doctorUser}
      />
      <Router isLoggedIn={doctorUser ? true : false} />
    </>
  );
}

export default App;
