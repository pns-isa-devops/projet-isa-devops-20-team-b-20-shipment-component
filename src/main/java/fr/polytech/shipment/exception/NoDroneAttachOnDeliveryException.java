package fr.polytech.shipment.exception;

import javax.xml.ws.WebFault;
import java.io.Serializable;

@WebFault(targetNamespace = "http://www.polytech.unice.fr/si/4a/isa/dronedelivery/drone")
public class NoDroneAttachOnDeliveryException extends Exception implements Serializable {

    private static final long serialVersionUID = -9563360531378864L;
    private String deliveryId;

    public NoDroneAttachOnDeliveryException(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    @Override
    public String getMessage() {
        return "Delivery " + deliveryId + " as no drone attached to him";
    }

}
