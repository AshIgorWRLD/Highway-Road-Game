import lombok.extern.slf4j.Slf4j;
import server.Server;

@Slf4j
public class Main {

    private static final int AVAILABLE_ARGUMENTS_NUMBER = 2;
    private static final int MINIMAL_AVAILABLE_PORT_NUMBER = 1024;
    private static final int MAXIMAL_AVAILABLE_PORT_NUMBER = 49151;
    private static final String NUMBERS_IN_STRING = ".*\\d+.*";

    public static void main(String[] args) {

        if (args.length != AVAILABLE_ARGUMENTS_NUMBER) {
            log.error("WRONG ARGUMENTS NUMBER");
            return;
        }

        if (NUMBERS_IN_STRING.equals(args[0])) {
            log.info("SECOND ARGUMENT SHOULD BE PORT NUMBER " + args[1]);
            return;
        }

        int port = Integer.parseInt(args[1]);
        if (MINIMAL_AVAILABLE_PORT_NUMBER > port || MAXIMAL_AVAILABLE_PORT_NUMBER < port) {
            log.info("PLEASE USE PORTS IN RANGE " + MINIMAL_AVAILABLE_PORT_NUMBER + "-"
                    + MAXIMAL_AVAILABLE_PORT_NUMBER);
            return;
        }

        Server server = new Server(args[0], port);
        server.startServer();
    }
}
