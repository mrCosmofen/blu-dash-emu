import { IQueryData } from 'app/shared/model/query-data.model';
import { IQuery } from 'app/shared/model/query.model';

export interface IRecord {
  id?: number;
  recordId?: number;
  queryData?: IQueryData[];
  query?: IQuery;
}

export const defaultValue: Readonly<IRecord> = {};
