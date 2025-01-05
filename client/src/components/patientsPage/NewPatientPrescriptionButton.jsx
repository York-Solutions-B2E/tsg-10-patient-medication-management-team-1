import { Tooltip } from "@mui/material";
import { AddSharp } from "@mui/icons-material";
import Button from "../common/Button";

const NewPatientPrescriptionButton = ({ onClick, prescriptionCount }) => {
  return (
    <Tooltip title="Create New Prescription">
      <Button
        type="primary"
        text={prescriptionCount}
        tooltipText={"Create New Prescription"}
        action={onClick}
        className="new-patient-scirpt-button"
        icon={<AddSharp />}
      />
    </Tooltip>
  );
};

export default NewPatientPrescriptionButton;
