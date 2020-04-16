import { IBluField } from 'app/shared/model/blu-field.model';
import { IBluFormData } from 'app/shared/model/blu-form-data.model';

export interface IBluFieldCurrencyValue {
  id?: number;
  fieldValue?: number;
  currency?: string;
  bluField?: IBluField;
  bluFormData?: IBluFormData;
}

export const defaultValue: Readonly<IBluFieldCurrencyValue> = {};
