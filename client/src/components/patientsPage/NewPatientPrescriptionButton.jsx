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
        style={{
          backgroundColor: "#FFD755", // Golden yellow color
          color: "black", // Black text for contrast
          border: "1px solid black", // Border to match design
          boxShadow: "5px 5px black", // Drop shadow
          padding: "10px 20px", // Padding for button sizing
          fontWeight: "bold", // Bold text
          cursor: "pointer", // Pointer cursor on hover
          textTransform: "uppercase", // Uppercase text
          transition: "transform 0.2s ease", // Animation for hover/active states
        }}
      />
    </Tooltip>
  );
};

export default NewPatientPrescriptionButton;
