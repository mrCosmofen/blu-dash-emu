import { IBluFieldStringValue } from 'app/shared/model/blu-field-string-value.model';
import { IBluFieldCurrencyValue } from 'app/shared/model/blu-field-currency-value.model';
import { IBluFieldNumberValue } from 'app/shared/model/blu-field-number-value.model';
import { IBluForm } from 'app/shared/model/blu-form.model';
import { BluFieldType } from 'app/shared/model/enumerations/blu-field-type.model';

export interface IBluField {
  id?: number;
  key?: string;
  label?: string;
  dataType?: BluFieldType;
  dataFormat?: string;
  fieldValues?: string;
  bluFieldStringValue?: IBluFieldStringValue;
  bluFieldCurrencyValue?: IBluFieldCurrencyValue;
  bluFieldNumberValue?: IBluFieldNumberValue;
  bluForm?: IBluForm;
}

export const defaultValue: Readonly<IBluField> = {};
