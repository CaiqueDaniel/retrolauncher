export interface BusDispatcher {
  dispatch(name: string, message?: any): void;
}
