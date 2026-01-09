import { renderHook, act, waitFor } from '@testing-library/react';
import { usePlatformFormPresenter } from './usePlatformFormPresenter';
import { PlatformFormContext } from './PlatformFormContext';
import { PlatformRepository } from '../../domain/Platform';
import { Alert } from '~/modules/shared/application/Alert';
import { RouteNavigator } from '~/modules/shared/application/RouteNavigator';
import { describe, it, expect, Mocked, vi, beforeEach } from 'vitest';
import { ReactNode } from 'react';
import { PlatformFormData } from './PlatformFormData';
import { PlatformTypesService } from '../../services/PlatformTypesService';

describe('usePlatformFormPresenter', () => {
    let repository: Mocked<PlatformRepository>;
    let platformTypesService: Mocked<PlatformTypesService>;
    let alert: Mocked<Alert>;
    let routeNavigator: Mocked<RouteNavigator>;

    beforeEach(() => {
        repository = {
            save: vi.fn()
        };

        platformTypesService = {
            getPlatformTypes: vi.fn()
        };

        alert = {
            success: vi.fn(),
            error: vi.fn()
        };

        routeNavigator = {
            navigateTo: vi.fn()
        };

        platformTypesService.getPlatformTypes.mockResolvedValue([]);
    });

    afterEach(() => {
        vi.clearAllMocks();
    });

    it('should initialize with default values', async () => {
        platformTypesService.getPlatformTypes.mockResolvedValue(['Console', 'PC']);
        const { result } = renderHook(() => usePlatformFormPresenter(), { wrapper });

        expect(result.current.initialValues).toEqual({
            name: '',
            type: '',
            path: '',
        });
        expect(result.current.isSubmiting).toBe(false);
        expect(result.current.platformTypes).toEqual([]);
        expect(result.current.validationSchema).toBeDefined();

        await waitFor(() => {
            expect(result.current.platformTypes).toEqual(['Console', 'PC']);
        });
    });

    it('should handle error on load platform types', async () => {
        platformTypesService.getPlatformTypes.mockRejectedValue(new Error('Error on load platform types'));
        const { result } = renderHook(() => usePlatformFormPresenter(), { wrapper });

        await waitFor(() => {
            expect(result.current.platformTypes).toEqual([]);
            expect(alert.error).toHaveBeenCalledWith('Erro ao listar tipos de plataformas');
        });
    });

    it('should submit form successfully', async () => {
        const platformData: PlatformFormData = {
            name: 'PlayStation 5',
            type: 'Console',
            path: '/path/to/ps5'
        };

        const { result } = renderHook(() => usePlatformFormPresenter(), { wrapper });

        await act(async () => {
            await result.current.onSubmit(platformData);
        });

        expect(repository.save).toHaveBeenCalledWith(platformData);
        expect(alert.success).toHaveBeenCalledWith('Plataforma salva com sucesso!');
        expect(result.current.isSubmiting).toBe(false);
        expect(routeNavigator.navigateTo).toHaveBeenCalledWith('/');
    });

    it('should handle submit error', async () => {
        const platformData: PlatformFormData = {
            name: 'PlayStation 5',
            type: 'Console',
            path: '/path/to/ps5'
        };

        repository.save.mockRejectedValue(new Error('Network error'));

        const { result } = renderHook(() => usePlatformFormPresenter(), { wrapper });

        await act(async () => {
            await result.current.onSubmit(platformData);
        });

        expect(repository.save).toHaveBeenCalledWith(platformData);
        expect(alert.error).toHaveBeenCalledWith('Erro ao salvar jogo!');
        expect(result.current.isSubmiting).toBe(false);
    });

    it('should set isSubmiting to true during submission', async () => {
        const platformData: PlatformFormData = {
            name: 'PlayStation 5',
            type: 'Console',
            path: '/path/to/ps5'
        };

        let resolvePromise: (value: any) => void;
        const promise = new Promise((resolve) => {
            resolvePromise = resolve;
        });

        repository.save.mockReturnValue(promise as any);

        const { result } = renderHook(() => usePlatformFormPresenter(), { wrapper });

        act(() => {
            result.current.onSubmit(platformData);
        });

        await waitFor(() => {
            expect(result.current.isSubmiting).toBe(true);
        });

        await act(async () => {
            resolvePromise!({ id: '1', ...platformData });
            await promise;
        });

        expect(result.current.isSubmiting).toBe(false);
    });

    it('should navigate to home on cancel', () => {
        const { result } = renderHook(() => usePlatformFormPresenter(), { wrapper });

        act(() => {
            result.current.onCancel();
        });

        expect(routeNavigator.navigateTo).toHaveBeenCalledWith('/');
    });

    function wrapper({ children }: { children: ReactNode }) {
        return (
            <PlatformFormContext.Provider value={{
                repository,
                platformTypesService,
                alert,
                routeNavigator
            }}>
                {children}
            </PlatformFormContext.Provider>
        );
    }
});
