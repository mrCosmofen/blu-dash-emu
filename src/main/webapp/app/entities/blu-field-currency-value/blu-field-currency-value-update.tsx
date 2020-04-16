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
import { getEntity, updateEntity, createEntity, reset } from './blu-field-currency-value.reducer';
import { IBluFieldCurrencyValue } from 'app/shared/model/blu-field-currency-value.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBluFieldCurrencyValueUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFieldCurrencyValueUpdate = (props: IBluFieldCurrencyValueUpdateProps) => {
  const [bluFieldId, setBluFieldId] = useState('0');
  const [bluFormDataId, setBluFormDataId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { bluFieldCurrencyValueEntity, bluFields, bluFormData, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/blu-field-currency-value');
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
        ...bluFieldCurrencyValueEntity,
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
          <h2 id="emulatorApp.bluFieldCurrencyValue.home.createOrEditLabel">
            <Translate contentKey="emulatorApp.bluFieldCurrencyValue.home.createOrEditLabel">
              Create or edit a BluFieldCurrencyValue
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bluFieldCurrencyValueEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="blu-field-currency-value-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="blu-field-currency-value-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="fieldValueLabel" for="blu-field-currency-value-fieldValue">
                  <Translate contentKey="emulatorApp.bluFieldCurrencyValue.fieldValue">Field Value</Translate>
                </Label>
                <AvField id="blu-field-currency-value-fieldValue" type="string" className="form-control" name="fieldValue" />
              </AvGroup>
              <AvGroup>
                <Label id="currencyLabel" for="blu-field-currency-value-currency">
                  <Translate contentKey="emulatorApp.bluFieldCurrencyValue.currency">Currency</Translate>
                </Label>
                <AvField id="blu-field-currency-value-currency" type="text" name="currency" />
              </AvGroup>
              <AvGroup>
                <Label for="blu-field-currency-value-bluField">
                  <Translate contentKey="emulatorApp.bluFieldCurrencyValue.bluField">Blu Field</Translate>
                </Label>
                <AvInput id="blu-field-currency-value-bluField" type="select" className="form-control" name="bluField.id">
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
                <Label for="blu-field-currency-value-bluFormData">
                  <Translate contentKey="emulatorApp.bluFieldCurrencyValue.bluFormData">Blu Form Data</Translate>
                </Label>
                <AvInput id="blu-field-currency-value-bluFormData" type="select" className="form-control" name="bluFormData.id">
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
              <Button tag={Link} id="cancel-save" to="/blu-field-currency-value" replace color="info">
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
  bluFieldCurrencyValueEntity: storeState.bluFieldCurrencyValue.entity,
  loading: storeState.bluFieldCurrencyValue.loading,
  updating: storeState.bluFieldCurrencyValue.updating,
  updateSuccess: storeState.bluFieldCurrencyValue.updateSuccess
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

export default connect(mapStateToProps, mapDispatchToProps)(BluFieldCurrencyValueUpdate);
