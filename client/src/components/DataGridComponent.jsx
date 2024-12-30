import {TextField} from "@mui/material";
import {DataGrid} from "@mui/x-data-grid";
import {useState} from "react";

const DataGridComponent = ({title, isLoading, error, rows, columns}) => {
    const [searchTerm, setSearchTerm] = useState("");

    const filteredRows = rows.filter(row => {
        console.log(row)
        return (
            row.firstName.toLowerCase().includes(searchTerm.toLowerCase()) ||
            row.lastName.toLowerCase().includes(searchTerm.toLowerCase()) ||
            row.Address.toLowerCase().includes(searchTerm.toLowerCase()) ||
            row.Dob.toLowerCase().includes(searchTerm.toLowerCase())
        );
    });

    return (
        <div>
            <section>
                <h1>{title}</h1>
                <TextField
                    label="search"
                    variant="outlined"
                    onChange={(e) => setSearchTerm(e.target.value)}
                    margin="normal"
                    InputProps={{
                        style: {
                            width: '300px', // Set the width here
                        },
                    }}
                />
            </section>
            {isLoading ? (
                <p>Loading...</p>
            ) : error ? (
                <p style={{ color: 'red' }}>{error}</p> // Display the error message in red
            ) : (
                <div style={{ height: 400, width: '100%' }}>
                    <DataGrid data-testid="data-grid" rows={filteredRows} columns={columns} pageSize={5} />
                </div>
            )}

        </div>
    )
}

export default DataGridComponent;