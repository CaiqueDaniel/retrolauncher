export namespace game_controller {
	
	export class CreateInputDto {
	    name: string;
	    platform: string;
	    path: string;
	    cover: string;
	
	    static createFrom(source: any = {}) {
	        return new CreateInputDto(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.name = source["name"];
	        this.platform = source["platform"];
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
	    platform: string;
	    path: string;
	    cover: string;
	
	    static createFrom(source: any = {}) {
	        return new UpdateInputDto(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.id = source["id"];
	        this.name = source["name"];
	        this.platform = source["platform"];
	        this.path = source["path"];
	        this.cover = source["cover"];
	    }
	}

}

export namespace get_game {
	
	export class Output {
	    Id: string;
	    Name: string;
	    Platform: string;
	    Cover: string;
	    Path: string;
	
	    static createFrom(source: any = {}) {
	        return new Output(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.Id = source["Id"];
	        this.Name = source["Name"];
	        this.Platform = source["Platform"];
	        this.Cover = source["Cover"];
	        this.Path = source["Path"];
	    }
	}

}

export namespace list_games {
	
	export class Output {
	    Id: string;
	    Name: string;
	    Platform: string;
	    Path: string;
	    Cover: string;
	
	    static createFrom(source: any = {}) {
	        return new Output(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.Id = source["Id"];
	        this.Name = source["Name"];
	        this.Platform = source["Platform"];
	        this.Path = source["Path"];
	        this.Cover = source["Cover"];
	    }
	}

}

export namespace list_platforms {
	
	export class Output {
	    Id: string;
	    Name: string;
	
	    static createFrom(source: any = {}) {
	        return new Output(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.Id = source["Id"];
	        this.Name = source["Name"];
	    }
	}

}

export namespace platform_controller {
	
	export class CreateInputDto {
	    name: string;
	    type: string;
	    path: string;
	
	    static createFrom(source: any = {}) {
	        return new CreateInputDto(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.name = source["name"];
	        this.type = source["type"];
	        this.path = source["path"];
	    }
	}

}

