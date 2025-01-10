import BasicModal from "../common/BasicModal";
import { Card, CardContent } from "@mui/material";

const DetailsModal = ({ prescription, disclosure }) => (
  <BasicModal
    width="400px"
    title="Prescription Details"
    className="details-modal"
    isOpen={disclosure.isOpen}
    onClose={disclosure.onClose}
  >
    <Card>
      <CardContent>
        <p>Created At: {prescription.createdAt}</p>
        <p>Updated At: {prescription.updatedAt}</p>
        <p>Instructions: {prescription.instructions}</p>
        <p>Dosage: {prescription.dosage}</p>
        <p>Medication Name: {prescription.medicationName}</p>
        <p>Medication Code: {prescription.medicationCode}</p>
      </CardContent>
    </Card>
  </BasicModal>
);

export default DetailsModal;
