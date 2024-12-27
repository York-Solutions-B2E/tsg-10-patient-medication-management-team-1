import { Route, Routes, Navigate } from "react-router-dom";
import PatientsPage from "../components/patientsPage/PatientsPage";
import PublicPage from "../components/publicPage/PublicPage";
import PrescriptionRouter from "./PrescriptionRouter";

const Router = ({ isLoggedIn }) => {
  return (
    <Routes>
      {isLoggedIn ? (
        <>
          <Route path="/patients" element={<PatientsPage />} />
          <Route path="/prescriptions/*" element={<PrescriptionRouter />} />
          <Route path="*" element={<Navigate to="/patients" />} />
        </>
      ) : (
        <>
          <Route path="/" element={<PublicPage />} />
          <Route path="*" element={<Navigate to="/" />} />
        </>
      )}
    </Routes>
  );
};

export default Router;
