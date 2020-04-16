import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBluFieldCurrencyValue, defaultValue } from 'app/shared/model/blu-field-currency-value.model';

export const ACTION_TYPES = {
  FETCH_BLUFIELDCURRENCYVALUE_LIST: 'bluFieldCurrencyValue/FETCH_BLUFIELDCURRENCYVALUE_LIST',
  FETCH_BLUFIELDCURRENCYVALUE: 'bluFieldCurrencyValue/FETCH_BLUFIELDCURRENCYVALUE',
  CREATE_BLUFIELDCURRENCYVALUE: 'bluFieldCurrencyValue/CREATE_BLUFIELDCURRENCYVALUE',
  UPDATE_BLUFIELDCURRENCYVALUE: 'bluFieldCurrencyValue/UPDATE_BLUFIELDCURRENCYVALUE',
  DELETE_BLUFIELDCURRENCYVALUE: 'bluFieldCurrencyValue/DELETE_BLUFIELDCURRENCYVALUE',
  RESET: 'bluFieldCurrencyValue/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBluFieldCurrencyValue>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BluFieldCurrencyValueState = Readonly<typeof initialState>;

// Reducer

export default (state: BluFieldCurrencyValueState = initialState, action): BluFieldCurrencyValueState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BLUFIELDCURRENCYVALUE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BLUFIELDCURRENCYVALUE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BLUFIELDCURRENCYVALUE):
    case REQUEST(ACTION_TYPES.UPDATE_BLUFIELDCURRENCYVALUE):
    case REQUEST(ACTION_TYPES.DELETE_BLUFIELDCURRENCYVALUE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BLUFIELDCURRENCYVALUE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BLUFIELDCURRENCYVALUE):
    case FAILURE(ACTION_TYPES.CREATE_BLUFIELDCURRENCYVALUE):
    case FAILURE(ACTION_TYPES.UPDATE_BLUFIELDCURRENCYVALUE):
    case FAILURE(ACTION_TYPES.DELETE_BLUFIELDCURRENCYVALUE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BLUFIELDCURRENCYVALUE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BLUFIELDCURRENCYVALUE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BLUFIELDCURRENCYVALUE):
    case SUCCESS(ACTION_TYPES.UPDATE_BLUFIELDCURRENCYVALUE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BLUFIELDCURRENCYVALUE):
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

const apiUrl = 'api/blu-field-currency-values';

// Actions

export const getEntities: ICrudGetAllAction<IBluFieldCurrencyValue> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BLUFIELDCURRENCYVALUE_LIST,
  payload: axios.get<IBluFieldCurrencyValue>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBluFieldCurrencyValue> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BLUFIELDCURRENCYVALUE,
    payload: axios.get<IBluFieldCurrencyValue>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBluFieldCurrencyValue> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BLUFIELDCURRENCYVALUE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBluFieldCurrencyValue> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BLUFIELDCURRENCYVALUE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBluFieldCurrencyValue> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BLUFIELDCURRENCYVALUE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
