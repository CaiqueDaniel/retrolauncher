export interface PayloadMapper<O> {
  transform(input: Record<string, any>): O;
}
