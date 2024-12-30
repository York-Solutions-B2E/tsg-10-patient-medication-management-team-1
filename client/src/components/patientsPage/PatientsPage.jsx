import {useEffect, useState} from "react";
import axios from "axios";
import {DataGrid} from "@mui/x-data-grid";
import {TextField} from "@mui/material";
import "../../styles/patients-page.scss"
import {useAppContext} from "../../context/AppContext.jsx"
import DataGridComponent from "../DataGridComponent.jsx";

const PatientsPage = () => {
  const { handleGetPatients, isLoading, error } = useAppContext();
  const [patients, setPatients] = useState([]);

  const title = "Patient List"

  useEffect(() => {
    const fetchPatients = async () => {
      try {
        const patientData = await handleGetPatients(); // Example with default page and limit
        setPatients(patientData.json());
        console.log("SDFLJSDF", patientData);
      } catch (err) {
        console.error("Error fetching patients:", err);
      }
    };

    fetchPatients();
  }, [])

  const columns = [
    { field: 'id', headerName: 'ID', width: 90 },
    { field: 'firstName', headerName: 'First Name', width: 150 },
    { field: 'lastName', headerName: 'Last Name', width: 150 },
    { field: 'Dob', headerName: 'Date of Birth', width: 180 },
    { field: 'Address', headerName: 'Address', width: 180 },
    { field: 'Prescriptions', headerName: 'Prescriptions', width: 150 },
  ]

  const rows = patients.map((patient) => ({
    id: patient.Id,
    firstName: patient.firstName,
    lastName: patient.lastName,
    Dob: patient.Dob,
    Address: `${patient.Address.Street_one} ${patient.Address.Street_two ? `, ${patient.Address.Street_two}` : ''}, ${patient.Address.City}, ${patient.Address.State} ${patient.Address.Zip}`,
    Prescriptions: patient.Prescriptions,
  }));

  return (
      <div className="patients-page">
        <DataGridComponent
            title={title}
            isLoading={isLoading}
            error={error}
            columns={columns}
            rows={rows}
        />
      </div>
  )
};

export default PatientsPage;
