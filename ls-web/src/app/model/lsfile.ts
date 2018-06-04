export class LsFile {
    private _type: string;
    private _canonicalPath: string;
    private _name: string;
    private _modDate: string;
    private _size: number;
    private _attr: string;

    get type(): string {
        return this._type;
    }
    set type(newType) {
      this._type = newType;
    }

    get canonicalPath(): string {
        return this._canonicalPath;
    }
    set canonicalPath(newCanonicalPath) {
      this._canonicalPath = newCanonicalPath;
    }

    get name(): string {
        return this._name;
    }
    set name(newName) {
      this._name = newName;
    }

    get modDate(): string {
        return this._modDate;
    }
    set modDate(newModDate) {
      this._modDate = newModDate;
    }

    get size(): number {
        return this._size;
    }
    set size(newSize) {
      this._size = newSize;
    }

    get attr(): string {
        return this._attr;
    }
    set attr(newAttr) {
      this._attr = newAttr;
    }

}
