import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import QueryData from './query-data';
import QueryDataDetail from './query-data-detail';
import QueryDataUpdate from './query-data-update';
import QueryDataDeleteDialog from './query-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={QueryDataDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={QueryDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={QueryDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={QueryDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={QueryData} />
    </Switch>
  </>
);

export default Routes;
