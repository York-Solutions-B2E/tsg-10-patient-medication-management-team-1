import { useState, useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { useAppContext } from "../../context/AppContext";

const PrescriptionCreationPage = () => {
  const [searchParams] = useSearchParams();
  const [pharmacies, setPharmacies] = useState([]);
  const [medications, setMedications] = useState([]);

  const { handleGetPharmacies, handleGetMedications, isLoading, doctorUser } =
    useAppContext();

  const formFields = [
    { name: "doctorId", hidden: true, defaultValue: doctorUser.id },
    {
      name: "patientId",
      label: "Patient ID",
      type: "text",
      defaultValue: searchParams.get("patientId") || "",
    },
    { name: "pharmacyId", label: "Pharmacy ID", type: "text" },
    {
      name: "medicationCode",
      label: "Medication",
      type: "select",
      options: medications,
      autocomplete: true,
    },
    { name: "quantity", label: "Quantity", type: "number" },
    { name: "dosage", label: "Dosage", type: "text" },
    { name: "instructions", label: "Instructions", type: "text" },
  ];

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
  }, [pharmacies, medications]);

  return <div>PrescriptionCreationPage</div>;
};

export default PrescriptionCreationPage;
