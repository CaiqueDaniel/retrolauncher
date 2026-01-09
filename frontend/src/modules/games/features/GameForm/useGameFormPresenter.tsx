import { useEffect, useState } from "react";
import { useGameFormContext } from "./GameFormContext";
import { GameFormData } from "./GameFormData";
import * as yup from 'yup'
import { PlatformSearchResult } from "~/modules/platforms/services/PlatformSearchService";

export function useGameFormPresenter() {
    const { repository, alert, platformSearchService, routeNavigator } = useGameFormContext();
    const [isSubmiting, setIsSubmiting] = useState(false);
    const [platforms, setPlatforms] = useState<PlatformSearchResult[]>([]);
    const [isLoading, setIsLoading] = useState(false);

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

    const onCancel = () => routeNavigator.navigateTo('/');

    useEffect(fetchPlatforms, []);

    return {
        onSubmit,
        onCancel,
        isSubmiting,
        initialValues,
        validationSchema,
        platforms,
        isLoading,
    }

    function fetchPlatforms() {
        setIsLoading(true);
        platformSearchService.listAll()
            .then(setPlatforms)
            .catch(() => alert.error("Erro ao buscar plataformas"))
            .finally(() => setIsLoading(false));
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