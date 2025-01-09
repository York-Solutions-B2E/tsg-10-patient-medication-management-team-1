import { useState, useEffect } from "react";
// import "../../styles/patients-page.scss";
import { useAppContext } from "../../context/AppContext.jsx";
import { useNavigate, useSearchParams } from "react-router-dom";
import DataGridComponent from "../common/DataGridComponent.jsx";
import DeletePatientModal from "./DeletePatientModal.jsx";
import CreatePatientModal from "./CreatePatientModal.jsx";
import EditPatientModal from "./EditPatientModal.jsx";
import NewPatientButton from "./NewPatientButton.jsx";
import EditPatientButton from "./EditPatientButton.jsx";
import DeletePatientButton from "./DeletePatientButton.jsx";
import NewPatientPrescriptionButton from "./NewPatientPrescriptionButton.jsx";

import useDisclosure from "../../hooks/useDisclosure.js";

import states from "../../utils/states.js";

const PatientsPage = () => {
  const [searchParams] = useSearchParams();
  const [patients, setPatients] = useState([]);
  const [page, setPage] = useState(0);
  const [limit] = useState(10);
  const [totalLoadedPages, setTotalLoadedPages] = useState(0);
  const [lastPage, setLastPage] = useState(false);
  const [selectedPatientId, setSelectedPatientId] = useState(null);

  const createModalDisc = useDisclosure();
  const editModalDisc = useDisclosure();
  const deleteConfirmDisc = useDisclosure();

  const filterName = searchParams.get("filterName");
  const filterValue = searchParams.get("filterValue");
  console.log(filterName, filterValue);

  const {
    handleGetPatients,
    handleCreatePatient,
    handleUpdatePatient,
    handleDeletePatient,
    isLoading,
  } = useAppContext();

  const navigate = useNavigate();

  // DataGrid columns

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
        <NewPatientPrescriptionButton
          onClick={() => {
            navigate(`/prescriptions/create?patientId=${params.row.patientId}`);
          }}
          prescriptionCount={params.row.prescriptionCount}
        />
      ),
    },
    {
      field: "actions",
      headerName: "",
      width: 150,
      renderCell: (params) => (
        <>
          <EditPatientButton
            onClick={() => {
              setSelectedPatientId(params.row.id);
              editModalDisc.onOpen();
            }}
          />

          <DeletePatientButton
            onClick={() => {
              setSelectedPatientId(params.row.id);
              deleteConfirmDisc.onOpen();
            }}
          />
        </>
      ),
      sortable: false,
      filterable: false,
    },
  ];

  // Search filter options and methods

  const filterOptions = [
    { value: "id", label: "ID" },
    { value: "lastName", label: "Last Name" },
    { value: "firstName", label: "First Name" },
    { value: "dob", label: "Date of Birth" },
  ];

  const handleSearch = (name, value) => {
    navigate(`/patients?filterName=${name}&filterValue=${value}`, {
      replace: true,
    });
    setPatients([]);
    setTotalLoadedPages(0);
    setLastPage(false);
    setPage(0);
  };

  // Form fields for create and edit modals

  const getFormFields = (patient = null) => [
    {
      name: "firstName",
      type: "text",
      label: "First Name",
      required: true,
      defaultValue: patient ? patient.firstName : "",
    },
    {
      name: "lastName",
      type: "text",
      label: "Last Name",
      required: true,
      defaultValue: patient ? patient.lastName : "",
    },
    {
      name: "dob",
      type: "date",
      label: "Date of Birth",
      disableFuture: true,
      required: true,
      defaultValue: patient ? patient.dob : null,
    },
    {
      name: "gender",
      type: "select",
      label: "Gender",
      options: [
        { value: "MALE", label: "Male" },
        { value: "FEMALE", label: "Female" },
      ],
      required: true,
      defaultValue: patient ? patient.gender : "",
    },
    {
      name: "email",
      type: "email",
      label: "Email",
      required: true,
      validation: "email",
      defaultValue: patient ? patient.email : "",
    },
    {
      name: "phone",
      type: "text",
      label: "Phone",
      required: true,
      validation: "phone",
      defaultValue: patient ? patient.phone : "",
    },
    { type: "divider", label: "Address" },
    {
      name: "street1",
      type: "text",
      label: "Street 1",
      required: true,
      defaultValue: patient ? patient.street1 : "",
    },
    {
      name: "street2",
      type: "text",
      label: "Street 2",
      defaultValue: patient ? patient.street2 : "",
    },
    {
      name: "city",
      type: "text",
      label: "City",
      required: true,
      defaultValue: patient ? patient.city : "",
    },
    {
      name: "state",
      type: "select",
      label: "State",
      options: states,
      autocomplete: true,
      required: true,
      defaultValue: patient ? patient.state : "",
    },
    {
      name: "zipCode",
      type: "text",
      label: "Zip",
      required: true,
      validation: "zip",
      defaultValue: patient ? patient.zipCode : "",
    },
  ];

  // Reset method to clear the page and reload data

  const resetPage = () => {
    setPatients([]);
    setTotalLoadedPages(0);
    setLastPage(false);
    setPage(0);
  };

  // Submit methods for create, update, and delete

  const onCreateSubmit = async (values) => {
    const newPatient = await handleCreatePatient(values);
    console.log(newPatient);
    createModalDisc.onClose();
    resetPage();
    navigate(`/patients?filterName=id&filterValue=${newPatient.id}`);
  };

  const onUpdateSubmit = async (values) => {
    const updatedPatient = await handleUpdatePatient(selectedPatientId, values);
    setPatients(
      patients.map((p) => (p.id === updatedPatient.id ? updatedPatient : p))
    );
    editModalDisc.onClose();
  };

  const onDeleteSubmit = async () => {
    await handleDeletePatient(selectedPatientId);
    deleteConfirmDisc.onClose();
    resetPage();
    navigate("/patients");
  };

  useEffect(() => {
    const fetchPatients = async () => {
      const pageToLoad = page === 0 ? 0 : page - 1;
      console.log(searchParams.get("filterName"));
      const { content, number, last } = await handleGetPatients(
        pageToLoad,
        limit,
        searchParams.get("filterName") || null,
        searchParams.get("filterValue") || null
      );
      setPatients(content);
      setPage(number + 1);
      setLastPage(last);
      setTotalLoadedPages(number + 1);
    };
    if (patients.length === 0 || (!lastPage && page > totalLoadedPages)) {
      fetchPatients();
    }
  }, [
    handleGetPatients,
    page,
    limit,
    searchParams,
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
      <div className="button-area">
        <NewPatientButton onClick={createModalDisc.onOpen} />
      </div>
      <CreatePatientModal
        disclosure={createModalDisc}
        fields={getFormFields()}
        onSubmit={onCreateSubmit}
      />
      <EditPatientModal
        disclosure={editModalDisc}
        fields={getFormFields(
          selectedPatientId
            ? patients.find((p) => p.id === selectedPatientId)
            : null
        )}
        onSubmit={onUpdateSubmit}
      />
      <DeletePatientModal
        disclosure={deleteConfirmDisc}
        onSubmit={onDeleteSubmit}
      />
    </div>
  );
};

export default PatientsPage;
