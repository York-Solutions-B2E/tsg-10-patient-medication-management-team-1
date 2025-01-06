import BasicModal from "../common/BasicModal";

const DetailsModal = ({ details, disclosure }) => (
  <BasicModal
    content={details}
    className="details-modal"
    isOpen={disclosure.isOpen}
    onClose={disclosure.onClose}
  />
);

export default DetailsModal;
