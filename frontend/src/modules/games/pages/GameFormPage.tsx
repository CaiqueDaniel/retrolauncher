import { GameForm } from "../features/GameForm/GameForm";
import { FormLayout } from "~/modules/shared/layouts/FormLayout";

export function GameFormPage() {
    return (
        <FormLayout>
            <GameForm />
        </FormLayout>
    )
}