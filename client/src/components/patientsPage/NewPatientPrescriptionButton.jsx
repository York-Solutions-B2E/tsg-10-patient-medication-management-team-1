import { AddSharp } from "@mui/icons-material";
import Button from "../common/Button";

const NewPatientPrescriptionButton = ({ onClick, prescriptionCount }) => {
  return (
    <Button
      tooltipText={"Create New Prescription"}
      type="primary"
      text={prescriptionCount}
      action={onClick}
      className="new-patient-scirpt-button"
      icon={<AddSharp />}
    />
  );
};

export default NewPatientPrescriptionButton;
