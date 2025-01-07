import { Tooltip, IconButton } from "@mui/material";
import { Edit } from "@mui/icons-material";

const EditPatientButton = ({ onClick }) => {
  return (
    <Tooltip title="Edit Patient">
      <IconButton onClick={onClick}>
        <Edit />
      </IconButton>
    </Tooltip>
  );
};

export default EditPatientButton;
