import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './blu-field-string-value.reducer';
import { IBluFieldStringValue } from 'app/shared/model/blu-field-string-value.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBluFieldStringValueProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const BluFieldStringValue = (props: IBluFieldStringValueProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { bluFieldStringValueList, match, loading } = props;
  return (
    <div>
      <h2 id="blu-field-string-value-heading">
        <Translate contentKey="emulatorApp.bluFieldStringValue.home.title">Blu Field String Values</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="emulatorApp.bluFieldStringValue.home.createLabel">Create new Blu Field String Value</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {bluFieldStringValueList && bluFieldStringValueList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluFieldStringValue.fieldValue">Field Value</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluFieldStringValue.bluField">Blu Field</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluFieldStringValue.bluFormData">Blu Form Data</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bluFieldStringValueList.map((bluFieldStringValue, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${bluFieldStringValue.id}`} color="link" size="sm">
                      {bluFieldStringValue.id}
                    </Button>
                  </td>
                  <td>{bluFieldStringValue.fieldValue}</td>
                  <td>
                    {bluFieldStringValue.bluField ? (
                      <Link to={`blu-field/${bluFieldStringValue.bluField.id}`}>{bluFieldStringValue.bluField.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {bluFieldStringValue.bluFormData ? (
                      <Link to={`blu-form-data/${bluFieldStringValue.bluFormData.id}`}>{bluFieldStringValue.bluFormData.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bluFieldStringValue.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bluFieldStringValue.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bluFieldStringValue.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="emulatorApp.bluFieldStringValue.home.notFound">No Blu Field String Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ bluFieldStringValue }: IRootState) => ({
  bluFieldStringValueList: bluFieldStringValue.entities,
  loading: bluFieldStringValue.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFieldStringValue);
