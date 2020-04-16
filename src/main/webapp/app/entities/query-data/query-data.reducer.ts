import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IQueryData, defaultValue } from 'app/shared/model/query-data.model';

export const ACTION_TYPES = {
  FETCH_QUERYDATA_LIST: 'queryData/FETCH_QUERYDATA_LIST',
  FETCH_QUERYDATA: 'queryData/FETCH_QUERYDATA',
  CREATE_QUERYDATA: 'queryData/CREATE_QUERYDATA',
  UPDATE_QUERYDATA: 'queryData/UPDATE_QUERYDATA',
  DELETE_QUERYDATA: 'queryData/DELETE_QUERYDATA',
  RESET: 'queryData/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IQueryData>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type QueryDataState = Readonly<typeof initialState>;

// Reducer

export default (state: QueryDataState = initialState, action): QueryDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_QUERYDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_QUERYDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_QUERYDATA):
    case REQUEST(ACTION_TYPES.UPDATE_QUERYDATA):
    case REQUEST(ACTION_TYPES.DELETE_QUERYDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_QUERYDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_QUERYDATA):
    case FAILURE(ACTION_TYPES.CREATE_QUERYDATA):
    case FAILURE(ACTION_TYPES.UPDATE_QUERYDATA):
    case FAILURE(ACTION_TYPES.DELETE_QUERYDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_QUERYDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_QUERYDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_QUERYDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_QUERYDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_QUERYDATA):
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

const apiUrl = 'api/query-data';

// Actions

export const getEntities: ICrudGetAllAction<IQueryData> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_QUERYDATA_LIST,
  payload: axios.get<IQueryData>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IQueryData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_QUERYDATA,
    payload: axios.get<IQueryData>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IQueryData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_QUERYDATA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IQueryData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_QUERYDATA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IQueryData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_QUERYDATA,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
