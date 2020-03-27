package arquillian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import fr.polytech.shipment.components.DeliveryInitializer;

/**
 * AbstractTCFTest
 */
public class AbstractShipmentTest {

    @Deployment
    public static WebArchive createDeployment() {
        // @formatter:off
        return ShrinkWrap.create(WebArchive.class)
                // Components and Interfaces
                .addPackage(DeliveryInitializer.class.getPackage())
                // libraries
                .addAsLibraries(Maven.resolver()
                            .loadPomFromFile("pom.xml")
                            .importRuntimeDependencies()
                            .resolve()
                            .withTransitivity()
                            .asFile());
    }
}
