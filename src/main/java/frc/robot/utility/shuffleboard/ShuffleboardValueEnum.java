package frc.robot.utility.shuffleboard;

public interface ShuffleboardValueEnum<T> {
    // public final ShuffleboardValue<T> shuffleboardValue;
    public ShuffleboardValue<T> getShuffleboardValue();
    public default T get() {
        return getShuffleboardValue().get();
    }
    public default void set(T value) {
        getShuffleboardValue().set(value);
    }
}