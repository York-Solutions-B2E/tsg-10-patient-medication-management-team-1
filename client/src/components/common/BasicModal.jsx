import {
  Modal,
  Card,
  CardHeader,
  CardContent,
  CardActionArea,
} from "@mui/material";
import { CheckSharp, CancelSharp } from "@mui/icons-material";
import Button from "./Button";

const ConfirmModal = ({
  title,
  content,
  action,
  isLoading,
  isOpen,
  onClose,
  children,
}) => {
  return (
    <Modal open={isOpen} onClose={onClose}>
      <Card>
        {title && <CardHeader title={title} />}
        {content && (
          <CardContent>
            <span>{content}</span>
          </CardContent>
        )}
        {children}
        <CardActionArea>
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
        </CardActionArea>
      </Card>
    </Modal>
  );
};

ConfirmModal.defaultProps = {
  title: null,
  content: null,
  action: null,
  isLoading: false,
  isOpen: false,
  onClose: () => {},
};

export default ConfirmModal;
