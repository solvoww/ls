export class LsPath {
    private _path: string;

    get path(): string {
        return this._path;
    }
    set path(newPath) {
      this._path = newPath;
    }

}
