# Metodologia de Criação de Testes

Este documento descreve a metodologia padrão para criação de testes unitários no projeto, especialmente para React hooks e presenters.

## Stack de Testes

- **Framework**: Vitest
- **Testing Library**: @testing-library/react
- **Mocking**: vi (built-in do Vitest)

## Estrutura Geral de um Teste

```tsx
import { renderHook, act, waitFor } from '@testing-library/react';
import { describe, it, expect, Mocked, vi, beforeEach } from 'vitest';
import { ReactNode } from 'react';

describe('NomeDoHook/Presenter', () => {
    let dependency1: Mocked<Type1>;
    let dependency2: Mocked<Type2>;

    beforeEach(() => {
        // Inicializar mocks antes de cada teste
        dependency1 = {
            method1: vi.fn(),
            method2: vi.fn()
        };

        dependency2 = {
            method: vi.fn()
        };
    });

    it('should describe what it tests', () => {
        // Arrange
        const { result } = renderHook(() => useHook(), { wrapper });

        // Act & Assert
        expect(result.current.someValue).toBe(expectedValue);
    });

    function wrapper({ children }: { children: ReactNode }) {
        return (
            <SomeContext.Provider value={{ dependency1, dependency2 }}>
                {children}
            </SomeContext.Provider>
        );
    }
});
```

## Princípios Fundamentais

### 1. Use Doubles em Memória (Mocks)

**Não faça:** Criar classes completas que implementam toda a lógica
```tsx
// ❌ Evitar
class InMemoryService implements Service {
    private data: any[] = [];
    
    async save(item: any) {
        this.data.push(item);
        return item;
    }
    
    async getAll() {
        return this.data;
    }
}
```

**Faça:** Use mocks do Vitest com `vi.fn()`
```tsx
// ✅ Preferir
let service: Mocked<Service>;

beforeEach(() => {
    service = {
        save: vi.fn(),
        getAll: vi.fn()
    };
});

// Configure o comportamento conforme necessário em cada teste
service.save.mockResolvedValue(expectedValue);
```

### 2. Organize Testes com beforeEach

Configure mocks frescos antes de cada teste para evitar vazamento de estado:

```tsx
beforeEach(() => {
    repository = { save: vi.fn() };
    alert = { success: vi.fn(), error: vi.fn() };
    navigator = { navigateTo: vi.fn() };
});
```

### 3. Use Wrapper para Context Providers

Quando o hook depende de contextos, crie uma função `wrapper` dentro do describe:

```tsx
function wrapper({ children }: { children: ReactNode }) {
    return (
        <MyContext.Provider value={{ dependency1, dependency2 }}>
            {children}
        </MyContext.Provider>
    );
}
```

## Padrões de Teste Comuns

### Testar Inicialização

```tsx
it('should initialize with default values', () => {
    const { result } = renderHook(() => useMyHook(), { wrapper });

    expect(result.current.initialValue).toBe(expectedValue);
    expect(result.current.isLoading).toBe(false);
});
```

### Testar Ações Assíncronas (Success Case)

```tsx
it('should perform action successfully', async () => {
    const data = { name: 'Test' };
    service.save.mockResolvedValue(data);

    const { result } = renderHook(() => useMyHook(), { wrapper });

    await act(async () => {
        await result.current.onSubmit(data);
    });

    expect(service.save).toHaveBeenCalledWith(data);
    expect(alert.success).toHaveBeenCalledWith('Success message');
});
```

### Testar Tratamento de Erros

```tsx
it('should handle errors', async () => {
    service.save.mockRejectedValue(new Error('Network error'));

    const { result } = renderHook(() => useMyHook(), { wrapper });

    await act(async () => {
        await result.current.onSubmit(data);
    });

    expect(alert.error).toHaveBeenCalledWith('Error message');
});
```

### Testar Estados de Loading/Submitting

