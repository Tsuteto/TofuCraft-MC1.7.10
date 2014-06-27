package tsuteto.tofu.api.tileentity;

/**
 * For implementation of TF-powered machines
 *
 * @author Tsuteto
 *
 */
public interface ITfConsumer
{
    /**
     * Maximum TF amount the machine can charge this tick.
     *
     * @return Max tf amount to charge this tick
     */
    double getMaxTfCapacity();


    /**
     * How much TF the machine can consume this tick.
     *
     * @return tf amount consumed
     */
    double getCurrentTfConsumed();


    /**
     * Charges TF amount specified to the machine
     *
     * @param amount tf amount to charge.
     */
    void chargeTf(double amount);
}
