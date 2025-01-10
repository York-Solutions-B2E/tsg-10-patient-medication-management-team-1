import { useState, useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { useAppContext } from "../../context/AppContext";
import { useNavigate } from "react-router-dom";
import Form from "../common/Form.jsx";

const PrescriptionCreationPage = () => {
  const [searchParams] = useSearchParams();
  const [pharmacies, setPharmacies] = useState([]);
  const [medications, setMedications] = useState([]);
  const [fetched, setFetched] = useState(false);

  const navigate = useNavigate();

  const {
    handleGetPharmacies,
    handleGetMedications,
    handleCreatePrescription,
    isLoading,
    doctorUser,
  } = useAppContext();

  const formFields = [
    {
      name: "doctorId",
      hidden: true,
      defaultValue: doctorUser ? doctorUser.id : "",
    },
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
      options:
        pharmacies.length > 0
          ? pharmacies.map((p) => {
              return { value: p.id, label: `${p.name} ${p.id}` };
            })
          : [],
      required: true,
    },
    {
      name: "medicationId",
      label: "Medication",
      type: "select",
      options: medications.map((m) => {
        return {
          value: m.id,
          label: `${m.medicationCode} ${m.medicationName}`,
        };
      }),
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
    console.log(pharmacies);
    const fetchData = async () => {
      const pharmaciesData = await handleGetPharmacies();
      const medicationsData = await handleGetMedications();
      console.log(pharmaciesData);
      console.log(medicationsData);
      setPharmacies(pharmaciesData);
      setMedications(medicationsData);
      setFetched(true);
    };
    // if (pharmacies.length === 0 && medications.length === 0) {
    if (!fetched) {
      fetchData();
    }
  }, []);

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
