import { useState } from "react";
import { usePlatformFormContext } from "./PlatformFormContext";
import { PlatformFormData } from "./PlatformFormData";
import * as yup from 'yup'

export function usePlatformFormPresenter() {
    const { repository, alert } = usePlatformFormContext();
    const [isSubmiting, setIsSubmiting] = useState(false);

    const onSubmit = async (values: PlatformFormData) => {
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

const initialValues: PlatformFormData = {
    name: '',
    type: '',
    path: '',
}

const validationSchema = yup.object({
    name: yup.string().required("Nome é obrigatório"),
    type: yup.string().required("Tipo é obrigatório"),
    path: yup.string().required("Caminho é obrigatório"),
});