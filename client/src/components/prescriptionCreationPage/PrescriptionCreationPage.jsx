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
      className: "instructions-field",
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
  }, [
    fetched,
    handleGetPharmacies,
    handleGetMedications,
    pharmacies,
    medications,
  ]);

  return (
    <div
      style={{
        padding: "20px",
        backgroundColor: "#fff8e1",
        minHeight: "100vh",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <div
        className="header-box"
        style={{
          textAlign: "center",
          marginBottom: "20px",
          boxShadow: "10px 10px 0px black",
          padding: "10px",
          width: "400px",
        }}
      >
        Create New Prescription
      </div>
      <div
        style={{
          backgroundColor: "#ffffff", // White background for clarity
          padding: "30px", // More padding for better spacing
          borderRadius: "10px", // Rounded corners for aesthetics
          boxShadow: "10px 10px 0px black", // Drop shadow for depth
          width: "400px", // Fixed width for consistent layout
          display: "flex", // Flexbox for alignment
          flexDirection: "column", // Stacks elements vertically
          gap: "20px", // Space between child elements
          border: "2px solid black", // Black border for definition
          margin: "20px auto", // Centered with some vertical margin
        }}
      >
        <div style={{ display: "flex", flexDirection: "column", gap: "15px" }}>
          <Form
            title="Create Prescription"
            fields={formFields}
            onSubmit={handleSubmit}
            isLoading={isLoading}
          />
        </div>
      </div>
    </div>
  );
};

export default PrescriptionCreationPage;
