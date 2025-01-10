import { useState, useEffect } from "react";
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
  const [totalLoadedPages, setTotalLoadedPages] = useState(0);
  const [lastPage, setLastPage] = useState(false);
  const [selectedPatientId, setSelectedPatientId] = useState(null);

  const [pageModel, setPageModel] = useState({ page: 0, pageSize: 5 });

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
    {
      field: "id",
      headerName: "ID",
      width: 90,
      headerClassName: "super-app-theme--header",
      headerAlign: "center",
    },
    {
      field: "lastName",
      headerName: "Last Name",
      width: 150,
      headerClassName: "super-app-theme--header",
      headerAlign: "center",
    },
    {
      field: "firstName",
      headerName: "First Name",
      width: 150,
      headerClassName: "super-app-theme--header",
      headerAlign: "center",
    },
    {
      field: "dob",
      headerName: "Date of Birth",
      width: 180,
      headerClassName: "super-app-theme--header",
      headerAlign: "center",
    },
    {
      field: "prescriptionCount",
      headerName: "Prescriptions",
      width: 150,
      headerClassName: "super-app-theme--header",
      headerAlign: "center",
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
      headerClassName: "super-app-theme--header",
      headerAlign: "center",
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

  const handleSearch = async (name, value) => {
    const { content, number, last } = await handleGetPatients(
      0,
      5,
      name,
      value
    );
    setTotalLoadedPages(number + 1);
    setLastPage(last);
    setPageModel({ ...pageModel, page: number });
    setPatients(content);
    navigate(`/patients?filterName=${name}&filterValue=${value}`, {
      replace: true,
    });
  };

  // Form fields for create and edit modals

  const getFormFields = (patient = null) => {
    const f = [
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
    if (patient) {
      f.push({
        name: "id",
        hidden: true,
        defaultValue: patient.id,
      });
    }
    return f;
  };

  // Reset method to clear the page and reload data

  const resetPage = () => {
    setPatients([]);
    setTotalLoadedPages(0);
    setLastPage(false);
    setPageModel({ ...pageModel, page: 0 });
  };

  // Submit methods for create, update, and delete

  const onCreateSubmit = async (values) => {
    const newPatient = await handleCreatePatient(values);
    createModalDisc.onClose();
    resetPage();
    navigate(`/patients?filterName=id&filterValue=${newPatient.id}`);
  };

  const onUpdateSubmit = async (values) => {
    const updatedPatient = await handleUpdatePatient(values);
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
      const { content, number, last } = await handleGetPatients(
        pageModel.page,
        pageModel.pageSize,
        searchParams.get("filterName") || null,
        searchParams.get("filterValue") || null
      );
      setPatients([...patients, ...content]);
      setPageModel({ ...pageModel, page: number });
      setLastPage(last);
      setTotalLoadedPages(number + 1);
    };
    if (
      (patients.length === 0 && totalLoadedPages === 0) ||
      (!lastPage && pageModel.page == totalLoadedPages)
    ) {
      fetchPatients();
    }
  }, [
    handleGetPatients,
    pageModel,
    searchParams,
    lastPage,
    patients,
    totalLoadedPages,
  ]);

  return (
    <div data-testid="grid-container" className="patients-page">
      {/* <div className="header-box">Patient List</div> */}
      <DataGridComponent
        isLoading={isLoading}
        title="Patient List"
        rows={patients}
        columns={columns}
        filterOptions={filterOptions}
        filterable={true}
        lastPage={lastPage}
        totalPages={totalLoadedPages}
        pageModel={pageModel}
        setPageModel={setPageModel}
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
