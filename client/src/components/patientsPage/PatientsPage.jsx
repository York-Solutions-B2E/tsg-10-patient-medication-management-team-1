import {useEffect, useState} from "react";
import axios from "axios";
import {DataGrid} from "@mui/x-data-grid";
import {TextField} from "@mui/material";

const PatientsPage = () => {
  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    const fetchPatients = async () => {
      try {
        const response = await axios.get('http://localhost:3000/patients')
        setPatients(response.data);
        setLoading(false);
      } catch (error) {
        setError(error);
        setLoading(false);
      }
    }

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

  const filteredRows = rows.filter(row => {
    return (
        row.firstName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        row.lastName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        row.Address.toLowerCase().includes(searchTerm.toLowerCase()) ||
        row.Dob.toLowerCase().includes(searchTerm.toLowerCase())
    );
  });

  return (
      <div>
        <TextField
            label="Search Patients"
            variant="outlined"
            onChange={(e) => setSearchTerm(e.target.value)}
            fullWidth
            margin="normal"
        />
        <div style={{height: 400, width: '100%'}}>
          <DataGrid rows={filteredRows} columns={columns} pageSize={5}/>
        </div>
      </div>
  )
};

export default PatientsPage;
