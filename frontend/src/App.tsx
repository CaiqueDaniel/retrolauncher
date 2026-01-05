import { RouterProvider } from "react-router-dom";
import { routes } from "./routes";
import { theme } from "./theme";
import { ThemeProvider } from "@mui/material";
import { ToastContainer } from "react-toastify";

function App() {
  return (
    <ThemeProvider theme={theme}>
      <ToastContainer />
      <RouterProvider router={routes} />
    </ThemeProvider>
  )
}

export default App;
