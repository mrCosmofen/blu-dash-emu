import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBluFieldNumberValue, defaultValue } from 'app/shared/model/blu-field-number-value.model';

export const ACTION_TYPES = {
  FETCH_BLUFIELDNUMBERVALUE_LIST: 'bluFieldNumberValue/FETCH_BLUFIELDNUMBERVALUE_LIST',
  FETCH_BLUFIELDNUMBERVALUE: 'bluFieldNumberValue/FETCH_BLUFIELDNUMBERVALUE',
  CREATE_BLUFIELDNUMBERVALUE: 'bluFieldNumberValue/CREATE_BLUFIELDNUMBERVALUE',
  UPDATE_BLUFIELDNUMBERVALUE: 'bluFieldNumberValue/UPDATE_BLUFIELDNUMBERVALUE',
  DELETE_BLUFIELDNUMBERVALUE: 'bluFieldNumberValue/DELETE_BLUFIELDNUMBERVALUE',
  RESET: 'bluFieldNumberValue/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBluFieldNumberValue>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BluFieldNumberValueState = Readonly<typeof initialState>;

// Reducer

export default (state: BluFieldNumberValueState = initialState, action): BluFieldNumberValueState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BLUFIELDNUMBERVALUE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BLUFIELDNUMBERVALUE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BLUFIELDNUMBERVALUE):
    case REQUEST(ACTION_TYPES.UPDATE_BLUFIELDNUMBERVALUE):
    case REQUEST(ACTION_TYPES.DELETE_BLUFIELDNUMBERVALUE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BLUFIELDNUMBERVALUE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BLUFIELDNUMBERVALUE):
    case FAILURE(ACTION_TYPES.CREATE_BLUFIELDNUMBERVALUE):
    case FAILURE(ACTION_TYPES.UPDATE_BLUFIELDNUMBERVALUE):
    case FAILURE(ACTION_TYPES.DELETE_BLUFIELDNUMBERVALUE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BLUFIELDNUMBERVALUE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BLUFIELDNUMBERVALUE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BLUFIELDNUMBERVALUE):
    case SUCCESS(ACTION_TYPES.UPDATE_BLUFIELDNUMBERVALUE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BLUFIELDNUMBERVALUE):
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

const apiUrl = 'api/blu-field-number-values';

// Actions

export const getEntities: ICrudGetAllAction<IBluFieldNumberValue> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BLUFIELDNUMBERVALUE_LIST,
  payload: axios.get<IBluFieldNumberValue>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBluFieldNumberValue> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BLUFIELDNUMBERVALUE,
    payload: axios.get<IBluFieldNumberValue>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBluFieldNumberValue> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BLUFIELDNUMBERVALUE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBluFieldNumberValue> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BLUFIELDNUMBERVALUE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBluFieldNumberValue> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BLUFIELDNUMBERVALUE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
