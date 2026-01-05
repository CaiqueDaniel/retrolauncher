import { Paper } from "@mui/material";
import { GameForm } from "../features/GameForm/GameForm";

export function GameFormPage() {
    return (
        <Paper sx={{ p: 3, mb: 3 }}>
            <GameForm />
        </Paper>
    )
}