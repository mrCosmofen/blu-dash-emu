import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './data-set.reducer';
import { IDataSet } from 'app/shared/model/data-set.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDataSetDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DataSetDetail = (props: IDataSetDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { dataSetEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emulatorApp.dataSet.detail.title">DataSet</Translate> [<b>{dataSetEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="key">
              <Translate contentKey="emulatorApp.dataSet.key">Key</Translate>
            </span>
          </dt>
          <dd>{dataSetEntity.key}</dd>
          <dt>
            <span id="label">
              <Translate contentKey="emulatorApp.dataSet.label">Label</Translate>
            </span>
          </dt>
          <dd>{dataSetEntity.label}</dd>
          <dt>
            <span id="landingPage">
              <Translate contentKey="emulatorApp.dataSet.landingPage">Landing Page</Translate>
            </span>
          </dt>
          <dd>{dataSetEntity.landingPage}</dd>
        </dl>
        <Button tag={Link} to="/data-set" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/data-set/${dataSetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ dataSet }: IRootState) => ({
  dataSetEntity: dataSet.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DataSetDetail);
