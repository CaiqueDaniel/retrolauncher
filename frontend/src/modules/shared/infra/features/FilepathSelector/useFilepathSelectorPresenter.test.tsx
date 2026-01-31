import { renderHook, act, waitFor } from '@testing-library/react';
import { useFilepathSelectorPresenter } from './useFilepathSelectorPresenter';
import { FilepathSelectorContext } from './FilepathSelectorContext';
import { PathSelectionService } from '~/modules/shared/application/FilepathSelectionService';
import { describe, it, expect, Mocked } from 'vitest';
import { ReactNode } from 'react';

describe('useFilepathSelectorPresenter', () => {
    let service: Mocked<PathSelectionService>;

    beforeAll(() => {
        service = {
            selectPath: vi.fn()
        }
    })

    it('should initialize with provided value', () => {
        const initialValue = 'initial/path';

        service.selectPath.mockResolvedValue(initialValue);

        const { result } = renderHook(() => useFilepathSelectorPresenter({ value: initialValue }), { wrapper });

        expect(result.current.value).toBe(initialValue);
    });

    it('should update value when file is selected', async () => {
        const expectedFile = '/path/to/file';

        service.selectPath.mockResolvedValue(expectedFile);

        const { result } = renderHook(() => useFilepathSelectorPresenter({ value: '' }), { wrapper });

        await act(async () => {
            result.current.onClick();
        });

        await waitFor(() => {
            expect(result.current.value).toBe(expectedFile);
        });
    });

    function wrapper({ children }: { children: ReactNode }) {
        return (
            <FilepathSelectorContext.Provider value={{ filepathSelectionService: service }}>
                {children}
            </FilepathSelectorContext.Provider>
        );
    }
});
