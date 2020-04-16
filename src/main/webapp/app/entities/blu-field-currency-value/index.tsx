import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BluFieldCurrencyValue from './blu-field-currency-value';
import BluFieldCurrencyValueDetail from './blu-field-currency-value-detail';
import BluFieldCurrencyValueUpdate from './blu-field-currency-value-update';
import BluFieldCurrencyValueDeleteDialog from './blu-field-currency-value-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BluFieldCurrencyValueDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BluFieldCurrencyValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BluFieldCurrencyValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BluFieldCurrencyValueDetail} />
      <ErrorBoundaryRoute path={match.url} component={BluFieldCurrencyValue} />
    </Switch>
  </>
);

export default Routes;
