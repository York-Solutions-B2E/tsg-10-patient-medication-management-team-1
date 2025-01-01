import { useState, useEffect } from "react";
import "../../styles/patients-page.scss";
import { useAppContext } from "../../context/AppContext.jsx";
import { useNavigate, useSearchParams } from "react-router-dom";
import DataGridComponent from "../common/DataGridComponent.jsx";
import { Tooltip, IconButton } from "@mui/material";
import { Delete, Edit } from "@mui/icons-material";

const PatientsPage = () => {
  const [searchParams] = useSearchParams();
  const [filter] = useState(searchParams.get("filter") || null);
  const [filterValue] = useState(searchParams.get("filterValue") || null);
  const [patients, setPatients] = useState([]);
  const [page, setPage] = useState(0);
  const [limit] = useState(10);
  const [totalLoadedPages, setTotalLoadedPages] = useState(0);
  const [lastPage, setLastPage] = useState(false);

  const { handleGetPatients, isLoading } = useAppContext();

  const navigate = useNavigate();

  const columns = [
    { field: "id", headerName: "ID", width: 90 },
    { field: "lastName", headerName: "Last Name", width: 150 },
    {
      field: "firstName",
      headerName: "First Name",
      width: 150,
    },
    { field: "dob", headerName: "Date of Birth", width: 180 },
    {
      field: "prescriptionCount",
      headerName: "Prescriptions",
      width: 150,
      renderCell: (params) => (
        <Tooltip title="View Prescriptions">
          <button
            onClick={() =>
              navigate(
                `/prescriptions?filter=patientId&filterValue=${params.id}`
              )
            }
          >
            {params.value}
          </button>
        </Tooltip>
      ),
    },
    {
      field: "actions",
      headerName: "",
      width: 150,
      renderCell: (params) => (
        <>
          <Tooltip title="Edit Patient">
            <IconButton onClick={() => console.log("Edit Modal ToDo")}>
              <Edit />
            </IconButton>
          </Tooltip>
          <Tooltip title="Delete Patient">
            <IconButton onClick={() => console.log(params)}>
              <Delete />
            </IconButton>
          </Tooltip>
        </>
      ),
      sortable: false,
      filterable: false,
    },
  ];

  const filterOptions = [
    { value: "id", label: "ID" },
    { value: "lastName", label: "Last Name" },
    { value: "firstName", label: "First Name" },
    { value: "dob", label: "Date of Birth" },
  ];

  const handleSearch = (name, value) => {
    setPatients([]);
    setTotalLoadedPages(0);
    setLastPage(false);
    setPage(0);
    navigate(`/patients?filter=${name}&filterValue=${value}`, {
      replace: true,
    });
  };

  useEffect(() => {
    const fetchPatients = async () => {
      const pageToLoad = page === 0 ? 0 : page - 1;
      const { content, pageNumber, last } = await handleGetPatients(
        pageToLoad,
        limit,
        filter,
        filterValue
      );
      setPatients(content);
      setPage(pageNumber + 1);
      setLastPage(last);
      setTotalLoadedPages(pageNumber + 1);
    };
    if (patients.length === 0 || (!lastPage && page > totalLoadedPages)) {
      fetchPatients();
    }
  }, [
    handleGetPatients,
    page,
    limit,
    filter,
    filterValue,
    lastPage,
    patients,
    totalLoadedPages,
  ]);

  return (
    <div data-testid="grid-container" className="patients-page">
      <DataGridComponent
        title="Patient List"
        isLoading={isLoading}
        rows={patients}
        columns={columns}
        filterOptions={filterOptions}
        filterable={true}
        page={page}
        setPage={setPage}
        lastPage={lastPage}
        totalPages={totalLoadedPages}
        pageSize={limit}
        searchFunction={handleSearch}
      />
    </div>
  );
};

export default PatientsPage;
