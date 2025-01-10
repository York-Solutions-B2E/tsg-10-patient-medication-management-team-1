import { useState } from "react";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";
import {
  MenuItem,
  TextField,
  Pagination,
  IconButton,
  Tooltip,
  Typography,
} from "@mui/material";
import {
  ReadMoreSharp,
  NavigateNextSharp,
  NavigateBeforeSharp,
} from "@mui/icons-material";
import { DataGrid } from "@mui/x-data-grid";

const DataGridComponent = ({
  title,
  columns,
  rows,
  isLoading,
  filterOptions,
  filterable,
  lastPage,
  totalPages,
  searchFunction,
  pageModel,
  setPageModel,
}) => {
  const [searchParams] = useSearchParams();
  const [filter, setFilter] = useState(searchParams.get("filter") || "");
  const [filterValue, setFilterValue] = useState(
    searchParams.get("filterValue") || ""
  );

  const navigate = useNavigate();

  const { pathname } = useLocation();

  const filterDropdownOptions = !filterable
    ? []
    : filterOptions
    ? filterOptions
    : columns.length > 0
    ? columns.map((col) => {
        return { value: col.field, label: col.headerName };
      })
    : [];

  const NoDataDisplay = () => (
    <div>
      <Typography>No data to show!</Typography>);
    </div>
  );

  return (
    <div>
      {title && <div className="data-grid-title header-box">{title}</div>}
      {filterable && (
        <div>
          <TextField
            label="Filter"
            disabled={isLoading}
            variant="outlined"
            select
            value={filter}
            onChange={(e) => setFilter(e.target.value)}
            data-testid="filter-input"
            sx={{
              backgroundColor: "white",
              border: "1px solid black",
              borderRadius: "4px",
            }}
          >
            {filterDropdownOptions.map((option) => (
              <MenuItem key={option.value} role="option" value={option.value}>
                {option.label}
              </MenuItem>
            ))}
          </TextField>
          <TextField
            label="Search"
            variant="outlined"
            disabled={isLoading}
            value={filterValue}
            onChange={(e) => setFilterValue(e.target.value)}
            data-testid="search-input"
            sx={{
              backgroundColor: "white",
              border: "1px solid black",
              borderRadius: "4px",
              marginLeft: "15px",
              marginBottom: "15px",
            }}
          />
          <button
            disabled={isLoading}
            onClick={() => {
              navigate(pathname);
              setFilter("");
              setFilterValue("");
            }}
            data-testid="clear-button"
            style={{
              backgroundColor: "#ffcccc", // Light red
              color: "black",
              border: "1px solid black",
              padding: "10px 20px",
              fontSize: "16px",
              fontWeight: "600",
              cursor: "pointer",
              textTransform: "uppercase",
              transition: "transform 0.2s ease",
              boxShadow: "5px 5px black", // Drop shadow
              marginRight: "15px", // Adds spacing between buttons
              marginLeft: "15px", // Adds spacing between buttons
            }}
          >
            Clear
          </button>
          <button
            disabled={isLoading}
            onClick={
              searchFunction
                ? () => searchFunction(filter, filterValue)
                : () =>
                    navigate(
                      filter === "" && filterValue === ""
                        ? `${pathname}?filter=${filter}&filterValue=${filterValue}`
                        : pathname
                    )
            }
            data-testid="search-button"
            style={{
              backgroundColor: "#ffd755", // Gold
              color: "black",
              border: "1px solid black",
              padding: "10px 20px",
              fontSize: "16px",
              fontWeight: "600",
              cursor: "pointer",
              textTransform: "uppercase",
              transition: "transform 0.2s ease",
              boxShadow: "5px 5px black", // Drop shadow
            }}
          >
            Search
          </button>
        </div>
      )}
      <div style={{ height: 400, width: "100%" }}>
        <DataGrid
          paginationModel={pageModel}
          sx={{
            "&.MuiDataGrid-root": {
              backgroundColor: "#e3f2fd", // Light blue background
              boxShadow: "10px 10px black", // Drop shadow
              border: "1px solid black", // Optional: Matches the border of Patient List
              borderRadius: "8px", // Optional: Adds a rounded corner
            },
          }}
          data-testid="data-grid"
          rows={rows}
          columns={columns}
          loading={isLoading}
          slots={{
            noRowsOverlay: NoDataDisplay,
          }}
        />
      </div>
      <div
        className="button-container"
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          gap: "15px",
          marginTop: "20px",
        }}
      >
        <Tooltip title={"Previous Page"}>
          <IconButton
            disabled={isLoading || pageModel.page == 0}
            onClick={() =>
              setPageModel({ ...pageModel, page: pageModel.page - 1 })
            }
            data-testid="prev-button"
          >
            <NavigateBeforeSharp />
          </IconButton>
        </Tooltip>
        <Pagination
          hidePrevButton
          hideNextButton
          disabled={isLoading}
          count={totalPages}
          page={pageModel.page + 1}
          onChange={(e, value) => {
            setPageModel({ ...pageModel, page: value - 1 });
          }}
        />
        <Tooltip
          title={
            lastPage && pageModel.page + 1 === totalPages
              ? "No more pages"
              : !lastPage && pageModel.page + 1 === totalPages
              ? "Load More"
              : "Next Page"
          }
        >
          <IconButton
            disabled={
              isLoading || (lastPage && pageModel.page + 1 === totalPages)
            }
            onClick={() => {
              setPageModel({ ...pageModel, page: pageModel.page + 1 });
            }}
            data-testid="next-button"
          >
            {/* {!lastPage && page === totalPages ? ( */}
            {!lastPage && pageModel.page === totalPages - 1 ? (
              <ReadMoreSharp />
            ) : (
              <NavigateNextSharp />
            )}
          </IconButton>
        </Tooltip>
      </div>
    </div>
  );
};

export default DataGridComponent;
