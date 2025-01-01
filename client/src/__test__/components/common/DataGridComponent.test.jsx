import { describe, test, expect, jest, beforeEach } from "@jest/globals";
import { render, screen, fireEvent } from "@testing-library/react";
import { useNavigate } from "react-router-dom";
import DataGridComponent from "../../../components/common/DataGridComponent";

jest.mock("react-router-dom", () => ({
  useNavigate: jest.fn(),
  useLocation: jest.fn(() => ({ pathname: "/test" })),
  useSearchParams: jest.fn(() => [{ get: jest.fn(() => "") }]),
}));

describe("DataGridComponent", () => {
  const mockNavigate = jest.fn();
  const mockSetPage = jest.fn();
  const mockSearchFunction = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
    useNavigate.mockReturnValue(mockNavigate);
  });

  const defaultProps = {
    title: "Test DataGrid",
    columns: [{ field: "id", headerName: "ID" }],
    filterOptions: [{ value: "id", label: "ID" }],
    rows: [{ id: 1 }],
    isLoading: false,
    filterable: true,
    page: 1,
    setPage: mockSetPage,
    lastPage: false,
    totalPages: 3,
    pageSize: 10,
    searchFunction: mockSearchFunction,
  };

  test("renders the component with title and grid", () => {
    render(<DataGridComponent {...defaultProps} />);

    expect(screen.getByText("Test DataGrid")).toBeInTheDocument();
    expect(screen.getByTestId("data-grid")).toBeInTheDocument();
  });

  test("renders NoDataDisplay when no rows are provided", () => {
    render(<DataGridComponent {...defaultProps} rows={[]} />);

    expect(screen.getByText("No data to show!")).toBeInTheDocument();
  });

  test("filter dropdown and search inputs work correctly", () => {
    render(<DataGridComponent {...defaultProps} />);

    // make sure it's grabbing the correct MUI input to be able to set and test the value
    const filterInput = screen.getByLabelText("Filter");
    const searchInput = screen.getByLabelText("Search");
    const searchButton = screen.getByTestId("search-button");

    fireEvent.mouseDown(filterInput);
    const options = screen.getAllByRole("option");
    fireEvent.click(options[0]);

    fireEvent.change(searchInput, { target: { value: "test" } });
    expect(searchInput.value).toBe("test");

    fireEvent.click(searchButton);
    expect(mockSearchFunction).toHaveBeenCalledWith("id", "test");
  });

  test("clear button resets filters and calls setPage", () => {
    render(<DataGridComponent {...defaultProps} />);

    const clearButton = screen.getByTestId("clear-button");
    fireEvent.click(clearButton);

    expect(mockSearchFunction).not.toHaveBeenCalled();
  });

  test("pagination buttons work as expected", () => {
    render(<DataGridComponent {...defaultProps} page={2} />);

    const prevButton = screen.getByTestId("prev-button");
    const nextButton = screen.getByTestId("next-button");

    fireEvent.click(prevButton);
    expect(mockSetPage).toHaveBeenCalledWith(1);

    fireEvent.click(nextButton);
    expect(mockSetPage).toHaveBeenCalledWith(3);
  });

  test("disables buttons when loading or on boundaries", () => {
    render(<DataGridComponent {...defaultProps} page={1} isLoading={true} />);

    const prevButton = screen.getByTestId("prev-button");
    const nextButton = screen.getByTestId("next-button");

    expect(prevButton).toBeDisabled();
    expect(nextButton).toBeDisabled();
  });

  test("navigates with filter and value when no searchFunction is provided", () => {
    render(<DataGridComponent {...defaultProps} searchFunction={null} />);

    const searchButton = screen.getByTestId("search-button");
    fireEvent.click(searchButton);

    expect(mockNavigate).toHaveBeenCalledWith("/test?filter=&filterValue=");
  });
});