```tsx
it('should set loading state during async operation', async () => {
    let resolvePromise: (value: any) => void;
    const promise = new Promise((resolve) => {
        resolvePromise = resolve;
    });

    service.save.mockReturnValue(promise as any);

    const { result } = renderHook(() => useMyHook(), { wrapper });

    act(() => {
        result.current.onSubmit(data);
    });

    await waitFor(() => {
        expect(result.current.isLoading).toBe(true);
    });

    await act(async () => {
        resolvePromise!(data);
        await promise;
    });

    expect(result.current.isLoading).toBe(false);
});
```

### Testar Navegação

```tsx
it('should navigate on cancel', () => {
    const { result } = renderHook(() => useMyHook(), { wrapper });

    act(() => {
        result.current.onCancel();
    });

    expect(routeNavigator.navigateTo).toHaveBeenCalledWith('/expected-path');
});
```

### Testar Atualização de Estado

```tsx
it('should update state when action is performed', async () => {
    const newValue = 'new value';
    service.method.mockResolvedValue(newValue);

    const { result } = renderHook(() => useMyHook(), { wrapper });

    await act(async () => {
        result.current.onClick();
    });

    await waitFor(() => {
        expect(result.current.value).toBe(newValue);
    });
});
```

## Nomenclatura de Testes

### Arquivo de Teste
- Mesmo nome do arquivo testado + `.test.tsx`
- Exemplo: `useMyHook.tsx` → `useMyHook.test.tsx`

### Descrição dos Testes
Use o padrão "should + [ação/resultado]":

```tsx
✅ it('should initialize with default values', ...)
✅ it('should submit form successfully', ...)
✅ it('should handle submit error', ...)
✅ it('should navigate to home on cancel', ...)

❌ it('test initialization', ...)
❌ it('works correctly', ...)
```

## Comandos para Executar Testes

```bash
# Executar todos os testes
npm run test

# Executar um arquivo específico
npm run test -- --run path/to/file.test.tsx

# Modo watch (útil durante desenvolvimento)
npm run test -- path/to/file.test.tsx
```

## Checklist de Cobertura

Para um presenter/hook típico, certifique-se de testar:

- [ ] Inicialização com valores padrão
- [ ] Casos de sucesso de cada ação
- [ ] Tratamento de erros de cada ação assíncrona
- [ ] Estados de loading/submitting
- [ ] Navegação (se aplicável)
- [ ] Atualização de estado após ações
- [ ] Validação (se aplicável)

## Exemplos Completos

### Exemplo 1: Hook Simples com Estado

Ver: `/app/frontend/src/modules/shared/infra/features/FilepathSelector/useFilepathSelectorPresenter.test.tsx`

### Exemplo 2: Presenter com Múltiplas Dependências

Ver: `/app/frontend/src/modules/platforms/features/PlatformForm/usePlatformFormPresenter.test.tsx`

## Boas Práticas

1. **Isolamento**: Cada teste deve ser independente e não depender da ordem de execução
2. **Clareza**: O nome do teste deve deixar claro o que está sendo testado
3. **AAA Pattern**: Organize os testes em Arrange, Act, Assert
4. **Mocks Limpos**: Use `beforeEach` para garantir mocks frescos em cada teste
5. **Async/Await**: Sempre use `act` e `waitFor` para operações assíncronas
6. **Verificações Específicas**: Teste comportamentos específicos, não implementações
7. **Mock apenas o necessário**: Não mock tudo, foque nas dependências externas

## Antipadrões a Evitar

❌ **Não testar implementação interna**
```tsx
// Evitar testar como algo é feito
expect(useState).toHaveBeenCalled();
```

❌ **Não criar mocks complexos desnecessários**
```tsx
// Evitar implementações completas quando vi.fn() é suficiente
class ComplexMockImplementation { ... }
```

❌ **Não esquecer de limpar mocks**
```tsx
// Sem beforeEach, os testes podem interferir uns nos outros
```

❌ **Não testar vários cenários no mesmo teste**
```tsx
// Um teste = um cenário
it('should do everything', () => { ... }) // ❌
```

## Recursos Adicionais

- [Vitest Documentation](https://vitest.dev/)
- [Testing Library - React](https://testing-library.com/docs/react-testing-library/intro/)
- [Testing Library - Async Utilities](https://testing-library.com/docs/dom-testing-library/api-async/)
