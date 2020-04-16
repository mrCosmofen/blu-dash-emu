import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BluForm from './blu-form';
import BluFormDetail from './blu-form-detail';
import BluFormUpdate from './blu-form-update';
import BluFormDeleteDialog from './blu-form-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BluFormDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BluFormUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BluFormUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BluFormDetail} />
      <ErrorBoundaryRoute path={match.url} component={BluForm} />
    </Switch>
  </>
);

export default Routes;
