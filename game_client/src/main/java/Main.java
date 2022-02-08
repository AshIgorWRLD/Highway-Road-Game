import lombok.extern.slf4j.Slf4j;
import networkmodule.ClientModule;
import view.MainFrame;
import road.RoadLogic;

@Slf4j
public class Main {

    private static final int RIGHT_DESKTOP_ARGS_NUMBER = 3;
    private static final int RIGHT_ONLINE_DRIVER_ARGS_NUMBER = 6;
    private static final int RIGHT_ONLINE_SPAMMER_ARGS_NUMBER = 4;
    private static final int MINIMAL_AVAILABLE_PORT_NUMBER = 1024;
    private static final int MAXIMAL_AVAILABLE_PORT_NUMBER = 49151;
    private static final String NUMBERS_IN_STRING = ".*\\d+.*";
    private static final int GAME_MODE_POSITION = 0;
    private static final int DESKTOP_PLAYER_NAME_POSITION = 1;
    private static final int DESKTOP_CAR_NAME_POSITION = 2;
    private static final int ONLINE_PLAYER_TYPE_POSITION = 1;
    private static final int PORT_POSITION = 2;
    private static final int IP_POSITION = 3;
    private static final int ONLINE_PLAYER_NAME_POSITION = 4;
    private static final int ONLINE_CAR_NAME_POSITION = 5;

    public static void main(String[] args) {

        if(RIGHT_DESKTOP_ARGS_NUMBER != args.length && RIGHT_ONLINE_DRIVER_ARGS_NUMBER != args.length
        && RIGHT_ONLINE_SPAMMER_ARGS_NUMBER != args.length){
            log.error("WRONG ARGUMENTS NUMBER");
        }

        if("DESKTOP".equals(args[GAME_MODE_POSITION])){
            if(RIGHT_DESKTOP_ARGS_NUMBER != args.length){
                log.error("NO ARGS MATCH");
                return;
            }
            RoadLogic roadLogic = new RoadLogic(false, args[DESKTOP_CAR_NAME_POSITION]);
            roadLogic.addPlayer();
            MainFrame mainFrame = new MainFrame(true, roadLogic,
                    args[DESKTOP_PLAYER_NAME_POSITION]);
        }else if("ONLINE".equals(args[GAME_MODE_POSITION])){

            if("DRIVER".equals(args[ONLINE_PLAYER_TYPE_POSITION])){
                if(args.length != RIGHT_ONLINE_DRIVER_ARGS_NUMBER){
                    log.error("NO ARGS MATCH");
                    return;
                }

                if (NUMBERS_IN_STRING.equals(args[PORT_POSITION])) {
                    log.error("THIRD ARGUMENT SHOULD BE PORT NUMBER " + args[PORT_POSITION]);
                    return;
                }

                int port = Integer.parseInt(args[PORT_POSITION]);
                if (MINIMAL_AVAILABLE_PORT_NUMBER > port || MAXIMAL_AVAILABLE_PORT_NUMBER < port) {
                    log.error("PLEASE USE PORTS IN RANGE " + MINIMAL_AVAILABLE_PORT_NUMBER + "-"
                            + MAXIMAL_AVAILABLE_PORT_NUMBER);
                    return;
                }

                ClientModule clientModule = new ClientModule(args[ONLINE_CAR_NAME_POSITION]);
                clientModule.connectDriver(args[IP_POSITION], port, args[ONLINE_PLAYER_NAME_POSITION]);

            }else if("SPAMMER".equals(args[ONLINE_PLAYER_TYPE_POSITION])){
                if(args.length != RIGHT_ONLINE_SPAMMER_ARGS_NUMBER){
                    log.error("NO ARGS MATCH");
                    return;
                }

                if (NUMBERS_IN_STRING.equals(args[PORT_POSITION])) {
                    log.error("THIRD ARGUMENT SHOULD BE PORT NUMBER " + args[PORT_POSITION]);
                    return;
                }

                int port = Integer.parseInt(args[PORT_POSITION]);
                if (MINIMAL_AVAILABLE_PORT_NUMBER > port || MAXIMAL_AVAILABLE_PORT_NUMBER < port) {
                    log.error("PLEASE USE PORTS IN RANGE " + MINIMAL_AVAILABLE_PORT_NUMBER + "-"
                            + MAXIMAL_AVAILABLE_PORT_NUMBER);
                    return;
                }

                ClientModule clientModule = new ClientModule("SUBARU");
                clientModule.connectSpammer(args[IP_POSITION], port, "");
            }else{
                log.error("WRONG ARGUMENTS");
            }

        }else{
            log.error("YOU NEED TO CHOOSE 'desktop' OR 'online' APP TYPE");
        }
    }
}
