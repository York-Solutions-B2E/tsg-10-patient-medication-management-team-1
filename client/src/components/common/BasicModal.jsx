import {
  Modal,
  Card,
  CardHeader,
  CardContent,
  CardActionArea,
  Box,
} from "@mui/material";
import { CheckSharp, CancelSharp } from "@mui/icons-material";
import Button from "./Button";

const BasicModal = ({
  title,
  content,
  action,
  isLoading,
  isOpen,
  onClose,
  children,
  width,
}) => {
  return (
    <Modal open={isOpen} onClose={onClose}>
      <Box sx={{ width: width, margin: "auto", marginTop: "10%" }}>
        <Card>
          {title && <CardHeader title={title} />}
          {content && (
            <CardContent>
              <span>{content}</span>
            </CardContent>
          )}
          <Box sx={{ padding: "16px" }}>{children}</Box>
          <CardActionArea>
            <Box sx={{ display: "flex", justifyContent: "space-between" }}>
              {action && (
                <Button
                  icon={<CheckSharp />}
                  type="primary"
                  action={action}
                  text="Confirm"
                  loading={isLoading}
                />
              )}
              <Button
                icon={<CancelSharp />}
                type="secondary"
                loading={isLoading}
                action={onClose}
                text={action ? "Cancel" : "Close"}
              />
            </Box>
          </CardActionArea>
        </Card>
      </Box>
    </Modal>
  );
};

BasicModal.defaultProps = {
  title: null,
  content: null,
  action: null,
  isLoading: false,
  isOpen: false,
  onClose: () => {},
};

export default BasicModal;
