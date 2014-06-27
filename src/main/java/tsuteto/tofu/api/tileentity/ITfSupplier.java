package tsuteto.tofu.api.tileentity;

/**
 * For implementation of TF machines that provide Tofu Force
 * 
 * @author Tsuteto
 *
 */
public interface ITfSupplier
{
    /**
     * Maximum TF amount the machine can offer this tick.
     * 
     * @return Max tf amount offered this tick
     */
    double getMaxTfOffered();
    
    /**
     * Draws TF amount specified from the machine
     *
     * @param amount tf amount to draw. It can be negative when charging.
     */
    void drawTf(double amount);
}
