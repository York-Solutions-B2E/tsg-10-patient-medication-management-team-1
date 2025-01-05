import { Tooltip, IconButton } from "@mui/material";
import { Delete } from "@material-ui/icons";

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
