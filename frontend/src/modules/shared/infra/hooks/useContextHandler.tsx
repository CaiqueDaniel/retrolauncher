import { useContext } from "react";

export function useContextHandler<T>(
  contextType: React.Context<T | undefined>
): T {
  const context = useContext(contextType);
  if (!context) throw new Error("Context was not provided");
  return context;
}
