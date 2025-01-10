import { createContext, useContext, useState, useEffect } from "react";
import { useCookies } from "react-cookie";
import useDisclosure from "../hooks/useDisclosure";
import ManagementApi from "../managementApi";

export const AppContext = createContext();

export const useAppContext = () => useContext(AppContext);

export const AppProvider = ({ children }) => {
  const [isLoading, setIsLoading] = useState(false);
  const [doctorUser, setDoctorUser] = useState(null);
  const [error, setError] = useState(null);
  const [alert, setAlert] = useState(null);
  const [cookies, , removeCookies] = useCookies(["XSRF-TOKEN"]);

  const newPatientDisc = useDisclosure();
  const editPatientDisc = useDisclosure();
  const confirmPatientDeleteDisc = useDisclosure();
  const confirmRxCancelDisc = useDisclosure();
  const confirmNewRxDisc = useDisclosure();

  // Auth functions

  const handleLogin = () => {
    let port = window.location.port ? `:${window.location.port}` : "";
    if (port === ":3000") {
      port = ":8080";
    }
    window.location.href = `//${window.location.hostname}${port}/oauth2/authorization/okta`;
  };

  const handleLogout = async () => {
    setIsLoading(true);
    try {
      const { logoutUrl, idToken } = await ManagementApi.logout();
      setDoctorUser(null);
      window.location.href = `${logoutUrl}?id_token_hint=${idToken}&post_logout_redirect_uri=${window.location.origin}`;
      removeCookies("XSRF-TOKEN", { path: "/" });
    } catch (error) {
      setError(error);
      setIsLoading(false);
    }
  };

  const handleAuthenticate = async () => {
    setIsLoading(true);
    try {
      const doctorUser = await ManagementApi.authenticateDoctorUser();
      console.log(doctorUser);
      setDoctorUser(doctorUser);
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  // Patient functions
  /**
   * get patient by id
   *
   * @param {Number} patientId
   * @returns
   */
  const handleGetPatient = async (patientId) => {
    setIsLoading(true);
    try {
      return await ManagementApi.getPatient(patientId);
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleGetPatients = async (
    page = 1,
    limit = 10,
    filter = null,
    filterValue = null
  ) => {
    setIsLoading(true);
    try {
      return await ManagementApi.getPagePatients(
        page,
        limit,
        filter,
        filterValue
      );
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleCreatePatient = async (patientData) => {
    setIsLoading(true);
    try {
      return await ManagementApi.createPatient(patientData);
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleUpdatePatient = async (patientData) => {
    setIsLoading(true);
    try {
      return await ManagementApi.updatePatient(patientData);
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleDeletePatient = async (patientId) => {
    setIsLoading(true);
    try {
      const message = await ManagementApi.deletePatient(patientId);
      setAlert({ message, type: "success" });
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  // Prescription functions

  const handleCreatePrescription = async (prescriptionData) => {
    setIsLoading(true);
    try {
      return await ManagementApi.createPrescription(prescriptionData);
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleCancelPrescription = async (prescriptionId) => {
    setIsLoading(true);
    try {
      const cancelledScript = await ManagementApi.cancelPrescription(
        prescriptionId
      );
      setAlert({ message: "Prescription canceled", type: "success" });
      return cancelledScript;
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleGetPrescription = async (prescriptionId) => {
    setIsLoading(true);
    try {
      return await ManagementApi.getPrescription(prescriptionId);
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleGetPrescriptions = async (
    page = 1,
    limit = 10,
    filter = null,
    filterValue = null
  ) => {
    setIsLoading(true);
    try {
      return await ManagementApi.getPagePrescriptions(
        page,
        limit,
        filter,
        filterValue
      );
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  // Pharmacy functions

  const handleGetPharmacies = async () => {
    setIsLoading(true);
    try {
      return await ManagementApi.getAllPharmacies();
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleAddPharmacy = async (pharmacyData) => {
    setIsLoading(true);
    try {
      return await ManagementApi.createPharmacy(pharmacyData);
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  // Medication functions

  /**
   * Get all medications
   *
   * @returns {Promise<MedicationData[]>} {MedicationData[]}
   */
  const handleGetMedications = async () => {
    setIsLoading(true);
    try {
      return await ManagementApi.getAllMedications();
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    console.log("cookies", cookies["XSRF-TOKEN"]);
    console.log("doctorUser", doctorUser);
    if (cookies["XSRF-TOKEN"] && !doctorUser) {
      handleAuthenticate();
    }
  }, [doctorUser, cookies]);

  return (
    <AppContext.Provider
      value={{
        isLoading,
        doctorUser,
        error,
        alert,
        setAlert,
        newPatientDisc,
        editPatientDisc,
        confirmPatientDeleteDisc,
        confirmRxCancelDisc,
        confirmNewRxDisc,
        handleLogin,
        handleLogout,
        handleGetPatient,
        handleGetPatients,
        handleCreatePatient,
        handleUpdatePatient,
        handleDeletePatient,
        handleCreatePrescription,
        handleCancelPrescription,
        handleGetPrescription,
        handleGetPrescriptions,
        handleGetPharmacies,
        handleAddPharmacy,
        handleGetMedications,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};
