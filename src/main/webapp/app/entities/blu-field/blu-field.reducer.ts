import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBluField, defaultValue } from 'app/shared/model/blu-field.model';

export const ACTION_TYPES = {
  FETCH_BLUFIELD_LIST: 'bluField/FETCH_BLUFIELD_LIST',
  FETCH_BLUFIELD: 'bluField/FETCH_BLUFIELD',
  CREATE_BLUFIELD: 'bluField/CREATE_BLUFIELD',
  UPDATE_BLUFIELD: 'bluField/UPDATE_BLUFIELD',
  DELETE_BLUFIELD: 'bluField/DELETE_BLUFIELD',
  RESET: 'bluField/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBluField>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BluFieldState = Readonly<typeof initialState>;

// Reducer

export default (state: BluFieldState = initialState, action): BluFieldState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BLUFIELD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BLUFIELD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BLUFIELD):
    case REQUEST(ACTION_TYPES.UPDATE_BLUFIELD):
    case REQUEST(ACTION_TYPES.DELETE_BLUFIELD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BLUFIELD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BLUFIELD):
    case FAILURE(ACTION_TYPES.CREATE_BLUFIELD):
    case FAILURE(ACTION_TYPES.UPDATE_BLUFIELD):
    case FAILURE(ACTION_TYPES.DELETE_BLUFIELD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BLUFIELD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BLUFIELD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BLUFIELD):
    case SUCCESS(ACTION_TYPES.UPDATE_BLUFIELD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BLUFIELD):
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

const apiUrl = 'api/blu-fields';

// Actions

export const getEntities: ICrudGetAllAction<IBluField> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BLUFIELD_LIST,
  payload: axios.get<IBluField>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBluField> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BLUFIELD,
    payload: axios.get<IBluField>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBluField> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BLUFIELD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBluField> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BLUFIELD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBluField> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BLUFIELD,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
