import { jest, describe, it, expect, beforeEach } from "@jest/globals";
import { render, screen, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom";
import Form from "../../../components/common/Form";
import useForm from "../../../hooks/useForm";
import { LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import dayjs from "dayjs";

jest.mock("../../../hooks/useForm", () => jest.fn());
jest.mock("@mui/lab", () => ({
  DatePicker: jest.fn(({ renderInput }) => renderInput({})),
}));

const mockUseForm = (
  values,
  errors,
  isValid,
  handleChange,
  handleBlur,
  validateForm,
  clearForm
) => {
  useForm.mockReturnValue({
    values,
    errors,
    isValid,
    handleChange,
    handleBlur,
    validateForm,
    clearForm,
  });
};

describe("Form Component", () => {
  const mockFields = [
    { name: "name", label: "Name", type: "text", required: true },
    { name: "email", label: "Email", type: "email", validation: "email" },
    { name: "phone", label: "Phone", type: "text", validation: "phone" },
    {
      name: "gender",
      label: "Gender",
      type: "radio",
      options: [
        { value: "male", label: "Male" },
        { value: "female", label: "Female" },
      ],
    },
    {
      name: "birthdate",
      label: "Birthdate",
      type: "date",
      defaultValue: dayjs(),
    },
    { name: "terms", label: "Accept Terms", type: "checkbox" },
  ];

  const mockOnSubmit = jest.fn();
  const mockHandleChange = jest.fn();
  const mockHandleBlur = jest.fn();
  const mockValidateForm = jest.fn();
  const mockClearForm = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it("renders all fields correctly", () => {
    mockUseForm(
      {
        name: "",
        email: "",
        phone: "",
        gender: "",
        birthdate: null,
        terms: false,
      },
      {},
      false,
      mockHandleChange,
      mockHandleBlur,
      mockValidateForm
    );

    render(
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <Form fields={mockFields} onSubmit={mockOnSubmit} isLoading={false} />
      </LocalizationProvider>
    );

    // Check all input fields are rendered
    expect(screen.getByLabelText("Name")).toBeInTheDocument();
    expect(screen.getByLabelText("Email")).toBeInTheDocument();
    expect(screen.getByLabelText("Phone")).toBeInTheDocument();
    expect(screen.getByLabelText("Male")).toBeInTheDocument();
    expect(screen.getByLabelText("Female")).toBeInTheDocument();
    expect(screen.getByLabelText("Accept Terms")).toBeInTheDocument();
    expect(screen.getByText("Submit")).toBeInTheDocument();
  });

  it("calls onSubmit with valid values on submit", () => {
    mockUseForm(
      {
        name: "John Doe",
        email: "john.doe@example.com",
        phone: "1234567890",
        gender: "male",
        birthdate: "2023-01-01",
        terms: true,
      },
      {},
      true,
      mockHandleChange,
      mockHandleBlur,
      mockValidateForm
    );

    render(
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <Form fields={mockFields} onSubmit={mockOnSubmit} isLoading={false} />
      </LocalizationProvider>
    );

    const submitButton = screen.getByText("Submit");
    fireEvent.click(submitButton);

    expect(mockValidateForm).toHaveBeenCalled();
    expect(mockOnSubmit).toHaveBeenCalledWith({
      name: "John Doe",
      email: "john.doe@example.com",
      phone: "1234567890",
      gender: "male",
      birthdate: "2023-01-01",
      terms: true,
    });
  });

  it("handles input changes correctly", () => {
    mockUseForm(
      {
        name: "",
        email: "",
        phone: "",
        gender: "",
        birthdate: null,
        terms: false,
      },
      {},
      false,
      mockHandleChange,
      mockHandleBlur,
      mockValidateForm
    );

    render(
      <Form fields={mockFields} onSubmit={mockOnSubmit} isLoading={false} />
    );

    const nameInput = screen.getByLabelText("Name");
    fireEvent.change(nameInput, { target: { value: "John" } });

    expect(mockHandleChange).toHaveBeenCalled();
  });

  it("clears the form when clear button is clicked", () => {
    mockUseForm(
      {
        name: "John Doe",
        email: "john.doe@example.com",
        phone: "1234567890",
        gender: "male",
        birthdate: "2023-01-01",
        terms: true,
      },
      {},
      true,
      mockHandleChange,
      mockHandleBlur,
      mockValidateForm,
      mockClearForm
    );

    render(
      <Form
        fields={mockFields}
        onSubmit={mockOnSubmit}
        clearable={true}
        isLoading={false}
      />
    );

    const clearButton = screen.getByText("Clear");
    fireEvent.click(clearButton);

    expect(mockClearForm).toHaveBeenCalled();
  });
});
