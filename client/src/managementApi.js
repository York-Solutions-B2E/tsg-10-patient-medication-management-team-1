import axios from "axios";

const BASE_URL = "/api";

/**
 * Management API class to make calls to the backend API.
 */
class ManagementApi {
  static token;

  // API initialization

  /**
   * Set the static token property for the API class to
   * be used in all API calls.
   *
   * The XSRF-TOKEN cookie should be passed as the token.
   * @param {String} token
   */
  static setToken(token) {
    this.token = token;
  }

  // Request method

  /**
   * Request method to make a call to the backend API.
   *
   * This method should be called by other methods in the class only.
   *
   * Use async function and await this method to get the response data.
   * @param {String} endpoint
   * @param {String} method - default is "get"
   * @param {{}} data {RequestData} - default is empty {}
   * @returns {Promise<ResponseDataObject>} {ResponseData}
   */
  static async request(endpoint, method = "get", data = {}) {
    console.debug("API Call:", endpoint, data, method);
    const url = `${BASE_URL}${endpoint}`;
    const headers = this.token ? { "X-XSRF-TOKEN": this.token } : {};
    try {
      return (
        await axios({ url, method, data, headers, withCredentials: true })
      ).data;
    } catch (err) {
      console.error("API Error:", err.response);
      let message = err.response.data.error.message;
      throw Array.isArray(message) ? message : [message];
    }
  }

  // Patient methods

  /**
   * Create a new patient in the database and return the created patient.
   *
   * Use async function and await this method to get the response data.
   * @param {{
   *    firstName: String,
   *    lastName: String,
   *    dob: String,
   *    gender: String,
   *    email: String,
   *    phone: String,
   *    address: String,
   *    city: String,
   *    state: String,
   *    zip: String,
   * }} patientData
   * @returns {Promise<PatientData>} {PatientData}
   */
  static async createPatient(patientData) {
    return this.request("/patients", "post", patientData);
  }

  /**
   * Get a patient by ID.
   *
   * Use async function and await this method to get the response data.
   * @param {String} patientId
   * @returns {Promise<PatientData>} {PatientData}
   */
  static async getPatient(patientId) {
    return this.request(`/patients/${patientId}`);
  }

  /**
   * Get a page of patients from the database.
   *
   * Can include an optional filter query to filter the patients.
   *
   * Use async function and await this method to get the response data.
   * @param {Number} page
   * @param {Number} limit
   * @param {String} filter
   * @param {String} filterValue
   * @returns {Promise<PatientData[]>} {PatientData[]}
   */
  static async getPagePatients(
    page = 1,
    limit = 10,
    filter = null,
    filterValue = null
  ) {
    return this.request(
      `/patients?page=${page}&limit=${limit}` +
        (filter ? `&filter=${filter}&filterValue=${filterValue}` : "")
    );
  }

  /**
   * Update a patient by ID. Returns the updated patient.
   *
   * Use async function and await this method to get the response data.
   * @param {{
   *    id: String,
   *    firstName: String,
   *    lastName: String,
   *    dob: String,
   *    gender: String,
   *    email: String,
   *    phone: String,
   *    address: String,
   *    city: String,
   *    state: String,
   *    zip: String,
   * }} patientData
   * @returns {Promise<PatientData>} {PatientData}
   */
  static async updatePatient(patientData) {
    return this.request(`/patients`, "put", patientData);
  }

  /**
   * Delete a patient by ID. Returns a success message.
   *
   * Use async function and await this method to get the response data.
   * @param {String} patientId
   * @returns {Promise<{ message: String }>} { message: String }
   */
  static async deletePatient(patientId) {
    return this.request(`/patients/${patientId}`, "delete");
  }

  // Prescription methods

  /**
   * Create a new prescription in the database and return the created prescription.
   *
   * Use async function and await this method to get the response data.
   * @param {{
   *    patientId: String,
   *    medicationCode: String,
   *    dosage: String,
   *    refills: Number,
   *    instructions: String,
   *    doctorId: String,
   * }} prescriptionData
   * @returns {Promise<PrescriptionData>} {PrescriptionData}
   */
  static async createPrescription(prescriptionData) {
    return this.request("/prescriptions", "post", prescriptionData);
  }

  /**
   * Cancel a prescription by ID. Returns the canceled prescription.
   *
   * Use async function and await this method to get the response data.
   * @param {Number} prescriptionId
   * @returns {Promise<PrescriptionData>} {PrescriptionData}
   */
  static async cancelPrescription(prescriptionId) {
    return this.request(`/prescriptions/${prescriptionId}/cancel`, "patch");
  }

  /**
   * Get a page of prescriptions from the database.
   *
   * Can include an optional filter query to filter the prescriptions.
   *
   * Use async function and await this method to get the response data.
   * @param {Number} page
   * @param {Number} limit
   * @param {String} filter
   * @param {String} filterValue
   * @returns {Promise<PrescriptionData[]>} {PrescriptionData[]}
   */
  static async getPagePrescriptions(
    page = 1,
    limit = 10,
    filter = null,
    filterValue = null
  ) {
    return this.request(
      `/prescriptions?page=${page}&limit=${limit}` +
        (filter ? `&filter=${filter}&filterValue=${filterValue}` : "")
    );
  }

  /**
   * Get a prescription by ID.
   *
   * Use async function and await this method to get the response data.
   * @param {Number} prescriptionId
   * @returns {Promise<PrescriptionData>} {PrescriptionData}
   */
  static async getPrescription(prescriptionId) {
    return this.request(`/prescriptions/${prescriptionId}`);
  }

  // Pharmacy methods

  /**
   * Get all pharmacies
   *
   * Use async function and await this method to get the response data.
   * @returns {Promise<PharmacyData[]>} {PharmacyData[]}
   */
  static async getPharmacies() {
    return this.request("/pharmacies");
  }

  /**
   * Create a new pharmacy
   *
   * Use async function and await this method to get the response data.
   * @param {{
   *   name: String,
   *  address: String,
   * city: String,
   * state: String,
   * zip: String,
   * phone: String,
   * email: String,
   * }} pharmacyData
   * @returns {Promise<PharmacyData>} {PharmacyData}
   */
  static async createPharmacy(pharmacyData) {
    return this.request("/pharmacies", "post", pharmacyData);
  }

  // Authentication methods

  /**
   * Send token to the backend to authenticate the doctor user. Returns the doctor user data.
   *
   * If the doctor user does not exist in the database, a new doctor user is created.
   *
   * Use async function and await this method to get the response data.
   * @returns {Promise<DoctorUserData>} {DoctorUserData}
   */
  static async authenticateDoctorUser() {
    return this.request("/user", "post");
  }

  /**
   * Logout the doctor user by removing the token from the backend.
   *
   * Returns the logout url and idToken for okta logout.
   */
  static async logout() {
    const response = await this.request("/logout", "post");
    this.token = null;
    return response;
  }
}
export default ManagementApi;
