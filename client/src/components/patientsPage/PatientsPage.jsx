import {useEffect, useState} from "react";
import axios from "axios";
import {DataGrid} from "@mui/x-data-grid";
const PatientsPage = () => {
  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

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

  return (
      <div>
        <div>Hello</div>
        <div style={{height: 400, width: '100%'}}>
          <DataGrid rows={rows} columns={columns} pageSize={5}/>
        </div>
      </div>
  )
};

export default PatientsPage;
