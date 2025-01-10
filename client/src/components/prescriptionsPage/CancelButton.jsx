import { Tooltip, IconButton } from "@mui/material";
import { CancelPresentationSharp } from "@mui/icons-material";

const CancelButton = ({ onClick, disabled }) => (
  <Tooltip title="Cancel Prescription">
    <IconButton disabled={disabled} onClick={onClick}>
      <CancelPresentationSharp />
    </IconButton>
  </Tooltip>
);

export default CancelButton;
