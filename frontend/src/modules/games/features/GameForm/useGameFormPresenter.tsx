import { useEffect, useState } from "react";
import { useGameFormContext } from "./GameFormContext";
import { GameFormData } from "./GameFormData";
import * as yup from "yup";

export function useGameFormPresenter(props: Props) {
  const { repository, alert, platformTypesService, routeNavigator } =
    useGameFormContext();
  const [initialValues, setInitialValues] = useState<GameFormData>(emptyValues);
  const [isSubmiting, setIsSubmiting] = useState(false);
  const [platforms, setPlatforms] = useState<string[]>([]);
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

  function fetchGame() {
    if (!props.id) return;

    setIsLoadingGame(true);
    repository
      .get(props.id)
      .then(setInitialValues)
      .catch(() => alert.error("Erro ao buscar jogo"))
      .finally(() => setTimeout(() => setIsLoadingGame(false), 10));
  }

  function fetchPlatforms() {
    setIsLoadingPlatform(true);
    platformTypesService
      .getPlatformTypes()
      .then(setPlatforms)
      .catch(() => alert.error("Erro ao listar tipos de plataformas"))
      .finally(() => setTimeout(() => setIsLoadingPlatform(false), 10));
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
  platformType: yup.string().required("Plataforma é obrigatória"),
  platformPath: yup.string().required("Caminho da plataforma é obrigatório"),
  path: yup.string().required("Caminho é obrigatório"),
  cover: yup.string().required("Capa é obrigatória"),
});

type Props = {
  id?: string;
};
