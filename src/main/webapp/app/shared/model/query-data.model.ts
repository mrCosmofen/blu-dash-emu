import { IDataModel } from 'app/shared/model/data-model.model';
import { IRecord } from 'app/shared/model/record.model';

export interface IQueryData {
  id?: number;
  dataValue?: string;
  dataModel?: IDataModel;
  record?: IRecord;
}

export const defaultValue: Readonly<IQueryData> = {};
