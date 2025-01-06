import { Tooltip, IconButton } from "@mui/material";
import { ContentPasteSearchSharp } from "@mui/icons-material";

const DetailsButton = ({ onClick }) => (
  <Tooltip title="View Instructions">
    <IconButton onClick={onClick}>
      <ContentPasteSearchSharp />
    </IconButton>
  </Tooltip>
);

export default DetailsButton;
