import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './blu-field-string-value.reducer';
import { IBluFieldStringValue } from 'app/shared/model/blu-field-string-value.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBluFieldStringValueDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFieldStringValueDetail = (props: IBluFieldStringValueDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bluFieldStringValueEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emulatorApp.bluFieldStringValue.detail.title">BluFieldStringValue</Translate> [
          <b>{bluFieldStringValueEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="fieldValue">
              <Translate contentKey="emulatorApp.bluFieldStringValue.fieldValue">Field Value</Translate>
            </span>
          </dt>
          <dd>{bluFieldStringValueEntity.fieldValue}</dd>
          <dt>
            <Translate contentKey="emulatorApp.bluFieldStringValue.bluField">Blu Field</Translate>
          </dt>
          <dd>{bluFieldStringValueEntity.bluField ? bluFieldStringValueEntity.bluField.id : ''}</dd>
          <dt>
            <Translate contentKey="emulatorApp.bluFieldStringValue.bluFormData">Blu Form Data</Translate>
          </dt>
          <dd>{bluFieldStringValueEntity.bluFormData ? bluFieldStringValueEntity.bluFormData.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/blu-field-string-value" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blu-field-string-value/${bluFieldStringValueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bluFieldStringValue }: IRootState) => ({
  bluFieldStringValueEntity: bluFieldStringValue.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFieldStringValueDetail);
