/**
 * Shields are used to protect the ship from enemy fire
 */

package src.object;

public final class Shield {
    
    private boolean active = false;
    private int quantity = 5;

    public void destroy(){
        quantity -= 1;
    }

    public boolean isActive(){
        return active;
    }

    public void enable(){
        active = true;
    }

    public void disable(){
        active = false;
    }

    public int getQuantity() {
        return quantity;
    }

    public void add(){
        quantity += 1;
    }
    
}