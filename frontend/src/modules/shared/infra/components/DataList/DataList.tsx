import { Box } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";

export function DataList({ columns, rows }: Props) {
  return (
    <Box sx={{ height: 600, width: "100%" }}>
      <DataGrid
        rows={rows}
        columns={columns.map((column) => ({
          ...column,
          flex: 1,
          renderHeader: () => <b>{column.headerName}</b>,
        }))}
        initialState={{
          pagination: {
            paginationModel: {
              pageSize: 5,
            },
          },
        }}
        pageSizeOptions={[5]}
        checkboxSelection
        disableRowSelectionOnClick
      />
    </Box>
  );
}

type Props = {
  columns: Column[];
  rows: Record<string, string>[];
};

type Column = {
  field: string;
  headerName: string;
};
