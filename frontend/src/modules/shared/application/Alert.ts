export interface Alert {
    success(message: string): void;
    error(message: string): void;
}