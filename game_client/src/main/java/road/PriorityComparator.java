package road;

import cars.Car;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Car> {
    @Override
    public int compare(Car car1, Car car2) {
        return car1.getPriority().compareTo(car2.getPriority());
    }
}
