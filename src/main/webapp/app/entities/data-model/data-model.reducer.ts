import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDataModel, defaultValue } from 'app/shared/model/data-model.model';

export const ACTION_TYPES = {
  FETCH_DATAMODEL_LIST: 'dataModel/FETCH_DATAMODEL_LIST',
  FETCH_DATAMODEL: 'dataModel/FETCH_DATAMODEL',
  CREATE_DATAMODEL: 'dataModel/CREATE_DATAMODEL',
  UPDATE_DATAMODEL: 'dataModel/UPDATE_DATAMODEL',
  DELETE_DATAMODEL: 'dataModel/DELETE_DATAMODEL',
  RESET: 'dataModel/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDataModel>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type DataModelState = Readonly<typeof initialState>;

// Reducer

export default (state: DataModelState = initialState, action): DataModelState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DATAMODEL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DATAMODEL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DATAMODEL):
    case REQUEST(ACTION_TYPES.UPDATE_DATAMODEL):
    case REQUEST(ACTION_TYPES.DELETE_DATAMODEL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DATAMODEL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DATAMODEL):
    case FAILURE(ACTION_TYPES.CREATE_DATAMODEL):
    case FAILURE(ACTION_TYPES.UPDATE_DATAMODEL):
    case FAILURE(ACTION_TYPES.DELETE_DATAMODEL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DATAMODEL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DATAMODEL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DATAMODEL):
    case SUCCESS(ACTION_TYPES.UPDATE_DATAMODEL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DATAMODEL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/data-models';

// Actions

export const getEntities: ICrudGetAllAction<IDataModel> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DATAMODEL_LIST,
  payload: axios.get<IDataModel>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IDataModel> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DATAMODEL,
    payload: axios.get<IDataModel>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDataModel> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DATAMODEL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDataModel> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DATAMODEL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDataModel> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DATAMODEL,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
