import {useEffect, useState} from "react";
import axios from "axios";

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


  return (
      <div>Test</div>
  )
};

export default PatientsPage;
