import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BluForm from './blu-form';
import BluField from './blu-field';
import BluFieldValue from './blu-field-value';
import DataSet from './data-set';
import Query from './query';
import DataModel from './data-model';
import Record from './record';
import QueryData from './query-data';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}blu-form`} component={BluForm} />
      <ErrorBoundaryRoute path={`${match.url}blu-field`} component={BluField} />
      <ErrorBoundaryRoute path={`${match.url}blu-field-value`} component={BluFieldValue} />
      <ErrorBoundaryRoute path={`${match.url}data-set`} component={DataSet} />
      <ErrorBoundaryRoute path={`${match.url}query`} component={Query} />
      <ErrorBoundaryRoute path={`${match.url}data-model`} component={DataModel} />
      <ErrorBoundaryRoute path={`${match.url}record`} component={Record} />
      <ErrorBoundaryRoute path={`${match.url}query-data`} component={QueryData} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
