import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBluForm } from 'app/shared/model/blu-form.model';
import { getEntities as getBluForms } from 'app/entities/blu-form/blu-form.reducer';
import { getEntity, updateEntity, createEntity, reset } from './blu-field.reducer';
import { IBluField } from 'app/shared/model/blu-field.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBluFieldUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFieldUpdate = (props: IBluFieldUpdateProps) => {
  const [bluFormId, setBluFormId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { bluFieldEntity, bluForms, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/blu-field');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getBluForms();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...bluFieldEntity,
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
          <h2 id="emulatorApp.bluField.home.createOrEditLabel">
            <Translate contentKey="emulatorApp.bluField.home.createOrEditLabel">Create or edit a BluField</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bluFieldEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="blu-field-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="blu-field-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="keyLabel" for="blu-field-key">
                  <Translate contentKey="emulatorApp.bluField.key">Key</Translate>
                </Label>
                <AvField id="blu-field-key" type="text" name="key" />
              </AvGroup>
              <AvGroup>
                <Label id="labelLabel" for="blu-field-label">
                  <Translate contentKey="emulatorApp.bluField.label">Label</Translate>
                </Label>
                <AvField id="blu-field-label" type="text" name="label" />
              </AvGroup>
              <AvGroup>
                <Label id="dataTypeLabel" for="blu-field-dataType">
                  <Translate contentKey="emulatorApp.bluField.dataType">Data Type</Translate>
                </Label>
                <AvInput
                  id="blu-field-dataType"
                  type="select"
                  className="form-control"
                  name="dataType"
                  value={(!isNew && bluFieldEntity.dataType) || 'ENUM'}
                >
                  <option value="ENUM">{translate('emulatorApp.BluFieldType.ENUM')}</option>
                  <option value="NUMBER">{translate('emulatorApp.BluFieldType.NUMBER')}</option>
                  <option value="PERCENT">{translate('emulatorApp.BluFieldType.PERCENT')}</option>
                  <option value="TEXT">{translate('emulatorApp.BluFieldType.TEXT')}</option>
                  <option value="DATETIME">{translate('emulatorApp.BluFieldType.DATETIME')}</option>
                  <option value="CURRENCY">{translate('emulatorApp.BluFieldType.CURRENCY')}</option>
                  <option value="LATLONG">{translate('emulatorApp.BluFieldType.LATLONG')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="dataFormatLabel" for="blu-field-dataFormat">
                  <Translate contentKey="emulatorApp.bluField.dataFormat">Data Format</Translate>
                </Label>
                <AvField id="blu-field-dataFormat" type="text" name="dataFormat" />
              </AvGroup>
              <AvGroup>
                <Label id="valuesLabel" for="blu-field-values">
                  <Translate contentKey="emulatorApp.bluField.values">Values</Translate>
                </Label>
                <AvField id="blu-field-values" type="text" name="values" />
              </AvGroup>
              <AvGroup>
                <Label for="blu-field-bluForm">
                  <Translate contentKey="emulatorApp.bluField.bluForm">Blu Form</Translate>
                </Label>
                <AvInput id="blu-field-bluForm" type="select" className="form-control" name="bluForm.id">
                  <option value="" key="0" />
                  {bluForms
                    ? bluForms.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/blu-field" replace color="info">
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
  bluForms: storeState.bluForm.entities,
  bluFieldEntity: storeState.bluField.entity,
  loading: storeState.bluField.loading,
  updating: storeState.bluField.updating,
  updateSuccess: storeState.bluField.updateSuccess
});

const mapDispatchToProps = {
  getBluForms,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFieldUpdate);
