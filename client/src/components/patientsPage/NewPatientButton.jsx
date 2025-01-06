import { Tooltip, IconButton } from "@mui/material";
import { AddSharp } from "@mui/icons-material";

const NewPatientButton = ({ onClick }) => {
  return (
    <Tooltip title="Create New Patient">
      <IconButton onClick={onClick}>
        <AddSharp />
      </IconButton>
    </Tooltip>
  );
};

export default NewPatientButton;
