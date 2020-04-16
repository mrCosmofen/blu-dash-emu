import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBluFieldValue, defaultValue } from 'app/shared/model/blu-field-value.model';

export const ACTION_TYPES = {
  FETCH_BLUFIELDVALUE_LIST: 'bluFieldValue/FETCH_BLUFIELDVALUE_LIST',
  FETCH_BLUFIELDVALUE: 'bluFieldValue/FETCH_BLUFIELDVALUE',
  CREATE_BLUFIELDVALUE: 'bluFieldValue/CREATE_BLUFIELDVALUE',
  UPDATE_BLUFIELDVALUE: 'bluFieldValue/UPDATE_BLUFIELDVALUE',
  DELETE_BLUFIELDVALUE: 'bluFieldValue/DELETE_BLUFIELDVALUE',
  RESET: 'bluFieldValue/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBluFieldValue>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BluFieldValueState = Readonly<typeof initialState>;

// Reducer

export default (state: BluFieldValueState = initialState, action): BluFieldValueState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BLUFIELDVALUE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BLUFIELDVALUE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BLUFIELDVALUE):
    case REQUEST(ACTION_TYPES.UPDATE_BLUFIELDVALUE):
    case REQUEST(ACTION_TYPES.DELETE_BLUFIELDVALUE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BLUFIELDVALUE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BLUFIELDVALUE):
    case FAILURE(ACTION_TYPES.CREATE_BLUFIELDVALUE):
    case FAILURE(ACTION_TYPES.UPDATE_BLUFIELDVALUE):
    case FAILURE(ACTION_TYPES.DELETE_BLUFIELDVALUE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BLUFIELDVALUE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BLUFIELDVALUE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BLUFIELDVALUE):
    case SUCCESS(ACTION_TYPES.UPDATE_BLUFIELDVALUE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BLUFIELDVALUE):
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

const apiUrl = 'api/blu-field-values';

// Actions

export const getEntities: ICrudGetAllAction<IBluFieldValue> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BLUFIELDVALUE_LIST,
  payload: axios.get<IBluFieldValue>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBluFieldValue> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BLUFIELDVALUE,
    payload: axios.get<IBluFieldValue>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBluFieldValue> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BLUFIELDVALUE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBluFieldValue> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BLUFIELDVALUE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBluFieldValue> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BLUFIELDVALUE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
