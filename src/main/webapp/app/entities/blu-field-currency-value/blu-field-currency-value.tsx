import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './blu-field-currency-value.reducer';
import { IBluFieldCurrencyValue } from 'app/shared/model/blu-field-currency-value.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBluFieldCurrencyValueProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const BluFieldCurrencyValue = (props: IBluFieldCurrencyValueProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { bluFieldCurrencyValueList, match, loading } = props;
  return (
    <div>
      <h2 id="blu-field-currency-value-heading">
        <Translate contentKey="emulatorApp.bluFieldCurrencyValue.home.title">Blu Field Currency Values</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="emulatorApp.bluFieldCurrencyValue.home.createLabel">Create new Blu Field Currency Value</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {bluFieldCurrencyValueList && bluFieldCurrencyValueList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluFieldCurrencyValue.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluFieldCurrencyValue.currency">Currency</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluFieldCurrencyValue.bluField">Blu Field</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluFieldCurrencyValue.bluFormData">Blu Form Data</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bluFieldCurrencyValueList.map((bluFieldCurrencyValue, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${bluFieldCurrencyValue.id}`} color="link" size="sm">
                      {bluFieldCurrencyValue.id}
                    </Button>
                  </td>
                  <td>{bluFieldCurrencyValue.value}</td>
                  <td>{bluFieldCurrencyValue.currency}</td>
                  <td>
                    {bluFieldCurrencyValue.bluField ? (
                      <Link to={`blu-field/${bluFieldCurrencyValue.bluField.id}`}>{bluFieldCurrencyValue.bluField.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {bluFieldCurrencyValue.bluFormData ? (
                      <Link to={`blu-form-data/${bluFieldCurrencyValue.bluFormData.id}`}>{bluFieldCurrencyValue.bluFormData.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bluFieldCurrencyValue.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bluFieldCurrencyValue.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bluFieldCurrencyValue.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="emulatorApp.bluFieldCurrencyValue.home.notFound">No Blu Field Currency Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ bluFieldCurrencyValue }: IRootState) => ({
  bluFieldCurrencyValueList: bluFieldCurrencyValue.entities,
  loading: bluFieldCurrencyValue.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFieldCurrencyValue);
