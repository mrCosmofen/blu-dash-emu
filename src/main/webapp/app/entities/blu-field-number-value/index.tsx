import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BluFieldNumberValue from './blu-field-number-value';
import BluFieldNumberValueDetail from './blu-field-number-value-detail';
import BluFieldNumberValueUpdate from './blu-field-number-value-update';
import BluFieldNumberValueDeleteDialog from './blu-field-number-value-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BluFieldNumberValueDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BluFieldNumberValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BluFieldNumberValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BluFieldNumberValueDetail} />
      <ErrorBoundaryRoute path={match.url} component={BluFieldNumberValue} />
    </Switch>
  </>
);

export default Routes;
