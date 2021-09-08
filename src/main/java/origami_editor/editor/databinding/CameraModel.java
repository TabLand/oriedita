package origami_editor.editor.databinding;

import origami_editor.graphic2d.oritacalc.OritaCalc;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CameraModel {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private double rotation;
    private double scale;

    public CameraModel() {
        reset();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public void reset() {
        scale = 1.0;
        rotation = 0.0;

        this.pcs.firePropertyChange(null, null, null);
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        double oldRotation = this.rotation;
        this.rotation = OritaCalc.angle_between_m180_180(rotation);
        this.pcs.firePropertyChange("rotation", oldRotation, this.rotation);
    }

    public void increaseRotation() {
        setRotation(rotation + 11.25);
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        double oldScale = this.scale;
        this.scale = Math.max(scale, 0.0);
        this.pcs.firePropertyChange("scale", oldScale, scale);
    }

    public void zoomIn() {
        setScale(scale * Math.sqrt(Math.sqrt(Math.sqrt(2))));
    }

    public void zoomOut() {
        setScale(scale / Math.sqrt(Math.sqrt(Math.sqrt(2))));
    }

    public void decreaseRotation() {
        setRotation(rotation - 11.25);
    }
}