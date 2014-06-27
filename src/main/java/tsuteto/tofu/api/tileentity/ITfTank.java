package tsuteto.tofu.api.tileentity;

/**
 * For implementation of TF machines that accumulate or provide Tofu Force
 * 
 * @author Tsuteto
 *
 */
public interface ITfTank extends ITfSupplier
{
    /**
     * Maximum TF amount the machine can charge this tick.
     *
     * @return tf amount  this tick
     */
    double getMaxTfCapacity();
}
