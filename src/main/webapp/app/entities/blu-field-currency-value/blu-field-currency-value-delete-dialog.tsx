import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBluFieldCurrencyValue } from 'app/shared/model/blu-field-currency-value.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './blu-field-currency-value.reducer';

export interface IBluFieldCurrencyValueDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFieldCurrencyValueDeleteDialog = (props: IBluFieldCurrencyValueDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/blu-field-currency-value');
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.bluFieldCurrencyValueEntity.id);
  };

  const { bluFieldCurrencyValueEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="emulatorApp.bluFieldCurrencyValue.delete.question">
        <Translate contentKey="emulatorApp.bluFieldCurrencyValue.delete.question" interpolate={{ id: bluFieldCurrencyValueEntity.id }}>
          Are you sure you want to delete this BluFieldCurrencyValue?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-bluFieldCurrencyValue" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ bluFieldCurrencyValue }: IRootState) => ({
  bluFieldCurrencyValueEntity: bluFieldCurrencyValue.entity,
  updateSuccess: bluFieldCurrencyValue.updateSuccess
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFieldCurrencyValueDeleteDialog);
