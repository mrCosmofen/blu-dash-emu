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
import { getEntity, updateEntity, createEntity, reset } from './blu-field-number-value.reducer';
import { IBluFieldNumberValue } from 'app/shared/model/blu-field-number-value.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBluFieldNumberValueUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFieldNumberValueUpdate = (props: IBluFieldNumberValueUpdateProps) => {
  const [bluFieldId, setBluFieldId] = useState('0');
  const [bluFormDataId, setBluFormDataId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { bluFieldNumberValueEntity, bluFields, bluFormData, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/blu-field-number-value');
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
        ...bluFieldNumberValueEntity,
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
          <h2 id="emulatorApp.bluFieldNumberValue.home.createOrEditLabel">
            <Translate contentKey="emulatorApp.bluFieldNumberValue.home.createOrEditLabel">Create or edit a BluFieldNumberValue</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bluFieldNumberValueEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="blu-field-number-value-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="blu-field-number-value-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="valueLabel" for="blu-field-number-value-value">
                  <Translate contentKey="emulatorApp.bluFieldNumberValue.value">Value</Translate>
                </Label>
                <AvField id="blu-field-number-value-value" type="string" className="form-control" name="value" />
              </AvGroup>
              <AvGroup>
                <Label for="blu-field-number-value-bluField">
                  <Translate contentKey="emulatorApp.bluFieldNumberValue.bluField">Blu Field</Translate>
                </Label>
                <AvInput id="blu-field-number-value-bluField" type="select" className="form-control" name="bluField.id">
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
                <Label for="blu-field-number-value-bluFormData">
                  <Translate contentKey="emulatorApp.bluFieldNumberValue.bluFormData">Blu Form Data</Translate>
                </Label>
                <AvInput id="blu-field-number-value-bluFormData" type="select" className="form-control" name="bluFormData.id">
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
              <Button tag={Link} id="cancel-save" to="/blu-field-number-value" replace color="info">
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
  bluFieldNumberValueEntity: storeState.bluFieldNumberValue.entity,
  loading: storeState.bluFieldNumberValue.loading,
  updating: storeState.bluFieldNumberValue.updating,
  updateSuccess: storeState.bluFieldNumberValue.updateSuccess
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

export default connect(mapStateToProps, mapDispatchToProps)(BluFieldNumberValueUpdate);
