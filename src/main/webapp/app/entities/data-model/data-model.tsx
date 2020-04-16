import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './data-model.reducer';
import { IDataModel } from 'app/shared/model/data-model.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDataModelProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const DataModel = (props: IDataModelProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { dataModelList, match, loading } = props;
  return (
    <div>
      <h2 id="data-model-heading">
        <Translate contentKey="emulatorApp.dataModel.home.title">Data Models</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="emulatorApp.dataModel.home.createLabel">Create new Data Model</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {dataModelList && dataModelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.dataModel.key">Key</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.dataModel.label">Label</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.dataModel.dataFormat">Data Format</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.dataModel.maxLength">Max Length</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.dataModel.precision">Precision</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.dataModel.modelValues">Model Values</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.dataModel.dataSet">Data Set</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {dataModelList.map((dataModel, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${dataModel.id}`} color="link" size="sm">
                      {dataModel.id}
                    </Button>
                  </td>
                  <td>{dataModel.key}</td>
                  <td>{dataModel.label}</td>
                  <td>{dataModel.dataFormat}</td>
                  <td>{dataModel.maxLength}</td>
                  <td>{dataModel.precision}</td>
                  <td>
                    <Translate contentKey={`emulatorApp.Status.${dataModel.modelValues}`} />
                  </td>
                  <td>{dataModel.dataSet ? <Link to={`data-set/${dataModel.dataSet.id}`}>{dataModel.dataSet.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${dataModel.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${dataModel.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${dataModel.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="emulatorApp.dataModel.home.notFound">No Data Models found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ dataModel }: IRootState) => ({
  dataModelList: dataModel.entities,
  loading: dataModel.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DataModel);
