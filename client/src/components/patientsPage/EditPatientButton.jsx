import { Tooltip, IconButton } from "@mui/material";
import { Edit } from "@material-ui/icons";

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
