import "../../styles/PublicPage.scss";
import { useAppContext } from "../../context/AppContext"; // Import the context hook

const PublicPage = () => {
  const { handleLogin, isLoading } = useAppContext(); // Destructure handleLogin and isLoading from context

  return (
    <div className="public-page">
      <div className="header-box">The Medical Prescription Manager</div>
      <button
        className="login-button"
        onClick={handleLogin}
        disabled={isLoading} // Disable the button while loading
      >
        {isLoading ? "Loading..." : "Login"} {/* Show feedback */}
      </button>
    </div>
  );
};

export default PublicPage;
