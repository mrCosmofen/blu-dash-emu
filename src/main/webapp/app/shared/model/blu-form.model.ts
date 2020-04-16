import { IBluField } from 'app/shared/model/blu-field.model';

export interface IBluForm {
  id?: number;
  key?: string;
  label?: string;
  category?: string;
  productKey?: string;
  pollingInterval?: number;
  modified?: number;
  bluFields?: IBluField[];
}

export const defaultValue: Readonly<IBluForm> = {};
