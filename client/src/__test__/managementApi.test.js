import { describe, test, expect, afterEach, jest } from "@jest/globals";

// const BASE_URL = import.meta.env.VITE_APP_BASE_URL;

jest.mock("axios");
import axios from "axios";
import ManagementApi from "../managementApi";

describe("ManagementApi", () => {
  afterEach(() => {
    jest.clearAllMocks();
    ManagementApi.token = undefined;
  });

  // setup tests
  test("should set the token", async () => {
    ManagementApi.setToken("token");
    expect(ManagementApi.token).toBe("token");
  });

  // request tests

  test("should send a request and get a response", async () => {
    const mockData = { mockData: "mock data" };
    axios.mockResolvedValue({ data: mockData });
    const result = await ManagementApi.request("/path", "get");

    expect(result).toEqual(mockData);
    expect(axios).toHaveBeenCalledTimes(1);
    expect(axios).toHaveBeenCalledWith({
      method: "get",
      url: "/api/path",
      data: {},
      headers: {},
      withCredentials: true,
    });
  });

  test("request should catch an error", async () => {
    axios.mockRejectedValue({
      response: { data: { error: { message: "error" } } },
    });

    try {
      await ManagementApi.request("/path", "get");
    } catch (error) {
      expect(error).toEqual(["error"]);
    }
  });

  test("should throw an array of messages", async () => {
    axios.mockRejectedValue({
      response: { data: { error: { message: ["error1", "error2"] } } },
    });

    try {
      await ManagementApi.request("/path", "get");
    } catch (error) {
      expect(error).toEqual(["error1", "error2"]);
    }
  });

  // patient tests

  test("should create a new patient", async () => {
    const mockData = { mockData: "mock data" };
    axios.mockResolvedValue({ data: mockData });
    ManagementApi.setToken("token");
    const result = await ManagementApi.createPatient({ name: "name" });

    expect(result).toEqual(mockData);
    expect(axios).toHaveBeenCalledTimes(1);
    expect(axios).toHaveBeenCalledWith({
      method: "post",
      url: "/api/patients",
      data: { name: "name" },
      headers: { "X-XSRF-TOKEN": "token" },
      withCredentials: true,
    });
  });

  test("should get patient by id", async () => {
    const mockData = { mockData: "mock data" };
    axios.mockResolvedValue({ data: mockData });
    ManagementApi.setToken("token");
    const result = await ManagementApi.getPatient("abcd1234");

    expect(result).toEqual(mockData);
    expect(axios).toHaveBeenCalledTimes(1);
    expect(axios).toHaveBeenCalledWith({
      method: "get",
      url: "/api/patients/abcd1234",
      data: {},
      headers: { "X-XSRF-TOKEN": "token" },
      withCredentials: true,
    });
  });

  test("should get page of patients with defaults no filter", async () => {
    const mockData = { mockData: "mock data" };
    axios.mockResolvedValue({ data: mockData });
    ManagementApi.setToken("token");
    const result = await ManagementApi.getPagePatients();

    expect(result).toEqual(mockData);
    expect(axios).toHaveBeenCalledTimes(1);
    expect(axios).toHaveBeenCalledWith({
      method: "get",
      url: "/api/patients?page=1&limit=10",
      data: {},
      headers: { "X-XSRF-TOKEN": "token" },
      withCredentials: true,
    });
  });

  test("should get page of patients with filter", async () => {
    const mockData = { mockData: "mock data" };
    axios.mockResolvedValue({ data: mockData });
    ManagementApi.setToken("token");
    const result = await ManagementApi.getPagePatients(
      2,
      20,
      "filter",
      "filterValue"
    );

    expect(result).toEqual(mockData);
    expect(axios).toHaveBeenCalledTimes(1);
    expect(axios).toHaveBeenCalledWith({
      method: "get",
      url: "/api/patients?page=2&limit=20&filter=filter&filterValue=filterValue",
      data: {},
      headers: { "X-XSRF-TOKEN": "token" },
      withCredentials: true,
    });
  });

  test("should update patient", async () => {
    const mockData = { mockData: "mock data" };
    axios.mockResolvedValue({ data: mockData });
    ManagementApi.setToken("token");
    const result = await ManagementApi.updatePatient({
      name: "name",
    });

    expect(result).toEqual(mockData);
    expect(axios).toHaveBeenCalledTimes(1);
    expect(axios).toHaveBeenCalledWith({
      method: "put",
      url: "/api/patients",
      data: { name: "name" },
      headers: { "X-XSRF-TOKEN": "token" },
      withCredentials: true,
    });
  });

  test("should delete patient by id", async () => {
    const mockData = { mockData: "mock data" };
    axios.mockResolvedValue({ data: mockData });
    ManagementApi.setToken("token");
    const result = await ManagementApi.deletePatient("abcd1234");

    expect(result).toEqual(mockData);
    expect(axios).toHaveBeenCalledTimes(1);
    expect(axios).toHaveBeenCalledWith({
      method: "delete",
      url: "/api/patients/abcd1234",
      data: {},
      headers: { "X-XSRF-TOKEN": "token" },
      withCredentials: true,
    });
  });

  // prescription tests

  test("should create a new prescription", async () => {
    const mockData = { mockData: "mock data" };
    axios.mockResolvedValue({ data: mockData });
    ManagementApi.setToken("token");
    const result = await ManagementApi.createPrescription({ name: "name" });

    expect(result).toEqual(mockData);
    expect(axios).toHaveBeenCalledTimes(1);
    expect(axios).toHaveBeenCalledWith({
      method: "post",
      url: "/api/prescriptions",
      data: { name: "name" },
      headers: { "X-XSRF-TOKEN": "token" },
      withCredentials: true,
    });
  });

  test("should cancel a prescription", async () => {
    const mockData = { mockData: "mock data" };
    axios.mockResolvedValue({ data: mockData });
    ManagementApi.setToken("token");
    const result = await ManagementApi.cancelPrescription(3);

    expect(result).toEqual(mockData);
    expect(axios).toHaveBeenCalledTimes(1);
    expect(axios).toHaveBeenCalledWith({
      method: "patch",
      url: "/api/prescriptions/3/cancel",
      data: {},
      headers: { "X-XSRF-TOKEN": "token" },
      withCredentials: true,
    });
  });

  test("should get page of prescriptions with defaults no filter", async () => {
    const mockData = { mockData: "mock data" };
    axios.mockResolvedValue({ data: mockData });
    ManagementApi.setToken("token");
    const result = await ManagementApi.getPagePrescriptions();

    expect(result).toEqual(mockData);
    expect(axios).toHaveBeenCalledTimes(1);
    expect(axios).toHaveBeenCalledWith({
      method: "get",
      url: "/api/prescriptions?page=1&limit=10",
      data: {},
      headers: { "X-XSRF-TOKEN": "token" },
      withCredentials: true,
    });
  });

  test("should get page of prescriptions with filter", async () => {
    const mockData = { mockData: "mock data" };
    axios.mockResolvedValue({ data: mockData });
    ManagementApi.setToken("token");
    const result = await ManagementApi.getPagePrescriptions(
      2,
      20,
      "filter",
      "filterValue"
    );

    expect(result).toEqual(mockData);
    expect(axios).toHaveBeenCalledTimes(1);
    expect(axios).toHaveBeenCalledWith({
      method: "get",
      url: "/api/prescriptions?page=2&limit=20&filter=filter&filterValue=filterValue",
      data: {},
      headers: { "X-XSRF-TOKEN": "token" },
      withCredentials: true,
    });
  });

  test("should get prescription by id", async () => {
    const mockData = { mockData: "mock data" };
    axios.mockResolvedValue({ data: mockData });
    ManagementApi.setToken("token");
    const result = await ManagementApi.getPrescription(3);

    expect(result).toEqual(mockData);
    expect(axios).toHaveBeenCalledTimes(1);
    expect(axios).toHaveBeenCalledWith({
      method: "get",
      url: "/api/prescriptions/3",
      data: {},
      headers: { "X-XSRF-TOKEN": "token" },
      withCredentials: true,
    });
  });

  // authentication tests

  test("should get doctor user data from token", async () => {
    const mockData = { mockData: "mock data" };
    axios.mockResolvedValue({ data: mockData });
    ManagementApi.setToken("token");
    const result = await ManagementApi.authenticateDoctorUser();

    expect(result).toEqual(mockData);
    expect(axios).toHaveBeenCalledTimes(1);
    expect(axios).toHaveBeenCalledWith({
      method: "post",
      url: "/api/auth",
      data: {},
      headers: { "X-XSRF-TOKEN": "token" },
      withCredentials: true,
    });
  });

  test("should logout doctor user", async () => {
    const mockData = { mockData: "mock data" };
    axios.mockResolvedValue({ data: mockData });
    ManagementApi.setToken("token");
    const result = await ManagementApi.logout();

    expect(result).toEqual(mockData);
    expect(axios).toHaveBeenCalledTimes(1);
    expect(axios).toHaveBeenCalledWith({
      method: "post",
      url: "/api/auth/logout",
      data: {},
      headers: { "X-XSRF-TOKEN": "token" },
      withCredentials: true,
    });
    expect(ManagementApi.token).toBe(null);
  });
});
