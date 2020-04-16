import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDataModel } from 'app/shared/model/data-model.model';
import { getEntities as getDataModels } from 'app/entities/data-model/data-model.reducer';
import { IRecord } from 'app/shared/model/record.model';
import { getEntities as getRecords } from 'app/entities/record/record.reducer';
import { getEntity, updateEntity, createEntity, reset } from './query-data.reducer';
import { IQueryData } from 'app/shared/model/query-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IQueryDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QueryDataUpdate = (props: IQueryDataUpdateProps) => {
  const [dataModelId, setDataModelId] = useState('0');
  const [recordId, setRecordId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { queryDataEntity, dataModels, records, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/query-data');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getDataModels();
    props.getRecords();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...queryDataEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="emulatorApp.queryData.home.createOrEditLabel">
            <Translate contentKey="emulatorApp.queryData.home.createOrEditLabel">Create or edit a QueryData</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : queryDataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="query-data-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="query-data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="recordIdLabel" for="query-data-recordId">
                  <Translate contentKey="emulatorApp.queryData.recordId">Record Id</Translate>
                </Label>
                <AvField id="query-data-recordId" type="text" name="recordId" />
              </AvGroup>
              <AvGroup>
                <Label id="valueLabel" for="query-data-value">
                  <Translate contentKey="emulatorApp.queryData.value">Value</Translate>
                </Label>
                <AvField id="query-data-value" type="text" name="value" />
              </AvGroup>
              <AvGroup>
                <Label for="query-data-dataModel">
                  <Translate contentKey="emulatorApp.queryData.dataModel">Data Model</Translate>
                </Label>
                <AvInput id="query-data-dataModel" type="select" className="form-control" name="dataModel.id">
                  <option value="" key="0" />
                  {dataModels
                    ? dataModels.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="query-data-record">
                  <Translate contentKey="emulatorApp.queryData.record">Record</Translate>
                </Label>
                <AvInput id="query-data-record" type="select" className="form-control" name="record.id">
                  <option value="" key="0" />
                  {records
                    ? records.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/query-data" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  dataModels: storeState.dataModel.entities,
  records: storeState.record.entities,
  queryDataEntity: storeState.queryData.entity,
  loading: storeState.queryData.loading,
  updating: storeState.queryData.updating,
  updateSuccess: storeState.queryData.updateSuccess
});

const mapDispatchToProps = {
  getDataModels,
  getRecords,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QueryDataUpdate);
