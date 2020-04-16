import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './blu-form-data.reducer';
import { IBluFormData } from 'app/shared/model/blu-form-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBluFormDataProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const BluFormData = (props: IBluFormDataProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { bluFormDataList, match, loading } = props;
  return (
    <div>
      <h2 id="blu-form-data-heading">
        <Translate contentKey="emulatorApp.bluFormData.home.title">Blu Form Data</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="emulatorApp.bluFormData.home.createLabel">Create new Blu Form Data</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {bluFormDataList && bluFormDataList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluFormData.formKey">Form Key</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluFormData.retrieved">Retrieved</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.bluFormData.bluForm">Blu Form</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bluFormDataList.map((bluFormData, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${bluFormData.id}`} color="link" size="sm">
                      {bluFormData.id}
                    </Button>
                  </td>
                  <td>{bluFormData.formKey}</td>
                  <td>{bluFormData.retrieved}</td>
                  <td>{bluFormData.bluForm ? <Link to={`blu-form/${bluFormData.bluForm.id}`}>{bluFormData.bluForm.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bluFormData.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bluFormData.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bluFormData.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="emulatorApp.bluFormData.home.notFound">No Blu Form Data found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ bluFormData }: IRootState) => ({
  bluFormDataList: bluFormData.entities,
  loading: bluFormData.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFormData);
