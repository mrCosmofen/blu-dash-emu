import { IBluField } from 'app/shared/model/blu-field.model';
import { IBluFormData } from 'app/shared/model/blu-form-data.model';

export interface IBluFieldNumberValue {
  id?: number;
  value?: number;
  bluField?: IBluField;
  bluFormData?: IBluFormData;
}

export const defaultValue: Readonly<IBluFieldNumberValue> = {};
