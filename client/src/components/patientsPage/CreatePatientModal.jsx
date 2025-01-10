import BasicModal from "../common/BasicModal";
import Form from "../common/Form";
import { Box } from "@mui/material";

const CreatePatientModal = ({ disclosure, fields, onSubmit }) => {
  return (
    <BasicModal
      title="Create Patient"
      isOpen={disclosure.isOpen}
      onClose={disclosure.onClose}
      width="800px"
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

export default CreatePatientModal;
