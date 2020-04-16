import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BluFieldStringValue from './blu-field-string-value';
import BluFieldStringValueDetail from './blu-field-string-value-detail';
import BluFieldStringValueUpdate from './blu-field-string-value-update';
import BluFieldStringValueDeleteDialog from './blu-field-string-value-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BluFieldStringValueDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BluFieldStringValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BluFieldStringValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BluFieldStringValueDetail} />
      <ErrorBoundaryRoute path={match.url} component={BluFieldStringValue} />
    </Switch>
  </>
);

export default Routes;
