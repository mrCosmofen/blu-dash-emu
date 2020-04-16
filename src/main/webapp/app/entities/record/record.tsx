import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './record.reducer';
import { IRecord } from 'app/shared/model/record.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecordProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Record = (props: IRecordProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { recordList, match, loading } = props;
  return (
    <div>
      <h2 id="record-heading">
        <Translate contentKey="emulatorApp.record.home.title">Records</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="emulatorApp.record.home.createLabel">Create new Record</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {recordList && recordList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.record.recordId">Record Id</Translate>
                </th>
                <th>
                  <Translate contentKey="emulatorApp.record.query">Query</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {recordList.map((record, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${record.id}`} color="link" size="sm">
                      {record.id}
                    </Button>
                  </td>
                  <td>{record.recordId}</td>
                  <td>{record.query ? <Link to={`query/${record.query.id}`}>{record.query.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${record.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${record.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${record.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="emulatorApp.record.home.notFound">No Records found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ record }: IRootState) => ({
  recordList: record.entities,
  loading: record.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Record);
