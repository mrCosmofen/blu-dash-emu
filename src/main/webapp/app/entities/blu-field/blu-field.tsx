import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './blu-field.reducer';
import { IBluField } from 'app/shared/model/blu-field.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBluFieldProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const BluField = (props: IBluFieldProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { bluFieldList, match, loading } = props;
  return (
    <div>
      <h2 id="blu-field-heading">
        <Translate contentKey="emulatorApp.bluField.home.title">Blu Fields</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="emulatorApp.bluField.home.createLabel">Create new Blu Field</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {bluFieldList && bluFieldList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluField.key">Key</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluField.label">Label</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluField.dataType">Data Type</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluField.dataFormat">Data Format</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluField.fieldValues">Field Values</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluField.bluForm">Blu Form</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bluFieldList.map((bluField, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${bluField.id}`} color="link" size="sm">
                      {bluField.id}
                    </Button>
                  </td>
                  <td>{bluField.key}</td>
                  <td>{bluField.label}</td>
                  <td>
                    <Translate contentKey={`emulatorApp.BluFieldType.${bluField.dataType}`} />
                  </td>
                  <td>{bluField.dataFormat}</td>
                  <td>{bluField.fieldValues}</td>
                  <td>{bluField.bluForm ? <Link to={`blu-form/${bluField.bluForm.id}`}>{bluField.bluForm.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bluField.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bluField.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bluField.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="emulatorApp.bluField.home.notFound">No Blu Fields found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ bluField }: IRootState) => ({
  bluFieldList: bluField.entities,
  loading: bluField.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluField);
