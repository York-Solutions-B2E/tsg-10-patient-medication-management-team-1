import {
  describe,
  expect,
  test,
  jest,
  beforeEach,
  afterEach,
} from "@jest/globals";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import PatientsPage from "../../../components/patientsPage/PatientsPage";
import { useAppContext } from "../../../context/AppContext";
import { useNavigate, useSearchParams } from "react-router-dom";

// Mock dependencies
jest.mock("../../../context/AppContext.jsx", () => ({
  useAppContext: jest.fn(),
}));

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useNavigate: jest.fn(),
  useSearchParams: jest.fn(),
}));

// Mock the filter section with divs for simplicity
jest.mock("../../../components/common/DataGridComponent", () => ({
  __esModule: true,
  default: ({
    title,
    isLoading,
    rows,
    columns,
    filterOptions,
    filterable,
    page,
    setPage,
    lastPage,
    totalPages,
    pageSize,
    searchFunction,
  }) => (
    // use this component to mock the DataGridComponent for testing. It does not need to display a table, just test that the props are passed correctly
    <div data-testid="mock-data-grid">
      <div data-testid="loading">{isLoading ? "Loading..." : "Loaded"}</div>
      <div data-testid="title">{title}</div>
      <div data-testid="page">{page}</div>
      <div data-testid="pageSize">{pageSize}</div>
      <div data-testid="lastPage">{lastPage}</div>
      <div data-testid="totalPages">{totalPages}</div>
      <div data-testid="filterable">{filterable.toString()}</div>
      <div data-testid="filterOptions">
        {filterOptions.map((option) => (
          <>
            <div data-id={`filterValue-${option.value}`} key={option.value}>
              {option.value}
            </div>
            <div key={option.name} data-testid={`filterName-${option.label}`}>
              {option.label}
            </div>
          </>
        ))}
      </div>
      <div data-testid="columns">
        {columns.map((col) => (
          <div
            key={col.field}
          >{`${col.field} ${col.headerName} ${col.width}`}</div>
        ))}
      </div>
      <div data-testid="rows">
        {rows.map((row) => (
          <div key={row.id}>
            {columns.map((col) => (
              <span key={col.field}>{row[col.field]}</span>
            ))}
          </div>
        ))}
      </div>
      <div data-testid="renderCell">
        {columns.map((col) =>
          col.renderCell ? col.renderCell({ id: 3, value: 5 }) : null
        )}
      </div>
      <button data-testid="page-button" onClick={() => setPage(2)}>
        Page
      </button>
      <button
        data-testid="search-button"
        onClick={() => searchFunction("id", 1)}
      >
        Search
      </button>
    </div>
  ),
}));

describe("PatientsPage", () => {
  const mockNavigate = jest.fn();
  const mockGetPatients = jest.fn();

  beforeEach(() => {
    // Mock the useNavigate hook
    useNavigate.mockReturnValue(mockNavigate);

    // Mock useAppContext
    useAppContext.mockReturnValue({
      handleGetPatients: mockGetPatients,
      isLoading: false,
    });

    // Mock searchParams (for filter and filterValue)
    useSearchParams.mockReturnValue([
      new URLSearchParams({ filter: "lastName", filterValue: "Doe" }),
    ]);

    mockGetPatients.mockResolvedValue({
      content: [
        {
          id: 1,
          lastName: "Doe",
          firstName: "John",
          dob: "1990-01-01",
          prescriptionCount: 5,
        },
        {
          id: 2,
          lastName: "Smith",
          firstName: "Jane",
          dob: "1985-02-15",
          prescriptionCount: 3,
        },
      ],
      pageNumber: 0,
      last: true,
    });
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  test("renders PatientsPage and displays loading state", () => {
    render(
      <MemoryRouter>
        <PatientsPage />
      </MemoryRouter>
    );

    expect(screen.getByTestId("mock-data-grid")).toBeInTheDocument();
    expect(screen.getByTestId("loading")).toHaveTextContent("Loaded");
  });

  test("displays patient data correctly", async () => {
    render(
      <MemoryRouter>
        <PatientsPage />
      </MemoryRouter>
    );

    await waitFor(() => expect(mockGetPatients).toHaveBeenCalled());

    expect(screen.getByTestId("title")).toHaveTextContent("Patient List");
    expect(screen.getByTestId("rows")).toBeInTheDocument();
    expect(screen.getByText("Doe")).toBeInTheDocument();
    expect(screen.getByText("Smith")).toBeInTheDocument();
  });

  test("handles search functionality", async () => {
    render(
      <MemoryRouter>
        <PatientsPage />
      </MemoryRouter>
    );

    fireEvent.click(screen.getByTestId("search-button"));

    await waitFor(() =>
      expect(mockNavigate).toHaveBeenCalledWith(
        "/patients?filter=id&filterValue=1",
        { replace: true }
      )
    );
  });

  test("handles pagination", async () => {
    render(
      <MemoryRouter>
        <PatientsPage />
      </MemoryRouter>
    );

    fireEvent.click(screen.getByTestId("page-button"));

    await waitFor(() =>
      expect(mockGetPatients).toHaveBeenCalledWith(1, 10, "lastName", "Doe")
    );
  });

  test("renders correct filter options", async () => {
    render(
      <MemoryRouter>
        <PatientsPage />
      </MemoryRouter>
    );

    await waitFor(() => expect(mockGetPatients).toHaveBeenCalled());

    expect(screen.getByTestId("filterName-ID")).toBeInTheDocument();
    expect(screen.getByTestId("filterName-Last Name")).toBeInTheDocument();
    expect(screen.getByTestId("filterName-First Name")).toBeInTheDocument();
    expect(screen.getByTestId("filterName-Date of Birth")).toBeInTheDocument();
  });

  test("triggers prescription link correctly", async () => {
    render(
      <MemoryRouter>
        <PatientsPage />
      </MemoryRouter>
    );

    const prescriptionButton = screen.getAllByText("5")[0];
    fireEvent.click(prescriptionButton);

    await waitFor(() =>
      expect(mockNavigate).toHaveBeenCalledWith(
        "/prescriptions?filter=patientId&filterValue=3"
      )
    );
  });

  test("renders edit and delete actions", async () => {
    render(
      <MemoryRouter>
        <PatientsPage />
      </MemoryRouter>
    );

    const editButton = screen.getAllByRole("button")[0];
    const deleteButton = screen.getAllByRole("button")[1];

    fireEvent.click(editButton);
    fireEvent.click(deleteButton);

    // Since no console log is available, you could add a check here for any actual side effects
  });
});
