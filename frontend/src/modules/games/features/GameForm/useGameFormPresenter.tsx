import { useState } from "react";
import { useGameFormContext } from "./GameFormContext";
import { GameFormData } from "./GameFormData";
import * as yup from 'yup'

export function useGameFormPresenter() {
    const { repository, alert } = useGameFormContext();
    const [isSubmiting, setIsSubmiting] = useState(false);

    const onSubmit = async (values: GameFormData) => {
        setIsSubmiting(true);

        try {
            await repository.save(values);
            alert.success("Jogo salvo com sucesso!");
        }
        catch (error) {
            alert.error("Erro ao salvar jogo!");
        }
        finally {
            setIsSubmiting(false);
        }
    }

    const onCancel = () => {

    }

    return {
        onSubmit,
        onCancel,
        isSubmiting,
        initialValues,
        validationSchema,
    }
}

const initialValues: GameFormData = {
    name: '',
    platform: '',
    path: '',
    cover: '',
}

const validationSchema = yup.object({
    name: yup.string().required("Nome é obrigatório"),
    platform: yup.string().required("Plataforma é obrigatória"),
    path: yup.string().required("Caminho é obrigatório"),
    cover: yup.string().required("Capa é obrigatória"),
});