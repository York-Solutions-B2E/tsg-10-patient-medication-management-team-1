import "../../styles/Navbar.scss";

import Button from "./Button";

import {
  GroupSharp,
  MedicationSharp,
  DrawSharp,
  LocalHospitalSharp,
} from "@mui/icons-material";

import { useNavigate, useLocation } from "react-router-dom";

const Navbar = ({ isLoggedIn, logout, userInfo, isLoading }) => {
  const navigate = useNavigate();
  const { pathname } = useLocation();

  return (
    <nav className="navbar">
      {/* Home Link */}
      <div className="nav-left">
        <Button
          onClick={() => navigate("/patients")}
          type="nav-item home"
          text="Home"
          icon={<LocalHospitalSharp />}
        />
      </div>

      {/* Title in the Center */}
      <div className="nav-center">Medical Prescription Manager</div>
      <span className="nav-center-subtitle">
        {userInfo && `Dr. ${userInfo.firstName} ${userInfo.lastName}`}
      </span>
      {/* Links on the Right */}
      {isLoggedIn && (
        <div className="nav-right">
          <Button
            onClick={() => navigate("/patients")}
            selected={pathname.includes("/patients")}
            type="nav-tab"
            text="Patients"
            tooltipText="Patient Management"
            icon={<GroupSharp />}
          />

          <Button
            onClick={() => navigate("/prescriptions")}
            selected={
              pathname.includes("/prescriptions") &&
              !pathname.includes("/create")
            }
            type="nav-tab"
            text="Prescriptions"
            tooltipText="Prescription Management"
            icon={<MedicationSharp />}
          />
          <Button
            onClick={() => navigate("/prescriptions/create")}
            selected={pathname.includes("/prescriptions/create")}
            type="nav-tab"
            text="New Prescription"
            tooltipText="Create New Prescription"
            icon={<DrawSharp />}
          />
          <Button
            loading={isLoading}
            onClick={logout}
            type="nav-tab"
            text="Logout"
            tooltipText="Logout"
          />
        </div>
      )}
    </nav>
  );
};

export default Navbar;
