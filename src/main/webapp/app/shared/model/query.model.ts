import { IRecord } from 'app/shared/model/record.model';

export interface IQuery {
  id?: number;
  key?: string;
  label?: string;
  records?: IRecord[];
}

export const defaultValue: Readonly<IQuery> = {};
