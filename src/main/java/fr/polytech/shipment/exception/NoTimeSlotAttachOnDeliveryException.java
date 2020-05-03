package fr.polytech.shipment.exception;

import java.io.Serializable;

import javax.xml.ws.WebFault;

@WebFault(targetNamespace = "http://www.polytech.unice.fr/si/4a/isa/dronedelivery/drone")
public class NoTimeSlotAttachOnDeliveryException extends Exception implements Serializable {

    private static final long serialVersionUID = -9563360531378864L;
    private String droneId;

    public NoTimeSlotAttachOnDeliveryException(String droneId) {
        this.droneId = droneId;
    }

    @Override
    public String getMessage() {
        return "Drone " + droneId + " as no Timeslot attached to him";
    }

}
