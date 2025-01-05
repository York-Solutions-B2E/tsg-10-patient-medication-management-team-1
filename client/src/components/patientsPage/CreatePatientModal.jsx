import BasicModal from "../common/BasicModal";
import Form from "../common/Form";

const CreatePatientModal = ({ disclosure, fields, onSubmit }) => {
  return (
    <BasicModal
      title="Create Patient"
      isOpen={disclosure.isOpen}
      onClose={disclosure.onClose}
    >
      <Form fields={fields} onSubmit={onSubmit} />
    </BasicModal>
  );
};

export default CreatePatientModal;
