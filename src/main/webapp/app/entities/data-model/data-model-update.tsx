import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IQueryData } from 'app/shared/model/query-data.model';
import { getEntities as getQueryData } from 'app/entities/query-data/query-data.reducer';
import { IDataSet } from 'app/shared/model/data-set.model';
import { getEntities as getDataSets } from 'app/entities/data-set/data-set.reducer';
import { getEntity, updateEntity, createEntity, reset } from './data-model.reducer';
import { IDataModel } from 'app/shared/model/data-model.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDataModelUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DataModelUpdate = (props: IDataModelUpdateProps) => {
  const [queryDataId, setQueryDataId] = useState('0');
  const [dataSetId, setDataSetId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { dataModelEntity, queryData, dataSets, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/data-model');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getQueryData();
    props.getDataSets();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...dataModelEntity,
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
          <h2 id="emulatorApp.dataModel.home.createOrEditLabel">
            <Translate contentKey="emulatorApp.dataModel.home.createOrEditLabel">Create or edit a DataModel</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : dataModelEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="data-model-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="data-model-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="keyLabel" for="data-model-key">
                  <Translate contentKey="emulatorApp.dataModel.key">Key</Translate>
                </Label>
                <AvField id="data-model-key" type="text" name="key" />
              </AvGroup>
              <AvGroup>
                <Label id="labelLabel" for="data-model-label">
                  <Translate contentKey="emulatorApp.dataModel.label">Label</Translate>
                </Label>
                <AvField id="data-model-label" type="text" name="label" />
              </AvGroup>
              <AvGroup>
                <Label id="dataFormatLabel" for="data-model-dataFormat">
                  <Translate contentKey="emulatorApp.dataModel.dataFormat">Data Format</Translate>
                </Label>
                <AvField id="data-model-dataFormat" type="text" name="dataFormat" />
              </AvGroup>
              <AvGroup>
                <Label id="maxLengthLabel" for="data-model-maxLength">
                  <Translate contentKey="emulatorApp.dataModel.maxLength">Max Length</Translate>
                </Label>
                <AvField id="data-model-maxLength" type="string" className="form-control" name="maxLength" />
              </AvGroup>
              <AvGroup>
                <Label id="precisionLabel" for="data-model-precision">
                  <Translate contentKey="emulatorApp.dataModel.precision">Precision</Translate>
                </Label>
                <AvField id="data-model-precision" type="string" className="form-control" name="precision" />
              </AvGroup>
              <AvGroup>
                <Label id="modelValuesLabel" for="data-model-modelValues">
                  <Translate contentKey="emulatorApp.dataModel.modelValues">Model Values</Translate>
                </Label>
                <AvInput
                  id="data-model-modelValues"
                  type="select"
                  className="form-control"
                  name="modelValues"
                  value={(!isNew && dataModelEntity.modelValues) || 'WITHDRAWN'}
                >
                  <option value="WITHDRAWN">{translate('emulatorApp.Status.WITHDRAWN')}</option>
                  <option value="CARRIER_REJECTED">{translate('emulatorApp.Status.CARRIER_REJECTED')}</option>
                  <option value="TENDER_BYPASSED">{translate('emulatorApp.Status.TENDER_BYPASSED')}</option>
                  <option value="SPOTMARKET">{translate('emulatorApp.Status.SPOTMARKET')}</option>
                  <option value="OLD">{translate('emulatorApp.Status.OLD')}</option>
                  <option value="EXPIRED">{translate('emulatorApp.Status.EXPIRED')}</option>
                  <option value="PENDING">{translate('emulatorApp.Status.PENDING')}</option>
                  <option value="LOAD_REMOVED">{translate('emulatorApp.Status.LOAD_REMOVED')}</option>
                  <option value="VOIDED">{translate('emulatorApp.Status.VOIDED')}</option>
                  <option value="COMPLETED">{translate('emulatorApp.Status.COMPLETED')}</option>
                  <option value="ACCEPTED">{translate('emulatorApp.Status.ACCEPTED')}</option>
                  <option value="SHIPPER_REJECTED">{translate('emulatorApp.Status.SHIPPER_REJECTED')}</option>
                  <option value="CANCELED">{translate('emulatorApp.Status.CANCELED')}</option>
                  <option value="TENDERED">{translate('emulatorApp.Status.TENDERED')}</option>
                  <option value="NO_OFFERS">{translate('emulatorApp.Status.NO_OFFERS')}</option>
                  <option value="REMOVED">{translate('emulatorApp.Status.REMOVED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="data-model-dataSet">
                  <Translate contentKey="emulatorApp.dataModel.dataSet">Data Set</Translate>
                </Label>
                <AvInput id="data-model-dataSet" type="select" className="form-control" name="dataSet.id">
                  <option value="" key="0" />
                  {dataSets
                    ? dataSets.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/data-model" replace color="info">
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
  queryData: storeState.queryData.entities,
  dataSets: storeState.dataSet.entities,
  dataModelEntity: storeState.dataModel.entity,
  loading: storeState.dataModel.loading,
  updating: storeState.dataModel.updating,
  updateSuccess: storeState.dataModel.updateSuccess
});

const mapDispatchToProps = {
  getQueryData,
  getDataSets,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DataModelUpdate);
