import { IBluFieldStringValue } from 'app/shared/model/blu-field-string-value.model';
import { IBluFieldCurrencyValue } from 'app/shared/model/blu-field-currency-value.model';
import { IBluFieldNumberValue } from 'app/shared/model/blu-field-number-value.model';
import { IBluForm } from 'app/shared/model/blu-form.model';

export interface IBluFormData {
  id?: number;
  retrieved?: number;
  bluFieldStringValues?: IBluFieldStringValue[];
  bluFieldCurrencyValues?: IBluFieldCurrencyValue[];
  bluFieldNumberValues?: IBluFieldNumberValue[];
  bluForm?: IBluForm;
}

export const defaultValue: Readonly<IBluFormData> = {};
