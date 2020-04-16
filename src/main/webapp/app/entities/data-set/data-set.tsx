import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './data-set.reducer';
import { IDataSet } from 'app/shared/model/data-set.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDataSetProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const DataSet = (props: IDataSetProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { dataSetList, match, loading } = props;
  return (
    <div>
      <h2 id="data-set-heading">
        <Translate contentKey="emulatorApp.dataSet.home.title">Data Sets</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="emulatorApp.dataSet.home.createLabel">Create new Data Set</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {dataSetList && dataSetList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.dataSet.key">Key</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.dataSet.label">Label</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.dataSet.landingPage">Landing Page</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {dataSetList.map((dataSet, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${dataSet.id}`} color="link" size="sm">
                      {dataSet.id}
                    </Button>
                  </td>
                  <td>{dataSet.key}</td>
                  <td>{dataSet.label}</td>
                  <td>{dataSet.landingPage}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${dataSet.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${dataSet.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${dataSet.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="emulatorApp.dataSet.home.notFound">No Data Sets found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ dataSet }: IRootState) => ({
  dataSetList: dataSet.entities,
  loading: dataSet.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DataSet);
