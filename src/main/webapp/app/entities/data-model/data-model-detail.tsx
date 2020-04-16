import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './data-model.reducer';
import { IDataModel } from 'app/shared/model/data-model.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDataModelDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DataModelDetail = (props: IDataModelDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { dataModelEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emulatorApp.dataModel.detail.title">DataModel</Translate> [<b>{dataModelEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="key">
              <Translate contentKey="emulatorApp.dataModel.key">Key</Translate>
            </span>
          </dt>
          <dd>{dataModelEntity.key}</dd>
          <dt>
            <span id="label">
              <Translate contentKey="emulatorApp.dataModel.label">Label</Translate>
            </span>
          </dt>
          <dd>{dataModelEntity.label}</dd>
          <dt>
            <span id="dataFormat">
              <Translate contentKey="emulatorApp.dataModel.dataFormat">Data Format</Translate>
            </span>
          </dt>
          <dd>{dataModelEntity.dataFormat}</dd>
          <dt>
            <span id="maxLength">
              <Translate contentKey="emulatorApp.dataModel.maxLength">Max Length</Translate>
            </span>
          </dt>
          <dd>{dataModelEntity.maxLength}</dd>
          <dt>
            <span id="precision">
              <Translate contentKey="emulatorApp.dataModel.precision">Precision</Translate>
            </span>
          </dt>
          <dd>{dataModelEntity.precision}</dd>
          <dt>
            <span id="values">
              <Translate contentKey="emulatorApp.dataModel.values">Values</Translate>
            </span>
          </dt>
          <dd>{dataModelEntity.values}</dd>
          <dt>
            <Translate contentKey="emulatorApp.dataModel.dataSet">Data Set</Translate>
          </dt>
          <dd>{dataModelEntity.dataSet ? dataModelEntity.dataSet.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/data-model" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/data-model/${dataModelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ dataModel }: IRootState) => ({
  dataModelEntity: dataModel.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DataModelDetail);
