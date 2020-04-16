import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './blu-field-value.reducer';
import { IBluFieldValue } from 'app/shared/model/blu-field-value.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBluFieldValueDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFieldValueDetail = (props: IBluFieldValueDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bluFieldValueEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emulatorApp.bluFieldValue.detail.title">BluFieldValue</Translate> [<b>{bluFieldValueEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details"></dl>
        <Button tag={Link} to="/blu-field-value" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blu-field-value/${bluFieldValueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bluFieldValue }: IRootState) => ({
  bluFieldValueEntity: bluFieldValue.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFieldValueDetail);
