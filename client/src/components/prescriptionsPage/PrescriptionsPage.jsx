import { useState, useEffect } from "react";
// import "../../styles/patients-page.scss";
import { useAppContext } from "../../context/AppContext.jsx";
import { useNavigate, useSearchParams } from "react-router-dom";
import DataGridComponent from "../common/DataGridComponent.jsx";
import CancelButton from "./CancelButton.jsx";
import DetailsButton from "./DetailsButton.jsx";
import ConfirmModal from "./ConfirmModal.jsx";
import DetailsModal from "./DetailsModal.jsx";
import useDisclosure from "../../hooks/useDisclosure.js";

const PrescriptionsPage = () => {
  const [searchParams] = useSearchParams();
  const [prescriptions, setPrescriptions] = useState([]);
  const [page, setPage] = useState(0);
  const [limit] = useState(10);
  const [totalLoadedPages, setTotalLoadedPages] = useState(0);
  const [lastPage, setLastPage] = useState(false);
  const [selectedPrescriptionId, setSelectedPrescriptionId] = useState(null);

  const detailsModalDisc = useDisclosure();
  const cancelModalDisc = useDisclosure();

  const { handleGetPrescriptions, handleCancelPrescription, isLoading } =
    useAppContext();

  const navigate = useNavigate();

  const columns = [
    { field: "id", headerName: "ID", width: 70 },
    { field: "prescriptionId", headerName: "Prescription ID", width: 120 },
    { field: "patientName", headerName: "Patient", width: 120 },
    { field: "patientId", headerName: "Patient ID", width: 90 },
    {
      field: "medicationName",
      headerName: "Medication",
      width: 120,
    },
    { field: "medicationCode", headerName: "Rx Code", width: 100 },
    { field: "createdAt", headerName: "Date Issued", width: 150 },
    { field: "updateAt", headerName: "Last Updated", width: 150 },
    { field: "doctorName", headerName: "Issuing Doctor", width: 130 },
    { field: "pharmacyName", headerName: "Pharmacy", width: 130 },
    { field: "dosage", headerName: "Dosage", width: 70 },
    { field: "quantity", headerName: "Quantity", width: 90 },
    { field: "status", headerName: "Status", width: 130 },
    {
      field: "details",
      headerName: "",
      width: 30,
      renderCell: (params) => {
        return (
          <DetailsButton
            onClick={() => {
              console.log(params.row.id);
              setSelectedPrescriptionId(params.row.id);
              detailsModalDisc.onOpen();
            }}
          />
        );
      },
    },
    {
      field: "actions",
      headerName: "",
      width: 30,
      renderCell: (params) => {
        return (
          <CancelButton
            disabled={
              params.row.status === "CANCELLED" ||
              params.row.status === "PICKED_UP"
            }
            onClick={() => {
              console.log(params.row.id);
              setSelectedPrescriptionId(params.row.id);
              cancelModalDisc.onOpen();
            }}
          />
        );
      },
    },
  ];

  const filterOptions = [
    { value: "prescriptionId", label: "Unique ID" },
    { value: "patientId", label: "Patient ID" },
    { value: "patientName", label: "Patient Name" },
    { value: "doctorName", label: "Doctor Name" },
    { value: "pharmacyName", label: "Pharmacy" },
    { value: "medicationName", label: "Medication" },
    { value: "medicationCode", label: "Medication Code" },
    { value: "status", label: "Status" },
  ];

  const handleSearch = (name, value) => {
    // setPrescriptions([]);
    // setTotalLoadedPages(0);
    // setLastPage(false);
    // setPage(0);
    navigate(`/prescriptions?filterName=${name}&filterValue=${value}`, {
      replace: true,
    });
  };

  const cancelPrescription = async () => {
    const cancelledPrescription = await handleCancelPrescription(
      selectedPrescriptionId
    );
    setPrescriptions(
      prescriptions.map((p) =>
        p.id === selectedPrescriptionId ? cancelledPrescription : p
      )
    );
    setSelectedPrescriptionId(null);
    cancelModalDisc.onClose();
  };

  useEffect(() => {
    console.log(prescriptions);
    console.log();
    const fetchPatients = async () => {
      const pageToLoad = page === 0 ? 0 : page - 1;
      const { content, number, last } = await handleGetPrescriptions(
        pageToLoad,
        limit,
        searchParams.get("filterName") || null,
        searchParams.get("filterValue") || null
      );
      setPrescriptions(content);
      setPage(number + 1);
      setLastPage(last);
      setTotalLoadedPages(number + 1);
    };
    if (prescriptions.length === 0 || (!lastPage && page > totalLoadedPages)) {
      fetchPatients();
    }
  }, [
    handleGetPrescriptions,
    page,
    limit,
    searchParams,
    lastPage,
    prescriptions,
    totalLoadedPages,
  ]);

  return (
    <div data-testid="grid-container" className="patients-page">
      <DataGridComponent
        title="Prescription List"
        isLoading={isLoading}
        rows={prescriptions}
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
      <ConfirmModal
        confirm={cancelPrescription}
        disclosure={cancelModalDisc}
        isLoading={isLoading}
      />
      <DetailsModal
        prescription={
          selectedPrescriptionId !== null
            ? prescriptions.find((p) => p.id === selectedPrescriptionId)
            : ""
        }
        disclosure={detailsModalDisc}
      />
    </div>
  );
};

export default PrescriptionsPage;
