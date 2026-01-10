import { useParams } from "react-router-dom";
import { GameForm } from "../features/GameForm/GameForm";
import { FormLayout } from "~/modules/shared/layouts/FormLayout";

export function GameFormPage() {
  const { id } = useParams();

  return (
    <FormLayout>
      <GameForm id={id} />
    </FormLayout>
  );
}
