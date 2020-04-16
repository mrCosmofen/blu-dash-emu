import { IBluField } from 'app/shared/model/blu-field.model';
import { IBluFormData } from 'app/shared/model/blu-form-data.model';

export interface IBluForm {
  id?: number;
  key?: string;
  label?: string;
  category?: string;
  productKey?: string;
  pollingInterval?: number;
  modified?: number;
  bluFields?: IBluField[];
  bluFormData?: IBluFormData[];
}

export const defaultValue: Readonly<IBluForm> = {};
