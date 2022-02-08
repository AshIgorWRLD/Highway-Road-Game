package cars;

public class CarName {

    public enum Names {
        FORD_FOCUS,
        LAND_CRUISER,
        BLACK_LAND_CRUISER,
        SUBARU,
        KIA,
        TRUCK,
        BUS,
        BENZ_TRUCK,
        VOLKSWAGEN_POLO,
        ROOT_TAXI
    }

    public Names GetName(String str) {
        switch (str) {
            case "FORD_FOCUS" -> {
                return Names.FORD_FOCUS;
            }
            case "LAND_CRUISER" -> {
                return Names.LAND_CRUISER;
            }
            case "BLACK_LAND_CRUISER" -> {
                return Names.BLACK_LAND_CRUISER;
            }
            case "SUBARU" -> {
                return Names.SUBARU;
            }
            case "KIA" -> {
                return Names.KIA;
            }
            case "TRUCK" -> {
                return Names.TRUCK;
            }
            case "BENZ_TRUCK" -> {
                return Names.BENZ_TRUCK;
            }
            case "ROOT_TAXI" -> {
                return Names.ROOT_TAXI;
            }
            case "VOLKSWAGEN_POLO" -> {
                return Names.VOLKSWAGEN_POLO;
            }
            case "BUS" -> {
                return Names.BUS;
            }
        }
        return null;
    }
}
