import { useState, useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { useAppContext } from "../../context/AppContext";
import { useNavigate } from "react-router-dom";
import Form from "../common/Form.jsx";

const PrescriptionCreationPage = () => {
  const [searchParams] = useSearchParams();
  const [pharmacies, setPharmacies] = useState([]);
  const [medications, setMedications] = useState([]);

  const navigate = useNavigate();

  const {
    handleGetPharmacies,
    handleGetMedications,
    handleCreatePrescription,
    isLoading,
    doctorUser,
  } = useAppContext();

  const formFields = [
    { name: "doctorId", hidden: true, defaultValue: doctorUser.id },
    {
      name: "patientId",
      label: "Patient ID",
      type: "text",
      required: true,
      validation: "patientId",
      defaultValue: searchParams.get("patientId") || "",
    },
    {
      name: "pharmacyId",
      label: "Pharmacy",
      type: "select",
      options: pharmacies,
      required: true,
    },
    {
      name: "medicationCode",
      label: "Medication",
      type: "select",
      options: medications,
      autocomplete: true,
      required: true,
    },
    { name: "quantity", label: "Quantity", type: "number", required: true },
    { name: "dosage", label: "Dosage", type: "text", required: true },
    {
      name: "instructions",
      label: "Instructions",
      type: "text",
      required: true,
    },
  ];

  const handleSubmit = async (values) => {
    const newPrescription = await handleCreatePrescription(values);
    if (newPrescription) {
      navigate(
        `/prescriptions?filterName=patientId&filterValue=${values.patientId}`
      );
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      const { pharmacies, medications } = await Promise.all([
        handleGetPharmacies(),
        handleGetMedications(),
      ]);
      setPharmacies(pharmacies);
      setMedications(medications);
    };
    if (pharmacies.length === 0 && medications.length === 0) {
      fetchData();
    }
  }, [pharmacies, medications, handleGetPharmacies, handleGetMedications]);

  return (
    <div>
      <Form
        title="Create Prescription"
        fields={formFields}
        onSubmit={handleSubmit}
        isLoading={isLoading}
      />
    </div>
  );
};

export default PrescriptionCreationPage;
