import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import bluForm, {
  BluFormState
} from 'app/entities/blu-form/blu-form.reducer';
// prettier-ignore
import bluField, {
  BluFieldState
} from 'app/entities/blu-field/blu-field.reducer';
// prettier-ignore
import bluFieldValue, {
  BluFieldValueState
} from 'app/entities/blu-field-value/blu-field-value.reducer';
// prettier-ignore
import dataSet, {
  DataSetState
} from 'app/entities/data-set/data-set.reducer';
// prettier-ignore
import query, {
  QueryState
} from 'app/entities/query/query.reducer';
// prettier-ignore
import dataModel, {
  DataModelState
} from 'app/entities/data-model/data-model.reducer';
// prettier-ignore
import record, {
  RecordState
} from 'app/entities/record/record.reducer';
// prettier-ignore
import queryData, {
  QueryDataState
} from 'app/entities/query-data/query-data.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly bluForm: BluFormState;
  readonly bluField: BluFieldState;
  readonly bluFieldValue: BluFieldValueState;
  readonly dataSet: DataSetState;
  readonly query: QueryState;
  readonly dataModel: DataModelState;
  readonly record: RecordState;
  readonly queryData: QueryDataState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  bluForm,
  bluField,
  bluFieldValue,
  dataSet,
  query,
  dataModel,
  record,
  queryData,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
