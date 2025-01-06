import BasicModal from "../common/BasicModal";
import Form from "../common/Form";

const EditPatientModal = ({ disclosure, fields, onSubmit }) => {
  return (
    <BasicModal
      title="Edit Patient"
      isOpen={disclosure.isOpen}
      onClose={disclosure.onClose}
    >
      <Form fields={fields} onSubmit={onSubmit} />
    </BasicModal>
  );
};

export default EditPatientModal;
