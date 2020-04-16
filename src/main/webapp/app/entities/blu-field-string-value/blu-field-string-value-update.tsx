import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBluField } from 'app/shared/model/blu-field.model';
import { getEntities as getBluFields } from 'app/entities/blu-field/blu-field.reducer';
import { IBluFormData } from 'app/shared/model/blu-form-data.model';
import { getEntities as getBluFormData } from 'app/entities/blu-form-data/blu-form-data.reducer';
import { getEntity, updateEntity, createEntity, reset } from './blu-field-string-value.reducer';
import { IBluFieldStringValue } from 'app/shared/model/blu-field-string-value.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBluFieldStringValueUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFieldStringValueUpdate = (props: IBluFieldStringValueUpdateProps) => {
  const [bluFieldId, setBluFieldId] = useState('0');
  const [bluFormDataId, setBluFormDataId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { bluFieldStringValueEntity, bluFields, bluFormData, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/blu-field-string-value');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getBluFields();
    props.getBluFormData();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...bluFieldStringValueEntity,
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
          <h2 id="emulatorApp.bluFieldStringValue.home.createOrEditLabel">
            <Translate contentKey="emulatorApp.bluFieldStringValue.home.createOrEditLabel">Create or edit a BluFieldStringValue</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bluFieldStringValueEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="blu-field-string-value-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="blu-field-string-value-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="valueLabel" for="blu-field-string-value-value">
                  <Translate contentKey="emulatorApp.bluFieldStringValue.value">Value</Translate>
                </Label>
                <AvField id="blu-field-string-value-value" type="text" name="value" />
              </AvGroup>
              <AvGroup>
                <Label for="blu-field-string-value-bluField">
                  <Translate contentKey="emulatorApp.bluFieldStringValue.bluField">Blu Field</Translate>
                </Label>
                <AvInput id="blu-field-string-value-bluField" type="select" className="form-control" name="bluField.id">
                  <option value="" key="0" />
                  {bluFields
                    ? bluFields.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="blu-field-string-value-bluFormData">
                  <Translate contentKey="emulatorApp.bluFieldStringValue.bluFormData">Blu Form Data</Translate>
                </Label>
                <AvInput id="blu-field-string-value-bluFormData" type="select" className="form-control" name="bluFormData.id">
                  <option value="" key="0" />
                  {bluFormData
                    ? bluFormData.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/blu-field-string-value" replace color="info">
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
  bluFields: storeState.bluField.entities,
  bluFormData: storeState.bluFormData.entities,
  bluFieldStringValueEntity: storeState.bluFieldStringValue.entity,
  loading: storeState.bluFieldStringValue.loading,
  updating: storeState.bluFieldStringValue.updating,
  updateSuccess: storeState.bluFieldStringValue.updateSuccess
});

const mapDispatchToProps = {
  getBluFields,
  getBluFormData,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFieldStringValueUpdate);
