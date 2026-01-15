import { useEffect, useState } from "react";
import { useGameFormContext } from "./GameFormContext";
import { GameFormData } from "./GameFormData";
import * as yup from "yup";
import { PlatformSearchResult } from "~/modules/platforms/services/PlatformSearchService";

export function useGameFormPresenter(props: Props) {
  const { repository, alert, platformSearchService, routeNavigator } =
    useGameFormContext();
  const [initialValues, setInitialValues] = useState<GameFormData>(emptyValues);
  const [isSubmiting, setIsSubmiting] = useState(false);
  const [platforms, setPlatforms] = useState<PlatformSearchResult[]>([]);
  const [isLoadingPlatform, setIsLoadingPlatform] = useState(false);
  const [isLoadingGame, setIsLoadingGame] = useState(false);

  const onSubmit = async (values: GameFormData) => {
    setIsSubmiting(true);

    try {
      await repository.save(values);
      alert.success("Jogo salvo com sucesso!");
      routeNavigator.navigateTo("/");
    } catch (error) {
      alert.error("Erro ao salvar jogo!");
    } finally {
      setIsSubmiting(false);
    }
  };

  const onCancel = () => routeNavigator.navigateTo("/");

  useEffect(fetchPlatforms, []);
  useEffect(fetchGame, [props.id]);

  return {
    onSubmit,
    onCancel,
    isSubmiting,
    initialValues,
    validationSchema,
    platforms,
    isLoadingPlatform,
    isLoadingGame,
  };

  function fetchPlatforms() {
    setIsLoadingPlatform(true);
    platformSearchService
      .listAll()
      .then(setPlatforms)
      .catch(() => alert.error("Erro ao buscar plataformas"))
      .finally(() => setIsLoadingPlatform(false));
  }

  function fetchGame() {
    if (!props.id) return;

    setIsLoadingGame(true);
    repository
      .get(props.id)
      .then(setInitialValues)
      .catch(() => alert.error("Erro ao buscar jogo"))
      .finally(() => setTimeout(() => setIsLoadingGame(false), 10));
  }
}

const emptyValues: GameFormData = {
  name: "",
  platformType: "",
  platformPath: "",
  path: "",
  cover: "",
};

const validationSchema = yup.object({
  name: yup.string().required("Nome é obrigatório"),
  platform: yup.string().required("Plataforma é obrigatória"),
  path: yup.string().required("Caminho é obrigatório"),
  cover: yup.string().required("Capa é obrigatória"),
});

type Props = {
  id?: string;
};
