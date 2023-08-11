/*
 * This is the entity class, it is responsible for storing the position and speed.
 * Contains Advance One Frame method that performs the logic on y position to simulate bouncing
 * Author: Jordan Kent
 * Date: June 23, 2023
 */
package cst8218.kent0037.bouncer.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Bouncer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Min(0)
    @Max (500)
    private Integer x;
    
    @NotNull
    @Min(0)
    @Max (500)
    private Integer y;
    
    @NotNull
    @Min(0)
    private Integer ySpeed;  
    
    private boolean isBounced = false;
    // Constants
    public static final int FRAME_WIDTH = 500;
    public static final int FRAME_HEIGHT = 500;
    public static final int GRAVITY_ACCEL = 1;
    public static final int DECAY_RATE = 1;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the x
     */
    public Integer getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public Integer getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * @return the ySpeed
     */
    public Integer getySpeed() {
        return ySpeed;
    }

    /**
     * @param ySpeed the ySpeed to set
     */
    public void setySpeed(Integer ySpeed) {
        this.ySpeed = ySpeed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bouncer)) {
            return false;
        }
        Bouncer other = (Bouncer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.kent0037.bouncer.entity.Bouncer[ id=" + id + " ]";
    }
    
    public void advanceOneFrame() {
        if(ySpeed <= 0){
            isBounced = false;
        }
        if (!isBounced) {
            y += ySpeed;
            ySpeed += GRAVITY_ACCEL;

            if (y >= FRAME_HEIGHT) {
                y = FRAME_HEIGHT - ySpeed;
                ySpeed -= DECAY_RATE;
                isBounced = true;
            }
        } else {
            y -= ySpeed;
            ySpeed -= DECAY_RATE;

            if (y <= 0) {
                y = 0 + ySpeed;
                ySpeed += GRAVITY_ACCEL;
                isBounced = false;
            }
        }
    }
}
