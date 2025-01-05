import { useState, useEffect } from "react";
// import "../../styles/patients-page.scss";
import { useAppContext } from "../../context/AppContext.jsx";
import { useNavigate, useSearchParams } from "react-router-dom";
import DataGridComponent from "../common/DataGridComponent.jsx";
import { Tooltip, IconButton } from "@mui/material";
import { Delete, Edit } from "@mui/icons-material";
import Form from "../common/Form.jsx";
import BasicModal from "../common/BasicModal.jsx";
import useDisclosure from "../../hooks/useDisclosure.js";

import states from "../../utils/states.js";

const PatientsPage = () => {
  const [searchParams] = useSearchParams();
  const [filter] = useState(searchParams.get("filter") || null);
  const [filterValue] = useState(searchParams.get("filterValue") || null);
  const [patients, setPatients] = useState([]);
  const [page, setPage] = useState(0);
  const [limit] = useState(10);
  const [totalLoadedPages, setTotalLoadedPages] = useState(0);
  const [lastPage, setLastPage] = useState(false);
  const [selectedPatientId, setSelectedPatientId] = useState(null);

  const createModalDisc = useDisclosure();
  const editModalDisc = useDisclosure();
  const deleteConfirmDisc = useDisclosure();

  const {
    handleGetPatients,
    handleCreatePatient,
    handleUpdatePatient,
    handleDeletePatient,
    isLoading,
  } = useAppContext();

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
            <IconButton
              onClick={() => {
                setSelectedPatientId(params.row.id);
                editModalDisc.onOpen();
              }}
            >
              <Edit />
            </IconButton>
          </Tooltip>
          <Tooltip title="Delete Patient">
            <IconButton
              onClick={() => {
                setSelectedPatientId(params.row.id);
                deleteConfirmDisc.onOpen();
              }}
            >
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
      required: true,
      defaultValue: patient ? patient.dob : "",
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
      name: "streetOne",
      type: "text",
      label: "Street 1",
      required: true,
      defaultValue: patient ? patient.address.streetOne : "",
    },
    {
      name: "streetTwo",
      type: "text",
      label: "Street 2",
      defaultValue: patient ? patient.address.streetTwo : "",
    },
    {
      name: "city",
      type: "text",
      label: "City",
      required: true,
      defaultValue: patient ? patient.address.city : "",
    },
    {
      name: "state",
      type: "select",
      label: "State",
      options: states,
      autocomplete: true,
      required: true,
      defaultValue: patient ? patient.address.state : "",
    },
    {
      name: "zip",
      type: "text",
      label: "Zip",
      required: true,
      validation: "zip",
      defaultValue: patient ? patient.address.zip : "",
    },
  ];

  const resetPage = () => {
    setPatients([]);
    setTotalLoadedPages(0);
    setLastPage(false);
    setPage(0);
  };

  const editFields = getFormFields(
    selectedPatientId ? patients.find((p) => p.id === selectedPatientId) : null
  );
  const createFields = getFormFields();

  const onCreateSubmit = async (values) => {
    const newPatient = await handleCreatePatient(values);
    createModalDisc.onClose();
    resetPage();
    navigate(`/patients?filterName=id&filterValue=${newPatient.id}`);
  };

  const onUpdateSubmit = async (values) => {
    const updatedPatient = await handleUpdatePatient(values);
    editModalDisc.onClose();
    resetPage();
    navigate(`/patients?filterName=id&filterValue=${updatedPatient.id}`);
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
      <BasicModal
        title="Create Patient"
        isOpen={true}
        onClose={createModalDisc.onClose}
      >
        <Form
          fields={createFields}
          onSubmit={onCreateSubmit}
          // onCancel={createModalDisc.onClose}
          clearable={true}
        />
      </BasicModal>
      <BasicModal
        title="Edit Patient"
        isOpen={editModalDisc.isOpen}
        onClose={editModalDisc.onClose}
      >
        <Form
          fields={editFields}
          onSubmit={onUpdateSubmit}
          // onCancel={editModalDisc.onClose}
        />
      </BasicModal>
      <BasicModal
        title="Delete Patient"
        isOpen={deleteConfirmDisc.isOpen}
        onClose={deleteConfirmDisc.onClose}
        content="Are you sure you want to delete this patient?"
        action={onDeleteSubmit}
      />
    </div>
  );
};

export default PatientsPage;
