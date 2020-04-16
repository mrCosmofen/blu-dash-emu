import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BluFormData from './blu-form-data';
import BluFormDataDetail from './blu-form-data-detail';
import BluFormDataUpdate from './blu-form-data-update';
import BluFormDataDeleteDialog from './blu-form-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BluFormDataDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BluFormDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BluFormDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BluFormDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={BluFormData} />
    </Switch>
  </>
);

export default Routes;
