import BasicModal from "../common/BasicModal";

const ConfirmModal = ({ confirm, disclosure, isLoading }) => (
  <BasicModal
    title="Cancel Prescription"
    width="400px"
    content="Are you sure you want to cancel this prescription?"
    action={confirm}
    className="confirm-modal"
    isLoading={isLoading}
    isOpen={disclosure.isOpen}
    onClose={disclosure.onClose}
  />
);

export default ConfirmModal;
