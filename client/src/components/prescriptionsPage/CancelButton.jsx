import { Tooltip, IconButton } from "@mui/material";
import { CancelPresentationSharp } from "@mui/icons-material";

const CancelButton = ({ onClick }) => (
  <Tooltip title="Cancel Prescription">
    <IconButton onClick={onClick}>
      <CancelPresentationSharp />
    </IconButton>
  </Tooltip>
);

export default CancelButton;
