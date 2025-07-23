export namespace GameQueryRepository {
    export interface Repository {
        search(params: Params): Promise<Output[]>;
    }

    export class Params {
        name?: string
    }

    export type Output = {
        id: string;
        name: string;
        platform: string;
        cover: string;
    }
}