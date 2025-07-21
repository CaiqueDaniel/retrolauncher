export type PaginatedResponse<T> = {
  currentPage: number;
  lastPage: number;
  items: T[];
};
