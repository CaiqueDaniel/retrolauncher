import { renderHook, act, waitFor } from '@testing-library/react';
import { useGameFormPresenter } from './useGameFormPresenter';
import { GameFormContext } from './GameFormContext';
import { GameRepository } from '../../domain/Game';
import { Alert } from '~/modules/shared/application/Alert';
import { describe, it, expect, Mocked, vi, beforeEach, afterEach } from 'vitest';
import { ReactNode } from 'react';
import { GameFormData } from './GameFormData';
import { PlatformTypesService } from '../../services/PlatformTypesService';
import { RouteNavigator } from '~/modules/shared/application/RouteNavigator';

describe('useGameFormPresenter', () => {
    let repository: Mocked<GameRepository>;
    let platformTypesService: Mocked<PlatformTypesService>;
    let alert: Mocked<Alert>;
    let routeNavigator: Mocked<RouteNavigator>;

    beforeEach(() => {
        repository = {
            save: vi.fn(),
            get: vi.fn()
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
        platformTypesService.getPlatformTypes.mockResolvedValue([
            'Nintendo 64',
            'PlayStation 2',
        ]);
        const { result } = renderHook(() => useGameFormPresenter({}), { wrapper });

        expect(result.current.initialValues).toEqual({
            name: '',
            platformType: '',
            platformPath: '',
            path: '',
            cover: '',
        });
        expect(result.current.isSubmiting).toBe(false);
        expect(result.current.validationSchema).toBeDefined();

        await waitFor(() => {
            expect(result.current.platforms).toEqual([
                'Nintendo 64',
                'PlayStation 2',
            ]);
        });
    });

    it('should handle platform search error', async () => {
        platformTypesService.getPlatformTypes.mockRejectedValue(new Error('error'));
        const { result } = renderHook(() => useGameFormPresenter({}), { wrapper });

        await waitFor(() => {
            expect(result.current.platforms).toEqual([]);
        });
    });

    it('should submit form successfully', async () => {
        const gameData: GameFormData = {
            name: 'The Legend of Zelda',
            platformType: 'Nintendo 64',
            platformPath: '/path/to/platform',
            path: '/path/to/zelda',
            cover: '/path/to/cover.jpg'
        };

        const { result } = renderHook(() => useGameFormPresenter({}), { wrapper });

        await act(async () => {
            await result.current.onSubmit(gameData);
        });

        expect(repository.save).toHaveBeenCalledWith(gameData);
        expect(alert.success).toHaveBeenCalledWith('Jogo salvo com sucesso!');
        expect(result.current.isSubmiting).toBe(false);
        expect(routeNavigator.navigateTo).toHaveBeenCalledWith('/');
    });

    it('should handle submit error', async () => {
        const gameData: GameFormData = {
            name: 'The Legend of Zelda',
            platformType: 'Nintendo 64',
            platformPath: '/path/to/platform',
            path: '/path/to/zelda',
            cover: '/path/to/cover.jpg'
        };

        repository.save.mockRejectedValue(new Error('Network error'));

        const { result } = renderHook(() => useGameFormPresenter({}), { wrapper });

        await act(async () => {
            await result.current.onSubmit(gameData);
        });

        expect(repository.save).toHaveBeenCalledWith(gameData);
        expect(alert.error).toHaveBeenCalledWith('Erro ao salvar jogo!');
        expect(result.current.isSubmiting).toBe(false);
    });

    it('should set isSubmiting to true during submission', async () => {
        const gameData: GameFormData = {
            name: 'The Legend of Zelda',
            platformType: 'Nintendo 64',
            path: '/path/to/zelda',
            platformPath: '/path/to/platform',
            cover: '/path/to/cover.jpg'
        };

        let resolvePromise: (value: any) => void;
        const promise = new Promise((resolve) => {
            resolvePromise = resolve;
        });

        repository.save.mockReturnValue(promise as any);

        const { result } = renderHook(() => useGameFormPresenter({}), { wrapper });

        act(() => {
            result.current.onSubmit(gameData);
        });

        await waitFor(() => {
            expect(result.current.isSubmiting).toBe(true);
        });

        await act(async () => {
            resolvePromise!({ id: '1', ...gameData });
            await promise;
        });

        expect(result.current.isSubmiting).toBe(false);
    });

    it('should validate required fields', () => {
        const { result } = renderHook(() => useGameFormPresenter({}), { wrapper });

        expect(result.current.validationSchema).toBeDefined();

        // Test that validation schema requires all fields
        expect(() => {
            result.current.validationSchema.validateSync({
                name: '',
                platform: '',
                path: '',
                cover: '',
            });
        }).toThrow();
    });

    it('should call onCancel without errors', () => {
        const { result } = renderHook(() => useGameFormPresenter({}), { wrapper });

        result.current.onCancel();

        expect(routeNavigator.navigateTo).toHaveBeenCalledWith('/');
    });

    function wrapper({ children }: { children: ReactNode }) {
        return (
            <GameFormContext.Provider value={{
                repository,
                platformTypesService,
                alert,
                routeNavigator
            }}>
                {children}
            </GameFormContext.Provider>
        );
    }
});
