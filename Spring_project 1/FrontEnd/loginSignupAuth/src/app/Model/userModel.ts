export class User {
  constructor(
    public id: number,
    public name: string,
    public age: number,
    public email: string,
    public authorities: { id: number; authority: string }[]
  ) {}
}
