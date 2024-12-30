import "../../styles/Navbar.scss";

import { Button } from "@mui/material";

import { useNavigate, useLocation } from "react-router-dom";

const Navbar = ({ isLoggedIn, logout }) => {
  const navigate = useNavigate();
  const { pathname } = useLocation();

  return (
    <nav className="navbar">
      {/* Home Link */}
      <div className="nav-left">
        <Button onClick={() => navigate("/patients")} className="nav-item">
          Home
        </Button>
      </div>

      {/* Title in the Center */}
      <div className="nav-center">Medical Prescription Manager</div>

      {/* Links on the Right */}
      {isLoggedIn && (
        <div className="nav-right">
          <Button
            onClick={() => navigate("/patients")}
            className={
              `nav-item` + (pathname.includes("/patients") ? " active" : "")
            }
          >
            Patient Management
          </Button>
          <Button
            onClick={() => navigate("/prescriptions")}
            // variant={pathname.includes("/prescriptions") && !pathname.includes("/create") ? "outlined" : "outlined"}
            className={
              `nav-item` +
              (pathname.includes("/prescriptions") &&
              !pathname.includes("/create")
                ? " active"
                : "")
            }
            // className="nav-item"
          >
            Prescription Management
          </Button>
          <Button
            onClick={() => navigate("/prescriptions/create")}
            // variant={pathname.includes("/prescriptions/create") ? "outlined" : "outlined"}
            className={
              `nav-item` +
              (pathname.includes("/prescriptions/create") ? " active" : "")
            }
            // className="nav-item"
          >
            New Prescription
          </Button>
          <Button onClick={logout} className="nav-item logout">
            Logout
          </Button>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
