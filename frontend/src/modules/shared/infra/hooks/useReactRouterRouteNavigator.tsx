import { useNavigate } from "react-router-dom";
import { RouteNavigator } from "../../application/RouteNavigator";

export function useReactRouterRouteNavigator(): RouteNavigator {
    const navigate = useNavigate();

    return {
        navigateTo: (path: string) => navigate(path),
    };
}