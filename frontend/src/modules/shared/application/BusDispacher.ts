export interface BusDispacher {
  dispatch(name: string, message?: any): void;
}
