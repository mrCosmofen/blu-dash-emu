import { IBluForm } from 'app/shared/model/blu-form.model';
import { BluFieldType } from 'app/shared/model/enumerations/blu-field-type.model';

export interface IBluField {
  id?: number;
  key?: string;
  label?: string;
  dataType?: BluFieldType;
  dataFormat?: string;
  values?: string;
  bluForm?: IBluForm;
}

export const defaultValue: Readonly<IBluField> = {};
