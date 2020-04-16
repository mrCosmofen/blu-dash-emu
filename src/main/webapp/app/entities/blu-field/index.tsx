import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BluField from './blu-field';
import BluFieldDetail from './blu-field-detail';
import BluFieldUpdate from './blu-field-update';
import BluFieldDeleteDialog from './blu-field-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BluFieldDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BluFieldUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BluFieldUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BluFieldDetail} />
      <ErrorBoundaryRoute path={match.url} component={BluField} />
    </Switch>
  </>
);

export default Routes;
