import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BluFieldValue from './blu-field-value';
import BluFieldValueDetail from './blu-field-value-detail';
import BluFieldValueUpdate from './blu-field-value-update';
import BluFieldValueDeleteDialog from './blu-field-value-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BluFieldValueDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BluFieldValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BluFieldValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BluFieldValueDetail} />
      <ErrorBoundaryRoute path={match.url} component={BluFieldValue} />
    </Switch>
  </>
);

export default Routes;
