import { IDataModel } from 'app/shared/model/data-model.model';

export interface IDataSet {
  id?: number;
  key?: string;
  label?: string;
  landingPage?: string;
  dataModels?: IDataModel[];
}

export const defaultValue: Readonly<IDataSet> = {};
