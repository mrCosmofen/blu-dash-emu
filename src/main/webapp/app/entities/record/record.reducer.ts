import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRecord, defaultValue } from 'app/shared/model/record.model';

export const ACTION_TYPES = {
  FETCH_RECORD_LIST: 'record/FETCH_RECORD_LIST',
  FETCH_RECORD: 'record/FETCH_RECORD',
  CREATE_RECORD: 'record/CREATE_RECORD',
  UPDATE_RECORD: 'record/UPDATE_RECORD',
  DELETE_RECORD: 'record/DELETE_RECORD',
  RESET: 'record/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRecord>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type RecordState = Readonly<typeof initialState>;

// Reducer

export default (state: RecordState = initialState, action): RecordState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RECORD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RECORD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RECORD):
    case REQUEST(ACTION_TYPES.UPDATE_RECORD):
    case REQUEST(ACTION_TYPES.DELETE_RECORD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RECORD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RECORD):
    case FAILURE(ACTION_TYPES.CREATE_RECORD):
    case FAILURE(ACTION_TYPES.UPDATE_RECORD):
    case FAILURE(ACTION_TYPES.DELETE_RECORD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECORD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECORD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RECORD):
    case SUCCESS(ACTION_TYPES.UPDATE_RECORD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RECORD):
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

const apiUrl = 'api/records';

// Actions

export const getEntities: ICrudGetAllAction<IRecord> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RECORD_LIST,
  payload: axios.get<IRecord>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IRecord> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RECORD,
    payload: axios.get<IRecord>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRecord> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RECORD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRecord> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RECORD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRecord> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RECORD,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
