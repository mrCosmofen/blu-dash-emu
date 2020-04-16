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
import { getEntity, updateEntity, createEntity, reset } from './blu-form-data.reducer';
import { IBluFormData } from 'app/shared/model/blu-form-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBluFormDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFormDataUpdate = (props: IBluFormDataUpdateProps) => {
  const [bluFormId, setBluFormId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { bluFormDataEntity, bluForms, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/blu-form-data');
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
        ...bluFormDataEntity,
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
          <h2 id="emulatorApp.bluFormData.home.createOrEditLabel">
            <Translate contentKey="emulatorApp.bluFormData.home.createOrEditLabel">Create or edit a BluFormData</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bluFormDataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="blu-form-data-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="blu-form-data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="formKeyLabel" for="blu-form-data-formKey">
                  <Translate contentKey="emulatorApp.bluFormData.formKey">Form Key</Translate>
                </Label>
                <AvField id="blu-form-data-formKey" type="text" name="formKey" />
              </AvGroup>
              <AvGroup>
                <Label id="retrievedLabel" for="blu-form-data-retrieved">
                  <Translate contentKey="emulatorApp.bluFormData.retrieved">Retrieved</Translate>
                </Label>
                <AvField id="blu-form-data-retrieved" type="string" className="form-control" name="retrieved" />
              </AvGroup>
              <AvGroup>
                <Label for="blu-form-data-bluForm">
                  <Translate contentKey="emulatorApp.bluFormData.bluForm">Blu Form</Translate>
                </Label>
                <AvInput id="blu-form-data-bluForm" type="select" className="form-control" name="bluForm.id">
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
              <Button tag={Link} id="cancel-save" to="/blu-form-data" replace color="info">
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
  bluFormDataEntity: storeState.bluFormData.entity,
  loading: storeState.bluFormData.loading,
  updating: storeState.bluFormData.updating,
  updateSuccess: storeState.bluFormData.updateSuccess
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

export default connect(mapStateToProps, mapDispatchToProps)(BluFormDataUpdate);
