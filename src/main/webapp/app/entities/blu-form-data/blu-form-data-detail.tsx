import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './blu-form-data.reducer';
import { IBluFormData } from 'app/shared/model/blu-form-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBluFormDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFormDataDetail = (props: IBluFormDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bluFormDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emulatorApp.bluFormData.detail.title">BluFormData</Translate> [<b>{bluFormDataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="formKey">
              <Translate contentKey="emulatorApp.bluFormData.formKey">Form Key</Translate>
            </span>
          </dt>
          <dd>{bluFormDataEntity.formKey}</dd>
          <dt>
            <span id="retrieved">
              <Translate contentKey="emulatorApp.bluFormData.retrieved">Retrieved</Translate>
            </span>
          </dt>
          <dd>{bluFormDataEntity.retrieved}</dd>
          <dt>
            <Translate contentKey="emulatorApp.bluFormData.bluForm">Blu Form</Translate>
          </dt>
          <dd>{bluFormDataEntity.bluForm ? bluFormDataEntity.bluForm.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/blu-form-data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blu-form-data/${bluFormDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bluFormData }: IRootState) => ({
  bluFormDataEntity: bluFormData.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFormDataDetail);
