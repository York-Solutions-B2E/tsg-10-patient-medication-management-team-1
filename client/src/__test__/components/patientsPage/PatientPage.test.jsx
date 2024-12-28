import {render, screen, waitFor} from '@testing-library/react';
import PatientPage from '../../../components/patientsPage/PatientsPage.jsx'
import {beforeEach} from "@jest/globals";
import axios from 'axios';

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
        await waitFor(() => expect(screen.getByText('John')).toBeInTheDocument());

        expect(screen.getByText('Jane')).toBeInTheDocument();
        expect(screen.getByText('Springfield')).toBeInTheDocument();

    })
})