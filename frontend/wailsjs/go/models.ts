export namespace application {
	
	export class Achievement {
	    ID: number;
	    Title: string;
	    Description: string;
	    Points: number;
	    DateEarned?: string;
	
	    static createFrom(source: any = {}) {
	        return new Achievement(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.ID = source["ID"];
	        this.Title = source["Title"];
	        this.Description = source["Description"];
	        this.Points = source["Points"];
	        this.DateEarned = source["DateEarned"];
	    }
	}
	export class GetGameOutput {
	    Id: string;
	    Name: string;
	    PlatformType: string;
	    PlatformPath: string;
	    Cover: string;
	    Path: string;
	
	    static createFrom(source: any = {}) {
	        return new GetGameOutput(source);
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
	export class GetSettingsOutput {
	    RetroarchFolderPath: string;
	    RomsFolderPath: string;
	    RetroachivementsUsername: string;
	    RetroachivementsPassword: string;
	    RetroachivementsApiKey: string;
	
	    static createFrom(source: any = {}) {
	        return new GetSettingsOutput(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.RetroarchFolderPath = source["RetroarchFolderPath"];
	        this.RomsFolderPath = source["RomsFolderPath"];
	        this.RetroachivementsUsername = source["RetroachivementsUsername"];
	        this.RetroachivementsPassword = source["RetroachivementsPassword"];
	        this.RetroachivementsApiKey = source["RetroachivementsApiKey"];
	    }
	}
	export class ListGamesOutput {
	    Id: string;
	    Name: string;
	    PlatformType: string;
	    PlatformPath: string;
	    Path: string;
	    Cover: string;
	
	    static createFrom(source: any = {}) {
	        return new ListGamesOutput(source);
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

export namespace desktop {
	
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
	export class SaveSettingsInputDto {
	    retroarchFolderPath: string;
	    romsFolderPath: string;
	    retroachivementsUsername: string;
	    retroachivementsPassword: string;
	    retroachivementsApiKey: string;
	
	    static createFrom(source: any = {}) {
	        return new SaveSettingsInputDto(source);
	    }
	
	    constructor(source: any = {}) {
	        if ('string' === typeof source) source = JSON.parse(source);
	        this.retroarchFolderPath = source["retroarchFolderPath"];
	        this.romsFolderPath = source["romsFolderPath"];
	        this.retroachivementsUsername = source["retroachivementsUsername"];
	        this.retroachivementsPassword = source["retroachivementsPassword"];
	        this.retroachivementsApiKey = source["retroachivementsApiKey"];
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

