import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './blu-form.reducer';
import { IBluForm } from 'app/shared/model/blu-form.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBluFormUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFormUpdate = (props: IBluFormUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { bluFormEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/blu-form');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...bluFormEntity,
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
          <h2 id="emulatorApp.bluForm.home.createOrEditLabel">
            <Translate contentKey="emulatorApp.bluForm.home.createOrEditLabel">Create or edit a BluForm</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bluFormEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="blu-form-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="blu-form-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="keyLabel" for="blu-form-key">
                  <Translate contentKey="emulatorApp.bluForm.key">Key</Translate>
                </Label>
                <AvField id="blu-form-key" type="text" name="key" />
              </AvGroup>
              <AvGroup>
                <Label id="labelLabel" for="blu-form-label">
                  <Translate contentKey="emulatorApp.bluForm.label">Label</Translate>
                </Label>
                <AvField id="blu-form-label" type="text" name="label" />
              </AvGroup>
              <AvGroup>
                <Label id="categoryLabel" for="blu-form-category">
                  <Translate contentKey="emulatorApp.bluForm.category">Category</Translate>
                </Label>
                <AvField id="blu-form-category" type="text" name="category" />
              </AvGroup>
              <AvGroup>
                <Label id="productKeyLabel" for="blu-form-productKey">
                  <Translate contentKey="emulatorApp.bluForm.productKey">Product Key</Translate>
                </Label>
                <AvField id="blu-form-productKey" type="text" name="productKey" />
              </AvGroup>
              <AvGroup>
                <Label id="pollingIntervalLabel" for="blu-form-pollingInterval">
                  <Translate contentKey="emulatorApp.bluForm.pollingInterval">Polling Interval</Translate>
                </Label>
                <AvField id="blu-form-pollingInterval" type="string" className="form-control" name="pollingInterval" />
              </AvGroup>
              <AvGroup>
                <Label id="modifiedLabel" for="blu-form-modified">
                  <Translate contentKey="emulatorApp.bluForm.modified">Modified</Translate>
                </Label>
                <AvField id="blu-form-modified" type="string" className="form-control" name="modified" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/blu-form" replace color="info">
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
  bluFormEntity: storeState.bluForm.entity,
  loading: storeState.bluForm.loading,
  updating: storeState.bluForm.updating,
  updateSuccess: storeState.bluForm.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFormUpdate);
