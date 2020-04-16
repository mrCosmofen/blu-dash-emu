import { IBluField } from 'app/shared/model/blu-field.model';
import { IBluFormData } from 'app/shared/model/blu-form-data.model';

export interface IBluFieldStringValue {
  id?: number;
  value?: string;
  bluField?: IBluField;
  bluFormData?: IBluFormData;
}

export const defaultValue: Readonly<IBluFieldStringValue> = {};
