import { IQueryData } from 'app/shared/model/query-data.model';
import { IDataSet } from 'app/shared/model/data-set.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IDataModel {
  id?: number;
  key?: string;
  label?: string;
  dataFormat?: string;
  maxLength?: number;
  precision?: number;
  values?: Status;
  queryData?: IQueryData;
  dataSet?: IDataSet;
}

export const defaultValue: Readonly<IDataModel> = {};
