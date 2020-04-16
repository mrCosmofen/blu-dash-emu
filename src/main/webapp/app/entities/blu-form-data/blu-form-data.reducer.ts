import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBluFormData, defaultValue } from 'app/shared/model/blu-form-data.model';

export const ACTION_TYPES = {
  FETCH_BLUFORMDATA_LIST: 'bluFormData/FETCH_BLUFORMDATA_LIST',
  FETCH_BLUFORMDATA: 'bluFormData/FETCH_BLUFORMDATA',
  CREATE_BLUFORMDATA: 'bluFormData/CREATE_BLUFORMDATA',
  UPDATE_BLUFORMDATA: 'bluFormData/UPDATE_BLUFORMDATA',
  DELETE_BLUFORMDATA: 'bluFormData/DELETE_BLUFORMDATA',
  RESET: 'bluFormData/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBluFormData>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BluFormDataState = Readonly<typeof initialState>;

// Reducer

export default (state: BluFormDataState = initialState, action): BluFormDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BLUFORMDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BLUFORMDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BLUFORMDATA):
    case REQUEST(ACTION_TYPES.UPDATE_BLUFORMDATA):
    case REQUEST(ACTION_TYPES.DELETE_BLUFORMDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BLUFORMDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BLUFORMDATA):
    case FAILURE(ACTION_TYPES.CREATE_BLUFORMDATA):
    case FAILURE(ACTION_TYPES.UPDATE_BLUFORMDATA):
    case FAILURE(ACTION_TYPES.DELETE_BLUFORMDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BLUFORMDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BLUFORMDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BLUFORMDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_BLUFORMDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BLUFORMDATA):
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

const apiUrl = 'api/blu-form-data';

// Actions

export const getEntities: ICrudGetAllAction<IBluFormData> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BLUFORMDATA_LIST,
  payload: axios.get<IBluFormData>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBluFormData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BLUFORMDATA,
    payload: axios.get<IBluFormData>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBluFormData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BLUFORMDATA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBluFormData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BLUFORMDATA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBluFormData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BLUFORMDATA,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
