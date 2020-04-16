import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './record.reducer';
import { IRecord } from 'app/shared/model/record.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecordDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecordDetail = (props: IRecordDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { recordEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emulatorApp.record.detail.title">Record</Translate> [<b>{recordEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="recordId">
              <Translate contentKey="emulatorApp.record.recordId">Record Id</Translate>
            </span>
          </dt>
          <dd>{recordEntity.recordId}</dd>
          <dt>
            <Translate contentKey="emulatorApp.record.query">Query</Translate>
          </dt>
          <dd>{recordEntity.query ? recordEntity.query.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/record" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/record/${recordEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ record }: IRootState) => ({
  recordEntity: record.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecordDetail);
