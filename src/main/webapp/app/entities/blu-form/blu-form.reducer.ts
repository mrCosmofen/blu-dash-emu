import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBluForm, defaultValue } from 'app/shared/model/blu-form.model';

export const ACTION_TYPES = {
  FETCH_BLUFORM_LIST: 'bluForm/FETCH_BLUFORM_LIST',
  FETCH_BLUFORM: 'bluForm/FETCH_BLUFORM',
  CREATE_BLUFORM: 'bluForm/CREATE_BLUFORM',
  UPDATE_BLUFORM: 'bluForm/UPDATE_BLUFORM',
  DELETE_BLUFORM: 'bluForm/DELETE_BLUFORM',
  RESET: 'bluForm/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBluForm>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BluFormState = Readonly<typeof initialState>;

// Reducer

export default (state: BluFormState = initialState, action): BluFormState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BLUFORM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BLUFORM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BLUFORM):
    case REQUEST(ACTION_TYPES.UPDATE_BLUFORM):
    case REQUEST(ACTION_TYPES.DELETE_BLUFORM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BLUFORM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BLUFORM):
    case FAILURE(ACTION_TYPES.CREATE_BLUFORM):
    case FAILURE(ACTION_TYPES.UPDATE_BLUFORM):
    case FAILURE(ACTION_TYPES.DELETE_BLUFORM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BLUFORM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BLUFORM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BLUFORM):
    case SUCCESS(ACTION_TYPES.UPDATE_BLUFORM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BLUFORM):
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

const apiUrl = 'api/blu-forms';

// Actions

export const getEntities: ICrudGetAllAction<IBluForm> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BLUFORM_LIST,
  payload: axios.get<IBluForm>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBluForm> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BLUFORM,
    payload: axios.get<IBluForm>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBluForm> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BLUFORM,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBluForm> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BLUFORM,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBluForm> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BLUFORM,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
