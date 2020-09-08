export interface IProduct {
  id?: number;
  title?: string;
  price?: number;
  brand?: string;
  imageContentType?: string;
  image?: any;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public title?: string,
    public price?: number,
    public brand?: string,
    public imageContentType?: string,
    public image?: any
  ) {}
}
