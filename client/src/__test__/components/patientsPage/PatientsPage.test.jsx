import {render, screen, waitFor, fireEvent} from '@testing-library/react';
import {beforeEach} from "@jest/globals";
import { useAppContext } from "../../../context/AppContext.jsx";
import '@testing-library/jest-dom';
import PatientsPage from "../../../components/patientsPage/PatientsPage.jsx";

jest.mock("../../../context/AppContext.jsx");

const mockAllPatients = [
    {
        "Id": "1",
        "firstName": "John",
        "lastName": "Doe",
        "Dob": "1990-05-15",
        "Address": {
            "Street_one": "123 Maple St",
            "Street_two": "Apt 101",
            "City": "Springfield",
            "State": "IL",
            "Zip": "62701"
        },
        "Prescriptions": 3
    },
    {
        "Id": "2",
        "firstName": "Jane",
        "lastName": "Smith",
        "Dob": "1985-08-22",
        "Address": {
            "Street_one": "456 Oak Ave",
            "Street_two": "Suite 202",
            "City": "Chicago",
            "State": "IL",
            "Zip": "60604"
        },
        "Prescriptions": 5
    },
    // Add more objects if necessary
];



describe('<PatientPage />', () => {
    beforeEach(() => {

    })

    afterEach(() => {
        jest.clearAllMocks();
        jest.resetAllMocks();// Clears any mock function calls or mock implementations
    });

    it("renders PatientsPage with mocked context", async () => {
        // Provide mock implementation for useAppContext
        useAppContext.mockReturnValue({
            handleGetPatients: jest.fn().mockResolvedValue(mockAllPatients), // Mock the patient fetch
            isLoading: false, // Simulate not loading
            error: null, // No error
        });

        render(<PatientsPage />);

        // Verify that the component renders patient data correctly
        await waitFor(() => {
            expect(screen.getByText(1)).toBeInTheDocument()
            expect(screen.getByText(/John/i)).toBeInTheDocument();
            expect(screen.getByText(/Jane/i)).toBeInTheDocument();
            expect(screen.getByText(/1985-08-22/i)).toBeInTheDocument();
            expect(screen.getByText(/1990-05-15/i)).toBeInTheDocument();
        });
    });

    it("displays error message when fetch fails", async () => {
        // Mock useAppContext to simulate an error
        useAppContext.mockReturnValue({
            handleGetPatients: jest.fn().mockRejectedValue(new Error("Failed to fetch patients")), // Simulate fetch error
            isLoading: false, // Not loading
            error: "Failed to fetch patients", // Set the error message
        });

        render(<PatientsPage />);

        // Wait for the error message to be rendered
        await waitFor(() => {
            expect(screen.getByText(/failed to fetch patients/i)).toBeInTheDocument();
        });
    });

    it("does not show error message if no error", async () => {
        // Mock useAppContext to simulate successful data fetching
        useAppContext.mockReturnValue({
            handleGetPatients: jest.fn().mockResolvedValue([]), // Simulate successful fetch
            isLoading: false, // Not loading
            error: null, // No error
        });

        render(<PatientsPage />);

        // Ensure that the error message is not rendered
        expect(screen.queryByText(/failed to fetch patients/i)).not.toBeInTheDocument();
    });
})