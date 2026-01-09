import { useEffect, useState } from "react";
import { usePlatformFormContext } from "./PlatformFormContext";
import { PlatformFormData } from "./PlatformFormData";
import * as yup from 'yup'

export function usePlatformFormPresenter() {
    const { repository, platformTypesService, alert, routeNavigator } = usePlatformFormContext();
    const [isSubmiting, setIsSubmiting] = useState(false);
    const [platformTypes, setPlatformTypes] = useState<string[]>([]);
    const [isLoading, setIsLoading] = useState(false);

    const onSubmit = async (values: PlatformFormData) => {
        setIsSubmiting(true);

        try {
            await repository.save(values);
            alert.success("Plataforma salva com sucesso!");
            routeNavigator.navigateTo('/');
        }
        catch (error) {
            alert.error("Erro ao salvar jogo!");
        }
        finally {
            setIsSubmiting(false);
        }
    }

    const onCancel = () => {
        routeNavigator.navigateTo('/');
    }

    useEffect(getPlatformTypes, [])

    return {
        onSubmit,
        onCancel,
        platformTypes,
        isSubmiting,
        isLoading,
        initialValues,
        validationSchema,
    }

    function getPlatformTypes() {
        setIsLoading(true);
        platformTypesService.getPlatformTypes()
            .then(setPlatformTypes)
            .catch(() => alert.error("Erro ao listar tipos de plataformas"))
            .finally(() => setIsLoading(false));
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