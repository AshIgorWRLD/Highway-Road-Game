package cars;

public class CarIntersection {
    private static final int COORDINATE_CORRELATION = 40;

    public boolean carIntersectionQuestion(Car car1, Car car2, boolean isBoxToBox) {
        float xMin1 = car1.getX() + ((float) car1.getWidth()) / COORDINATE_CORRELATION;
        float xMax1 = car1.getX() + car1.getWidth();
        float yMin1 = car1.getY() + car1.getFreeSpaceH();
        float yMax1 = car1.getY() + car1.getHeight() - ((float) car1.getHeight()) / COORDINATE_CORRELATION;
        float xMin2 = car2.getX() + ((float) car2.getWidth()) / COORDINATE_CORRELATION;
        float xMax2 = car2.getX() + car2.getWidth();
        float yMin2 = car2.getY() + car2.getFreeSpaceH();
        float yMax2 = car2.getY() + car2.getHeight() - ((float) car2.getHeight()) / COORDINATE_CORRELATION;

        if (isBoxToBox) {
            xMin1 = car1.getX();
            xMin2 = car2.getX();
            xMax1 = car1.getX() + car1.getBoxToBoxWidth();
            xMax2 = car2.getX() + car2.getBoxToBoxWidth();
            yMax1 = car1.getY() + car1.getHeight();
            yMax2 = car2.getY() + car2.getHeight();
        }
        boolean yCheck = (yMin1 <= yMin2) && (yMin2 <= yMax1) || (yMin1 <= yMax2) && (yMax2 <= yMax1) ||
                (yMin2 <= yMin1) && (yMin1 <= yMax2) || (yMin2 <= yMax1) && (yMax1 <= yMax2);
        boolean xCheck = (xMin1 <= xMin2) && (xMin2 <= xMax1) || (xMin1 <= xMax2) && (xMax2 <= xMax1) ||
                (xMin2 <= xMin1) && (xMin1 <= xMax2) || (xMin2 <= xMax1) && (xMax1 <= xMax2);

        return yCheck && xCheck;
    }

}
