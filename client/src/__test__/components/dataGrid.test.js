import DataGridComponent from "../../components/DataGridComponent.jsx";
import {render, screen, waitFor, fireEvent, } from "@testing-library/react";
import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';

const mockSetSearchTerm = jest.fn();
const mockRows = [
    {
        id: 1,
        firstName: "John",
        lastName: "Doe",
        Address: "123 Main St, Springfield, IL 62701",
        Dob: "1985-10-10",
        Prescriptions: "Prescription A",
    },
    {
        id: 2,
        firstName: "Jane",
        lastName: "Smith",
        Address: "456 Oak Rd, Bloomington, IL 61701",
        Dob: "1990-05-15",
        Prescriptions: "Prescription B",
    },
];
const mockColumns = [
    { field: 'id', headerName: 'ID', width: 90 },
    { field: 'firstName', headerName: 'First Name', width: 150 },
    { field: 'lastName', headerName: 'Last Name', width: 150 },
    { field: 'Dob', headerName: 'Date of Birth', width: 180 },
    { field: 'Address', headerName: 'Address', width: 180 },
    { field: 'Prescriptions', headerName: 'Prescriptions', width: 150 },
];

describe("</DataGridComponent>", () => {
    it('renders correctly', async () => {
        render(
            <DataGridComponent
                title="Patient List"
                isLoading={false}
                error={null}
                rows={mockRows}
                columns={mockColumns}
            />
        );

        expect(screen.getByText(/John/i)).toBeInTheDocument();
        expect(screen.getByText(/Jane/i)).toBeInTheDocument();
        expect(screen.getByText(/Doe/i)).toBeInTheDocument();
        expect(screen.getByText(/Smith/i)).toBeInTheDocument();
        expect(screen.getByText(/123 Main St, Springfield, IL 62701/i)).toBeInTheDocument();
        expect(screen.getByText(/456 Oak Rd, Bloomington, IL 61701/i)).toBeInTheDocument();
        expect(screen.getByText(/1985-10-10/i)).toBeInTheDocument();
        expect(screen.getByText(/1990-05-15/i)).toBeInTheDocument();
        expect(screen.getByText(/Prescription A/i)).toBeInTheDocument();
        expect(screen.getByText(/Prescription B/i)).toBeInTheDocument();
    })


    it('filters rows based on search term', async () => {
        render(
            <DataGridComponent
                title="Patient List"
                isLoading={false}
                error={null}
                rows={mockRows}
                columns={mockColumns}
            />
        );

        const searchInput = screen.getByLabelText(/search/i);

        // Type 'John' into the search input
        fireEvent.change(searchInput, { target: { value: 'John' } });

        // Check that only John is displayed in the grid
        expect(screen.getByText(/John/i)).toBeInTheDocument();
        expect(screen.queryByText(/Jane/i)).toBeNull(); // Jane should not be in the document

        // Type 'Smith' into the search input
        fireEvent.change(searchInput, { target: { value: 'Smith' } });

        // Check that only Jane is displayed in the grid
        expect(screen.getByText(/Jane/i)).toBeInTheDocument();
        expect(screen.queryByText(/John/i)).toBeNull(); // John should not be in the document

        // Type 'Oak' into the search input
        fireEvent.change(searchInput, { target: { value: 'Oak' } });

        // Check that only Jane is displayed (as 'Oak' is part of her address)
        expect(screen.getByText(/Jane/i)).toBeInTheDocument();
        expect(screen.queryByText(/John/i)).toBeNull();
    });

    it('filters rows based on search term, including address', async () => {
        render(
            <DataGridComponent
                title="Patient List"
                isLoading={false}
                error={null}
                rows={mockRows}
                columns={mockColumns}
            />
        );

        const searchInput = screen.getByLabelText(/search/i);

        // Test filter with 'Main' (which should match '123 Main St, Springfield')
        fireEvent.change(searchInput, { target: { value: 'Main' } });
        expect(screen.getByText(/John/i)).toBeInTheDocument(); // John should be visible because of the 'Main' address
        expect(screen.queryByText(/Jane/i)).toBeNull(); // Jane should not be in the document

        // Test filter with 'Oak' (which should match '456 Oak Rd, Bloomington')
        fireEvent.change(searchInput, { target: { value: 'Oak' } });
        expect(screen.getByText(/Jane/i)).toBeInTheDocument(); // Jane should be visible because of the 'Oak' address
        expect(screen.queryByText(/John/i)).toBeNull(); // John should not be in the document

        // Test filter with a non-matching address term ('Blvd' should match no addresses)
        fireEvent.change(searchInput, { target: { value: 'Blvd' } });
        expect(screen.queryByText(/John/i)).toBeNull(); // John should not be in the document
        expect(screen.queryByText(/Jane/i)).toBeNull(); // Jane should not be in the document
    });

    // it('filters rows based on search term, including DOB', async () => {
    //     render(
    //         <DataGridComponent
    //             title="Patient List"
    //             isLoading={false}
    //             error={null}
    //             rows={mockRows}
    //             columns={mockColumns}
    //         />
    //     );
    //
    //     const searchInput = screen.getByLabelText(/search/i);
    //
    //     await userEvent.type(searchInput, '1990-05-15');
    //
    //     // Wait for the rows to be filtered and re-rendered
    //     await waitFor(() => {
    //         expect(screen.getByText(/Jane/i)).toBeInTheDocument(); // Jane should be visible
    //         expect(screen.queryByText(/John/i)).toBeNull(); // John should not be in the document
    //     });
    //
    //     // Test with another DOB for John
    //     await userEvent.clear(searchInput);
    //     await userEvent.type(searchInput, '1985-10-10');
    //     await waitFor(() => {
    //         expect(screen.getByText(/John/i)).toBeInTheDocument(); // John should be visible
    //         expect(screen.queryByText(/Jane/i)).toBeNull(); // Jane should not be in the document
    //     });
    //
    //     // Test with a non-matching DOB
    //     await userEvent.clear(searchInput);
    //     await userEvent.type(searchInput, '1991-05-15');
    //     await waitFor(() => {
    //         expect(screen.queryByText(/Jane/i)).toBeNull(); // Jane should not be in the document
    //         expect(screen.queryByText(/John/i)).toBeNull(); // John should not be in the document
    //     });
    //})
})