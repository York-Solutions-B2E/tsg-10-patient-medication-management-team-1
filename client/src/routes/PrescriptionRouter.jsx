import { Route, Routes, Navigate } from "react-router-dom";
import PrescriptionsPage from "../components/prescriptionsPage/PrescriptionsPage";
import PrescriptionCreationPage from "../components/prescriptionCreationPage/PrescriptionCreationPage";

const PrescriptionRouter = () => {
  return (
    <Routes>
      <Route path="/" element={<PrescriptionsPage />} />
      <Route path="/create" element={<PrescriptionCreationPage />} />
      <Route path="*" element={<Navigate to="/" />} />
    </Routes>
  );
};

export default PrescriptionRouter;
