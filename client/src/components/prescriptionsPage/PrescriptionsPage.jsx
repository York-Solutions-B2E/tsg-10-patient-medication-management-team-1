import { useState, useEffect } from "react";
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
  const [pageModel, setPageModel] = useState({ page: 0, pageSize: 5 });
  const [totalLoadedPages, setTotalLoadedPages] = useState(0);
  const [lastPage, setLastPage] = useState(false);
  const [selectedPrescriptionId, setSelectedPrescriptionId] = useState(null);

  const detailsModalDisc = useDisclosure();
  const cancelModalDisc = useDisclosure();

  const { handleGetPrescriptions, handleCancelPrescription, isLoading } =
    useAppContext();

  const navigate = useNavigate();

  const columns = [
    { field: "prescriptionId", headerName: "Prescription ID", width: 120 },
    { field: "patientName", headerName: "Patient", width: 120 },
    { field: "patientId", headerName: "Patient ID", width: 90 },
    { field: "doctorName", headerName: "Issuing Doctor", width: 130 },
    {
      field: "medicationName",
      headerName: "Medication",
      width: 120,
    },
    { field: "medicationCode", headerName: "Rx Code", width: 100 },
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

  const handleSearch = async (name, value) => {
    const { content, number, last } = await handleGetPrescriptions(
      0,
      5,
      name != "" ? name : null,
      value != "" ? value : null
    );
    setPrescriptions(content);
    setPageModel({ ...pageModel, page: number });
    setLastPage(last);
    setTotalLoadedPages(number + 1);
    navigate(
      "/prescriptions" +
        (name != "" ? `?filterName=${name}&filterValue=${value}` : ""),
      {
        replace: true,
      }
    );
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
    const fetchPatients = async () => {
      const { content, number, last } = await handleGetPrescriptions(
        pageModel.page,
        pageModel.pageSize,
        searchParams.get("filterName") || null,
        searchParams.get("filterValue") || null
      );
      setPrescriptions([...prescriptions, ...content]);
      setPageModel({ ...pageModel, page: number });
      setLastPage(last);
      setTotalLoadedPages(number + 1);
    };
    if (
      (prescriptions.length === 0 && totalLoadedPages === 0) ||
      (!lastPage && pageModel.page >= totalLoadedPages)
    ) {
      fetchPatients();
    }
  }, [
    handleGetPrescriptions,
    pageModel,
    searchParams,
    lastPage,
    prescriptions,
    totalLoadedPages,
  ]);

  return (
    <div data-testid="grid-container" className="prescriptions-page">
      <DataGridComponent
        title="Prescription List"
        isLoading={isLoading}
        rows={prescriptions}
        columns={columns}
        filterOptions={filterOptions}
        filterable={true}
        pageModel={pageModel}
        setPageModel={setPageModel}
        lastPage={lastPage}
        totalPages={totalLoadedPages}
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
