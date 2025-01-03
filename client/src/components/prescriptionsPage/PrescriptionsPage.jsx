import { useState, useEffect } from "react";
import "../../styles/patients-page.scss";
import { useAppContext } from "../../context/AppContext.jsx";
import { useNavigate, useSearchParams } from "react-router-dom";
import DataGridComponent from "../common/DataGridComponent.jsx";
import BasicModal from "../common/BasicModal.jsx";
import { Tooltip, IconButton } from "@mui/material";
import {
  ContentPasteSearchSharp,
  CancelPresentationSharp,
} from "@mui/icons-material";
import useDisclosure from "../../hooks/useDisclosure.js";

const PrescriptionsPage = () => {
  const [searchParams] = useSearchParams();
  const [filter] = useState(searchParams.get("filter") || null);
  const [filterValue] = useState(searchParams.get("filterValue") || null);
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
    { field: "id", headerName: "ID", width: 90 },
    { field: "patientName", headerName: "Patient", width: 150 },
    {
      field: "medicationName",
      headerName: "Medication",
      width: 150,
    },
    { field: "createdAt", headerName: "Date Issued", width: 180 },
    { field: "updateAt", headerName: "Last Updated", width: 180 },
    { field: "doctorName", headerName: "Issuing Doctor", width: 180 },
    { field: "pharmacyName", headerName: "Pharmacy", width: 180 },
    { field: "dosage", headerName: "Dosage", width: 150 },
    { field: "status", headerName: "Status", width: 150 },
    {
      field: "details",
      headerName: "",
      width: 130,
      renderCell: (params) => {
        setSelectedPrescriptionId(params.row.id);
        return (
          <Tooltip title="View Instructions">
            <IconButton onClick={detailsModalDisc.onOpen}>
              <ContentPasteSearchSharp />
            </IconButton>
          </Tooltip>
        );
      },
    },
    {
      field: "actions",
      headerName: "",
      width: 150,
      renderCell: (params) => {
        setSelectedPrescriptionId(params.row.id);
        return (
          <>
            <Tooltip title="Cancel Prescription">
              <IconButton onClick={cancelModalDisc.onOpen}>
                <CancelPresentationSharp />
              </IconButton>
            </Tooltip>
          </>
        );
      },
    },
  ];

  const filterOptions = [
    { value: "id", label: "ID" },
    { value: "doctorName", label: "Doctor Name" },
    { value: "patientName", label: "Patient Name" },
    { value: "pharmacy", label: "Pharmacy" },
    { value: "status", label: "Status" },
  ];

  const handleSearch = (name, value) => {
    setPrescriptions([]);
    setTotalLoadedPages(0);
    setLastPage(false);
    setPage(0);
    navigate(`/prescriptions?filter=${name}&filterValue=${value}`, {
      replace: true,
    });
  };

  const cancelPrescription = async (prescriptionId) => {
    await handleCancelPrescription(prescriptionId);
    setPrescriptions([]);
    setTotalLoadedPages(0);
    setLastPage(false);
    setPage(0);
  };

  const ConfirmModal = () => (
    <BasicModal
      title="Cancel Prescription"
      content="Are you sure you want to cancel this prescription?"
      action={() => {
        cancelPrescription(selectedPrescriptionId);
        cancelModalDisc.onClose();
      }}
      isLoading={isLoading}
      isOpen={cancelModalDisc.isOpen}
      onClose={cancelModalDisc.onClose}
    />
  );

  const DetailsModal = () => (
    <BasicModal
      content={
        prescriptions.find((p) => p.id === selectedPrescriptionId).instructions
      }
      isOpen={detailsModalDisc.isOpen}
      onClose={detailsModalDisc.onClose}
    />
  );

  useEffect(() => {
    const fetchPatients = async () => {
      const pageToLoad = page === 0 ? 0 : page - 1;
      const { content, pageNumber, last } = await handleGetPrescriptions(
        pageToLoad,
        limit,
        filter,
        filterValue
      );
      setPrescriptions(content);
      setPage(pageNumber + 1);
      setLastPage(last);
      setTotalLoadedPages(pageNumber + 1);
    };
    if (prescriptions.length === 0 || (!lastPage && page > totalLoadedPages)) {
      fetchPatients();
    }
  }, [
    handleGetPrescriptions,
    page,
    limit,
    filter,
    filterValue,
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
      <ConfirmModal />
      <DetailsModal />
    </div>
  );
};

export default PrescriptionsPage;
