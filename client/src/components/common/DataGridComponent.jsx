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
  page,
  setPage,
  lastPage,
  totalPages,
  pageSize,
  searchFunction,
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
      {title && (
        <div className="data-grid-title">
          <h1>{title}</h1>
        </div>
      )}
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
          />
          <button
            disabled={isLoading}
            onClick={() => {
              setFilter("");
              setFilterValue("");
            }}
            data-testid="clear-button"
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
                      `${pathname}?filter=${filter}&filterValue=${filterValue}`
                    )
            }
            data-testid="search-button"
          >
            Search
          </button>
        </div>
      )}
      <div style={{ height: 400, width: "100%" }}>
        <DataGrid
          data-testid="data-grid"
          rows={rows}
          columns={columns}
          pageSize={pageSize}
          loading={isLoading}
          page={page}
          hideFooterPagination
          slots={{
            noRowsOverlay: NoDataDisplay,
          }}
        />
      </div>
      <div>
        <Tooltip title={"Previous Page"}>
          <IconButton
            disabled={isLoading || page <= 1}
            onClick={() => setPage(page - 1)}
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
          page={page}
          onChange={(e, value) => setPage(value)}
        />
        <Tooltip
          title={
            lastPage && page === totalPages
              ? "No more pages"
              : !lastPage && page === totalPages
              ? "Load More"
              : "Next Page"
          }
        >
          <IconButton
            disabled={isLoading || (lastPage && page === totalPages)}
            onClick={() => setPage(page + 1)}
            data-testid="next-button"
          >
            {!lastPage && page === totalPages ? (
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

DataGridComponent.defaultProps = {
  title: null,
  columns: [],
  rows: [],
  isLoading: false,
  filterOptions: [],
  filterable: true,
  page: 1,
  setPage: () => {},
  lastPage: false,
  totalPages: 0,
  limit: 10,
  searchFunction: null,
};

export default DataGridComponent;
