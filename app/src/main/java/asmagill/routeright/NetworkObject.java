package asmagill.routeright;

/**
 * Created by william on 3/13/16.
 */
public class NetworkObject {
    private final String title;

    private final String info;

    public NetworkObject(final String title, final String info){
        this.title = title;
        this.info = info;
    }

    public String getTitle(){
        return title;
    }

    public String getInfo(){
        return info;
    }
}
