import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IQuery } from 'app/shared/model/query.model';
import { getEntities as getQueries } from 'app/entities/query/query.reducer';
import { getEntity, updateEntity, createEntity, reset } from './record.reducer';
import { IRecord } from 'app/shared/model/record.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRecordUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecordUpdate = (props: IRecordUpdateProps) => {
  const [queryId, setQueryId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { recordEntity, queries, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/record');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getQueries();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...recordEntity,
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
          <h2 id="emulatorApp.record.home.createOrEditLabel">
            <Translate contentKey="emulatorApp.record.home.createOrEditLabel">Create or edit a Record</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : recordEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="record-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="record-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="recordIdLabel" for="record-recordId">
                  <Translate contentKey="emulatorApp.record.recordId">Record Id</Translate>
                </Label>
                <AvField id="record-recordId" type="string" className="form-control" name="recordId" />
              </AvGroup>
              <AvGroup>
                <Label for="record-query">
                  <Translate contentKey="emulatorApp.record.query">Query</Translate>
                </Label>
                <AvInput id="record-query" type="select" className="form-control" name="query.id">
                  <option value="" key="0" />
                  {queries
                    ? queries.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/record" replace color="info">
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
  queries: storeState.query.entities,
  recordEntity: storeState.record.entity,
  loading: storeState.record.loading,
  updating: storeState.record.updating,
  updateSuccess: storeState.record.updateSuccess
});

const mapDispatchToProps = {
  getQueries,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecordUpdate);
