import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DataModel from './data-model';
import DataModelDetail from './data-model-detail';
import DataModelUpdate from './data-model-update';
import DataModelDeleteDialog from './data-model-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DataModelDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DataModelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DataModelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DataModelDetail} />
      <ErrorBoundaryRoute path={match.url} component={DataModel} />
    </Switch>
  </>
);

export default Routes;
