import {render, screen, waitFor, fireEvent} from '@testing-library/react';
import PatientPage from '../../../components/patientsPage/PatientsPage.jsx'
import {beforeEach} from "@jest/globals";
import axios from 'axios';
import '@testing-library/jest-dom';

jest.mock('axios')

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

    it('should display all patients', async () => {
        axios.get.mockResolvedValue({ data: mockAllPatients });

        render(<PatientPage />);
        //await waitFor(() => expect(screen.getByText('John')).toBeInTheDocument());

        const firstName = await screen.findByText(/jane/i)
        const lastName = await screen.findByText(/smith/i)
        const id = await screen.findByText("2")
        const dob = await screen.findByText("1985-08-22")
        const address = await screen.findByText(/456 oak ave/i)

        expect(firstName).toBeInTheDocument();
        expect(lastName).toBeInTheDocument();
        expect(id).toBeInTheDocument();
        expect(dob).toBeInTheDocument();
        expect(address).toBeInTheDocument();
    })

    it('should display a loading screen then show patients', async () => {
        axios.get.mockResolvedValue({ data: mockAllPatients });

        render(<PatientPage />);

        expect(screen.getByText(/loading.../i)).toBeInTheDocument();

        const firstName = await screen.findByText(/jane/i)
        const lastName = await screen.findByText(/smith/i)
        expect(firstName).toBeInTheDocument();
        expect(lastName).toBeInTheDocument();
    })

    it('should display an error if there was an error loading patients', async () => {
        const mockError = new Error('Network error');
        axios.get.mockRejectedValue(mockError);

        render(<PatientPage />);

        // Check for the loading state
        expect(screen.getByText(/loading.../i)).toBeInTheDocument();

        await waitFor(() => {
            expect(screen.getByText(/error please try again later/i))
        })
    })
})