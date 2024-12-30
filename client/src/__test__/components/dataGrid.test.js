import DataGridComponent from "../components/DataGridComponent.jsx"
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

        // Check if the data grid rows are rendered
        mockRows.forEach((patient) => {
            expect(screen.getByText(patient.firstName)).toBeInTheDocument();
            expect(screen.getByText(patient.lastName)).toBeInTheDocument();
            expect(screen.getByText(patient.Address)).toBeInTheDocument();
            expect(screen.getByText(patient.Dob)).toBeInTheDocument();
            expect(screen.getByText(patient.Prescriptions)).toBeInTheDocument();
        });
    })
})