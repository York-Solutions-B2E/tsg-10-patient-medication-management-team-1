import { Tooltip, IconButton } from "@mui/material";
import { Delete } from "@mui/icons-material";

const DeletePatientButton = ({ onClick }) => {
  return (
    <Tooltip title="Delete Patient">
      <IconButton onClick={onClick}>
        <Delete />
      </IconButton>
    </Tooltip>
  );
};

export default DeletePatientButton;
