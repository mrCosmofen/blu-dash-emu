import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './query-data.reducer';
import { IQueryData } from 'app/shared/model/query-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQueryDataProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const QueryData = (props: IQueryDataProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { queryDataList, match, loading } = props;
  return (
    <div>
      <h2 id="query-data-heading">
        <Translate contentKey="emulatorApp.queryData.home.title">Query Data</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="emulatorApp.queryData.home.createLabel">Create new Query Data</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {queryDataList && queryDataList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.queryData.dataValue">Data Value</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.queryData.dataModel">Data Model</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.queryData.record">Record</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {queryDataList.map((queryData, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${queryData.id}`} color="link" size="sm">
                      {queryData.id}
                    </Button>
                  </td>
                  <td>{queryData.dataValue}</td>
                  <td>{queryData.dataModel ? <Link to={`data-model/${queryData.dataModel.id}`}>{queryData.dataModel.id}</Link> : ''}</td>
                  <td>{queryData.record ? <Link to={`record/${queryData.record.id}`}>{queryData.record.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${queryData.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${queryData.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${queryData.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="emulatorApp.queryData.home.notFound">No Query Data found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ queryData }: IRootState) => ({
  queryDataList: queryData.entities,
  loading: queryData.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QueryData);
