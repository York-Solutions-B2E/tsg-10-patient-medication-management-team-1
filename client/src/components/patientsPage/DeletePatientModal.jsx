import BasicModal from "../common/BasicModal";

const DeletePatientModal = ({ disclosure, onSubmit }) => {
  return (
    <BasicModal
      title="Delete Patient"
      isOpen={disclosure.isOpen}
      onClose={disclosure.onClose}
      content="Are you sure you want to delete this patient?"
      action={onSubmit}
      width="400px"
    />
  );
};

export default DeletePatientModal;
