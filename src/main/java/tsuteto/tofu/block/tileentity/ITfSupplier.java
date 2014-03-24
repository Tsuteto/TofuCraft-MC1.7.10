package tsuteto.tofu.block.tileentity;

/**
 * For implementation of the tf machine that provides Tofu Force
 * 
 * @author Tsuteto
 *
 */
public interface ITfSupplier
{
    /**
     * Maximum TF amount the machine can offer this tick.
     * 
     * @return tf amount offered this tick
     */
    float getMaxTfOffered();
    
    /**
     * Draws tf amount specified from the machine
     * @param amount tf amount to draw. It can be negative when charging.
     */
    void drawTf(float amount);
}
