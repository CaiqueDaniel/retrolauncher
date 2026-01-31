import { useCallback, useEffect, useState } from "react";
import * as Yup from "yup";
import { SettingsFormData } from "../../services/SettingsGateway";
import { useSettingsFormContext } from "./SettingsFormContext";

export function useSettingsFormPresenter() {
  const { gateway, alert, navigator } = useSettingsFormContext();
  const [initialValues, setInitialValues] = useState<SettingsFormData>({
    romsFolderPath: "",
    retroarchFolderPath: "",
  });
  const [isSubmiting, setIsSubmiting] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    loadSettings();
  }, []);

  const loadSettings = useCallback(async () => {
    try {
      setIsLoading(true);
      const settings = await gateway.getSettings();
      setInitialValues(settings);
    } catch (error) {
      console.log(error);
      alert.error("Erro ao carregar configurações");
    } finally {
      setIsLoading(false);
    }
  }, [gateway, alert]);

  const onSubmit = useCallback(
    async (values: SettingsFormData) => {
      try {
        setIsSubmiting(true);
        await gateway.saveSettings(values);
        alert.success("Configurações salvas com sucesso!");
      } catch (error) {
        alert.error("Erro ao salvar configurações");
      } finally {
        setIsSubmiting(false);
      }
    },
    [gateway, alert, loadSettings],
  );

  const onCancel = () => {
    navigator.navigateTo("..");
  };

  return {
    initialValues,
    onSubmit,
    onCancel,
    isSubmiting,
    isLoading,
    validationSchema,
  };
}

const validationSchema = Yup.object().shape({
  romsFolderPath: Yup.string().required(
    "Caminho da pasta de ROMs é obrigatório",
  ),
  retroarchFolderPath: Yup.string().required(
    "Caminho do Retroarch é obrigatório",
  ),
});
