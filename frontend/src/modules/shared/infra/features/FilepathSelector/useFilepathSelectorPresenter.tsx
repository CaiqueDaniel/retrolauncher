import { useState } from "react";
import { useFilepathSelectorContext } from "./FilepathSelectorContext";

export function useFilepathSelectorPresenter(props: Props) {
  const { filepathSelectionService } = useFilepathSelectorContext();
  const [value, setValue] = useState(props.value);

  const onClick = () => {
    filepathSelectionService
      .selectFile(props.extensions || ["*"])
      .then(setValue);
  };

  return { onClick, value };
}

type Props = {
  value: string;
  extensions?: string[];
};
