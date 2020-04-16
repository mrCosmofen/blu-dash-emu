import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './blu-field-number-value.reducer';
import { IBluFieldNumberValue } from 'app/shared/model/blu-field-number-value.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBluFieldNumberValueProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const BluFieldNumberValue = (props: IBluFieldNumberValueProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { bluFieldNumberValueList, match, loading } = props;
  return (
    <div>
      <h2 id="blu-field-number-value-heading">
        <Translate contentKey="emulatorApp.bluFieldNumberValue.home.title">Blu Field Number Values</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="emulatorApp.bluFieldNumberValue.home.createLabel">Create new Blu Field Number Value</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {bluFieldNumberValueList && bluFieldNumberValueList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluFieldNumberValue.fieldValue">Field Value</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluFieldNumberValue.bluField">Blu Field</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluFieldNumberValue.bluFormData">Blu Form Data</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bluFieldNumberValueList.map((bluFieldNumberValue, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${bluFieldNumberValue.id}`} color="link" size="sm">
                      {bluFieldNumberValue.id}
                    </Button>
                  </td>
                  <td>{bluFieldNumberValue.fieldValue}</td>
                  <td>
                    {bluFieldNumberValue.bluField ? (
                      <Link to={`blu-field/${bluFieldNumberValue.bluField.id}`}>{bluFieldNumberValue.bluField.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {bluFieldNumberValue.bluFormData ? (
                      <Link to={`blu-form-data/${bluFieldNumberValue.bluFormData.id}`}>{bluFieldNumberValue.bluFormData.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bluFieldNumberValue.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bluFieldNumberValue.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bluFieldNumberValue.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="emulatorApp.bluFieldNumberValue.home.notFound">No Blu Field Number Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ bluFieldNumberValue }: IRootState) => ({
  bluFieldNumberValueList: bluFieldNumberValue.entities,
  loading: bluFieldNumberValue.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFieldNumberValue);
