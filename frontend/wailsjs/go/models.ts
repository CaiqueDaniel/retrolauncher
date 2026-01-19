export namespace game_controller {
	
	export class CreateInputDto {
	    name: string;
	    platformType: string;
	    platformPath: string;
	    path: string;
	    cover: string;
	
	    static createFrom(source: any = {}) {
	        return new CreateInputDto(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.name = source["name"];
	        this.platformType = source["platformType"];
	        this.platformPath = source["platformPath"];
	        this.path = source["path"];
	        this.cover = source["cover"];
	    }
	}
	export class GetInputDto {
	    id: string;
	
	    static createFrom(source: any = {}) {
	        return new GetInputDto(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.id = source["id"];
	    }
	}
	export class ListInputDto {
	    name: string;
	
	    static createFrom(source: any = {}) {
	        return new ListInputDto(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.name = source["name"];
	    }
	}
	export class UpdateInputDto {
	    id: string;
	    name: string;
	    platformType: string;
	    platformPath: string;
	    path: string;
	    cover: string;
	
	    static createFrom(source: any = {}) {
	        return new UpdateInputDto(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.id = source["id"];
	        this.name = source["name"];
	        this.platformType = source["platformType"];
	        this.platformPath = source["platformPath"];
	        this.path = source["path"];
	        this.cover = source["cover"];
	    }
	}

}

export namespace get_game {
	
	export class Output {
	    Id: string;
	    Name: string;
	    PlatformType: string;
	    PlatformPath: string;
	    Cover: string;
	    Path: string;
	
	    static createFrom(source: any = {}) {
	        return new Output(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.Id = source["Id"];
	        this.Name = source["Name"];
	        this.PlatformType = source["PlatformType"];
	        this.PlatformPath = source["PlatformPath"];
	        this.Cover = source["Cover"];
	        this.Path = source["Path"];
	    }
	}

}

export namespace list_games {
	
	export class Output {
	    Id: string;
	    Name: string;
	    PlatformType: string;
	    PlatformPath: string;
	    Path: string;
	    Cover: string;
	
	    static createFrom(source: any = {}) {
	        return new Output(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.Id = source["Id"];
	        this.Name = source["Name"];
	        this.PlatformType = source["PlatformType"];
	        this.PlatformPath = source["PlatformPath"];
	        this.Path = source["Path"];
	        this.Cover = source["Cover"];
	    }
	}

}

