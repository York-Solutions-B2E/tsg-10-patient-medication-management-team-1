import { useState, useEffect } from "react";
import { useAppContext } from "../../context/AppContext";

const PrescriptionCreationPage = () => {
  const [prescriptions, setPrescriptions] = useState([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [limit, setLimit] = useState(10);
  const [filter, setFilter] = useState(null);
  const [filterValue, setFilterValue] = useState(null);

  const { handleGetPrescriptions, isLoading } = useAppContext();

  useEffect(() => {
    handleGetPrescriptions(page, limit, filter, filterValue).then(
      (response) => {
        setPrescriptions(response.prescriptions);
        setTotalPages(response.totalPages);
      }
      if ()
    );
  }


  return <div>PrescriptionCreationPage</div>;
};

export default PrescriptionCreationPage;
