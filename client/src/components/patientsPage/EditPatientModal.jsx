import { Box } from "@mui/material";
import BasicModal from "../common/BasicModal";
import Form from "../common/Form";

const EditPatientModal = ({ disclosure, fields, onSubmit }) => {
  return (
    <BasicModal
      width="800px"
      title="Edit Patient"
      isOpen={disclosure.isOpen}
      onClose={disclosure.onClose}
    >
      <Box
        sx={{
          width: "100%",
        }}
      >
        <Form fields={fields} onSubmit={onSubmit} />
      </Box>
    </BasicModal>
  );
};

export default EditPatientModal;
