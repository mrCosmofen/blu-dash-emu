import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './query-data.reducer';
import { IQueryData } from 'app/shared/model/query-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQueryDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QueryDataDetail = (props: IQueryDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { queryDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emulatorApp.queryData.detail.title">QueryData</Translate> [<b>{queryDataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="recordId">
              <Translate contentKey="emulatorApp.queryData.recordId">Record Id</Translate>
            </span>
          </dt>
          <dd>{queryDataEntity.recordId}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="emulatorApp.queryData.value">Value</Translate>
            </span>
          </dt>
          <dd>{queryDataEntity.value}</dd>
          <dt>
            <Translate contentKey="emulatorApp.queryData.dataModel">Data Model</Translate>
          </dt>
          <dd>{queryDataEntity.dataModel ? queryDataEntity.dataModel.id : ''}</dd>
          <dt>
            <Translate contentKey="emulatorApp.queryData.record">Record</Translate>
          </dt>
          <dd>{queryDataEntity.record ? queryDataEntity.record.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/query-data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/query-data/${queryDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ queryData }: IRootState) => ({
  queryDataEntity: queryData.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QueryDataDetail);
